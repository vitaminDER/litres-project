package com.example.springcourse.controller;

import com.example.springcourse.dto.person.PersonAuthenticationDto;
import com.example.springcourse.dto.person.PersonRegistrationDto;
import com.example.springcourse.service.PersonAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final PersonAuthenticationService personAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody PersonRegistrationDto personRegistrationDto) {
        personAuthenticationService.userSignup(personRegistrationDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userSignin(@Valid @RequestBody PersonAuthenticationDto personAuthenticationDto) {
        return ResponseEntity.ok(personAuthenticationService.userSignin(personAuthenticationDto));
    }
}