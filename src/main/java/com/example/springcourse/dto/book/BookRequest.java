package com.example.springcourse.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "title can't be empty")
    private String title;

    @NotBlank(message = "author can't be empty")
    private String author;

    @NotBlank(message = "year can't be empty")
    private String year;

    @NotBlank(message = "description can't be empty")
    private String description;

//    @NotBlank(message = "genreId can't be empty")
    private List<UUID> genreId;

    private String image;

}
