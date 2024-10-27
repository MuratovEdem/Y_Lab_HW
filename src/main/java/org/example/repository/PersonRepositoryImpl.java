package org.example.repository;

import org.example.DataBaseConnection;
import org.example.exception.JDBCExceptions;
import org.example.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public List<Person> getAll() {
        String query = "SELECT * FROM model.persons";
        Connection connection = DataBaseConnection.getConnection();

        List<Person> personsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = getPerson(resultSet);

                personsList.add(person);
            }

            resultSet.close();
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
        return personsList;
    }

    @Override
    public Person save(Person person) {
        Person createdPerson = null;

        String query = "INSERT INTO model.persons (email, password, name, is_admin, is_banned)" +
                "VALUES (?, ?, ?, ?, ?)";
        String querySelect = "SELECT * FROM model.persons WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
            connection.setAutoCommit(false);

            statement.setString(1, person.getEmail());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getName());
            statement.setBoolean(4, person.isAdmin());
            statement.setBoolean(5, person.isBanned());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            long personId = generatedKeys.getLong(1);

            connection.commit();
            connection.setAutoCommit(true);

            selectStatement.setLong(1, personId);

            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();

            createdPerson = getPerson(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            try {
                JDBCExceptions.printSQLException(e);
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                JDBCExceptions.printSQLException(ex);
            }
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
        return createdPerson;
    }

    @Override
    public Optional<Person> getById(long id) {
        String query = "SELECT * FROM model.persons WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Person person = getPerson(resultSet);
            resultSet.close();
            return Optional.of(person);
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        } finally {
            DataBaseConnection.closeConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM model.persons WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)){
            connection.setAutoCommit(false);

            statement.setLong(1, id);
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
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void editNameById(long id, String newName) {
        String query = "UPDATE model.persons SET name = ? WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            statement.setString(1, newName);
            statement.setLong(2, id);
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
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void editEmailById(long id, String newEmail) {
        String query = "UPDATE model.persons SET email = ? WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)){
            connection.setAutoCommit(false);

            statement.setString(1, newEmail);
            statement.setLong(2, id);
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
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void editPasswordById(long id, String newPassword) {
        String query = "UPDATE model.persons SET password = ? WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            statement.setString(1, newPassword);
            statement.setLong(2, id);
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
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
    }

    @Override
    public void editIsBannedById(long id, boolean isBanned) {
        String query = "UPDATE model.persons SET is_banned = ? WHERE id = ?";
        Connection connection = DataBaseConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);

            statement.setBoolean(1, isBanned);
            statement.setLong(2, id);
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
        } finally {
            DataBaseConnection.closeConnection(connection);
        }
    }

    private Person getPerson(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        boolean isAdmin = resultSet.getBoolean("is_admin");
        boolean isBanned = resultSet.getBoolean("is_banned");

        Person person = new Person(email, password, name);
        person.setId(id);
        person.setAdmin(isAdmin);
        person.setBanned(isBanned);

        return person;
    }
}
