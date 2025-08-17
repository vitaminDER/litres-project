package com.example.springcourse.repository;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.dto.person.PersonDtoRead;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("select per from Person per where per.id = :id")
    Optional<Person> findPersonById(UUID id);

    @Query("select per from Person per where per.userName = :username")
    Person findByUserName(@Param("username") String username);

    @Query("select per from  Person per join fetch per.favouriteBooks where per.userName = :username")
    Person findPersonFavouriteBooks(@Param("username") String username);

    @Query("select r from Review r left join fetch r.person where r.person.id = :personId")
    List<Review> findPersonReviewById(@Param("personId") UUID personId);
    @NativeQuery("Select * From Person")
    List<Person> findAllPerson(Person person);

    @Query("select per from Person per where per.login = :login")
    Optional<Person> findPersonByLogin(String login);

    @Query("SELECT p FROM Person p WHERE p.login = :login")
    Optional<Person> findByLogin(String login);

    boolean existsByLogin(String login);
}
