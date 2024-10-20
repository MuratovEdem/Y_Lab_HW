package org.example.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
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
