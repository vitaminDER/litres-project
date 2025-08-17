package com.example.springcourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book_rating")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRating {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "rating")
    private int bookRating;

    @ManyToMany(mappedBy = "bookRating", fetch = FetchType.LAZY)
    List<Book> book;

}


