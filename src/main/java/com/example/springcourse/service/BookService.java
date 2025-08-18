package com.example.springcourse.service;

import com.example.springcourse.dto.book.BookResponse;
import com.example.springcourse.dto.book.BookRequest;
import com.example.springcourse.dto.book.AllBookResponse;
import com.example.springcourse.dto.review.ReviewBookResponse;
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
import org.springframework.stereotype.Service;
import com.example.springcourse.dto.page.PageDto;
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


    public void saveBook(BookRequest bookRequest) {
        List<Genre> genres = genreRepository.findAllById(bookRequest.getGenreId());

        var book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setYear(bookRequest.getYear());
        book.setDescription(bookRequest.getDescription());
        book.setGenre(new ArrayList<>(genres));
        book.setImage(bookRequest.getImage());

        Book savedBook = bookRepository.save(book);
        log.info("Book saved successfully with ID: {}", savedBook.getId());
    }



    public BookRequest convertBookToDto(Book book) {
        var dto = new BookRequest();
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

    public BookResponse updateBook(UUID id, BookResponse bookDto) {
        Book book = bookRepository.findBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book can't be null");
        }
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        modelMapper.map(bookDto, book);

        Book updatedBook = bookRepository.save(book);

        return modelMapper.map(updatedBook, BookResponse.class);
    }

    @Transactional
    public BookResponse findBook(UUID id) {

        Book book = bookRepository.findBookById(id);
        if(book == null) {
            throw new BookNotFoundException("Book with id"+ id +" not found");
        }

        averageRatingBook(id);

        BookResponse response = modelMapper.map(book, BookResponse.class);
        response.setGenre(book.getGenre().stream()
                .map(genre -> modelMapper.map(genre, Genre.class))
                .collect(Collectors.toList()));

        return response;
    }

    @Transactional
    public PageDto<ReviewBookResponse> findReviewByBook(UUID bookId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Review> pageReviews = reviewRepository.findAllReviewsByBook(bookId, pageable);
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }
        return new PageDto<>(
                pageReviews.getContent().stream()
                        .map(this::convertToReviewBookResponse)
                        .collect(Collectors.toList()),
                pageReviews.getNumber()+1,
                pageReviews.getSize(),
                pageReviews.getTotalPages()
        );
    }

//    @Transactional
//    public List<ReviewBook> findReviewWithEvaluation(String title, Integer evaluation) {
//        List<String> errors = new ArrayList<>();
//        if (title == null || title.isBlank()) {
//            errors.add("Title can't be blank!");
//        }
//        if (evaluation > 5 || evaluation < 1) {
//            errors.add("Evaluation with " + evaluation + " need be more 0 and less 5");
//        }
//        if (!errors.isEmpty()) {
//            throw new BookValidationException("Error validation", errors);
//        }
//        return bookRepository.findReviewOnBookWithEvaluation(title, evaluation).stream()
//                .map(this::convertToReviewBookDto)
//                .collect(Collectors.toList());
//    }

    public List<AllBookResponse> showAllBooks(Book book) {
        return bookRepository.findAll(book).stream()
                .map(book1 -> modelMapper.map(book1, AllBookResponse.class))
                .collect(Collectors.toList());
    }

    public List<BookRequest> findBookByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookRequest findBookByTitle(String title) {
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


    BookRequest toDto(Book book) {
        return modelMapper.map(book, BookRequest.class);
    }

}
   



