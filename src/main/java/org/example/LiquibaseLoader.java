package org.example;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.exception.JDBCExceptions;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class LiquibaseLoader {

    public void runLiquibase() {
        try (DataBaseConnection dataBaseConnection = new DataBaseConnection()) {
            Connection connection = dataBaseConnection.getConnection();
            try {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

                Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml",
                        new ClassLoaderResourceAccessor(), database);
                liquibase.update();
            } catch (LiquibaseException e) {
                JDBCExceptions.printLiquibaseException(e);
            }
        } catch (SQLException e) {
            JDBCExceptions.printSQLException(e);
        }
    }
}