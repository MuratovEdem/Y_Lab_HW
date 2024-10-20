package org.example.repository;

import org.example.model.Habit;

import java.util.List;
import java.util.Optional;

public interface HabitRepositoryImpl {

    void saveByPersonId(long personId, Habit habit);

    List<Habit> getByPersonId(long personId);

    Optional<Habit> getById(long id);

    void removeById(long id);

    void update(Habit habit);

    void addMarkInHistoryExecutionById(long id);
}
