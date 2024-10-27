package org.example;

import org.example.controller.HabitController;
import org.example.controller.PersonController;
import org.example.controller.ReminderController;
import org.example.repository.HabitRepository;
import org.example.repository.HabitRepositoryImpl;
import org.example.repository.PersonRepository;
import org.example.repository.PersonRepositoryImpl;
import org.example.service.HabitService;
import org.example.service.PersonService;
import org.example.service.ReminderService;

public class Factory {

    public static HabitController getHabitController() {
        ReminderService reminderService = new ReminderService();
        ReminderController reminderController = new ReminderController(reminderService);
        HabitRepository habitRepository = new HabitRepositoryImpl();
        HabitService habitService = new HabitService(habitRepository, reminderController);
        return new HabitController(habitService);
    }

    public static PersonController getPersonController() {
        PersonRepository personRepository = new PersonRepositoryImpl();
        PersonService personService = new PersonService(personRepository);
        return new PersonController(personService);
    }
}
