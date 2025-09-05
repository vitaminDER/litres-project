package com.example.springcourse.entity.bookRating;

import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_rating")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRating  {

    @EmbeddedId
    private BookRatingKey id;


    @Column(name = "rating")
    private int bookRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;


}


