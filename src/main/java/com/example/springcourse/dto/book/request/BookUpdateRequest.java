package com.example.springcourse.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {

    private UUID bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookYear;

    private String bookDescription;

    private List<UUID> genresId;

    private String bookImage;

    private String isbn;
}
