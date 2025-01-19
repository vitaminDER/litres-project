package com.example.springcourse.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String title;

    private String author;

    private int year;

    private Integer ownerId;
}
