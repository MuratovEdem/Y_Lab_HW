package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String email;
    private String password;
    private String name;
    private boolean isAdmin;
    private boolean isBanned;
    private List<Habit> habits = new ArrayList<>();

    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        isAdmin = false;
        isBanned = false;
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public boolean isAdmin() {
        return isAdmin;
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

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", isBanned=" + isBanned +
                '}';
    }
}
