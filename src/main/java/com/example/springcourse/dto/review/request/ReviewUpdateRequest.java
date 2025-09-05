package com.example.springcourse.dto.review.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateRequest {

    private UUID bookId;

    private UUID personId;

    @Size(min = 10, max = 3000)
    private String comment;

    private UUID reviewId;
}
