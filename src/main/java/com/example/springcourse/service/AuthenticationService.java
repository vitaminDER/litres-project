package com.example.springcourse.service;

import com.example.springcourse.config.JwtConfig;
import com.example.springcourse.dto.auth.request.AuthenticationRequest;
import com.example.springcourse.dto.auth.request.RegistrationRequest;
import com.example.springcourse.dto.auth.response.AuthenticationResponse;
import com.example.springcourse.dto.auth.response.RegistrationResponse;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.role.Role;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.RoleRepository;
import io.jsonwebtoken.JwtException;
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

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

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
    private final JwtConfig jwtConfig;

    public ResponseEntity<RegistrationResponse> userSignup(RegistrationRequest registrationRequest) {

        if (personRepository.existsByLogin(registrationRequest.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with login already exists");
        }

        var person = new Person();
        person.setLogin(registrationRequest.getLogin());
        person.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        person.setEmail(registrationRequest.getEmail());
        person.setFirstName("");
        person.setLastName("");
        person.setBirthDate("");
        person.setEmail(registrationRequest.getEmail());
        person.setUserName("");

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found in database"));
        person.getRoles().add(userRole);

        person = personRepository.save(person);

        modelMapper.map(person, RegistrationRequest.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationResponse());
    }

    @Transactional
    public ResponseEntity<AuthenticationResponse> userSignin(AuthenticationRequest authenticationRequest) {

        try {
            UserDetails userDetails = loadUserByUsername(authenticationRequest.getLogin());
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
            Person person = ((PersonUserDetails) userDetails).getPerson();
            UUID personId = ((PersonUserDetails) userDetails).getPerson().getId();


            String token = jwtTokenService.generateToken(userDetails, personId);

            var response = new AuthenticationResponse();
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
    public AuthenticationResponse getUserInfo(UUID personId) {
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

            UUID personId = jwtTokenService.extractPersonId(token);
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



