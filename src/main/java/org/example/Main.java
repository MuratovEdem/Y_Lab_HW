package org.example;

import org.example.controller.HabitController;
import org.example.controller.PersonController;
import org.example.controller.ReminderController;
import org.example.frontend.MainMenu;
import org.example.repository.HabitRepository;
import org.example.repository.HabitRepositoryImpl;
import org.example.repository.PersonRepository;
import org.example.repository.PersonRepositoryImpl;
import org.example.service.HabitService;
import org.example.service.PersonService;
import org.example.service.ReminderService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        Connection connection = dataBaseConnection.getConnection();

        LiquibaseLoader liquibaseLoader = new LiquibaseLoader(connection);

        liquibaseLoader.runLiquibase();

        PersonRepositoryImpl personRepository = new PersonRepository(connection);

        HabitRepositoryImpl habitRepository = new HabitRepository(dataBaseConnection.getConnection());

        PersonService personService = new PersonService(personRepository);
        PersonController personController = new PersonController(personService);

        ReminderService reminderService = new ReminderService();
        ReminderController reminderController = new ReminderController(reminderService);

        HabitService habitService = new HabitService(habitRepository, reminderController);
        HabitController habitController = new HabitController(habitService);

        MainMenu mainMenu = new MainMenu(personController, habitController);

        reminderService.setMainMenu(mainMenu);

        mainMenu.start();

        dataBaseConnection.closeConnection();
    }
}