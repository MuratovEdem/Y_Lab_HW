package org.example.DTO;

import jakarta.validation.constraints.NotBlank;

public class PersonDTO {
    private long id;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    private boolean isAdmin;
    private boolean isBanned;

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isBanned() {
        return isBanned;
    }
}
