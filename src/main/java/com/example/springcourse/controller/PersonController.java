package com.example.springcourse.controller;

import com.example.springcourse.dto.person.*;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Person;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.savePerson(personDto));
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> userSignup(@RequestBody @Valid PersonRegistrationDto personRegistrationDto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(personService.userSignu(personRegistrationDto));
//    }
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> userSigin(@Valid @RequestBody PersonAuthenticationDto personAuthenticationDto) {
//        return ResponseEntity.ok(personService.userSignin(personAuthenticationDto));
//    }

    @GetMapping("/info/{id}")
    public ResponseEntity<PersonDtoRead> findPersonInfo(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonInfo(id));
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<List<ReviewPersonDto>> findPersonReview(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getReviewPerson(id));
    }


    @GetMapping()
    public List<PersonDtoRead> findAllPerson(Person person) {
        return personService.showAllPerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Integer id,
                                                  @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(id, personDto));

    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable Integer id) {
        this.personService.deletePerson(id);
    }

    @GetMapping("/favouriteBooks")
    public ResponseEntity<PersonFavouriteBooksDto> findFavouriteBooksPerson(@RequestParam("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonFavouriteBooks(username));
    }
}
