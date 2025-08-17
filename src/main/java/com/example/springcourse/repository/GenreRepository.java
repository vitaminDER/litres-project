package com.example.springcourse.repository;

import com.example.springcourse.entity.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    @Query("select genre from Genre genre")
    List<Genre> findAllByName(Genre genres);
}
