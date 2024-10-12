package org.example.frontend;

import org.example.controller.HabitController;
import org.example.controller.PersonController;
import org.example.frontend.DTO.PersonDTO;
import org.example.frontend.management.AdminManagement;
import org.example.frontend.management.HabitManagement;
import org.example.frontend.management.PersonManagement;
import org.example.model.Person;

import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private final PersonController personController;
    private final HabitController habitController;

    private Scanner scanner = new Scanner(System.in);
    private List<Person> personsList;
    private int personId;
    private PersonManagement personManagement;
    private HabitManagement habitManagement;
    private AdminManagement adminManagement;

    public MainMenu(PersonController personController, HabitController habitController) {
        this.personController = personController;
        this.habitController = habitController;

        personManagement = new PersonManagement(personController);
        habitManagement = new HabitManagement(habitController);
        adminManagement = new AdminManagement(personController);
    }

    public void start() {
        personsList = personController.getPersons();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Зарегистрироваться");
            System.out.println("2. Войти в систему");
            System.out.println("3. Сбросить пароль");

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    registration();
                    isRunning = false;
                    break;
                case "2":
                    logInToSystem();
                    isRunning = false;
                    break;
                case "3":
                    resetPassword();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Команда не распознана. Попробуйте снова");
                    break;
            }
        }

        menu();
    }

    public void printReminder(String reminder) {
        System.out.println(reminder);
    }

    public int getCurrentLoggedPersonId() {
        return personId;
    }

    private void menu() {
        personsList = personController.getPersons();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Управление аккаунтом");
            System.out.println("2. Управление привычками");
            System.out.println("3. Удалить аккаунт");
            System.out.println("4. Выйти из аккаунта");
            System.out.println("5. Закрыть");

            if (personsList.get(personId).isAdmin()) {
                System.out.println("6. Отобразить список всех пользователей");
            }

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    personManagement.personManagement(personId);
                    break;
                case "2":
                    habitManagement.habitManagement(personId);
                    break;
                case "3":
                    personController.removeByPersonId(personId);
                    System.out.println("Аккаунт успешно удален");
                    start();
                    break;
                case "4":
                    personId = -1;
                    start();
                    break;
                case "5":
                    isRunning = false;
                    break;
                case "6":
                    if (personsList.get(personId).isAdmin()) {
                        adminManagement.chooseOnePerson();
                    }
                    break;
                default:
                    System.out.println("Команда не распознана. Попробуйте снова");
                    break;
            }
        }
    }

    private void logInToSystem() {
        boolean isCorrectData = false;
        do {
            System.out.println("Введите ваш email:");
            String email = scanner.nextLine();

            System.out.println("Введите ваш пароль:");
            String password = scanner.nextLine();

            for (int i = 0; i < personsList.size(); i++) {
                if (personsList.get(i).getEmail().equals(email) && personsList.get(i).getPassword().equals(password)) {
                    personId = i;
                    System.out.println("Вы вошли в систему");
                    isCorrectData = true;
                    break;
                }
            }
            if (!isCorrectData) {
                System.out.println("Неправильный пароль или email. Попробуйте снова");
            }
        }
        while (!isCorrectData);
    }

    private void registration() {
        boolean isCorrectData = true;
        String email;
        String password;
        do {
            System.out.println("Введите ваш email:");
            email = scanner.nextLine();

            System.out.println("Введите ваш пароль:");
            password = scanner.nextLine();

            for (int i = 0; i < personsList.size(); i++) {
                if (personsList.get(i).getEmail().equals(email)) {
                    System.out.println("Данный email уже используются. Попробуйте снова");
                    isCorrectData = false;
                    break;
                }
            }
        }
        while (!isCorrectData);

        System.out.println("Введите ваше имя:");
        String name = scanner.nextLine();

        PersonDTO personDTO = new PersonDTO(email, password, name);

        personId = personController.create(personDTO);

        System.out.println("Регистрация прошла успешно");
        System.out.println("Вы вошли в систему");
    }

    private void resetPassword() {
        int personId = 0;
        String email;
        boolean isRunning = true;
        do {
            System.out.println("Введите ваш email:");
            email = scanner.nextLine();

            for (int i = 0; i < personsList.size(); i++) {
                if (personsList.get(i).getEmail().equals(email)) {
                    personId = i;
                    isRunning = false;
                    break;
                }
            }
        }
        while (isRunning);
        isRunning = true;

        do {
            System.out.println("На ваш 'email' отправлен код подтверждения");
            String code = personController.getPasswordResetCode();
            System.out.println("Код подтверждения: " + code);
            System.out.println("Введите код подтверждения");

            String userCode = scanner.nextLine();

            if (code.equals(userCode)) {
                isRunning = false;
            }
            else {
                System.out.println("Неправильный код подтверждения. Попробуйте снова");
            }
        }
        while (isRunning);

        System.out.println("Введите новый пароль: ");
        String newPassword = scanner.nextLine();

        personController.editPassword(personId, newPassword);
        this.personId = personId;

        System.out.println("Пароль успешно изменен");
        System.out.println("Вы вошли в систему");
    }

}
