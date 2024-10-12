package org.example;

import org.example.controller.HabitController;
import org.example.controller.PersonController;
import org.example.controller.ReminderController;
import org.example.frontend.MainMenu;
import org.example.model.Habit;
import org.example.model.Person;
import org.example.repository.Repository;
import org.example.service.HabitService;
import org.example.service.PersonService;
import org.example.service.ReminderService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();

        Person person = new Person("email", "1231", "zxc");
        person.setAdmin(true);

//        Habit habit = new Habit("sdf", "vxcvxf", 3);
//        person.addHabit(habit);

        persons.add(person);

        for (int i = 0; i < 5; i++) {
            String email = "email" + i;
            String password = "pass" + i;
            String name = "name" + i;
            Person person1 = new Person(email, password, name);
            persons.add(person1);
        }


        Repository repository = new Repository(persons);

        PersonService personService = new PersonService(repository);
        PersonController personController = new PersonController(personService);

        ReminderService reminderService = new ReminderService();
        ReminderController reminderController = new ReminderController(reminderService);

        HabitService habitService = new HabitService(repository, reminderController);
        HabitController habitController = new HabitController(habitService);

        MainMenu mainMenu = new MainMenu(personController, habitController);

        reminderService.setMainMenu(mainMenu);

        mainMenu.start();

//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();;
//        service.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Пора выполнить привычку!!");
//             }}, 0, 3, TimeUnit.SECONDS);

    }
}