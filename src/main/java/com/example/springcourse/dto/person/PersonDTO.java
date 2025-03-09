package com.example.springcourse.dto.person;

import com.example.springcourse.dto.book.BookFavouriteDTO;
import com.example.springcourse.entity.Review;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDTO {

    @NotBlank
    private String firstName;

    @NotBlank
     private String lastName;

    @Min(value = 0)
     private int age;

    @Email(message = "Invalid email format")
     private String email;

    private String userName;
}
