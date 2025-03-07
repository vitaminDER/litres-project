package com.example.springcourse.repository;

import com.example.springcourse.dto.BookDTO;
import com.example.springcourse.entity.Book;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {


    @Query("select bk from Book bk where bk.id = :id")
    Book findBookById(int id);

//    @Query("select book from Book book join fetch book.person where book.person.id = :person_id")
//    List<Book> findBookByPersonId(@Param("person_id")Integer person_id);

    @Query("select bk from Book bk where bk.year = :year")
    List<Book> findBookByYear(int year);

    @NativeQuery("select * from Book ")
    List<Book> findAll(Book book);



}

