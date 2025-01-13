package com.example.springcourse.service;

import com.example.springcourse.repository.BookRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

   private final BookRepository bookRepository;


   }