package org.example.repository;

import org.example.DataBaseConnection;
import org.example.exception.JDBCExceptions;
import org.example.model.Habit;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HabitRepositoryImpl implements HabitRepository {

    @Override
    public Habit saveByPersonId(long id, Habit habit) {
        Habit createdHabit = null;

        String query = "INSERT INTO model.habits (name, description, execution_frequency, number_executions," +
                " current_streak, date_creation, last_reminder, next_reminder, person_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String querySelect = "SELECT * FROM model.habits WHERE id = ?";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
                connection.setAutoCommit(false);

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

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                long habitId = generatedKeys.getLong(1);

                connection.commit();
                connection.setAutoCommit(true);

                selectStatement.setLong(1, habitId);

                ResultSet resultSet = selectStatement.executeQuery();
                resultSet.next();

                createdHabit = getHabit(resultSet);
                resultSet.close();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    JDBCExceptions.printSQLException(e);
                } catch (SQLException ex) {
                    JDBCExceptions.printSQLException(ex);
                }
            }
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }
        return createdHabit;
    }

    @Override
    public List<Habit> getByPersonId(long personId) {
        List<Habit> habitList = new ArrayList<>();
        String query = "SELECT * FROM model.habits WHERE person_id = ?";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, personId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    habitList.add(getHabit(resultSet));
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }

        return habitList;
    }

    @Override
    public Optional<Habit> getById(long id) {
        String query = "SELECT * FROM model.habits WHERE id = ?";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);

                ResultSet resultSet = statement.executeQuery();
                resultSet.next();

                Habit habit = getHabit(resultSet);

                resultSet.close();

                return Optional.of(habit);
            }
        } catch (SQLException ex) {
            JDBCExceptions.printSQLException(ex);
        }
        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM model.habits WHERE id = ?";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                statement.setLong(1, id);
                statement.execute();

                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    JDBCExceptions.printSQLException(e);
                } catch (SQLException ex) {
                    JDBCExceptions.printSQLException(ex);
                }
            }
        } catch (SQLException ex) {
            JDBCExceptions.printSQLException(ex);
        }
    }

    @Override
    public void update(Habit habit) {
        String query = "UPDATE model.habits SET name = ?, description = ?, execution_frequency = ?, number_executions = ?, " +
                "current_streak = ?, date_creation = ?, last_reminder = ?, next_reminder = ? " +
                "WHERE id = ?";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

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
                try {
                    JDBCExceptions.printSQLException(e);
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    JDBCExceptions.printSQLException(ex);
                }
            }
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }
    }

    @Override
    public void addMarkInHistoryExecutionById(long habitId) {
        String query = "INSERT INTO service.history_execution (date_execution, habit_id)" +
                "VALUES (?, ?)";

        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                statement.setDate(1, Date.valueOf(LocalDate.now()));
                statement.setLong(2, habitId);

                statement.execute();

                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
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
        long personId = resultSet.getLong("person_id");

        Habit habit = new Habit(name, description, executionFrequency);
        habit.setId(id);
        habit.setNumberExecutions(numberExecutions);
        habit.setCurrentStreak(currentStreak);
        habit.setDateCreation(dateCreation);
        habit.setLastReminder(lastReminder);
        habit.setNextReminder(nextReminder);
        habit.setPersonId(personId);

        return habit;
    }
}
