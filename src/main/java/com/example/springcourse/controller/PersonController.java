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

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    private final PersonRepository personRepository;

    @PostMapping("/add")
    public ResponseEntity<Person> addNewPerson(@Valid @RequestBody PersonDTO personDTO) {

        Person savedPerson = personService.savePerson(personDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping("/find/{id}")
    public Person findPersonById(@PathVariable Integer id) {
        return personRepository.findPersonById(id);

    }
}
