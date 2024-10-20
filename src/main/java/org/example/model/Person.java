package org.example.model;

import lombok.Data;

@Data
public class Person {
    private long id;
    private String email;
    private String password;
    private String name;

    private boolean isAdmin;
    private boolean isBanned;

    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        isAdmin = false;
        isBanned = false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", isBanned=" + isBanned +
                '}';
    }
}
