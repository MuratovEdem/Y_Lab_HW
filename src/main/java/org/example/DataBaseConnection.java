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

    private Connection connection;

    public Connection getConnection() {
        File configFile = new File("src\\main\\resources\\database.properties");

        try (FileReader reader = new FileReader(configFile)) {
            Properties properties = new Properties();
            properties.load(reader);

            String url = properties.getProperty("url");
            String user = properties.getProperty("username");
            String pass = properties.getProperty("password");

            connection = DriverManager.getConnection(url, user, pass);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }
    }
}
