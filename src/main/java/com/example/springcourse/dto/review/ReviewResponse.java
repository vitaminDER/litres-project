package com.example.springcourse.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private int id;

    private String title;

    private String comment;

    private int evaluation;

    private LocalDate createdDate;

}
