package com.example.springcourse.controller;

import com.example.springcourse.dto.PersonDTO;
import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

import java.io.PipedWriter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;


    private final PersonRepository personRepository;

    @PostMapping("/add")
    public ResponseEntity<Person> addNewPerson(@Valid @RequestBody PersonDTO personDTO) {

        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO can't be NULL!");
        }
        if (personDTO.getAge() < 0) {
            throw new IllegalArgumentException("Person age can't be less 0");
        }

        Person savedPerson = personService.savePerson(personDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping("/find/{id}")
    public Person findPersonById(@PathVariable Integer id) {
        return personRepository.findPersonById(id);

    }
}
