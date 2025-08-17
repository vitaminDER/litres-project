package com.example.springcourse.service;

import com.example.springcourse.dto.review.*;
import com.example.springcourse.entity.Book;
import java.nio.file.AccessDeniedException;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.exception.*;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenService jwtTokenService;

    public ReviewResponse savePersonReviewByBook(ReviewRequest reviewRequest, String authHeader) {

        UUID bookId = reviewRequest.getBookId();
        UUID personId = reviewRequest.getPersonId();

        String token = extractToken(authHeader);
        UUID tokenPersonId = jwtTokenService.extractPersonId(token);
        validateToken(token, tokenPersonId);

        if (!personId.equals(tokenPersonId)) {
            throw new SecurityException("Token doesn't match requested person");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Person person = personRepository.findById(tokenPersonId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        if (reviewRepository.existsByBookAndPerson(book, person)) {
            throw new ReviewAlreadyExistsException("Review already exists");
        }

        var review = new Review();
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

    private void validateToken(String token, UUID expectedPersonId) {
        if (expectedPersonId == null) {
            throw new SecurityException("Person ID cannot be null for token validation");
        }
        // 1. Проверка валидности токена
        if (!jwtTokenService.validateToken(token)) {
            throw new InvalidTokenException("Invalid or expired token");
        }
        // 2. Проверка соответствия personId
        UUID tokenPersonId = jwtTokenService.extractPersonId(token);
        if (!expectedPersonId.equals(tokenPersonId)) {
            throw new SecurityException("Token doesn't match requested person");
        }
    }

//    public ReviewRequest updateReview(UUID id, ReviewRequest reviewRequest) {
//        Review review = reviewRepository.findReviewById(id);
//        modelMapper.map(reviewRequest, review);
//        Review updatedReview = reviewRepository.save(review);
//        return modelMapper.map(updatedReview, ReviewRequest.class);
//    }

    public Object findReviewByPersonIdAndBookId(UUID bookId, UUID personId) {

        if (bookId == null || personId == null) {
            throw new IllegalArgumentException("BookId and PersonId must not be null");
        }

        Optional<Review> reviewOpt = reviewRepository.findReviewByPersonAndByBook(bookId, personId);


        ReviewPersonResponse reviewResponse = new ReviewPersonResponse();
        reviewResponse.setBookId(bookId);
        reviewResponse.setPersonId(personId);

        ReviewNotExistResponse reviewNotExistResponse = new ReviewNotExistResponse();

        Person person = new Person();

        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            reviewResponse.setBookId(review.getBook().getId());
            reviewResponse.setPersonId(review.getPerson().getId());
            reviewResponse.setReviewId(review.getId());
            reviewResponse.setComment(review.getComment());
            reviewResponse.setDate(review.getCreatedDate());
            return reviewResponse;
        } else {
            reviewNotExistResponse.setBookId(reviewResponse.getBookId().toString());
            reviewNotExistResponse.setPersonId(reviewResponse.getPersonId().toString());
            reviewNotExistResponse.setReviewId("");
            reviewNotExistResponse.setComment("");
            reviewNotExistResponse.setDate("");
        }
        return reviewNotExistResponse;
    }

    @Transactional
    public void deleteReviewById(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID currentPersonId = ((PersonUserDetails) authentication.getPrincipal()).getPerson().getId();

        if (!review.getPerson().getId().equals(currentPersonId)) {
            throw new ReviewAccessDeniedException("You can't delete another review");
        } else {
            reviewRepository.delete(review);
        }
    }
}
