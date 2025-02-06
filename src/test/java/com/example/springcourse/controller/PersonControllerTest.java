package com.example.springcourse.controller;

import com.example.springcourse.entity.Person;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

class PersonControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findPersonByIdTest_Success() {

        int id = 1;
        Person person = new Person();

        person.setId(id);
        person.setFirstName("Пенек");

        when(personRepository.findPersonById(id)).thenReturn(person);

        ResponseEntity<Person> response = personController.findPersonById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Пенек", response.getBody().getFirstName());
    }




}