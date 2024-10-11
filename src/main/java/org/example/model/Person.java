package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person {
    private String email;
    private String password;
    private String name;
    private List<Habit> habits = new ArrayList<>();

    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getPasswordResetCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100, 999));
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void removeHabit(Habit habit) {
        habits.remove(habit);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
