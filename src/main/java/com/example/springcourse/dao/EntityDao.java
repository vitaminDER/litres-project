package com.example.springcourse.dao;

import com.example.springcourse.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EntityDao extends CrudRepository<Book, Integer>{

}
