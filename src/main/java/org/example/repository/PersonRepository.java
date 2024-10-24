package org.example.repository;

import org.example.exception.JDBCExceptions;
import org.example.model.Person;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepository implements PersonRepositoryImpl {

    private final Connection connection;

    public PersonRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Person> getAll() {
        String query = "SELECT * FROM model.persons";

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
        }
        return personsList;
    }

    @Override
    public long save(Person person) {
        long resultId = -1;
        String query = "INSERT INTO model.persons (email, password, name, is_admin, is_banned)" +
                "VALUES (?, ?, ?, ?, ?)";
        String querySelect = "SELECT id FROM model.persons WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
            connection.setAutoCommit(false);

            statement.setString(1, person.getEmail());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getName());
            statement.setBoolean(4, person.isAdmin());
            statement.setBoolean(5, person.isBanned());

            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);

            selectStatement.setString(1, person.getEmail());

            ResultSet resultSet = selectStatement.executeQuery();
            resultId = resultSet.getLong("id");

            resultSet.close();
        } catch (SQLException e) {
            try {
                JDBCExceptions.printSQLException(e);
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                JDBCExceptions.printSQLException(ex);
            }
        }
        return resultId;
    }

    @Override
    public Optional<Person> getById(long id) {
        String query = "SELECT * FROM model.persons WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            Person person = getPerson(resultSet);
            resultSet.close();
            return Optional.of(person);
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }

        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        String query = "DELETE FROM model.persons WHERE id = ?";
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
        }
    }

    @Override
    public void editNameById(long id, String newName) {
        String query = "UPDATE model.persons SET name = ? WHERE id = ?";
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
        }
    }

    @Override
    public void editEmailById(long id, String newEmail) {
        String query = "UPDATE model.persons SET email = ? WHERE id = ?";
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
        }
    }

    @Override
    public void editPasswordById(long id, String newPassword) {
        String query = "UPDATE model.persons SET password = ? WHERE id = ?";

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
        }
    }

    @Override
    public void editIsBannedById(long id, boolean isBanned) {
        String query = "UPDATE model.persons SET is_banned = ? WHERE id = ?";

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
