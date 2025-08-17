package com.example.springcourse.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookFavouriteDto {

   private UUID id;
   private String title;
   private String author;
}
