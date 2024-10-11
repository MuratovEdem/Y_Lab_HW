package org.example;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(date.plusDays(1));

    }
}