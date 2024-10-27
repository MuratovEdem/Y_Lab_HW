package org.example.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private long id;
    private String name;
    private String description;
    private int executionFrequency;
    private int numberExecutions = 0;
    private int currentStreak = 0;
    private long personId;

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
        long maxNumberExecutions = (Duration.between(dateCreation.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() + 1) / executionFrequency;
        return (numberExecutions * 100L) / maxNumberExecutions;
    }

    public void setExecutionFrequency(int executionFrequency) {
        this.executionFrequency = executionFrequency;
        nextReminder = lastReminder.plusDays(this.executionFrequency);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumberExecutions(int numberExecutions) {
        this.numberExecutions = numberExecutions;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setLastReminder(LocalDate lastReminder) {
        this.lastReminder = lastReminder;
    }

    public void setNextReminder(LocalDate nextReminder) {
        this.nextReminder = nextReminder;
    }

    public void setHistoryExecution(List<LocalDate> historyExecution) {
        this.historyExecution = historyExecution;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getExecutionFrequency() {
        return executionFrequency;
    }

    public int getNumberExecutions() {
        return numberExecutions;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public long getPersonId() {
        return personId;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getLastReminder() {
        return lastReminder;
    }

    public LocalDate getNextReminder() {
        return nextReminder;
    }

    public List<LocalDate> getHistoryExecution() {
        return historyExecution;
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
