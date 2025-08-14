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

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select review from Review review where review.id = :id")
    Review findReviewById(Integer id);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.person WHERE r.book.id = :bookId")
    Page<Review> findAllReviewsByBook(@Param("bookId") Integer bookId, Pageable pageable);

    @Query("SELECT COUNT(rev) > 0 FROM Review rev WHERE rev.book = :book AND rev.person = :person")
    boolean existsByBookAndPerson(Book book, Person person);

    @Query("select rev from Review rev join fetch rev.book join fetch rev.person" +
            " where rev.book.id = :bookId and rev.person.id = :personId")
    Review findReviewByPersonAndByBook(@Param("bookId") Integer bookId, @Param("personId") Integer personId);


}

