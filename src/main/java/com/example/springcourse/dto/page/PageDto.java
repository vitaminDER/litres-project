package com.example.springcourse.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    List<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
}
