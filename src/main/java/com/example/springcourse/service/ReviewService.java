package com.example.springcourse.service;

import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewPersonResponse;
import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.dto.review.ReviewResponse;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.exception.BookNotFoundException;
import com.example.springcourse.exception.InvalidTokenException;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.exception.ReviewAlreadyExistsException;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenService jwtTokenService;

    public ReviewResponse savePersonReviewByBook(ReviewRequest reviewRequest, String authHeader ) {

        Integer bookId = reviewRequest.getBookId();
        Integer personId = reviewRequest.getPersonId();

        String token = extractToken(authHeader);
        Integer tokenPersonId = jwtTokenService.extractPersonId(token);
        validateToken(token, tokenPersonId);

        if (!personId.equals(tokenPersonId)) {
            throw new SecurityException("Token doesn't match requested person");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Person person = personRepository.findById(tokenPersonId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        if(reviewRepository.existsByBookAndPerson(book, person)) {
            throw new ReviewAlreadyExistsException("Review already exists");
        }

        Review review = new Review();
        review.setComment(reviewRequest.getComment());
        review.setBook(book);
        review.setPerson(person);

        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewResponse.class);
    }
    private String extractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header");
        }
        return authHeader.substring(7);
    }

    private void validateToken(String token, Integer expectedPersonId) {
        if (expectedPersonId == null) {
            throw new SecurityException("Person ID cannot be null for token validation");
        }
        // 1. Проверка валидности токена
        if (!jwtTokenService.validateToken(token)) {
            throw new InvalidTokenException("Invalid or expired token");
        }
        // 2. Проверка соответствия personId
        Integer tokenPersonId = jwtTokenService.extractPersonId(token);
        if (!expectedPersonId.equals(tokenPersonId)) {
            throw new SecurityException("Token doesn't match requested person");
        }
    }

    public ReviewRequest updateReview(Integer id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findReviewById(id);
        modelMapper.map(reviewRequest, review);
        Review updatedReview = reviewRepository.save(review);
        return modelMapper.map(updatedReview, ReviewRequest.class);
    }

    public ReviewPersonResponse findReviewByPersonIdAndBookId(Integer bookId, Integer personId) {
        Review review = reviewRepository.findReviewByPersonAndByBook(bookId, personId);
        if(bookId == null ||  personId == null ) {
            throw new IllegalArgumentException("BookId and PersonId must not be null");
        }
        return modelMapper.map(review, ReviewPersonResponse.class);
    }

    public void deleteReviewById(Integer id) {
        this.reviewRepository.deleteById(id);
    }
}
