//package org.example.service;
//
//import org.example.LiquibaseLoader;
//import org.example.model.Habit;
//import org.example.repository.HabitRepositoryImpl;
//import org.example.repository.HabitRepository;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.testcontainers.containers.PostgreSQLContainer;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class HabitServiceTest {
//    @Mock
//    ReminderService mockReminderService;
//
//    HabitService habitService;
//
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.8-alpine3.18");
//
//    @BeforeAll
//    static void beforeAll() {
//        postgreSQLContainer.start();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        postgreSQLContainer.stop();
//    }
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
//                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
//        LiquibaseLoader liquibaseLoader = new LiquibaseLoader(connection);
//        liquibaseLoader.runLiquibase();
//
//        HabitRepository habitRepository = new HabitRepositoryImpl();
//        habitService = new HabitService(habitRepository, mockReminderService);
//    }
//
//    @Test
//    void testCreateByPersonId() {
//        int personId = 1;
//
//        Habit habit = new Habit("name", "descr", 2);
//
//        ReminderService reminderService = Mockito.mock(ReminderService.class);
//        habitService.setReminderService(reminderService);
//
//        habitService.createByPersonId(personId, habit);
//
//        List<Habit> habitList = habitService.getHabitsByPersonId(personId);
//
//        assertEquals(1, habitList.size());
//    }
//
//    @Test
//    void testGetHabitsByPersonId() {
//        int personId = 1;
//
//        Habit habit = new Habit("nameDTO", "descrDTO", 2);
//        Habit habit1 = new Habit("nameDTO1", "descrDTO1", 3);
//
//        ReminderService reminderService = Mockito.mock(ReminderService.class);
//        habitService.setReminderService(reminderService);
//
//        habitService.createByPersonId(personId, habit);
//        habitService.createByPersonId(personId, habit1);
//
//        List<Habit> habitsExpected = new ArrayList<>();
//        habitsExpected.add(new Habit(habit.getName(), habit.getDescription(), habit.getExecutionFrequency()));
//        habitsExpected.add(new Habit(habit1.getName(), habit1.getDescription(), habit1.getExecutionFrequency()));
//
//        List<Habit> habitsActual = habitService.getHabitsByPersonId(personId);
//
//        assertEquals(habitsExpected.size(), habitsActual.size());
//    }
//
//    @Test
//    void testRemoveById() {
//        int personId = 1;
//        Habit habit = new Habit("nameDTO", "descrDTO", 2);
//        Habit habit1 = new Habit("nameDTO1", "descrDTO1", 3);
//
//        ReminderService reminderService = Mockito.mock(ReminderService.class);
//        habitService.setReminderService(reminderService);
//
//        habitService.createByPersonId(personId, habit);
//        habitService.createByPersonId(personId, habit1);
//
//        habitService.removeById(1);
//
//        assertEquals(1, habitService.getHabitsByPersonId(1).size());
//    }
//
//    @Test
//    void testUpdate() {
//        int personId = 1;
//        Habit habitActual = new Habit("name", "descr", 2);
//        Habit habitExpected = new Habit("name1", "descr1", 3);
//
//        habitService.createByPersonId(personId, habitExpected);
//
//        habitService.update(habitExpected);
//
//        assertEquals(habitExpected.getName(), habitActual.getName());
//        assertEquals(habitExpected.getDescription(), habitActual.getDescription());
//        assertEquals(habitExpected.getExecutionFrequency(), habitActual.getExecutionFrequency());
//    }
//
////    @Test
////    void testMarkCompletionAllOk() {
////        int personId = 1;
////        int executionFrequency = 1;
////        Habit habitExpected = new Habit("name1", "descr1", executionFrequency);
////
////        ReminderService reminderService = Mockito.mock(ReminderService.class);
////        habitService.setReminderService(reminderService);
////
////        habitService.createByPersonId(personId, habitExpected);
////
////        Habit habit = habitService.getById(1).get();
////
////        habit.setNextReminder(LocalDate.now());
////
////        LocalDate expectedNextReminder = LocalDate.now().plusDays(executionFrequency);
////        LocalDate expectedLasReminder = LocalDate.now();
////
////        int expectedNumberExecutions = 1;
////        int expectedCurrentStreak = 1;
////
////        assertTrue(habitService.markCompletion(habit));
////
////        Habit habitActual = habitService.getById(1).get();
////
////        assertEquals(expectedNextReminder, habitActual.getNextReminder());
////        assertEquals(expectedLasReminder, habitActual.getLastReminder());
////        assertEquals(expectedNumberExecutions, habitActual.getNumberExecutions());
////        assertEquals(expectedCurrentStreak, habitActual.getCurrentStreak());
////    }
////
////    @Test
////    void testMarkCompletionFalse() {
////        int executionFrequency = 1;
////        Habit habit = new Habit("name", "desr", executionFrequency);
////        habit.setNextReminder(LocalDate.now().plusDays(1));
////
////        assertFalse(habitService.markCompletion(habit));
////    }
//}
