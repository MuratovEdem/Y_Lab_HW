package org.example.repository;

import org.example.model.Habit;

import java.util.List;
import java.util.Optional;

public interface HabitRepositoryImpl {

    /**
     * Сохранить привычку для конкретного пользователя
     */
    void saveByPersonId(long personId, Habit habit);

    /**
     * Получить привычку по id пользователя
     */
    List<Habit> getByPersonId(long personId);

    /**
     * Получить привычку по id привычки
     */
    Optional<Habit> getById(long id);

    /**
     * Удалить привычку по id привычки
     */
    void removeById(long id);

    /**
     * Обновить данные привычки
     */
    void update(Habit habit);

    /**
     * Добавить запись о выполнении привычки в историю
     */
    void addMarkInHistoryExecutionById(long id);
}
