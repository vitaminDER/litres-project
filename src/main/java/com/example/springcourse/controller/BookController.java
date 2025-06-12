package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.dto.review.ReviewResponse;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/info/{id}")
    public ResponseEntity<BookDto> findBook(@PathVariable Integer id) {
        BookDto bookDTO = bookService.findBook(id);
        if (bookDTO == null) {
            log.info("Person can't be null");
            throw new PersonNotFoundException("Person with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/review/{bookId}")
    public ResponseEntity<List<ReviewBook>> findReviewByBook(@PathVariable Integer bookId) {
        List<ReviewBook> reviewBookDTO = bookService.findReviewByBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewBookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, BookDto bookDTO) {
        BookDto updatedBookDto = bookService.updateBook(id, bookDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        this.bookService.deleteBook(id);
    }


    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDTO) {
        BookDto savedBookDto = bookService.saveBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDto);
    }

}
