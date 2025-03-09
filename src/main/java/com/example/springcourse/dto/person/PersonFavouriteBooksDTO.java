package com.example.springcourse.dto.person;

import com.example.springcourse.dto.book.BookFavouriteDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonFavouriteBooksDTO {
    private List<BookFavouriteDTO> favouriteBooks;
}
