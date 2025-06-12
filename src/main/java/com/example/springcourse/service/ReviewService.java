package com.example.springcourse.service;

import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.dto.review.ReviewResponse;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
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

    public ReviewResponse savePersonReviewByBook(Integer bookId, Integer personId, ReviewRequest reviewRequest) {
        Book book = bookRepository.findBookById(bookId);
        Person person = personRepository.findPersonById(personId);
        Review review = modelMapper.map(reviewRequest, Review.class);
        review.setBook(book);
        review.setPerson(person);
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewResponse.class);
    }

    public ReviewRequest updateReview(Integer id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findReviewById(id);
        modelMapper.map(reviewRequest, review);
        Review updatedReview = reviewRepository.save(review);
        return modelMapper.map(updatedReview, ReviewRequest.class);
    }

    public ReviewBook findReviewById(Integer id) {
        Review review = reviewRepository.findReviewById(id);
        return modelMapper.map(review, ReviewBook.class);
    }

    public void deleteReviewById(Integer id) {
        this.reviewRepository.deleteById(id);
    }
}
