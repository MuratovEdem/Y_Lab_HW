package org.example.frontend.management;

import org.example.controller.HabitController;
import org.example.controller.PersonController;
import org.example.model.Habit;
import org.example.model.Person;

import java.util.List;
import java.util.Scanner;

public class AdminManagement {
    private final PersonController personController;
    private final HabitController habitController;

    private Scanner scanner = new Scanner(System.in);
    private Person currentPerson;

    public AdminManagement(PersonController personController, HabitController habitController) {
        this.personController = personController;
        this.habitController = habitController;
    }

    public void chooseOnePerson() {
        List<Person> persons = personController.getPersons();
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Введите номер пользователя, с которым хотите работать");
            for (int i = 1; i <= persons.size(); i++) {
                System.out.println(i + ". " + persons.get(i-1));
            }
            try {
                int userCommand = Integer.parseInt(scanner.nextLine());
                if (userCommand < 0 || userCommand > persons.size()) {
                    System.out.println("Такого пользователя нет");
                }
                else {
                    currentPerson = persons.get(userCommand-1);
                    isRunning = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введено некорректное значение");
            }
        }
        adminManagement();
    }

    private void adminManagement() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Просмотреть все привычки");
            System.out.println("2. Заблокирвать пользователя");
            System.out.println("3. Удалить пользователя");

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    checkHabits();
                    isRunning = false;
                    break;
                case "2":
                    banPerson();
                    isRunning = false;
                    break;
                case "3":
                    deletePerson();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Команда не распознана. Попробуйте снова");
                    break;
            }
        }
    }

    private void checkHabits() {
        List<Habit> habits = habitController.getHabitsByPersonId(currentPerson.getId());
        for (int i = 0; i < habits.size(); i++) {
            System.out.println(habits.get(i));
        }
    }

    private void banPerson() {
        if (personController.banPerson(currentPerson)){
            System.out.println("Пользователь успешно забанен");
        }
        else {
            System.out.println("Ошибка. Данный пользователь является администратором");
        }
    }

    private void deletePerson() {
        if (!currentPerson.isAdmin()) {
            personController.removeById(currentPerson.getId());
            System.out.println("Пользователь успешно удален");
        }
        else {
            System.out.println("Ошибка. Данный пользователя нельзя удалить");
        }
    }
}
