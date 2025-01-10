package com.example.springcourse.repository;

import com.example.springcourse.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EntityRepository  {
    List<Book> findAllBook();


}
