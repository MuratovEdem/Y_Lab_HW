package org.example;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.exception.JDBCExceptions;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseLoader {

    public void runLiquibase() {
        Connection connection = DataBaseConnection.getConnection();
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (LiquibaseException e) {
            JDBCExceptions.printLiquiBaseException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                JDBCExceptions.printSQLException(e);
            }
        }
    }
}