package org.example.service;

import org.example.frontend.DTO.PersonDTO;
import org.example.model.Person;
import org.example.repository.Repository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    @Mock
    Repository mockRepository;

    @Test
    void testGetPersons() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        Repository repository = new Repository(persons);
        PersonService personService = new PersonService(repository);

        assertEquals(persons, personService.getPersons());
    }

    @Test
    void testEditName() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        int personId = 0;
        String expectedName = "newName";

        Repository repository = new Repository(persons);
        PersonService personService = new PersonService(repository);

        personService.editName(personId, expectedName);

        assertEquals(expectedName, person.getName());
    }

    @Test
    void testEditEmail() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        int personId = 0;
        String expectedEmail = "newEmail";

        Repository repository = new Repository(persons);
        PersonService personService = new PersonService(repository);

        personService.editEmail(personId, expectedEmail);

        assertEquals(expectedEmail, person.getEmail());
    }

    @Test
    void testEditPassword() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        int personId = 0;
        String expectedPassword = "newPass";

        Repository repository = new Repository(persons);
        PersonService personService = new PersonService(repository);

        personService.editPassword(personId, expectedPassword);

        assertEquals(expectedPassword, person.getPassword());
    }

    @Test
    void testCreate() {
        PersonDTO personDTO = new PersonDTO("emailDTO", "pasDTO", "nameDTO");

        List<Person> persons = new ArrayList<>();
        Repository repository = new Repository(persons);
        PersonService personService = new PersonService(repository);

        int personId = personService.create(personDTO);
        Person actualPerson = repository.getPersonById(personId);

        assertEquals(personDTO.getEmail(), actualPerson.getEmail());
        assertEquals(personDTO.getName(), actualPerson.getName());
        assertEquals(personDTO.getPassword(), actualPerson.getPassword());
    }

    @Test
    void testRemoveByPersonId() {
        Person person = new Person("email", "pass", "name");
        Person person1 = new Person("email1", "pass1", "name1");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person1);
        int personId = 0;

        Repository repository = new Repository(new ArrayList<>(persons));
        PersonService personService = new PersonService(repository);

        persons.remove(personId);

        personService.removeByPersonId(personId);

        assertEquals(persons, personService.getPersons());
    }

    @Test
    void testBanPersonTrue() {
        Person person = new Person("email", "pass", "name");

        PersonService personService = new PersonService(mockRepository);

        assertTrue(personService.banPerson(person));
    }

    @Test
    void testBanPersonFalse() {
        Person person = new Person("email", "pass", "name");
        person.setAdmin(true);

        PersonService personService = new PersonService(mockRepository);

        assertFalse(personService.banPerson(person));
    }

    @Test
    void testDeletePersonTrue() {
        Person person = new Person("email", "pass", "name");
        Person person1 = new Person("email1", "pass1", "name1");
        List<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(person);
        expectedPersons.add(person1);

        Repository repository = new Repository(new ArrayList<>(expectedPersons));
        PersonService personService = new PersonService(repository);

        expectedPersons.remove(person);

        assertTrue(personService.deletePerson(person));
        assertEquals(expectedPersons, personService.getPersons());
    }

    @Test
    void testDeletePersonFalse() {
        Person person = new Person("email", "pass", "name");
        person.setAdmin(true);
        Person person1 = new Person("email1", "pass1", "name1");
        List<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(person);
        expectedPersons.add(person1);

        Repository repository = new Repository(new ArrayList<>(expectedPersons));
        PersonService personService = new PersonService(repository);

        assertFalse(personService.deletePerson(person));
        assertEquals(expectedPersons, personService.getPersons());
    }
}
