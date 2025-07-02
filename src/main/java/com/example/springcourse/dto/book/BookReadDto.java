package com.example.springcourse.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReadDto {

    private int id;
    private String title;
    private String author;
}
