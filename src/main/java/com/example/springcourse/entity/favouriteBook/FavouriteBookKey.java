package com.example.springcourse.entity.favouriteBook;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
public class FavouriteBookKey implements Serializable {

    @Column(name = "book_id")
    private UUID bookId;

    @Column(name = "person_id")
    private UUID personId;
}
