package com.example.springcourse.dto.book;

import com.example.springcourse.dto.person.PersonReviewDTO;
import com.example.springcourse.entity.Author;
import com.example.springcourse.entity.Genre;
import com.example.springcourse.entity.Review;
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

    private String description;

    private List<Genre> genres;

    private List<PersonReviewDTO> reviews;

    private String abracadabra;

}
