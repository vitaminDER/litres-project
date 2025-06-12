package com.example.springcourse.repository;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("select per from Person per where per.id = :id")
    Person findPersonById(Integer id);

    @Query("select per from Person per where per.id = :id")
    PersonDto findPersonDTOById(Integer id);

    @Query("select per from Person per where per.userName = :userName")
    Person findByUserName(String userName);

    @Query("select per from  Person per join fetch per.favouriteBooks where per.id = :id")
    Person findPersonFavouriteBooks(Integer id);

    @Query("select per from Person per  join fetch per.review where per.id = :id")
    Person findPersonReviewById(Integer id);
    @NativeQuery("Select * From Person")
    List<Person> findAllPerson(Person person);

}
