package org.example.service;

import org.example.frontend.DTO.PersonDTO;
import org.example.model.Person;
import org.example.repository.Repository;

import java.util.List;
import java.util.Random;

public class PersonService {
    private final Repository repository;

    public PersonService(Repository repository) {
        this.repository = repository;
    }

    public List<Person> getPersons() {
        return repository.getPersons();
    }

    public void editName(int personId, String newName) {
        repository.editName(personId, newName);
    }

    public void editEmail(int personId, String newEmail) {
        repository.editEmail(personId, newEmail);
    }

    public void editPassword(int personId, String newPassword) {
        repository.editPassword(personId, newPassword);
    }

    public int create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getEmail(), personDTO.getPassword(), personDTO.getName());
        return repository.savePerson(person);
    }

    public String getPasswordResetCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100, 999));
    }

    public void removeByPersonId(int personId) {
        repository.removeByPersonId(personId);
    }

    public boolean banPerson(Person currentPerson) {
        if (!currentPerson.isAdmin()) {
            currentPerson.setBanned(true);
            return true;
        }
        return false;
    }

    public boolean deletePerson(Person person) {
        if (!person.isAdmin()) {
            return repository.removePerson(person);
        }
        return false;
    }
}
