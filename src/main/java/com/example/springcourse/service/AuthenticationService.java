package com.example.springcourse.service;

import com.example.springcourse.config.JwtConfig;
import com.example.springcourse.dto.auth.request.AuthenticationDto;
import com.example.springcourse.dto.auth.request.RegistrationDto;
import com.example.springcourse.dto.auth.response.AuthenticationResponse;
import com.example.springcourse.dto.auth.response.JwtResponse;
import com.example.springcourse.dto.auth.response.RegistrationResponse;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.role.Role;
import com.example.springcourse.entity.token.RefreshToken;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.RefreshTokenRepository;
import com.example.springcourse.repository.RoleRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@Data
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtConfig jwtConfig;

    public ResponseEntity<RegistrationResponse> userSignup(RegistrationDto registrationDto) {
        System.out.println(registrationDto);
        if (personRepository.existsByLogin(registrationDto.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with login already exists");
        }
        var person = modelMapper.map(registrationDto, Person.class);
        person.setFirstName("");
        person.setLastName("");
        person.setAge(1);
        person.setEmail(registrationDto.getEmail());
        person.setUserName("");
        person.setLogin(registrationDto.getLogin());
        person.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        var savedPerson = personRepository.save(person);
        modelMapper.map(savedPerson, AuthenticationDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationResponse());
    }

    @Transactional
    public ResponseEntity<AuthenticationResponse> userSignin(AuthenticationDto authenticationDto) {

        try {
            UserDetails userDetails = loadUserByUsername(authenticationDto.getLogin());
            if (!passwordEncoder.matches(authenticationDto.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
            Person person = ((PersonUserDetails) userDetails).getPerson();
            Integer personId = ((PersonUserDetails) userDetails).getPerson().getId();

            String token = jwtTokenService.generateToken(userDetails, personId);

            AuthenticationResponse response = new AuthenticationResponse();
            response.setUserName(person.getUsername());
            response.setId(person.getId());
            response.setFirstName(person.getFirstName());
            response.setLastName(person.getLastName());
            response.setEmail(person.getEmail());
            response.setLogin(person.getLogin());
            response.setAuth(true);
            response.setRole(getPrimaryRole(person));
            response.setToken(token);

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

    }
    public AuthenticationResponse getUserInfo(Integer personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + personId));

        return modelMapper.map(person, AuthenticationResponse.class);
    }

    public Object validateTokenAndGetResponse(HttpServletRequest request) {
        Map<String, Boolean> errorResponse = Collections.singletonMap("isAuth", false);

        try {
            String token = jwtTokenService.resolveToken(request);

            if (token == null || !jwtTokenService.validateToken(token)) {
                return errorResponse;
            }

            Integer personId = jwtTokenService.extractPersonId(token);
            if (personId == null) {
                return errorResponse;
            }

            AuthenticationResponse userInfo = getUserInfo(personId);
            if (userInfo == null) {
                return errorResponse;
            }

            userInfo.setAuth(true);
            userInfo.setToken(token);
            userInfo.setRole(jwtTokenService.extractRoleFromToken(token));

            return userInfo;

        } catch (JwtException e) {
            return errorResponse;
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new PersonUserDetails(person);
    }

    private String getPrimaryRole(Person person) {
        return person.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("ROLE_USER");
    }


//    @Transactional
//    public JwtResponse refreshTokenPair(String oldRefreshToken) {
//        String oldTokenHash = hashToken(oldRefreshToken);
//
//        RefreshToken token = refreshTokenRepository.findByTokenHash(oldTokenHash)
//                .orElseThrow(() -> new TokenException("Invalid refresh token"));
//
//        validateToken(token, oldRefreshToken);
//
//        String newAccessToken = generateAccessToken(token.getPerson());
//        String newRefreshToken = generateRefreshToken(token.getPerson());
//        String newTokenHash = hashToken(newRefreshToken);
//
//        // Обновляем токен в БД
//        refreshTokenRepository.rotateRefreshToken(
//                oldTokenHash,
//                newTokenHash,
//                Instant.now().plus(jwtConfig.getRefreshExpiration(), ChronoUnit.MILLIS)
//        );
//
//        return new JwtResponse(newAccessToken, newRefreshToken);
//    }
//
//    private void validateRefreshToken(RefreshToken token, String rawToken) {
//        // Проверка подписи
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(rawToken);
//        } catch (JwtException e) {
//            throw new BadCredentialsException("Invalid refresh token signature");
//        }
//
//        // Проверка срока действия
//        if (token.getExpiresAt().isBefore(Instant.now())) {
//            throw new BadCredentialsException("Refresh token expired");
//        }
//
//        // Проверка отзыва
//        if (token.isRevoked()) {
//            throw new BadCredentialsException("Refresh token revoked");
//        }
//    }
//
//    private String generateAccessToken(Person person) {
//        return Jwts.builder()
//                .setSubject(person.getUsername())
//                .claim("roles", person.getRoles())
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtConfig.getAccessTokenExpiration())))
//                .signWith(secretKey)
//                .compact();
//    }
//
//    private String generateRefreshToken(Person person) {
//        return Jwts.builder()
//                .setSubject(person.getUsername())
//                .setExpiration(Date.from(Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration())))
//                .signWith(secretKey)
//                .compact();
//    }
//
//    private void updateStoredRefreshToken(RefreshToken token, String newRefreshToken) {
//        token.setTokenHash(hashRefreshToken(newRefreshToken));
//        token.setExpiresAt(Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration()));
//        token.setRevoked(false);
//        refreshTokenRepository.save(token);
//    }
//
//    private String hashRefreshToken(String token) {
//        return DigestUtils.sha256Hex(token);
//    }

}



