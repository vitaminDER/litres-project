package com.example.springcourse.exception;

public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
