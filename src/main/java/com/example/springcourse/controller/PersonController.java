package com.example.springcourse.controller;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.dto.person.PersonFavouriteBooksDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.entity.Person;
import com.example.springcourse.exception.PersonNotFoundException;
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


    @PostMapping()
    public ResponseEntity<PersonDto> createPerson(@RequestBody @Valid PersonDto personDto) {
        PersonDto savedPerson = personService.savePerson(personDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<PersonDto> findPersonInfo(@PathVariable Integer id) {
        PersonDto personDto = personService.getPersonInfo(id);
        if (personDto == null) {
            log.info("Person can't be null");
            throw new PersonNotFoundException("Person with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewBook> findPersonReview(@PathVariable Integer id) {
        ReviewBook reviewBookDto = personService.getReviewPerson(id);
        if (reviewBookDto == null) {
            log.info("User can't be null");
            throw new PersonNotFoundException("User with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewBookDto);
    }

    @GetMapping("/favouriteBooks/{id}")
    public ResponseEntity<PersonFavouriteBooksDto> findFavouriteBooksPerson(@PathVariable Integer id) {
        PersonFavouriteBooksDto personFavouriteBooksDto = personService.getPersonFavouriteBooks(id);
        if (personFavouriteBooksDto == null) {
            log.info("User can't be null");
            throw new PersonNotFoundException("User with ID " + id + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(personFavouriteBooksDto);
    }

    @GetMapping()
    public List<Person> findAllPerson(Person person) {
        return personService.showAllPerson(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Integer id, @RequestBody PersonDto personDto) {
        PersonDto updatedPersonDto = personService.updatePerson(id, personDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPersonDto);

    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable Integer id) {
        this.personService.deletePerson(id);
    }
}
