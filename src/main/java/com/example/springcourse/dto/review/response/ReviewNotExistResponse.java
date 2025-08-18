package com.example.springcourse.dto.review.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewNotExistResponse {

    private String bookId;

    private String personId;

    private String reviewId;

    private String comment;

    private String date;

}
