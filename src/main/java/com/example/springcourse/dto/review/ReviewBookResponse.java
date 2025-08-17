package com.example.springcourse.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBookResponse {


    private String username;

    private String comment;

    private LocalDate createdDate;

}
