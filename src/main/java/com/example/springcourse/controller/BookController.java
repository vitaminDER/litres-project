package com.example.springcourse.controller;

import com.example.springcourse.dto.BookDTO;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Integer id) {
        Book findBook = bookRepository.findBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(findBook);
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
