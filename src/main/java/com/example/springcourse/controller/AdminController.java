package com.example.springcourse.controller;

import com.example.springcourse.service.BookService;
import com.example.springcourse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminController {

    private final ReviewService reviewService;
    private final BookService bookService;


    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Integer id) {
        this.reviewService.deleteReviewById(id);
    }
    @CrossOrigin
    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Integer id) {
        this.bookService.deleteBook(id);
    }
}
