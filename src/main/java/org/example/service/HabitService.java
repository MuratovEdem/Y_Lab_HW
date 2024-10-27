package org.example.service;

import org.example.annotations.Logging;
import org.example.controller.ReminderController;
import org.example.model.Habit;
import org.example.repository.HabitRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Logging
public class HabitService {

    private final HabitRepository habitRepository;
    private ReminderController reminderController;

    public HabitService(HabitRepository habitRepository, ReminderController reminderController) {
        this.habitRepository = habitRepository;
        this.reminderController = reminderController;
    }

    public Habit createByPersonId(long personId, Habit habit) {
        reminderController.remindOfHabit(habit, personId);
        return habitRepository.saveByPersonId(personId, habit);
    }

    public List<Habit> getHabitsByPersonId(long personId) {
        return habitRepository.getByPersonId(personId);
    }

    public Optional<Habit> getById(long habitId) {
        return habitRepository.getById(habitId);
    }

    public void update(Habit habit) {
        habitRepository.update(habit);
    }

    public void removeById(long habitId) {
        habitRepository.removeById(habitId);
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

            habitRepository.update(habit);
            habitRepository.addMarkInHistoryExecutionById(habit.getId());
            return true;
        } else if (habit.getNextReminder().isBefore(LocalDate.now())) {
            habit.setCurrentStreak(0);
            habitRepository.update(habit);
        }
        return false;
    }

    public void setReminderController(ReminderController reminderController) {
        this.reminderController = reminderController;
    }
}
