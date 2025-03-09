package com.example.springcourse.service;

import com.example.springcourse.dto.book.BookDTO;
import com.example.springcourse.entity.Book;
import com.example.springcourse.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

   private final BookRepository bookRepository;
   private final ModelMapper modelMapper;


   public BookDTO saveBook(BookDTO bookDTO) {

      if(bookDTO == null) {
         log.info("Attempt to save null book");
         throw new IllegalArgumentException("Book can't be null");
      }

      Book book = modelMapper.map(bookDTO, Book.class);
      Book savedBook = bookRepository.save(book);
      log.info("Book saved successfully with ID: {}", savedBook.getId());

      return convertToBookDTO(savedBook);
   }

   public BookDTO updateBook(Integer id, BookDTO bookDTO) {
      Book book = bookRepository.findBookById(id);
      if(book == null) {
         throw new IllegalArgumentException("Book can't be null");
      }
      modelMapper.map(bookDTO, book);

      Book updatedBook = bookRepository.save(book);

      return convertToBookDTO(updatedBook);
   }

   public BookDTO getBookInfoById(Integer id) {
      Book book = bookRepository.findBookById(id);
      return modelMapper.map(book, BookDTO.class);
   }


   public void deleteBook (Integer id) {
      this.bookRepository.deleteById(id);
   }



    Book convertToBook(BookDTO bookDTO) {
      return modelMapper.map(bookDTO, Book.class);
   }

   BookDTO convertToBookDTO(Book book) {
      return modelMapper.map(book, BookDTO.class);
   }

   }
   



