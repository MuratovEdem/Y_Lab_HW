package org.example.frontend.management;

import org.example.controller.HabitController;
import org.example.frontend.DTO.HabitDTO;
import org.example.model.Habit;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HabitManagement {
    private Scanner scanner = new Scanner(System.in);
    private int personId;
    private Habit currentHabit;

    private final HabitController habitController;

    public HabitManagement(HabitController habitController) {
        this.habitController = habitController;
    }

    public void habitManagement(int personId) {
        this.personId = personId;

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Введите номер необходимой операции");
            System.out.println("1. Создать");
            System.out.println("2. Просмотреть данные привычки");
            System.out.println("3. Редактировать");
            System.out.println("4. Удалить");
            System.out.println("5. Отметить выполнение привычки");
            System.out.println("6. Сгенерировать статистику выполнения");
            System.out.println("7. В главное меню");

            String userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1":
                    create();
                    isRunning = false;
                    break;
                case "2":
                    read();
                    isRunning = false;
                    break;
                case "3":
                    update();
                    isRunning = false;
                    break;
                case "4":
                    remove();
                    isRunning = false;
                    break;
                case "5":
                    markCompletion();
                    isRunning = false;
                    break;
                case "6":
                    printHistoryExecution();
                    isRunning = false;
                    break;
                case "7":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Команда не распознана. Попробуйте снова");
                    break;
            }
        }
    }

    private void printAllHabitsAndChooseCurrentOne() {
        List<Habit> habits = habitController.getHabitsByPersonId(personId);
        boolean isRunning = true;
        if (habits.isEmpty()) {
            System.out.println("У вас еще нет привычек");
            return;
        }

        while (isRunning) {
            System.out.println("Введите номер привычки, с которой хотите работать");
            for (int i = 1; i <= habits.size(); i++) {
                System.out.println(i + ". " + habits.get(i-1).getName());
            }
            try {
                int userCommand = Integer.parseInt(scanner.nextLine());
                if (userCommand < 0 || userCommand > habits.size()) {
                    System.out.println("Такой привычки нет");
                }
                else {
                    currentHabit = habits.get(userCommand-1);
                    isRunning = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введено некорректное значение");
            }
        }
    }

    private void create() {
        boolean isRunning = true;

        System.out.println("Введите название привычки");
        String name = scanner.nextLine();

        System.out.println("Введите описание привычки");
        String description = scanner.nextLine();

        int executionFrequency = 0;

        while (isRunning) {
            System.out.println("Введите частоту привычки в днях");
            try {
                executionFrequency = scanner.nextInt();
                if (executionFrequency <= 0) {
                    System.out.println("Значение должно быть положительным");
                } else {
                    isRunning = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Введено некорректное значение");
            }
        }

        HabitDTO habitDTO = new HabitDTO(name, description, executionFrequency);

        habitController.createByPersonId(habitDTO, personId);
    }

    private void read() {
        printAllHabitsAndChooseCurrentOne();
        System.out.println(currentHabit);
    }

    private void update() {
        printAllHabitsAndChooseCurrentOne();

        System.out.println("Введите новое название");
        String name = scanner.nextLine();

        System.out.println("Введите новое описание");
        String description = scanner.nextLine();

        boolean isRunning = true;
        int executionFrequency = 0;

        while (isRunning) {
            System.out.println("Введите новую частоту привычки в днях");
            try {
                executionFrequency = scanner.nextInt();
                if (executionFrequency <= 0) {
                    System.out.println("Значение должно быть положительным");
                } else {
                    isRunning = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Введено некорректное значение");
            }
        }
        HabitDTO habitDTO = new HabitDTO(name, description, executionFrequency);

        habitController.update(habitDTO, currentHabit);

    }

    private void remove() {
        printAllHabitsAndChooseCurrentOne();
        habitController.removeByPersonId(currentHabit, personId);

    }

    private void markCompletion() {
        printAllHabitsAndChooseCurrentOne();

        if (!habitController.markCompletion(currentHabit)) {
            System.out.println("Сегодня не тот день");
        }
    }

    private void printHistoryExecution() {
        List<LocalDate> historyExecution = currentHabit.getHistoryExecution();
        System.out.println("Текущая серия выполнения " + currentHabit.getCurrentStreak());
        System.out.println("Процент выполнения " + currentHabit.getPercentageCompletion());

        for (int i = 0; i < historyExecution.size(); i++) {
            System.out.println(historyExecution.get(i));
        }
    }
}
