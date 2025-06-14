package com.example.springcourse.service;

import com.example.springcourse.dto.book.BookCreateDto;
import com.example.springcourse.dto.book.BookDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    public BookCreateDto saveBook(BookDto bookDto) {

        if (bookDto == null) {
            log.info("Attempt to save null book");
            throw new IllegalArgumentException("Book can't be null");
        }

        Book book = modelMapper.map(bookDto, Book.class);
        Book savedBook = bookRepository.save(book);
        log.info("Book saved successfully with ID: {}", savedBook.getId());

        return modelMapper.map(savedBook, BookCreateDto.class);
    }

    public BookCreateDto updateBook(Integer id, BookCreateDto bookDto) {
        Book book = bookRepository.findBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book can't be null");
        }
        modelMapper.map(bookDto, book);

        Book updatedBook = bookRepository.save(book);

        return modelMapper.map(updatedBook, BookCreateDto.class);
    }

    @Transactional
    public BookDto findBook(Integer id) {
        Book book = bookRepository.findBookById(id);
        return toDto(book);
    }

    @Transactional
    public List<ReviewBook> findReviewByBook(Integer bookId) {
        List<Review> reviews = bookRepository.findReviewBookById(bookId);
       return reviews.stream()
               .map(review -> {
                   ReviewBook dto = new ReviewBook();
                   dto.setPersonId(review.getPerson().getId());
                   dto.setUsername(review.getPerson().getUserName());
                   dto.setComment(review.getComment());
                   dto.setEvaluation(review.getEvaluation());
                   return dto;
               })
               .collect(Collectors.toList());
    }
    public List<BookDto> showAllBooks(Book book) {
        List<Book> books = bookRepository.findAll(book);
        return books.stream()
                .map(book1 -> {;
                    return modelMapper.map(book1, BookDto.class);
                })
                .collect(Collectors.toList());
    }


    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Запись с ID " + id + " не найдена");
        }
        this.bookRepository.deleteById(id);
    }

//    public double averageRatingBook();

    Book toEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    BookDto toDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

}
   



