package com.example.springcourse.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    @NotNull
    @NotBlank
    private String firstName;

    @NotBlank
    @NotNull
     private String lastName;

    @Min(value = 1)
     private int age;

    @Email(message = "Invalid email format")
    @NotBlank
    @NotNull
     private String email;

    @NotBlank
    @NotNull
    private String userName;
}
