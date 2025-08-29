package com.example.springcourse.service;

import com.example.springcourse.dto.book.*;
import com.example.springcourse.dto.page.PageResponse;
import com.example.springcourse.dto.review.response.ReviewBookResponse;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.entity.genre.Genre;
import com.example.springcourse.exception.BookNotFoundException;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.GenreRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;


    public void saveBook(BookCreateRequest bookCreateRequest) {
        List<Genre> genres = genreRepository.findAllById(bookCreateRequest.getGenreId());

        var book = new Book();
        book.setTitle(bookCreateRequest.getTitle());
        book.setAuthor(bookCreateRequest.getAuthor());
        book.setYear(bookCreateRequest.getYear());
        book.setDescription(bookCreateRequest.getDescription());
        book.setGenre(new ArrayList<>(genres));
        book.setImage(bookCreateRequest.getImage());

        Book savedBook = bookRepository.save(book);
        log.info("Book saved successfully with ID: {}", savedBook.getId());
    }


    public BookCreateRequest convertBookToDto(Book book) {
        var dto = new BookCreateRequest();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setYear(book.getYear());
        dto.setDescription(book.getDescription());
        dto.setImage(book.getImage());
        dto.setGenreId(book.getGenre().stream()
                .map(Genre::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public void updateBook(BookUpdateRequest updateRequest) {

        UUID bookId = updateRequest.getBookId();
        Book book = bookRepository.findBookInfoByIdForAdmin(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book can't be null");
        }

        List<Genre> genres = genreRepository.findAllById(updateRequest.getGenresId());

        book.setTitle(updateRequest.getBookTitle());
        book.setAuthor(updateRequest.getBookAuthor());
        book.setYear(updateRequest.getBookYear());
        book.setDescription(updateRequest.getBookDescription());
        book.setImage(updateRequest.getBookImage());
        book.setIsbn(updateRequest.getIsbn());
        book.getGenre().clear();
        book.getGenre().addAll(genres);

        Book updatedBook = bookRepository.save(book);
        ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @Transactional
    public BookResponse findBook(UUID id) {

        Book book = bookRepository.findBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id" + id + " not found");
        }

        averageRatingBook(id);

        BookResponse response = modelMapper.map(book, BookResponse.class);
        response.setGenre(book.getGenre().stream()
                .map(genre -> modelMapper.map(genre, Genre.class))
                .collect(Collectors.toList()));

        return response;
    }

    @Transactional
    public PageResponse<ReviewBookResponse> findReviewByBook(UUID bookId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Review> pageReviews = reviewRepository.findAllReviewsByBook(bookId, pageable);
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }
        return new PageResponse<>(
                pageReviews.getContent().stream()
                        .map(this::convertToReviewBookResponse)
                        .collect(Collectors.toList()),
                pageReviews.getNumber() + 1,
                pageReviews.getSize(),
                pageReviews.getTotalPages()
        );
    }

    @Transactional
    public AdminBookInfoResponse findBookInfoForAdmin(UUID bookId) {

        Book book = bookRepository.findBookInfoByIdForAdmin(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with id" + bookId + " not found");
        }

        averageRatingBook(bookId);

        AdminBookInfoResponse response = modelMapper.map(book, AdminBookInfoResponse.class);
        response.setGenres(book.getGenre().stream()
                .map(genre -> modelMapper.map(genre, Genre.class))
                .collect(Collectors.toList()));

        return response;
    }

    public PageResponse<AllBookResponse> getBooksForAdmin(BookSearchRequest searchRequest,
                                                          int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Book> pageBooks;

        if (!StringUtils.hasText(searchRequest.getSearchValue())) {
            pageBooks = bookRepository.findAll(pageable);
        } else {
            pageBooks = bookRepository.searchBooks(searchRequest.getSearchValue(),
                    searchRequest.getTypeSearch().name(), pageable);

        }
        return new PageResponse<>(
                pageBooks.getContent().stream()
                        .map(book -> modelMapper.map(book, AllBookResponse.class))
                        .collect(Collectors.toList()),
                pageBooks.getNumber() + 1,
                pageBooks.getSize(),
                pageBooks.getTotalPages()
        );
    }

    public List<AllBookResponse> showAllBooks(Book book) {
        return bookRepository.findAll(book).stream()
                .map(book1 -> modelMapper.map(book1, AllBookResponse.class))
                .collect(Collectors.toList());
    }


    public List<BookCreateRequest> findBookByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookCreateRequest findBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        return toDto(book);
    }


    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Запись с ID " + id + " не найдена");
        }
        this.bookRepository.deleteById(id);
    }

    public void averageRatingBook(UUID id) {
        Double averageRating = bookRepository.calculateAverageRatingBook(id);
        if (averageRating == null) {
            averageRating = 0.0;
        }
        Book book = bookRepository.findBookById(id);
        book.setRating(BigDecimal.valueOf(Math.floor(averageRating * 10) / 10));
        bookRepository.save(book);
    }

    ReviewBookResponse convertToReviewBookResponse(Review review) {
        Person person = Optional.ofNullable(review.getPerson())
                .orElseThrow(() -> new IllegalStateException("Review has no associated person"));

        ReviewBookResponse dto = new ReviewBookResponse();
        dto.setUsername(StringUtils.hasText(person.getUsername())
                ? person.getUsername()
                : person.getLogin());
        dto.setComment(review.getComment());
        dto.setCreatedDate(review.getCreatedDate());
        return dto;
    }


    BookCreateRequest toDto(Book book) {
        return modelMapper.map(book, BookCreateRequest.class);
    }

}
   



