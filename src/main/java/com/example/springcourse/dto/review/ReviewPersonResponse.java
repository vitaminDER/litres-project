package com.example.springcourse.dto.review;

import com.example.springcourse.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPersonResponse {

    private int bookId;

    private int personId;

    private ReviewResponse review;


}
