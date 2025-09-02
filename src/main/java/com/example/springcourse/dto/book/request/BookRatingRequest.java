package com.example.springcourse.dto.book.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRatingRequest {

    private UUID bookId;

    private UUID personId;

    @Min(1)
    @Max(5)
    private int bookRating;
}
