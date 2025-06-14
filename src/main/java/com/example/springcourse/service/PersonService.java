package com.example.springcourse.service;

import com.example.springcourse.dto.person.PersonDto;
import com.example.springcourse.dto.person.PersonDtoRead;
import com.example.springcourse.dto.person.PersonFavouriteBooksDto;
import com.example.springcourse.dto.review.ReviewBook;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
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
        if (person == null) {
            throw new RuntimeException("Person not found with id: " + id);
        }

        modelMapper.map(personDto, person);
        Person updatedPerson = personRepository.save(person);
        return modelMapper.map(updatedPerson, PersonDto.class);
    }

    public List<PersonDtoRead> showAllPerson(Person person) {
        List<Person> personList = personRepository.findAllPerson(person);
        return personList.stream()
                .map(person1 -> {
                    return modelMapper.map(person1, PersonDtoRead.class);
                })
                .collect(Collectors.toList());
    }

    public void deletePerson(Integer id) {
        this.personRepository.deleteById(id);
    }

    public List<ReviewPersonDto> getReviewPerson(Integer id) {
        List<Review> reviewsList = personRepository.findPersonReviewById(id);
        return reviewsList.stream()
                .map(review -> {
                  ReviewPersonDto dto = new ReviewPersonDto();
                  dto.setPersonId(review.getPerson().getId());
                  dto.setUsername(review.getPerson().getUserName());
                  dto.setTitle(review.getTitle());
                  dto.setComment(review.getComment());
                  dto.setEvaluation(review.getEvaluation());
                  return dto;
                })
                .collect(Collectors.toList());
    }

    public PersonDto getPersonInfo(Integer id) {
        Person person = personRepository.findPersonById(id);
        return toDto(person);
    }

    public PersonFavouriteBooksDto getPersonFavouriteBooks(Integer id) {
        Person person = personRepository.findPersonFavouriteBooks(id);
        return modelMapper.map(person, PersonFavouriteBooksDto.class);
    }

    PersonDto toDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    Person toEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

}


