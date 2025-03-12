package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookDTO;
import com.example.springcourse.entity.Book;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/info/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable Integer id) {
        BookDTO bookDTO = bookService.getBookInfoById(id);
        if(bookDTO == null) {
            throw new RuntimeException("BookDTO can't be null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, BookDTO bookDTO) {
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook (@PathVariable Integer id) {
        this.bookService.deleteBook(id);
    }
    
//    @GetMapping("/person/{person_id}")
//    public ResponseEntity<List<Book>> findBookByPersonId(@PathVariable Integer person_id) {
//        List<Book> books = bookRepository.findBookByPersonId(person_id);
//        if (books.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(books);
//    }

//    @GetMapping("/year/{year}")
//    public List<Book> findBookByYear(@PathVariable Integer year) {
//        List<Book> books = bookRepository.findBookByYear(year);
//
//        return books;
//    }

    @PostMapping()
    public ResponseEntity<BookDTO> addNewBook(@RequestBody @Valid BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.saveBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDTO);
    }
}
