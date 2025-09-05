package com.example.springcourse.dto.book.response;

import com.example.springcourse.entity.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookInfoResponse {

    private UUID bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookYear;

    private String bookDescription;

    private List<Genre> genres = new ArrayList<>();

    private String bookImage;

    private BigDecimal bookRating;

    private String isbn;
}
