package com.example.springcourse.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllBookResponse {

    private UUID id;
    private String title;
    private String author;
    private String image;
}
