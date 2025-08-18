package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookResponse;
import com.example.springcourse.dto.book.BookRequest;
import com.example.springcourse.dto.book.AllBookResponse;
import com.example.springcourse.dto.review.response.ReviewBookResponse;
import com.example.springcourse.entity.Book;
import com.example.springcourse.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springcourse.dto.page.PageDto;

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
    public ResponseEntity<PageDto<ReviewBookResponse>>findReviewByBook(@RequestParam("bookId") UUID bookId,
                                                                       @RequestParam() int pageNumber,
                                                                       @RequestParam() int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReviewByBook(bookId,pageNumber,pageSize));
    }

//    @CrossOrigin
//    @GetMapping("/reviewOnBook")
//    public ResponseEntity<List<ReviewBook>> findReviewOnBookByTitleAndEvaluation(@RequestParam("title") String title,
//                                                                                 @RequestParam("evaluation") Integer evaluation) {
//        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReviewWithEvaluation(title, evaluation));
//    }

    @CrossOrigin
    @GetMapping("/genre")
    public ResponseEntity<List<BookRequest>> findBookByGenre(@RequestParam("genre") String genre) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBookByGenre(genre));
    }

    @GetMapping("/title")
    public ResponseEntity<BookRequest> findBookByTitle(@RequestParam("title") String title) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBookByTitle(title));
    }


    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable UUID id,
                                                   @RequestBody BookResponse bookResponse) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(id, bookResponse));
    }



    @CrossOrigin
    @GetMapping()
    public List<AllBookResponse> getAllBook(Book books) {
        return bookService.showAllBooks(books);
    }

}
