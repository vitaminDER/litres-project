package com.example.springcourse.repository;

import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {


    @Query("select bk from Book bk where bk.id = :id")
    Book findBookById(@Param("id") UUID id);

    @Query("select bk from Book bk where bk.title = :title")
    Book findBookByTitle(@Param("title") String title);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.person WHERE r.book.id = :bookId")
    List<Review> findReviewOnBookById(@Param("bookId") UUID bookId);

    @Query("select bk from Book bk join fetch bk.genre g where g.name = :genre")
    List<Book> findBooksByGenre(@Param("genre") String genre);

    @NativeQuery("select * from Book")
    List<Book> findAll(Book book);

    @NativeQuery("select avg(rating) from book_rating where book_id = :id")
    Double calculateAverageRatingBook(@Param("id") UUID id);

}


