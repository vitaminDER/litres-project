package com.example.springcourse.exception;

import java.util.List;

public class BookValidationException extends RuntimeException{
    List<String> errors;

    public BookValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
