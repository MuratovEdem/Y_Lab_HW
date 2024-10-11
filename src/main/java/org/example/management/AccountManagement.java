package org.example.management;

import org.example.model.Person;

import java.util.Scanner;

public class AccountManagement {

    private Scanner scanner = new Scanner(System.in);
    private Person currentPerson;

    public void accountManagement(Person person) {
        currentPerson = person;
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Редактировать имя");
            System.out.println("2. Редактировать email");
            System.out.println("3. Редактировать пароль");

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    editName();
                    isRunning = false;
                    break;
                case "2":
                    editEmail();
                    isRunning = false;
                    break;
                case "3":
                    editPassword();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Команда не распознана. Попробуйте снова");
                    break;
            }
        }
    }

    private void editName() {
        System.out.println("Введите новое имя:");
        String newName = scanner.nextLine();

        currentPerson.setName(newName);
        System.out.println("Имя успешно изменено");
    }

    private void editEmail() {
        System.out.println("Введите новый email:");
        String newEmail = scanner.nextLine();

        currentPerson.setEmail(newEmail);
        System.out.println("Email успешно изменен");
    }

    private void editPassword() {
        System.out.println("Введите новый пароль:");
        String newPassword = scanner.nextLine();

        currentPerson.setPassword(newPassword);
        System.out.println("Пароль успешно изменен");
    }
}
