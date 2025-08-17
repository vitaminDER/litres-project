package com.example.springcourse.service;

import com.example.springcourse.dto.genre.GenreResponse;
import com.example.springcourse.entity.genre.Genre;
import com.example.springcourse.repository.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;


    public List<Genre> getAllGenre(Genre genres) {
        return genreRepository.findAllByName(genres).stream()
                .map(genre1 -> modelMapper.map(genre1, Genre.class))
                .collect(Collectors.toList());
    }

}
