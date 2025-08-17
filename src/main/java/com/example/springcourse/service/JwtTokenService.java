package com.example.springcourse.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.StringReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtTokenService {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;


    public JwtTokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs) {

        // 1. Проверка секрета
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }

        // 2. Валидация длины секрета (минимум 32 байта для HS256)
        if (secret.trim().length() < 32) { // Примерная проверка для Base64URL
            throw new IllegalArgumentException("JWT secret is too short. Must be at least 32 characters");
        }

        try {
            // 3. Декодирование с проверкой минимальной длины ключа
            byte[] keyBytes = Decoders.BASE64URL.decode(secret.trim());

            if (keyBytes.length < 32) { // 256 бит для HS256
                throw new IllegalArgumentException("Key length must be at least 256 bits (32 bytes)");
            }

            this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        } catch (DecodingException e) {
            throw new IllegalArgumentException("Invalid Base64URL-encoded JWT secret", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to initialize JWT secret key", e);
        }

        // 4. Валидация времени жизни токена
        if (expirationMs < 60_000) {
            log.warn("JWT expiration time is too short: {} ms", expirationMs);
        }
        this.jwtExpirationMs = expirationMs;

        // 5. Безопасное логирование (не весь ключ)
        log.info("JWT Service initialized. Key length: {} bits, Expiration: {} ms",
                secretKey.getEncoded().length * 8,
                expirationMs);
    }

    public String generateToken(UserDetails userDetails, UUID personId) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", getRoles(userDetails))
                .claim("personId", personId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256) // Явное указание алгоритма
                .compact();
    }

    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true; // Токен валиден, перевыпуск не нужен
        } catch (ExpiredJwtException ex) {
            log.warn("Token expired: {}", ex.getMessage());
            // Токен просрочен
            throw new JwtException("Token expired", ex);
        } catch (JwtException | IllegalArgumentException e) {
            // Другие ошибки токена
            throw new JwtException("Invalid token", e);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token).getBody();
        return claimsResolver.apply(claims);
    }

    // Извлечение конкретного personId
    public UUID extractPersonId(String token) {
        // Сначала извлекаем как строку
        String idString = extractClaim(token, claims -> claims.get("personId", String.class));
        // Затем преобразуем в UUID
        return UUID.fromString(idString);
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public Claims getAllClaimsFromToken(String token) {
        return parseToken(token).getBody();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public String extractRoleFromToken(String token) {
        try {
            // Извлекаем список ролей из токена
            List<String> roles = extractClaim(token, claims -> {
                Object rolesClaim = claims.get("roles");

                // Если claim отсутствует
                if (rolesClaim == null) {
                    return Collections.emptyList();
                }

                // Если это строка (одиночная роль)
                if (rolesClaim instanceof String) {
                    return Collections.singletonList((String) rolesClaim);
                }

                // Если это список
                if (rolesClaim instanceof List) {
                    return (List<String>) rolesClaim;
                }

                // Неподдерживаемый формат
                return Collections.emptyList();
            });

            // Проверяем наличие ролей
            if (roles.isEmpty()) {
                log.warn("No roles found in token");
                return "ROLE_GUEST"; // Возвращаем роль по умолчанию
            }

            return roles.get(0); // Возвращаем первую роль
        } catch (Exception e) {
            log.error("Error extracting role from token: {}", e.getMessage());
            throw new JwtException("Error extracting role from token", e);
        }
    }
}
