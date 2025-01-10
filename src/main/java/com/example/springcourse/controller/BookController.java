package com.example.springcourse.controller;

import com.example.springcourse.entity.Book;
import com.example.springcourse.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    @Autowired
    private EntityService entityService;



    @GetMapping("/b")
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(entityService.findAllBook());
    }



}
