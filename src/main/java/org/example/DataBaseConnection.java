package org.example;

import org.example.exception.JDBCExceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    public static Connection getConnection() {
        Connection connection = null;

        File configFile = new File("src\\main\\resources\\application.yml");

        try (FileReader reader = new FileReader(configFile)) {
            Properties properties = new Properties();
            properties.load(reader);

            String url = properties.getProperty("spring.datasource.url");
            String user = properties.getProperty("spring.datasource.username");
            String pass = properties.getProperty("spring.datasource.password");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, pass);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection error");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }
    }
}
