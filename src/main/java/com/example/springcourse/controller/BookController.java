package com.example.springcourse.controller;

import com.example.springcourse.dto.BookDTO;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    private final PersonRepository personRepository;

    @GetMapping("/{id}")
    public List<Book> findBookById(@PathVariable Integer id) {
        List<Book> books = bookRepository.findBookById(id);
        return books;
    }

    @GetMapping("/person/{person_id}")
    public ResponseEntity<List<Book>> findBookByPersonId(@PathVariable Integer person_id) {
        List<Book> books = bookRepository.findBookByPersonId(person_id);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/year/{year}")
    public List<Book> findBookByYear(@PathVariable Integer year) {
        List<Book> books = bookRepository.findBookByYear(year);

        return books;
    }

    @PostMapping("/newBook")
    public Book addNewBookByPersonId(@RequestBody BookDTO bookDTO) {

        Person person = personRepository.findPersonById(bookDTO.getOwnerId());

        Book theBook = new Book();
        theBook.setTitle(bookDTO.getTitle());
        theBook.setAuthor(bookDTO.getAuthor());
        theBook.setYear(bookDTO.getYear());
        theBook.setOwner(person);

        return bookRepository.save(theBook);


    }
}
