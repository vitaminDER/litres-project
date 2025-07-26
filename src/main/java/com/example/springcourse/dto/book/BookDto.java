package com.example.springcourse.dto.book;

import com.example.springcourse.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private int id;

    private String title;

    private String author;

    private int year;

    private double rating;

    private String description;

    private List<Genre> genre;

    private String image;


}
