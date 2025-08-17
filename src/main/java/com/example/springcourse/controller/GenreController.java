package com.example.springcourse.controller;

import com.example.springcourse.dto.genre.GenreResponse;
import com.example.springcourse.entity.genre.Genre;
import com.example.springcourse.service.GenreService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequiredArgsConstructor
@RequestMapping("api/genre")
public class GenreController {

    private final GenreService genreService;

    @CrossOrigin
    @GetMapping()
    public List<Genre> getAllGenres(Genre genres) {
        return genreService.getAllGenre(genres);
    }
}
