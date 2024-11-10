package org.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HabitDTO {

    private long id;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private int executionFrequency;
    private int numberExecutions;
    private int currentStreak;
    @NotNull
    private long personId;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExecutionFrequency(int executionFrequency) {
        this.executionFrequency = executionFrequency;
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
}
