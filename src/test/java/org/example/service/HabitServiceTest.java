package org.example.service;

import org.example.LiquibaseLoader;
import org.example.model.Habit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class HabitServiceTest {

    @Autowired
    private HabitService habitService;

    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.8-alpine3.18");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        LiquibaseLoader liquibaseLoader = new LiquibaseLoader();
        liquibaseLoader.runLiquibase();
    }

    @Test
    void testCreateByPersonId() {
        long personId = 1;

        Habit habit = new Habit("name", "descr", 2);

        habitService.createByPersonId(personId, habit);

        List<Habit> habitList = habitService.getHabitsByPersonId(personId);

        assertEquals(4, habitList.size());
    }

    @Test
    void testGetHabitsByPersonId() {
        long personId = 1;

        Habit habit = new Habit("nameDTO", "descrDTO", 2);
        Habit habit1 = new Habit("nameDTO1", "descrDTO1", 3);

        habitService.createByPersonId(personId, habit);
        habitService.createByPersonId(personId, habit1);

        List<Habit> habitsExpected = new ArrayList<>();
        habitsExpected.add(new Habit(habit.getName(), habit.getDescription(), habit.getExecutionFrequency()));
        habitsExpected.add(new Habit(habit1.getName(), habit1.getDescription(), habit1.getExecutionFrequency()));

        List<Habit> habitsActual = habitService.getHabitsByPersonId(personId);

        assertEquals(habitsExpected.size(), habitsActual.size());
    }

    @Test
    void testRemoveById() {
        long personId = 1;
        Habit habit = new Habit("nameDTO", "descrDTO", 2);
        Habit habit1 = new Habit("nameDTO1", "descrDTO1", 3);

        habitService.createByPersonId(personId, habit);
        habitService.createByPersonId(personId, habit1);

        habitService.removeById(1L);

        assertEquals(5, habitService.getHabitsByPersonId(1L).size());
    }

    @Test
    void testUpdate() {
        long personId = 1;
        Habit habitActual = new Habit("name", "descr", 2);
        Habit habitExpected = new Habit("name1", "descr1", 3);

        habitService.createByPersonId(personId, habitExpected);

        habitService.update(habitExpected);

        assertEquals(habitExpected.getName(), habitActual.getName());
        assertEquals(habitExpected.getDescription(), habitActual.getDescription());
        assertEquals(habitExpected.getExecutionFrequency(), habitActual.getExecutionFrequency());
    }

    @Test
    void testMarkCompletionAllOk() {
        long personId = 1;
        int executionFrequency = 1;
        Habit habitExpected = new Habit("name1", "descr1", executionFrequency);

        habitService.createByPersonId(personId, habitExpected);

        Habit habit = habitService.getById(1L).get();

        habit.setNextReminder(LocalDate.now());

        LocalDate expectedNextReminder = LocalDate.now().plusDays(executionFrequency);
        LocalDate expectedLasReminder = LocalDate.now();

        int expectedNumberExecutions = 1;
        int expectedCurrentStreak = 1;

        assertTrue(habitService.markCompletion(habit));

        Habit habitActual = habitService.getById(1L).get();

        assertEquals(expectedNextReminder, habitActual.getNextReminder());
        assertEquals(expectedLasReminder, habitActual.getLastReminder());
        assertEquals(expectedNumberExecutions, habitActual.getNumberExecutions());
        assertEquals(expectedCurrentStreak, habitActual.getCurrentStreak());
    }

    @Test
    void testMarkCompletionFalse() {
        int executionFrequency = 1;
        Habit habit = new Habit("name", "desr", executionFrequency);
        habit.setNextReminder(LocalDate.now().plusDays(1));

        assertFalse(habitService.markCompletion(habit));
    }
}
