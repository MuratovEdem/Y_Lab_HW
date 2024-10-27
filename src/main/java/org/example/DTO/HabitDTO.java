package org.example.DTO;

public class HabitDTO {
    private long id;
    private String name;
    private String description;
    private int executionFrequency;
    private int numberExecutions;
    private int currentStreak;
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
