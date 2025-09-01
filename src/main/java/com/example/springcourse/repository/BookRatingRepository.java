package com.example.springcourse.repository;

import com.example.springcourse.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, UUID> {

    @Query("select br from BookRating br where br.book.id= :bookId and br.person.id = :personId")
    Optional<BookRating> findRatingBookByPerson(@Param("bookId")UUID bookId,
                                                @Param("personId") UUID personId);

}
