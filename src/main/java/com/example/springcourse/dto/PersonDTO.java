package com.example.springcourse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {

    @NotBlank(message = "First name is required")
     String firstName;

    @NotBlank(message = "First name is required")
     String lastName;

    @Min(value = 0)
     int age;

    @Email(message = "Invalid email format")
     String email;
}
