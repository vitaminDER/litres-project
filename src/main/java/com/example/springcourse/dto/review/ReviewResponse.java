package com.example.springcourse.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private int id;

    private String title;

    private String comment;

    private int evaluation;

}
