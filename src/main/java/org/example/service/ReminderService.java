package org.example.service;

import org.example.frontend.MainMenu;
import org.example.model.Habit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderService {

    private MainMenu mainMenu;

    public void remindOfHabit(Habit habit, int personId) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (mainMenu.getCurrentLoggedPersonId() == personId) {
                    mainMenu.printReminder("Пора выполнить привычку!!! " + habit.getName());
                }
            }}, 0, habit.getExecutionFrequency(), TimeUnit.SECONDS);

    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
}
