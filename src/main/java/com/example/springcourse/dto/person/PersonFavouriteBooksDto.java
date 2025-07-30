package com.example.springcourse.dto.person;

import com.example.springcourse.dto.book.BookFavouriteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonFavouriteBooksDto {

    private List<BookFavouriteDto> favouriteBooks;
}
