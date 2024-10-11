package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private String name;
    private String description;
    private int executionFrequency;
    private int currentStreak = 0;

    private LocalDate dateCreation;
    private LocalDate lastReminder;
    private LocalDate nextReminder;

    private List<LocalDate> historyExecution;


    public Habit(String name, String description, int executionFrequency) {
        this.name = name;
        this.description = description;
        this.executionFrequency = executionFrequency;
        dateCreation = LocalDate.now();
        lastReminder = dateCreation;
        nextReminder = dateCreation.plusDays(executionFrequency);
        historyExecution = new ArrayList<>();
    }

    public void markCompletion() {
        historyExecution.add(LocalDate.now());
        nextReminder = LocalDate.now().plusDays(executionFrequency);
        currentStreak++;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setExecutionFrequency(int executionFrequency) {
        this.executionFrequency = executionFrequency;
        nextReminder = lastReminder.plusDays(this.executionFrequency);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", executionFrequency=" + executionFrequency +
                ", dateCreation=" + dateCreation +
                ", lastReminder=" + lastReminder +
                ", nextReminder=" + nextReminder +
                '}';
    }
}
