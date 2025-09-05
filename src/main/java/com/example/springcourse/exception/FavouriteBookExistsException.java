package com.example.springcourse.exception;

public class FavouriteBookExistsException extends RuntimeException{

    public FavouriteBookExistsException(String message) {
        super(message);
    }
}
