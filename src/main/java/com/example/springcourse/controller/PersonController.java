package com.example.springcourse.controller;

import com.example.springcourse.dto.person.PersonDTO;
import com.example.springcourse.dto.person.PersonReviewDTO;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;


    @PostMapping()
    public ResponseEntity<PersonDTO> addNewPerson(@RequestBody @Valid PersonDTO personDTO) {
        PersonDTO savedPerson = personService.savePerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<PersonDTO> findPersonInfoById(@PathVariable Integer id) {
        PersonDTO personDTO = personService.getPersonDTO(id);
        if (personDTO == null) {
            log.info("Person can't be null");
            throw new PersonNotFoundException("Person with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personDTO);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<PersonReviewDTO> findUserById(@PathVariable Integer id) {
        PersonReviewDTO personReviewDTO = personService.getUserName(id);
        if (personReviewDTO == null) {
            log.info("User can't be null");
            throw new PersonNotFoundException("User with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personReviewDTO);
    }

    @GetMapping()
    public List<Person> findAllPerson(Person person) {
        return personService.showAllPerson(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDTO> updatePersonById(@PathVariable Integer id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPersonDTO = personService.updatePerson(id, personDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPersonDTO);

    }

    @DeleteMapping("{id}")
    public void deletePersonById(@PathVariable Integer id) {
        this.personService.deletePerson(id);
    }
}
