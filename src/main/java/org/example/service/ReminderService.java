package org.example.service;

import org.example.model.Habit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderService {

    public void remindOfHabit(Habit habit, long personId) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("Пора выполнить привычку!!! " + habit.getName());

            }}, 0, habit.getExecutionFrequency(), TimeUnit.DAYS);
    }
}
