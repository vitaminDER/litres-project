package com.example.springcourse.repository;

import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("select review from Review review where review.id = :id")
    Optional<Review> findReviewById(UUID id);

    @Query("select rev from Review rev join fetch rev.person per where rev.id = :reviewId and per.id = :personId")
    Optional<Review> findReviewByPerson(@Param("reviewId") UUID reviewId, @Param("personId") UUID personId);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.person WHERE r.book.id = :bookId")
    Page<Review> findAllReviewsByBook(@Param("bookId") UUID bookId, Pageable pageable);

    @Query("SELECT COUNT(rev) > 0 FROM Review rev WHERE rev.book = :book AND rev.person = :person")
    boolean existsByBookAndPerson(Book book, Person person);

    @Query("select rev from Review rev join fetch rev.book join fetch rev.person" +
            " where rev.book.id = :bookId and rev.person.id = :personId")
    Optional<Review> findReviewByPersonAndByBook(@Param("bookId") UUID bookId, @Param("personId") UUID personId);


}

