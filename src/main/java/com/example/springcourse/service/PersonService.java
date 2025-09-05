package com.example.springcourse.service;

import com.example.springcourse.dto.book.request.FavouriteBookRequest;
import com.example.springcourse.dto.person.*;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.entity.favouriteBook.FavouriteBook;
import com.example.springcourse.entity.favouriteBook.FavouriteBookKey;
import com.example.springcourse.exception.BookNotFoundException;
import com.example.springcourse.exception.FavouriteBookExistsException;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.FavouriteBookRepository;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final FavouriteBookRepository favouriteBookRepository;


    public PersonDto updatePerson(UUID id, PersonDto personDto) {

        Person person = personRepository.findPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        if (personDto == null) {
            log.info("Attempt to save null person");
            throw new IllegalArgumentException("Person can't be NULL!");
        }

        modelMapper.map(personDto, person);
        Person updatedPerson = personRepository.save(person);
        return modelMapper.map(updatedPerson, PersonDto.class);
    }

    public void addFavouriteBookByPerson(FavouriteBookRequest favouriteBookRequest) {

        UUID bookId = favouriteBookRequest.getBookId();
        UUID personId = favouriteBookRequest.getPersonId();

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found!"));

        var person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found!"));

        var favouriteBook = favouriteBookRepository.findByBookIdAndPersonId(bookId, personId)
                .orElse(null);

        if (favouriteBook != null) {
            throw new FavouriteBookExistsException("Book is already in favourites!");
        } else {

            var key = new FavouriteBookKey();
            key.setBookId(bookId);
            key.setPersonId(personId);

            var newFavouriteBook = new FavouriteBook();
            newFavouriteBook.setId(key);
            newFavouriteBook.setBook(book);
            newFavouriteBook.setPerson(person);
            favouriteBookRepository.save(newFavouriteBook);
        }
    }

    public List<PersonDtoRead> showAllPerson(Person person) {
        return personRepository.findAllPerson(person).stream()
                .map(person1 -> modelMapper.map(person1, PersonDtoRead.class))
                .collect(Collectors.toList());
    }

    public void deletePerson(UUID id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        this.personRepository.deleteById(id);
    }

    public List<ReviewPersonDto> getReviewPerson(UUID id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        return personRepository.findPersonReviewById(id).stream()
                .map(this::convertToReviewPersonDto)
                .collect(Collectors.toList());
    }

    public PersonDtoRead getPersonInfo(UUID id) {
        Person person = personRepository.findPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        return modelMapper.map(person, PersonDtoRead.class);
    }

    public PersonFavouriteBooksDto getPersonFavouriteBooks(String username) {
        Person person = personRepository.findPersonFavouriteBooks(username);
        return modelMapper.map(person, PersonFavouriteBooksDto.class);
    }

    ReviewPersonDto convertToReviewPersonDto(Review review) {
        var reviewPersonDto = new ReviewPersonDto();
        reviewPersonDto.setPersonId(review.getPerson().getId());
        reviewPersonDto.setUsername(review.getPerson().getUsername());
//        reviewPersonDto.setTitle(review.getTitle());
        reviewPersonDto.setComment(review.getComment());
//        reviewPersonDto.setEvaluation(review.getEvaluation());
        return reviewPersonDto;
    }

    PersonDto toDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    Person toEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

}


