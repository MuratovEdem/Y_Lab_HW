package org.example;

public class Main {
    public static void main(String[] args) {

        LiquibaseLoader liquibaseLoader = new LiquibaseLoader(DataBaseConnection.getConnection());

        liquibaseLoader.runLiquibase();
    }
}