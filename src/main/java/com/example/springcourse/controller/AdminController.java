package com.example.springcourse.controller;

import com.example.springcourse.dto.book.AllBookResponse;
import com.example.springcourse.dto.book.BookRequest;
import com.example.springcourse.dto.book.BookSearchRequest;
import com.example.springcourse.dto.page.PageResponse;
import com.example.springcourse.entity.TypeSearch;
import com.example.springcourse.service.BookService;
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

    @GetMapping("/books")
    public PageResponse<AllBookResponse> getAllBook(@RequestParam("searchValue") String searchValue,
                                                    @RequestParam("typeSearch") TypeSearch typeSearch,
                                                    @RequestParam() int pageNumber,
                                                    @RequestParam() int pageSize) {
        BookSearchRequest request = new BookSearchRequest(searchValue, typeSearch);
        return bookService.getBooksForAdmin(request, pageNumber, pageSize);
    }


}
