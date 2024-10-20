package org.example.controller;

import org.example.model.Habit;
import org.example.service.ReminderService;

public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    public void remindOfHabit(Habit habit, long personId) {
        reminderService.remindOfHabit(habit, personId);
    }
}
