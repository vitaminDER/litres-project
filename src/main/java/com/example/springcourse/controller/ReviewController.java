package com.example.springcourse.controller;

import com.example.springcourse.dto.review.ReviewRequest;
import com.example.springcourse.dto.review.ReviewResponse;
import com.example.springcourse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest,
                                                       @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                reviewService.savePersonReviewByBook(reviewRequest, authHeader));
    }

    @GetMapping()
    public ResponseEntity<?> findReview(@RequestParam("bookId") UUID bookId,
                                        @RequestParam("personId") UUID personId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewByPersonIdAndBookId(bookId, personId));
    }

    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable UUID reviewId) {
        this.reviewService.deleteReviewById(reviewId);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<ReviewRequest> updateReview(@PathVariable UUID id,
//                                                      @Valid @RequestBody ReviewRequest reviewRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(id, reviewRequest));
//    }
}
