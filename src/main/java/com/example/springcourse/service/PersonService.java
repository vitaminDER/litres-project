package com.example.springcourse.service;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.dto.person.PersonDtoRead;
import com.example.springcourse.dto.person.PersonFavouriteBooksDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Book;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.repository.BookRepository;
import com.example.springcourse.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public PersonDto savePerson(PersonDto personDto) {

        if (personDto == null) {
            log.info("Attempt to save null person");
            throw new IllegalArgumentException("Person can't be NULL!");
        }
        if (personDto.getAge() < 0) {
            log.info("Attempt to save person with negative age: {}", personDto.getAge());
            throw new IllegalArgumentException("Person age can't be less 0");
        }

        Person person = modelMapper.map(personDto, Person.class);
        Person savedPerson = personRepository.save(person);
        log.info("Person saved successfully with ID: {}", savedPerson.getId());

        return toDto(savedPerson);
    }

    public PersonDto updatePerson(Integer id, PersonDto personDto) {

        Person person = personRepository.findPersonById(id);
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

    public List<PersonDtoRead> showAllPerson(Person person) {
        return personRepository.findAllPerson(person).stream()
                .map(person1 -> modelMapper.map(person1, PersonDtoRead.class))
                .collect(Collectors.toList());
    }

    public void deletePerson(Integer id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        this.personRepository.deleteById(id);
    }

    public List<ReviewPersonDto> getReviewPerson(Integer id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with id " + id + " not found");
        }
        return personRepository.findPersonReviewById(id).stream()
                .map(this::convertToReviewPersonDto)
                .collect(Collectors.toList());
    }

    public PersonDtoRead getPersonInfo(Integer id) {
        Person person = personRepository.findPersonById(id);
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
        reviewPersonDto.setUsername(review.getPerson().getUserName());
        reviewPersonDto.setTitle(review.getTitle());
        reviewPersonDto.setComment(review.getComment());
        reviewPersonDto.setEvaluation(review.getEvaluation());
        return reviewPersonDto;
    }

    PersonDto toDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    Person toEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

}


