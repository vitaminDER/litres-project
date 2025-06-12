package com.example.springcourse.dto.review;

import com.example.springcourse.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBook {

    private int personId;

    private String username;

    private String comment;

    private int evaluation;

}
