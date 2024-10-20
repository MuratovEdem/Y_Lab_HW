package org.example.service;

import org.example.LiquibaseLoader;
import org.example.frontend.DTO.PersonDTO;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    PersonService personService;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.8-alpine3.18");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        LiquibaseLoader liquibaseLoader = new LiquibaseLoader(connection);
        liquibaseLoader.runLiquibase();

        PersonRepositoryImpl personRepository = new PersonRepository(connection);
        personService = new PersonService(personRepository);
    }

    @Test
    void testGetPersons() {
        int expectedSize = 3;
        List<Person> persons = personService.getPersons();

        assertEquals(expectedSize, persons.size());
    }

    @Test
    void testEditName() {
        long personId = 1;

        String expectedName = "newName";
        personService.editName(1, expectedName);

        Person person = personService.getById(personId).get();
        assertEquals(expectedName, person.getName());
    }

    @Test
    void testEditEmail() {
        long personId = 1;

        String expectedEmail = "newName";
        personService.editName(1, expectedEmail);

        Person person = personService.getById(personId).get();
        assertEquals(expectedEmail, person.getEmail());
    }

    @Test
    void testEditPassword() {
        long personId = 1;

        String expectedPassword = "newPassword";
        personService.editName(1, expectedPassword);

        Person person = personService.getById(personId).get();
        assertEquals(expectedPassword, person.getPassword());
    }

    @Test
    void testCreate() {
        PersonDTO personDTO = new PersonDTO("emailDTO", "pasDTO", "nameDTO");

        personService.create(personDTO);

        assertEquals(4, personService.getPersons().size());
    }

    @Test
    void testRemoveById() {
        int personId = 1;

        personService.removeById(personId);

        Optional<Person> personOpt = personService.getById(personId);

        assertTrue(personOpt.isEmpty());
    }

    @Test
    void testBanPersonTrue() {
        long personId = 1;
        Optional<Person> personOpt = personService.getById(personId);

        Person person = personOpt.get();

        personService.banPerson(person);

        assertTrue(personService.getById(personId).get().isBanned());
    }

    @Test
    void testBanPersonFalse() {
        long personId = 2;
        Optional<Person> personOpt = personService.getById(personId);

        Person person = personOpt.get();

        personService.banPerson(person);

        assertFalse(personService.getById(personId).get().isBanned());
    }
}
