<<<<<<< HEAD
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

        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());

        return personRepository.save(person);
    }
}


