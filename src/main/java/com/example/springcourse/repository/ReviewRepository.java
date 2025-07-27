package com.example.springcourse.repository;

import com.example.springcourse.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select review from Review review where review.id = :id")
    Review findReviewById(Integer id);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.person WHERE r.book.id = :bookId")
    Page<Review> findAllReviewsByBook(@Param("bookId") Integer bookId, Pageable pageable);

}

