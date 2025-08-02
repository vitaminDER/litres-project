package com.example.springcourse.service;

import com.example.springcourse.dto.common.MessageResponse;
import com.example.springcourse.dto.person.PersonAuthenticationDto;
import com.example.springcourse.dto.person.PersonRegistrationDto;
import com.example.springcourse.entity.Person;
import com.example.springcourse.entity.role.Role;
import com.example.springcourse.repository.PersonRepository;
import com.example.springcourse.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.naming.AuthenticationException;
import java.util.Collections;

@Service
@Data
@AllArgsConstructor
public class PersonAuthenticationService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void userSignup(PersonRegistrationDto personRegistrationDto) {
        if(personRepository.existsByLogin(personRegistrationDto.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with login already exists");
        }
        var person = modelMapper.map(personRegistrationDto, Person.class);
        person.setFirstName("null");
        person.setLastName("null");
        person.setAge(1);
        person.setEmail("null");
        person.setUserName("null");
        person.setLogin(personRegistrationDto.getLogin());
        person.setPassword(passwordEncoder.encode(personRegistrationDto.getPassword()));
        var savedPerson = personRepository.save(person);
        modelMapper.map(savedPerson, PersonAuthenticationDto.class);
    }

    public ResponseEntity<?> userSignin(PersonAuthenticationDto personAuthenticationDto) {
        var userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with Role not found"));

        var person = personRepository.findPersonByLogin(personAuthenticationDto.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if(!person.getPassword().equals(personAuthenticationDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Password is wrong!");
        }

        if (!person.getRoles().contains((userRole))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Please signup!");
        } else {
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                person.getLogin(),
                person.getPassword(),
                Collections.emptyList()
        );
    }
}
