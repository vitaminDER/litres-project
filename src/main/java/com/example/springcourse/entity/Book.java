package com.example.springcourse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private BigDecimal rating;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    List<Genre> genre;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonBackReference
    Author authorBooks;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Review> review;

    @ManyToMany(mappedBy = "favouriteBooks", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Person> person;


}
