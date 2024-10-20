package org.example.repository;

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
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Person person = getPerson(resultSet);

                personsList.add(person);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personsList;
    }

    @Override
    public long save(Person person) {
        long resultId = -1;
        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO model.persons (email, password, name, is_admin, is_banned)" +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, person.getEmail());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getName());
            statement.setBoolean(4, person.isAdmin());
            statement.setBoolean(5, person.isBanned());

            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);

            query = "SELECT id FROM model.persons WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, person.getEmail());
            ResultSet resultSet = statement.executeQuery();
            resultId = resultSet.getLong("id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultId;
    }

    @Override
    public Optional<Person> getById(long id) {
        try {
            String query = "SELECT * FROM model.persons WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);;
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            Person person = getPerson(resultSet);
            return Optional.of(person);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void removeById(long id) {
        try {
            connection.setAutoCommit(false);
            String query = "DELETE FROM model.persons WHERE id = ?";

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
    public void editNameById(long id, String newName) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE model.persons SET name = ? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setLong(2, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editEmailById(long id, String newEmail) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE model.persons SET email = ? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newEmail);
            statement.setLong(2, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editPasswordById(long id, String newPassword) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE model.persons SET password = ? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setLong(2, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editIsBannedById(long id, boolean isBanned) {
        try {
            connection.setAutoCommit(false);
            String query = "UPDATE model.persons SET is_banned = ? WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, isBanned);
            statement.setLong(2, id);
            statement.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
