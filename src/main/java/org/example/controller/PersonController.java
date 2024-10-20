package org.example.controller;

import org.example.frontend.DTO.PersonDTO;
import org.example.model.Person;
import org.example.service.PersonService;

import java.util.List;

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public List<Person> getPersons() {
        return personService.getPersons();
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

    public long create(PersonDTO personDTO) {
        return personService.create(personDTO);
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
