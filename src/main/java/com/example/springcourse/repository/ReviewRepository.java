package com.example.springcourse.repository;

import com.example.springcourse.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    Review findReviewById(int id);
}

