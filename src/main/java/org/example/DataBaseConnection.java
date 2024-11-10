package org.example;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() {
        try {
            File configFile = ResourceUtils.getFile("classpath:application.yml");

            try (FileReader reader = new FileReader(configFile)) {
                Properties properties = new Properties();
                properties.load(reader);

                String url = properties.getProperty("url");
                String user = properties.getProperty("username");
                String pass = properties.getProperty("password");

                Class.forName(properties.getProperty("driver-class-name"));
                connection = DriverManager.getConnection(url, user, pass);
            } catch (IOException e) {
                System.err.println("I/O error");
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("Connection error");
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFound error");
        }

        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
