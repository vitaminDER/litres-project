package com.example.springcourse.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDtoRead {

    private int id;
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Min(value = 1)
    private int age;

    @Email(message = "Invalid email format")
    private String email;

    private String userName;
}
