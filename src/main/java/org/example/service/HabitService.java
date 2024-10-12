package org.example.service;

import org.example.controller.ReminderController;
import org.example.frontend.DTO.HabitDTO;
import org.example.model.Habit;
import org.example.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public class HabitService {

    private final Repository repository;
    private final ReminderController reminderController;

    public HabitService(Repository repository, ReminderController reminderController) {
        this.repository = repository;
        this.reminderController = reminderController;
    }

    public void createByPersonId(HabitDTO habitDTO, int personId) {
        Habit habit = new Habit(habitDTO.getName(), habitDTO.getDescription(), habitDTO.getExecutionFrequency());
        repository.saveHabitByPersonId(habit, personId);
        reminderController.remindOfHabit(habit, personId);
    }

    public List<Habit> getHabitsByPerson(int personId) {
        return repository.getHabitsByPerson(personId);
    }

    public void update(HabitDTO habitDTO, Habit habit) {
        habit.setName(habitDTO.getName());
        habit.setDescription(habit.getDescription());
        habit.setExecutionFrequency(habitDTO.getExecutionFrequency());
    }

    public void removeByPersonId(Habit habit, int personId) {
        repository.removeHabitByPersonId(habit, personId);
    }

    public boolean markCompletion(Habit habit) {
        if (habit.getNextReminder().equals(LocalDate.now())) {
            habit.getHistoryExecution().add(LocalDate.now());
            habit.setNextReminder(LocalDate.now().plusDays(habit.getExecutionFrequency()));
            habit.setLastReminder(LocalDate.now());

            int numberExecutions = habit.getNumberExecutions();
            numberExecutions++;
            habit.setNumberExecutions(numberExecutions);

            int currentStreak = habit.getCurrentStreak();
            currentStreak++;

            habit.setCurrentStreak(currentStreak);
            return true;
        } else if (habit.getNextReminder().isBefore(LocalDate.now())) {
            habit.setCurrentStreak(0);
        }
        return false;
    }
}
