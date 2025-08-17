package com.example.springcourse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException e) {
        return new ErrorResponse("PERSON NOT FOUND: " + e.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(new ErrorResponse("BOOK NOT FOUND: " + e.getMessage()));
    }

    @ExceptionHandler(BookValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationException(BookValidationException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message: ",  e.getMessage());
        body.put("errors: " , e.getErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
