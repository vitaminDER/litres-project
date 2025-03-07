package com.example.springcourse.dto.person;

import com.example.springcourse.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonReviewDTO {

    String userName;

    List<Review> review;

}
