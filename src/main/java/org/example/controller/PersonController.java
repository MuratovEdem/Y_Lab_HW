package org.example.controller;

import org.example.annotations.Logging;
import org.example.model.Person;
import org.example.service.PersonService;

import java.util.List;
import java.util.Optional;

@Logging
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public List<Person> getPersons() {
        return personService.getPersons();
    }

    public Optional<Person> getPersonById(long id) {
        return personService.getById(id);
    }

    public void editName(long personId, String newName) {
        personService.editName(personId, newName);
    }

    public void editEmail(long personId, String newEmail) {
        personService.editEmail(personId, newEmail);
    }

    public void editPassword(long personId, String newPassword) {
        personService.editPassword(personId, newPassword);
    }

    public Person create(Person person) {
        return personService.create(person);
    }

    public String getPasswordResetCode() {
        return personService.getPasswordResetCode();
    }

    public void removeById(long personId) {
        personService.removeById(personId);
    }

    public boolean banPerson(Person currentPerson) {
        return personService.banPerson(currentPerson);
    }
}
