package com.example.springcourse.controller;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.dto.person.PersonDtoRead;
import com.example.springcourse.dto.person.PersonFavouriteBooksDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewPersonDto;
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
        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<List<ReviewPersonDto>> findPersonReview(@PathVariable Integer id) {
        List<ReviewPersonDto> reviewBookDto = personService.getReviewPerson(id);
        return ResponseEntity.status(HttpStatus.OK).body(reviewBookDto);
    }


    @GetMapping()
    public List<PersonDtoRead> findAllPerson(Person person) {
        return personService.showAllPerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Integer id, @RequestBody PersonDto personDto) {
        PersonDto updatedPersonDto = personService.updatePerson(id, personDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPersonDto);

    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable Integer id) {
        this.personService.deletePerson(id);
    }

    @GetMapping("/favouriteBooks")
    public ResponseEntity<PersonFavouriteBooksDto> findFavouriteBooksPerson(@RequestParam("username") String username) {
        PersonFavouriteBooksDto personFavouriteBooksDto = personService.getPersonFavouriteBooks(username);
        return ResponseEntity.status(HttpStatus.OK).body(personFavouriteBooksDto);
    }
}
