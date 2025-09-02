package com.example.springcourse.controller;

import com.example.springcourse.dto.book.response.BookResponse;
import com.example.springcourse.dto.book.response.AllBookResponse;
import com.example.springcourse.dto.review.response.ReviewBookResponse;
import com.example.springcourse.entity.Book;
import com.example.springcourse.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springcourse.dto.page.PageResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @CrossOrigin
    @GetMapping("/info")
    public ResponseEntity<BookResponse> findBookInfo(@RequestParam("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBook(id));
    }

    @CrossOrigin
    @GetMapping("/review")
    public ResponseEntity<PageResponse<ReviewBookResponse>>findReviewByBook(@RequestParam("bookId") UUID bookId,
                                                                            @RequestParam() int pageNumber,
                                                                            @RequestParam() int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReviewByBook(bookId,pageNumber,pageSize));
    }


    @CrossOrigin
    @GetMapping()
    public List<AllBookResponse> getAllBook(Book books) {
        return bookService.showAllBooks(books);
    }

}
