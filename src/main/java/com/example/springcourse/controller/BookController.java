package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookCreateDto;
import com.example.springcourse.dto.book.BookDto;
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

    @GetMapping("/info/{id}")
    public ResponseEntity<BookDto> findBook(@PathVariable Integer id) {
        BookDto bookDTO = bookService.findBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/review/{bookId}")
    public ResponseEntity<List<ReviewBook>> findReviewByBook(@PathVariable Integer bookId) {
        List<ReviewBook> reviewBookDTO = bookService.findReviewByBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewBookDTO);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewBook>> findReviewOnBookWithEvaluation(@RequestParam ("title")  String title,
                                                                           @RequestParam ("evaluation") Integer evaluation){
        List<ReviewBook> reviewBooks = bookService.findReviewWithEvaluation(title, evaluation);
        return ResponseEntity.status(HttpStatus.OK).body(reviewBooks);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<BookDto>> findBookByGenre(@RequestParam ("genre") String genre) {
        List<BookDto> bookDtoList = bookService.findBookByGenre(genre);
        return  ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookCreateDto> updateBook(@PathVariable Integer id, @RequestBody BookCreateDto bookCreateDto) {
        BookCreateDto updatedBookDto = bookService.updateBook(id, bookCreateDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDto);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        this.bookService.deleteBook(id);
    }


    @PostMapping()
    public ResponseEntity<BookCreateDto> createBook(@RequestBody @Valid BookDto bookDTO) {
        BookCreateDto savedBookDto = bookService.saveBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDto);
    }

    @CrossOrigin
    @GetMapping()
    public List<BookDto> getAllBook(Book books) {
        return bookService.showAllBooks(books);
    }

}
