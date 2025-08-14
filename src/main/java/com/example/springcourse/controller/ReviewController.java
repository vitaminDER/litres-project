package com.example.springcourse.controller;

import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewPersonResponse;
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

    @PostMapping()
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest,
                                                       @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                reviewService.savePersonReviewByBook(reviewRequest, authHeader));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewRequest> updateReview(@PathVariable Integer id,
                                                      @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(id, reviewRequest));
    }

    @GetMapping()
    public ResponseEntity<ReviewPersonResponse> findReview(@RequestParam("bookId") Integer bookId,
                                                           @RequestParam("personId") Integer personId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReviewByPersonIdAndBookId(bookId, personId));
    }

//    @DeleteMapping("{id}")
//    public void deleteReview(@PathVariable Integer id,
//                             @RequestHeader("Authorization") String authHeader) {
//        this.reviewService.deleteReviewById(id, authHeader);
//    }

}
