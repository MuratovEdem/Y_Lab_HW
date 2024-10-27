package org.example;

import org.example.exception.JDBCExceptions;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    public static Connection getConnection() {
        Connection connection = null;
//        String property = System.getProperty("catalina.base");
//        String s = property + "/resources";
//        String path = s + "/database.properties";
//        System.out.println(path);

//        File configFile = new File("src\\main\\resources\\database.properties");
//        File configFile = new File("src/main/webapp/WEB-INF/classes/database.properties");
//        File configFile = new File("/database.properties");
//        File configFile = new File("..\\ylabHw\\WEB-INF\\classes\\database.properties");
//        File configFile = new File(path);

//
//        try (FileReader reader = new FileReader(configFile)) {
//            Properties properties = new Properties();
//            properties.load(reader);
//
//            String url = properties.getProperty("url");
//            String user = properties.getProperty("username");
//            String pass = properties.getProperty("password");
        try {
//
//            InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("..\\ylabHw\\WEB-INF\\classes\\database.properties");

//            Properties properties = new Properties();
////            properties.load(systemResourceAsStream);
//            properties.load(reader);
//
//            String url = properties.getProperty("url");
//            String user = properties.getProperty("username");
//            String pass = properties.getProperty("password");

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ylab_db", "ylab_user", "ylab_pass");
//            connection = DriverManager.getConnection(url, user, pass);
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("I/O error");
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
