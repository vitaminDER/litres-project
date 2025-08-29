package com.example.springcourse.dto.book;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "title can't be empty")
    private String title;

    @NotBlank(message = "author can't be empty")
    private String author;

    @NotBlank(message = "year can't be empty")
    private String year;

    @NotBlank(message = "description can't be empty")
    private String description;

    private List<UUID> genreId;

    private String image;

}
