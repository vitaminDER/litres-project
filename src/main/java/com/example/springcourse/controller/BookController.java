package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookCreateDto;
import com.example.springcourse.dto.book.BookDto;
import com.example.springcourse.dto.book.BookReadDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.entity.Book;
import com.example.springcourse.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @CrossOrigin
    @GetMapping("/info")
    public ResponseEntity<BookDto> findBookInfo(@RequestParam("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBook(id));
    }

    @CrossOrigin
    @GetMapping("/review")
    public ResponseEntity<List<ReviewBook>> findReviewByBook(@RequestParam("bookId") Integer bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReviewByBook(bookId));
    }

    @CrossOrigin
    @GetMapping("/reviewOnBook")
    public ResponseEntity<List<ReviewBook>> findReviewOnBookByTitleAndEvaluation(@RequestParam("title") String title,
                                                                                 @RequestParam("evaluation") Integer evaluation) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReviewWithEvaluation(title, evaluation));
    }

    @CrossOrigin
    @GetMapping("/genre")
    public ResponseEntity<List<BookDto>> findBookByGenre(@RequestParam("genre") String genre) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBookByGenre(genre));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<BookCreateDto> updateBook(@PathVariable Integer id,
                                                    @RequestBody BookCreateDto bookCreateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(id, bookCreateDto));
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        this.bookService.deleteBook(id);
    }


    @CrossOrigin
    @PostMapping()
    public ResponseEntity<BookCreateDto> createBook(@RequestBody @Valid BookDto bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookDTO));
    }

    @CrossOrigin
    @GetMapping()
    public List<BookReadDto> getAllBook(Book books) {
        return bookService.showAllBooks(books);
    }

}
