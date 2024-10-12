package org.example.frontend.DTO;

public class HabitDTO {
    private String name;
    private String description;
    private int executionFrequency;

    public HabitDTO(String name, String description, int executionFrequency) {
        this.name = name;
        this.description = description;
        this.executionFrequency = executionFrequency;
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
}
