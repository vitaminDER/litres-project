package com.example.springcourse.service;

import com.example.springcourse.dto.PersonDTO;
import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person savePerson(PersonDTO personDTO) {

        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO can't be NULL!");
        }
        if (personDTO.getAge() < 0) {
            throw new IllegalArgumentException("Person age can't be less 0");
        }

        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());

        return personRepository.save(person);
    }
}


