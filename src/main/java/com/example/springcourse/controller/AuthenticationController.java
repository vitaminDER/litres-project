package com.example.springcourse.controller;

import com.example.springcourse.dto.auth.request.AuthenticationRequest;
import com.example.springcourse.dto.auth.request.RegistrationRequest;
import com.example.springcourse.dto.auth.response.AuthenticationResponse;
import com.example.springcourse.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return authenticationService.userSignup(registrationRequest);
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> userSignin(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.userSignin(authenticationRequest);
    }

    @CrossOrigin
    @GetMapping("/me")
    public ResponseEntity<?> checkValidateToken(HttpServletRequest request) {
        Object response = authenticationService.validateTokenAndGetResponse(request);
        if (response instanceof Map) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }


//    @PostMapping("/refresh")
//    public JwtResponse refresh(@RequestBody RefreshRequest request) {
//        return authenticationService.refreshTokens(request.getRefreshToken());
//    }

//    @PostMapping("/refresh")
//    public ResponseEntity<AuthenticationResponse> refreshToken(
//            @RequestHeader("Authorization") String refreshToken) {
//        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
//    }


}