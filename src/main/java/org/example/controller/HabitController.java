package org.example.controller;

import org.example.annotations.Logging;
import org.example.model.Habit;
import org.example.service.HabitService;

import java.util.List;
import java.util.Optional;

@Logging
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    public Habit createByPersonId(long personId, Habit habit) {
        return habitService.createByPersonId(personId, habit);
    }

    public List<Habit> getHabitsByPersonId(long personId) {
        return habitService.getHabitsByPersonId(personId);
    }

    public Optional<Habit> getById(long id) {
        return habitService.getById(id);
    }

    public void update(Habit habit) {
        habitService.update(habit);
    }

    public void removeById(long habitId) {
        habitService.removeById(habitId);
    }

    public boolean markCompletion(Habit habit) {
        return habitService.markCompletion(habit);
    }
}
