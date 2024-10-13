package org.example.frontend.management;

import org.example.controller.PersonController;

import java.util.Scanner;

public class PersonManagement {

    private Scanner scanner = new Scanner(System.in);
    private int personId;
    private final PersonController personController;

    public PersonManagement(PersonController personController) {
        this.personController = personController;
    }

    public void personManagement(int personId) {
        this.personId = personId;
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

        personController.editName(personId, newName);
        System.out.println("Имя успешно изменено");
    }

    private void editEmail() {
        System.out.println("Введите новый email:");
        String newEmail = scanner.nextLine();

        personController.editEmail(personId, newEmail);
        System.out.println("Email успешно изменен");
    }

    private void editPassword() {
        System.out.println("Введите новый пароль:");
        String newPassword = scanner.nextLine();

        personController.editPassword(personId, newPassword);
        System.out.println("Пароль успешно изменен");
    }
}
