package com.example.springcourse.dto.book;

import com.example.springcourse.dto.genre.GenreResponse;
import com.example.springcourse.entity.genre.Genre;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private UUID bookId;

    private String title;

    private String author;

    private String year;

    private String description;

    private List<Genre> genre = new ArrayList<>();

    private String image;

    private BigDecimal rating;


}
