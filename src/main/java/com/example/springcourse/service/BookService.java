package com.example.springcourse.service;

import com.example.springcourse.dto.book.BookCreateDto;
import com.example.springcourse.dto.book.BookDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Review;
import com.example.springcourse.exception.BookNotFoundException;
import com.example.springcourse.exception.BookValidationException;
import com.example.springcourse.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
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
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        modelMapper.map(bookDto, book);

        Book updatedBook = bookRepository.save(book);

        return modelMapper.map(updatedBook, BookCreateDto.class);
    }

    @Transactional
    public BookDto findBook(Integer id) {
        Book book = bookRepository.findBookById(id);
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        return toDto(book);
    }

    @Transactional
    public List<ReviewBook> findReviewByBook(Integer bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }
        return bookRepository.findReviewOnBookById(bookId).stream()
                .map(this::convertToReviewBookDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewBook> findReviewWithEvaluation(String title, Integer evaluation) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) {
            errors.add("Title can't be blank!");
        }
        if (evaluation > 5 || evaluation < 1) {
            errors.add("Evaluation with " + evaluation + " need be more 0 and less 5");
        }
        if (!errors.isEmpty()) {
            throw new BookValidationException("Error validation", errors);
        }
        return bookRepository.findReviewOnBookWithEvaluation(title, evaluation).stream()
                .map(this::convertToReviewBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> showAllBooks(Book book) {
        return bookRepository.findAll(book).stream()
                .map(book1 -> modelMapper.map(book1, BookDto.class))
                .collect(Collectors.toList());
    }

    public List<BookDto> findBookByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Запись с ID " + id + " не найдена");
        }
        this.bookRepository.deleteById(id);
    }

    ReviewBook convertToReviewBookDto(Review review) {
        ReviewBook dto = new ReviewBook();
        dto.setPersonId(review.getPerson().getId());
        dto.setUsername(review.getPerson().getUserName());
        dto.setComment(review.getComment());
        dto.setEvaluation(review.getEvaluation());
        dto.setCreatedDate(review.getCreatedDate());
        return dto;
    }


    BookDto toDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

}
   



