package org.example.repository;

import org.example.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryImpl {
    List<Person> getAll();

    long save(Person person);

    Optional<Person> getById(long id);

    void removeById(long id);

    void editNameById(long id, String newName);

    void editEmailById(long id, String newEmail);

    void editPasswordById(long id, String newPassword);

    void editIsBannedById(long id, boolean isBanned);
}
