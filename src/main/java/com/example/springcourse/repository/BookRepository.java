package com.example.springcourse.repository;

import com.example.springcourse.dto.book.BookDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {


    @Query("select bk from Book bk where bk.id = :id")
    Book findBookById(@Param("id") int id);

    @Query("select bk from Book bk where bk.title = :title")
    Book findBookByTitle(@Param("title") String title);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.person WHERE r.book.id = :bookId")
    List<Review> findReviewOnBookById(@Param("bookId") Integer bookId);

    @Query("select r from Review r join fetch r.book b where  b.title = :title and r.evaluation = :evaluation")
    List<Review> findReviewOnBookWithEvaluation(@Param("title") String title, @Param("evaluation") Integer evaluation);

    @Query("select bk from Book bk join fetch bk.genre g where g.name = :genre")
    List<Book> findBooksByGenre(@Param("genre") String genre);

    @NativeQuery("select * from Book")
    List<Book> findAll(Book book);

    @NativeQuery("select avg(rating) from book_rating where book_id = :id")
    Double calculateAverageRatingBook(@Param("id") int id);
}


