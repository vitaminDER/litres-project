package com.example.springcourse.dto.review;

import com.example.springcourse.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPersonResponse {

    private UUID bookId;

    private UUID personId;

    private UUID reviewId;

    private String comment;

    private LocalDate date;


}
