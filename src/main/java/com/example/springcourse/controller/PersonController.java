package com.example.springcourse.controller;

import com.example.springcourse.dto.book.BookRatingRequest;
import com.example.springcourse.dto.person.*;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Person;
import com.example.springcourse.service.BookService;
import com.example.springcourse.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;
    private final BookService bookService;


    @GetMapping("/info/{id}")
    public ResponseEntity<PersonDtoRead> findPersonInfo(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonInfo(id));
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<List<ReviewPersonDto>> findPersonReview(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getReviewPerson(id));
    }

    @PostMapping()
    public ResponseEntity<?> makeRatingOnBook(@RequestBody @Valid BookRatingRequest bookRatingRequest) {
        bookService.makeRatingBookByPerson(bookRatingRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public List<PersonDtoRead> findAllPerson(Person person) {
        return personService.showAllPerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable UUID id,
                                                  @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(id, personDto));

    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable UUID id) {
        this.personService.deletePerson(id);
    }

    @GetMapping("/favouriteBooks")
    public ResponseEntity<PersonFavouriteBooksDto> findFavouriteBooksPerson(@RequestParam("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getPersonFavouriteBooks(username));
    }
}
