package org.example.controller;

import org.example.frontend.DTO.HabitDTO;
import org.example.model.Habit;
import org.example.service.HabitService;

import java.util.List;

public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    public void createByPersonId(HabitDTO habit, int personId) {
        habitService.createByPersonId(habit, personId);
    }

    public List<Habit> getHabitsByPersonId(int personId) {
        return habitService.getHabitsByPerson(personId);
    }

    public void update(HabitDTO habitDTO, Habit habit) {
        habitService.update(habitDTO, habit);
    }

    public void removeByPersonId(Habit habit, int personId) {
        habitService.removeByPersonId(habit, personId);
    }

    public boolean markCompletion(Habit habit) {
        return habitService.markCompletion(habit);
    }
}
