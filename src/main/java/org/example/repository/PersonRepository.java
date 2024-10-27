package org.example.repository;

import org.example.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    /**
     * Получить всех пользователей
     */
    List<Person> getAll();

    /**
     * Сохранить нового пользователя в бд
     */
    Person save(Person person);

    /**
     * Получить пользователя по id
     */
    Optional<Person> getById(long id);

    /**
     * Удалить пользователя по id
     */
    void removeById(long id);

    /**
     * Изменить имя пользователя
     */
    void editNameById(long id, String newName);

    /**
     * Изменить почту пользователя
     */
    void editEmailById(long id, String newEmail);

    /**
     * Изменить пароль пользователя
     */
    void editPasswordById(long id, String newPassword);

    /**
     * Забанить или разбанить пользователя
     */
    void editIsBannedById(long id, boolean isBanned);
}
