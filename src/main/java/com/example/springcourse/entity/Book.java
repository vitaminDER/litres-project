package com.example.springcourse.entity;

import com.example.springcourse.entity.genre.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private String year;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "image")
    private String image;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    List<Genre> genre;


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "author_book", referencedColumnName = "id")
//    @JsonBackReference
//    Author authorBooks;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    @JsonManagedReference("book-reviews")
    List<Review> review;

    @ManyToMany(mappedBy = "favouriteBooks", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Person> person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_rating",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    List<BookRating> bookRating;


}
