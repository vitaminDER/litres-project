package com.example.springcourse.service;

import com.example.springcourse.entity.Book;
import com.example.springcourse.repository.EntityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService implements EntityRepository {


    private final EntityManager entityManager;

    @Autowired
    public EntityService(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public List<Book> findAllBook() {
        Session session = entityManager.unwrap(Session.class);
        TypedQuery<Book> query = session.createQuery("from Book", Book.class);
        List<Book> books = query.getResultList();
        System.out.println("Books found: " + books.size());
        return books;

    }
}