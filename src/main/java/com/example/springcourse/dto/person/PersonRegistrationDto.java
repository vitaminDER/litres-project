package com.example.springcourse.dto.person;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRegistrationDto {

    @NotEmpty(message = "login can't be empty")
    @NotNull(message = "login can't be null")
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-zA-Z_]+")
    private String login;

    @NotEmpty(message = "password can't be empty")
    @NotNull(message = "password can't be null")
    @Size(min = 8, max = 8, message = "password should be 8 characters long minimum")
    private String password;
}
