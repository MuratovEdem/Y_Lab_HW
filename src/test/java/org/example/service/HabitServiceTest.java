package org.example.service;

import org.example.controller.ReminderController;
import org.example.frontend.DTO.HabitDTO;
import org.example.model.Habit;
import org.example.model.Person;
import org.example.repository.Repository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitServiceTest {

    @Mock
    Repository mockRepository;

    @Mock
    ReminderController mockReminderController;

    @Test
    void testCreateByPersonId() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        int personId = 0;

        HabitDTO habitDTO = new HabitDTO("namelDTO", "descrDTO", 2);

        ReminderService reminderService = Mockito.mock(ReminderService.class);
        ReminderController reminderController = new ReminderController(reminderService);

        Repository repository = new Repository(persons);
        HabitService personService = new HabitService(repository, reminderController);

        Habit actualHabit = personService.createByPersonId(habitDTO, personId);

        assertEquals(habitDTO.getName(), actualHabit.getName());
        assertEquals(habitDTO.getDescription(), actualHabit.getDescription());
        assertEquals(habitDTO.getExecutionFrequency(), actualHabit.getExecutionFrequency());
    }

    @Test
    void testGetHabitsByPersonId() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        Repository repository = new Repository(persons);
        HabitService habitService = new HabitService(repository, mockReminderController);

        int personId = 0;

        Habit habit = new Habit("name", "descr", 2);
        Habit habit1 = new Habit("name1", "descr1", 3);

        repository.saveHabitByPersonId(habit, personId);
        repository.saveHabitByPersonId(habit1, personId);

        List<Habit> habitsExpected = new ArrayList<>();
        habitsExpected.add(habit);
        habitsExpected.add(habit1);

        List<Habit> habitsActual = habitService.getHabitsByPersonId(personId);

        assertEquals(habitsExpected, habitsActual);
    }

    @Test
    void testRemoveByPersonId() {
        Person person = new Person("email", "pass", "name");
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        Repository repository = new Repository(persons);
        HabitService habitService = new HabitService(repository, mockReminderController);

        int personId = 0;

        Habit habit = new Habit("name", "descr", 2);
        Habit habit1 = new Habit("name1", "descr1", 3);

        repository.saveHabitByPersonId(habit, personId);
        repository.saveHabitByPersonId(habit1, personId);

        List<Habit> habitsExpected = new ArrayList<>();
        habitsExpected.add(habit1);

        habitService.removeByPersonId(habit, personId);

        assertEquals(habitsExpected, habitService.getHabitsByPersonId(personId));
    }

    @Test
    void testUpdate() {
        Habit habit = new Habit("name", "descr", 2);
        HabitDTO habitDTO = new HabitDTO("name1", "descr1", 3);

        HabitService habitService = new HabitService(mockRepository, mockReminderController);

        habitService.update(habitDTO, habit);

        assertEquals(habitDTO.getName(), habit.getName());
        assertEquals(habitDTO.getDescription(), habit.getDescription());
        assertEquals(habitDTO.getExecutionFrequency(), habit.getExecutionFrequency());
    }

    @Test
    void testMarkCompletionAllOk() {
        HabitService habitService = new HabitService(mockRepository, mockReminderController);

        int executionFrequency = 1;
        Habit habit = new Habit("name", "desr", executionFrequency);
        habit.setNextReminder(LocalDate.now());

        List<LocalDate> expectedHistoryExecution = new ArrayList<>();
        expectedHistoryExecution.add(LocalDate.now());

        LocalDate expectedNextReminder = LocalDate.now().plusDays(executionFrequency);
        LocalDate expectedLasReminder = LocalDate.now();

        int expectedNumberExecutions = 1;
        int expectedCurrentStreak = 1;

        assertTrue(habitService.markCompletion(habit));

        assertEquals(expectedHistoryExecution, habit.getHistoryExecution());
        assertEquals(expectedNextReminder, habit.getNextReminder());
        assertEquals(expectedLasReminder, habit.getLastReminder());
        assertEquals(expectedNumberExecutions, habit.getNumberExecutions());
        assertEquals(expectedCurrentStreak, habit.getCurrentStreak());
    }

    @Test
    void testMarkCompletionFalse() {
        HabitService habitService = new HabitService(mockRepository, mockReminderController);
        int executionFrequency = 1;
        Habit habit = new Habit("name", "desr", executionFrequency);
        habit.setNextReminder(LocalDate.now().plusDays(1));

        assertFalse(habitService.markCompletion(habit));
    }
}
