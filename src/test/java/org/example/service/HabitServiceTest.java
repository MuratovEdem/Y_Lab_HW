package org.example.service;

import org.example.LiquibaseLoader;
import org.example.controller.ReminderController;
import org.example.frontend.DTO.HabitDTO;
import org.example.model.Habit;
import org.example.model.Person;
import org.example.repository.HabitRepository;
import org.example.repository.HabitRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitServiceTest {
    @Mock
    ReminderController mockReminderController;

    HabitService habitService;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.8-alpine3.18");

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
        LiquibaseLoader liquibaseLoader = new LiquibaseLoader(connection);
        liquibaseLoader.runLiquibase();

        HabitRepositoryImpl habitRepository = new HabitRepository(connection);
        habitService = new HabitService(habitRepository, mockReminderController);
    }

    @Test
    void testCreateByPersonId() {
        int personId = 1;

        HabitDTO habitDTO = new HabitDTO("nameDTO", "descrDTO", 2);

        ReminderService reminderService = Mockito.mock(ReminderService.class);
        ReminderController reminderController = new ReminderController(reminderService);
        habitService.setReminderController(reminderController);

        habitService.createByPersonId(habitDTO, personId);

        List<Habit> habitList = habitService.getHabitsByPersonId(personId);

        assertEquals(1, habitList.size());
    }

    @Test
    void testGetHabitsByPersonId() {
        int personId = 1;

        HabitDTO habitDTO = new HabitDTO("nameDTO", "descrDTO", 2);
        HabitDTO habitDTO1 = new HabitDTO("nameDTO1", "descrDTO1", 3);

        ReminderService reminderService = Mockito.mock(ReminderService.class);
        ReminderController reminderController = new ReminderController(reminderService);
        habitService.setReminderController(reminderController);

        habitService.createByPersonId(habitDTO, personId);
        habitService.createByPersonId(habitDTO1, personId);

        Habit habit = new Habit(habitDTO.getName(), habitDTO.getDescription(), habitDTO.getExecutionFrequency());
        Habit habit1 = new Habit(habitDTO1.getName(), habitDTO1.getDescription(), habitDTO1.getExecutionFrequency());

        List<Habit> habitsExpected = new ArrayList<>();
        habitsExpected.add(habit);
        habitsExpected.add(habit1);

        List<Habit> habitsActual = habitService.getHabitsByPersonId(personId);

        assertEquals(habitsExpected.size(), habitsActual.size());
    }

    @Test
    void testRemoveById() {
        int personId = 1;
        HabitDTO habitDTO = new HabitDTO("nameDTO", "descrDTO", 2);
        HabitDTO habitDTO1 = new HabitDTO("nameDTO1", "descrDTO1", 3);

        ReminderService reminderService = Mockito.mock(ReminderService.class);
        ReminderController reminderController = new ReminderController(reminderService);
        habitService.setReminderController(reminderController);

        habitService.createByPersonId(habitDTO, personId);
        habitService.createByPersonId(habitDTO1, personId);

        habitService.removeById(1);

        assertEquals(1, habitService.getHabitsByPersonId(1).size());
    }

    @Test
    void testUpdate() {
        int personId = 1;
        Habit habit = new Habit("name", "descr", 2);
        HabitDTO habitDTO = new HabitDTO("name1", "descr1", 3);

        habitService.createByPersonId(habitDTO, personId);

        habitService.update(habitDTO, habit);

        assertEquals(habitDTO.getName(), habit.getName());
        assertEquals(habitDTO.getDescription(), habit.getDescription());
        assertEquals(habitDTO.getExecutionFrequency(), habit.getExecutionFrequency());
    }

    @Test
    void testMarkCompletionAllOk() {
        int personId = 1;
        int executionFrequency = 1;
        HabitDTO habitDTO = new HabitDTO("name1", "descr1", executionFrequency);

        ReminderService reminderService = Mockito.mock(ReminderService.class);
        ReminderController reminderController = new ReminderController(reminderService);
        habitService.setReminderController(reminderController);

        habitService.createByPersonId(habitDTO, personId);

        Habit habit = habitService.getById(1).get();

        habit.setNextReminder(LocalDate.now());

        LocalDate expectedNextReminder = LocalDate.now().plusDays(executionFrequency);
        LocalDate expectedLasReminder = LocalDate.now();

        int expectedNumberExecutions = 1;
        int expectedCurrentStreak = 1;

        assertTrue(habitService.markCompletion(habit));

        Habit habitActual = habitService.getById(1).get();

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
