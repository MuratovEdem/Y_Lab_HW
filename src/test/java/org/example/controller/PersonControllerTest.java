package org.example.controller;

import org.example.DTO.PersonDTO;
import org.example.model.Person;
import org.example.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;

public class PersonControllerTest {

    private PersonService personService = mock(PersonService.class);

    @Test
    void getPersonsTest() {
        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);
        Person person1 = new Person("jsonEmail2", "pass2", "jsonName2");
        person1.setId(2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);

        doReturn(personList).when(personService).getPersons();

        PersonController personController = new PersonController(personService);

        ResponseEntity<List<PersonDTO>> persons = personController.getPersons();

        assertEquals(HttpStatus.OK, persons.getStatusCode());

        assertEquals(1, persons.getBody().get(0).getId());
        assertEquals(2, persons.getBody().get(1).getId());
    }

    @Test
    void getPersonByIdTest() {
        Person person = new Person("jsonEmail", "pass", "jsonName");
        person.setId(1);

        doReturn(Optional.of(person)).when(personService).getById(anyLong());
        PersonController personController = new PersonController(personService);

        ResponseEntity<PersonDTO> personById = personController.getPersonById(1L);

        assertEquals(HttpStatus.OK, personById.getStatusCode());
        assertEquals(1, personById.getBody().getId());
    }

    @Test
    void editPersonDataTest() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1);
        personDTO.setEmail("email");
        personDTO.setName("name");

        doNothing().when(personService).editEmail(anyLong(), any());
        doNothing().when(personService).editName(anyLong(), any());

        PersonController personController = new PersonController(personService);

        ResponseEntity<Void> voidResponseEntity = personController.editPersonData(personDTO);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());
    }

    @Test
    void createTest() {
        Person person = new Person("email", "pass", "name");
        person.setId(1);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1);
        personDTO.setEmail("email");
        personDTO.setName("name");
        personDTO.setPassword("pass");

        doReturn(person).when(personService).create(any());

        PersonController personController = new PersonController(personService);

        ResponseEntity<PersonDTO> personDTOResponseEntity = personController.create(personDTO);

        assertEquals(HttpStatus.CREATED, personDTOResponseEntity.getStatusCode());
        assertEquals(1, personDTOResponseEntity.getBody().getId());
    }

    @Test
    void removeTest() {
        doNothing().when(personService).removeById(anyLong());

        PersonController personController = new PersonController(personService);

        ResponseEntity<Void> voidResponseEntity = personController.removeById(1L);

        assertEquals(HttpStatus.NO_CONTENT, voidResponseEntity.getStatusCode());
    }
}
