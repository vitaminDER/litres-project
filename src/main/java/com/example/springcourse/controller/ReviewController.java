package com.example.springcourse.controller;

import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.dto.review.ReviewResponse;
import com.example.springcourse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/book/{bookId}/person/{personId}")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable Integer bookId,
                                                       @PathVariable Integer personId,
                                                       @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                reviewService.savePersonReviewByBook(bookId, personId, reviewRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewRequest> updateReview(@PathVariable Integer id,
                                                      @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(id, reviewRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewBook> findReview(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewById(id));
    }

    @DeleteMapping("{id}")
    public void deleteReview(@PathVariable Integer id) {
        this.reviewService.deleteReviewById(id);
    }
}
