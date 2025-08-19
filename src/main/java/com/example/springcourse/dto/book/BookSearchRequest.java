package com.example.springcourse.dto.book;

import com.example.springcourse.entity.TypeSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequest {

    private String searchValue;

    private TypeSearch typeSearch;


}
