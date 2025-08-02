package com.example.springcourse.dto.person;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRegistrationDto {

    @NotEmpty(message = "login can't be empty")
    @NotNull(message = "login can't be null")
    @Min(4)
    @Max(10)
    @Pattern(regexp = "[a-zA-Z]")
    private String login;

    @NotEmpty(message = "password can't be empty")
    @NotNull(message = "password can't be null")
    @Size(min = 8, message = "password should be 8 characters long minimum")
    @Max(8)
    private String password;
}
