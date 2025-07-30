package com.example.springcourse.service;

import com.example.springcourse.dto.common.MessageResponse;
import com.example.springcourse.dto.person.*;
import com.example.springcourse.dto.review.ReviewPersonDto;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.Review;
import com.example.springcourse.entity.role.Role;
import com.example.springcourse.exception.PersonNotFoundException;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

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

//    public ResponseEntity<?> userSignup(PersonRegistrationDto personRegistrationDto) {
//        var person = modelMapper.map(personRegistrationDto, Person.class);
//        person.setFirstName("null");
//        person.setLastName("null");
//        person.setAge(1);
//        person.setEmail("null");
//        person.setUserName("null");
//        person.setLogin(personRegistrationDto.getLogin());
//        person.setPassword(personRegistrationDto.getPassword());
//        var savedPerson = personRepository.save(person);
//        return ResponseEntity.ok(modelMapper.map(savedPerson, PersonRegistrationDto.class));
//    }
//
//    public ResponseEntity<?> userSignin(PersonAuthenticationDto personAuthenticationDto) {
//        Role userRole = roleRepository.findByName("USER")
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with Role not found"));
//
//        Person person = personRepository.findPersonByLogin(personAuthenticationDto.getLogin())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
//
//        if (!person.getRoles().contains((userRole))) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Please signup!");
//        } else {
//            return ResponseEntity.ok(new MessageResponse("Success"));
//        }
//    }


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


