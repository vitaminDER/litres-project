package com.example.springcourse.dto;

import com.example.springcourse.entity.Author;
import com.example.springcourse.entity.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @Min(value = 0)
    private int year;

    private int personId;

    private String description;

    private String genre;

    List<Author> authorBook;

}
