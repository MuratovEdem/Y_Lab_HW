package org.example;

import org.example.management.AccountManagement;
import org.example.management.HabitsManagement;
import org.example.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Start {
    private Scanner scanner = new Scanner(System.in);
    private List<Person> personsList = new ArrayList<>();
    private Person currentPerson;
    private AccountManagement accountManagement = new AccountManagement();
    private HabitsManagement habitsManagement = new HabitsManagement();


    public void start() {
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

    private void menu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Управление аккаунтом");
            System.out.println("2. Управление привычками");
            System.out.println("3. Удалить аккаунт");

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    accountManagement.accountManagement(currentPerson);
                    isRunning = false;
                    break;
                case "2":
                    habitsManagement.habitsManagement();
                    isRunning = false;
                    break;
                case "3":
                    personsList.remove(currentPerson);
                    System.out.println("Аккаунт успешно удален");
                    start();
                    isRunning = false;
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
                    currentPerson = personsList.get(i);
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

        Person person = new Person(email, password, name);

        personsList.add(person);

        currentPerson = person;
        System.out.println("Регистрация прошла успешно");
        System.out.println("Вы вошли в систему");
    }

    private void resetPassword() {
        Person person = null;
        String email;
        boolean isRunning = true;
        do {
            System.out.println("Введите ваш email:");
            email = scanner.nextLine();

            for (int i = 0; i < personsList.size(); i++) {
                if (personsList.get(i).getEmail().equals(email)) {
                    person = personsList.get(i);
                    isRunning = false;
                    break;
                }
            }
        }
        while (isRunning);
        isRunning = true;

        do {
            System.out.println("На ваш 'email' отправлен код подтверждения");
            String code = person.getPasswordResetCode();
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

        person.setPassword(newPassword);
        currentPerson = person;
        System.out.println("Пароль успешно изменен");
        System.out.println("Вы вошли в систему");
    }

}
