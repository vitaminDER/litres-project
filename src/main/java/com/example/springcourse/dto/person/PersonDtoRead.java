package com.example.springcourse.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRead {

    private UUID id;
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String birthDate;

    @Email(message = "Invalid email format")
    private String email;

    private String userName;
}
