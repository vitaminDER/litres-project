package com.example.springcourse.exception;

public class ReviewAccessDeniedException extends RuntimeException {
    public ReviewAccessDeniedException(String message) {
        super(message);
    }
}