package com.example.springcourse.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPersonDto {
    private int personId;

    private String username;

    private String title;

    private String comment;

    private int evaluation;
}
