package org.example.exception;

import liquibase.exception.LiquibaseException;

import java.sql.SQLException;

public class JDBCExceptions {
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Код ошибки: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Сообщение: " + e.getMessage());

            Throwable t = ex.getCause();
            while (t != null) {
                System.out.println("Причина: " + t);
                t = t.getCause();
            }
        }
    }

    public static void printLiquibaseException(LiquibaseException ex) {
        ex.printStackTrace(System.err);

        System.err.println("Сообщение: " + ex.getMessage());

        Throwable t = ex.getCause();
        while (t != null) {
            System.out.println("Причина: " + t);
            t = t.getCause();

        }
    }
}
