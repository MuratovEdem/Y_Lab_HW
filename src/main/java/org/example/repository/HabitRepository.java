package org.example.repository;

import org.example.model.Habit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitRepository implements HabitRepositoryImpl{

    private final Connection connection;

    public HabitRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveByPersonId(long id, Habit habit) {
        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO model.habits (name, description, execution_frequency, number_executions," +
                    " current_streak, date_creation, last_reminder, next_reminder, person_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setInt(3, habit.getExecutionFrequency());
            statement.setInt(4, habit.getNumberExecutions());
            statement.setInt(5, habit.getCurrentStreak());
            statement.setDate(6, Date.valueOf(habit.getDateCreation()));
            statement.setDate(7, Date.valueOf(habit.getLastReminder()));
            statement.setDate(8, Date.valueOf(habit.getNextReminder()));
            statement.setLong(9, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Habit> getByPersonId(long personId) {
        List<Habit> habitList = new ArrayList<>();

        try {
            String query = "SELECT * FROM model.habits WHERE person_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);;
            statement.setLong(1, personId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                habitList.add(getHabit(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return habitList;
    }

    @Override
    public Optional<Habit> getById(long id) {
        try {
            String query = "SELECT * FROM model.habits WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);;
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Habit habit = getHabit(resultSet);
            return Optional.of(habit);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        try {
            connection.setAutoCommit(false);
            String query = "DELETE FROM model.habits WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Habit habit) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE model.habits SET name = ?, description = ?, execution_frequency = ?, number_executions = ?, " +
                    "current_streak = ?, date_creation = ?, last_reminder = ?, next_reminder = ? " +
                    "WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setInt(3, habit.getExecutionFrequency());
            statement.setInt(4, habit.getNumberExecutions());
            statement.setInt(5, habit.getCurrentStreak());
            statement.setDate(6, Date.valueOf(habit.getDateCreation()));
            statement.setDate(7, Date.valueOf(habit.getLastReminder()));
            statement.setDate(8, Date.valueOf(habit.getNextReminder()));
            statement.setLong(9, habit.getId());

            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addMarkInHistoryExecutionById(long habitId) {
        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO service.history_execution (date_execution, habit_id)" +
                    "VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setLong(2, habitId);

            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Habit getHabit(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int executionFrequency = resultSet.getInt("execution_frequency");
        int numberExecutions = resultSet.getInt("number_executions");
        int currentStreak = resultSet.getInt("current_streak");
        LocalDate dateCreation = resultSet.getDate("date_creation").toLocalDate();
        LocalDate lastReminder = resultSet.getDate("last_reminder").toLocalDate();
        LocalDate nextReminder = resultSet.getDate("next_reminder").toLocalDate();

        Habit habit = new Habit(name, description, executionFrequency);
        habit.setId(id);
        habit.setNumberExecutions(numberExecutions);
        habit.setCurrentStreak(currentStreak);
        habit.setDateCreation(dateCreation);
        habit.setLastReminder(lastReminder);
        habit.setNextReminder(nextReminder);

        return habit;
    }
}
