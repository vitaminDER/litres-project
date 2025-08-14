package com.example.springcourse.controller;

import com.example.springcourse.dto.auth.request.AuthenticationDto;
import com.example.springcourse.dto.auth.request.RegistrationDto;
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
    public ResponseEntity<?> userSignup(@Valid @RequestBody RegistrationDto registrationDto) {
        return authenticationService.userSignup(registrationDto);
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> userSignin(@Valid @RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.userSignin(authenticationDto);
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