package org.example.model;

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

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBanned() {
        return isBanned;
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
