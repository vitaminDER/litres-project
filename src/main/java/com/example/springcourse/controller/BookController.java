package com.example.springcourse.controller;

import com.example.springcourse.entity.Book;
import com.example.springcourse.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;


    @GetMapping("/{id}")
    public List<Book> findBookById(@PathVariable Integer id) {
        List<Book> books = bookRepository.findBookById(id);
        return books;
    }

    @GetMapping("/person/{person_id}")
    public List<Book> findBookByPersonId(@PathVariable Integer person_id) {
        return bookRepository.findBookByPersonId(person_id);
    }

}
