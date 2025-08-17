package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookRequest;
import com.example.springcourse.service.BookService;
import com.example.springcourse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminController {

    private final ReviewService reviewService;
    private final BookService bookService;


    @CrossOrigin
    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable UUID id) {
        this.bookService.deleteBook(id);
    }

    @CrossOrigin
    @PostMapping("/book")
    public ResponseEntity<?> createBook(@RequestBody @Valid BookRequest bookRequest) {
      bookService.saveBook(bookRequest);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
