package org.example;

import org.example.auditablestarter.annotations.EnableAudit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAudit
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        LiquibaseLoader liquibaseLoader = context.getBean(LiquibaseLoader.class);
        liquibaseLoader.runLiquibase();
    }
}