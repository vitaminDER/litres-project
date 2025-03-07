package com.example.springcourse.repository;

import com.example.springcourse.dto.person.PersonDTO;
import com.example.springcourse.dto.person.PersonReviewDTO;
import com.example.springcourse.entity.Person;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("select per from Person per where per.id = :id")
    Person findPersonById(Integer id);

    @Query("select per from Person per where per.id = :id")
    PersonDTO findPersonDTOById(Integer id);

//    @Query("select user.userName from Person user where user.id = :id")
//    PersonReviewDTO findUserById(Integer id);

    @NativeQuery("Select * From Person")
    List<Person> findAllPerson(Person person);

}
