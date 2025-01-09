package com.example.springcourse.controller;

import com.example.springcourse.dao.EntityDao;
import com.example.springcourse.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private EntityDao entityDao;


    @GetMapping("/books")
    public List<Book> findAllBooks() {
        List<Book> allBooks = (List<Book>) entityDao.findAll();
        return allBooks;
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        Book book = entityDao.findById(id).get();
        return book;

        }

}
