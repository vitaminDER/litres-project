package com.example.springcourse.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPersonDto {
    private UUID personId;

    private String username;

    private String title;

    private String comment;

    private int evaluation;
}
