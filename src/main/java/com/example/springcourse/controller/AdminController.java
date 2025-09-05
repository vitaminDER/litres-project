package com.example.springcourse.controller;

import com.example.springcourse.dto.book.response.AllBookResponse;
import com.example.springcourse.dto.book.request.BookCreateRequest;
import com.example.springcourse.dto.book.request.BookSearchRequest;
import com.example.springcourse.dto.book.request.BookUpdateRequest;
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
    public ResponseEntity<?> createBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        bookService.saveBook(bookCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/book")
    public PageResponse<AllBookResponse> getAllBook(@RequestParam("searchValue") String searchValue,
                                                    @RequestParam("typeSearch") TypeSearch typeSearch,
                                                    @RequestParam() int pageNumber,
                                                    @RequestParam() int pageSize) {
        BookSearchRequest request = new BookSearchRequest(searchValue, typeSearch);
        return bookService.getBooksForAdmin(request, pageNumber, pageSize);
    }

    @GetMapping("/book/info/{bookId}")
    public ResponseEntity<?> findBookInfo(@PathVariable UUID bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findBookInfoForAdmin(bookId));
    }

    @PutMapping("/book")
    public void updateBook(@RequestBody BookUpdateRequest updateRequest) {
       bookService.updateBook(updateRequest);
    }


}
