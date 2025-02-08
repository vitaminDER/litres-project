package com.example.springcourse.controller;

import com.example.springcourse.dto.PersonDTO;
import com.example.springcourse.entity.Person;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    private final PersonRepository personRepository;


    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addNewPerson(@RequestBody @Valid PersonDTO personDTO) {

        PersonDTO savedPerson = personService.savePerson(personDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Integer id) {

        Person person = personRepository.findPersonById(id);

        if (person == null) {
            log.info("Person can't be null");
            throw new PersonNotFoundException("Person with ID " + id + " not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }
}
