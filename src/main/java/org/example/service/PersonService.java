package org.example.service;

import org.example.frontend.DTO.PersonDTO;
import org.example.model.Person;
import org.example.repository.PersonRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PersonService {
    private final PersonRepositoryImpl personRepository;

    public PersonService(PersonRepositoryImpl personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        return personRepository.getAll();
    }

    public Optional<Person> getById(long personId) {
        return personRepository.getById(personId);
    }

    public void editName(long personId, String newName) {
        personRepository.editNameById(personId, newName);
    }

    public void editEmail(long personId, String newEmail) {
        personRepository.editEmailById(personId, newEmail);
    }

    public void editPassword(long personId, String newPassword) {
        personRepository.editPasswordById(personId, newPassword);
    }

    public long create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getEmail(), personDTO.getPassword(), personDTO.getName());
        return personRepository.save(person);
    }

    public String getPasswordResetCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100, 999));
    }

    public void removeById(long personId) {
        personRepository.removeById(personId);
    }

    public boolean banPerson(Person currentPerson) {
        if (!currentPerson.isAdmin()) {
            personRepository.editIsBannedById(currentPerson.getId(), true);
            return true;
        }
        return false;
    }
}
