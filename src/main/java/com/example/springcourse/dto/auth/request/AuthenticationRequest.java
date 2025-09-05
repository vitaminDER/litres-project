package com.example.springcourse.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "login can't be null")
    private String login;

    @NotNull(message = "password can't be null")
    @Size(min = 8, max = 8, message = "password should be 8 characters long minimum")
    private String password;
}
