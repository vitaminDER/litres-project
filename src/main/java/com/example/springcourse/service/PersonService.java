package com.example.springcourse.service;

import com.example.springcourse.dto.PersonDTO;
import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonDTO savePerson(PersonDTO personDTO) {

        if (personDTO == null) {
            log.info("Attempt to save null person");
            throw new IllegalArgumentException("Person can't be NULL!");
        }
        if (personDTO.getAge() < 0) {
            log.info("Attempt to save person with negative age: {}", personDTO.getAge());
            throw new IllegalArgumentException("Person age can't be less 0");
        }

        Person person = modelMapper.map(personDTO, Person.class);
        Person savedPerson = personRepository.save(person);
        log.info("Person saved successfully with ID: {}", savedPerson.getId());

        return convertToPersonDTO(savedPerson);
    }

    public PersonDTO updatePerson(Integer id, PersonDTO personDTO) {

        Person person = personRepository.findPersonById(id);
        if (person == null) {
            throw new RuntimeException("Person not found with id: " + id);
        }

        modelMapper.map(personDTO, person);
        Person updatedPerson = personRepository.save(person);

        return modelMapper.map(updatedPerson, PersonDTO.class);
    }

    public List<Person> showAllPerson(Person person) {
        log.info("All Person found");
        return personRepository.findAllPerson(person);
    }

    public void deletePerson(Integer id) {
        this.personRepository.deleteById(id);
    }

    PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

}


