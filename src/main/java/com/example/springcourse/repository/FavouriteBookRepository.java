package com.example.springcourse.repository;

import com.example.springcourse.entity.favouriteBook.FavouriteBook;
import com.example.springcourse.entity.favouriteBook.FavouriteBookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavouriteBookRepository  extends JpaRepository<FavouriteBook, UUID> {

    @Query("select fb from FavouriteBook fb where fb.book.id = :bookId and fb.person.id = :personId")
    Optional<FavouriteBook> findByBookIdAndPersonId(@Param("bookId") UUID bookId,
                                                    @Param("personId") UUID personId);
}
