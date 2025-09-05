package com.example.springcourse.dto.book.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteBookRequest {

    private UUID bookId;
    private UUID personId;
}
