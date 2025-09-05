package com.example.springcourse.entity.genre;

import com.example.springcourse.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "genre_book")
    private EGenre name;

    @ManyToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @JsonBackReference
    List<Book> booksGenres;

}


