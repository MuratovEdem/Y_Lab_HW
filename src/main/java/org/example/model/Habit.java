package org.example.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private String name;
    private String description;
    private int executionFrequency;
    private int numberExecutions = 0;
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

    public long getPercentageCompletion() {
        long maxNumberExecutions = Duration.between(LocalDate.now().atStartOfDay(), dateCreation.atStartOfDay()).toDays() / executionFrequency;
        return (numberExecutions * 100L) / (maxNumberExecutions + 1);
    }

    public void setExecutionFrequency(int executionFrequency) {
        this.executionFrequency = executionFrequency;
        nextReminder = lastReminder.plusDays(this.executionFrequency);
    }

    public int getExecutionFrequency() {
        return executionFrequency;
    }

    public int getNumberExecutions() {
        return numberExecutions;
    }

    public LocalDate getNextReminder() {
        return nextReminder;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<LocalDate> getHistoryExecution() {
        return historyExecution;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNextReminder(LocalDate nextReminder) {
        this.nextReminder = nextReminder;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setNumberExecutions(int numberExecutions) {
        this.numberExecutions = numberExecutions;
    }

    public void setDescription(String description) {
        this.description = description;
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
