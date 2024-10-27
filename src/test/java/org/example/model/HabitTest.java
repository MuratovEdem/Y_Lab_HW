package org.example.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabitTest {

    @Test
    void testSetExecutionFrequency() {
        int executionFrequency = 2;
        Habit habit = new Habit("someName", "someDescr", executionFrequency);
        LocalDate nextReminderExpected = habit.getLastReminder().plusDays(executionFrequency);

        habit.setExecutionFrequency(executionFrequency);
        assertEquals(nextReminderExpected, habit.getNextReminder());
    }

    @Test
    void testGetPercentageCompletion() {
        int executionFrequency = 2;
        Habit habit = new Habit("someName", "someDescr", executionFrequency);
        habit.setNumberExecutions(3);
        habit.setDateCreation(LocalDate.now().minusDays(10));

        long actual = habit.getPercentageCompletion();
        long expected = 60;

        assertEquals(expected, actual);
    }

}
