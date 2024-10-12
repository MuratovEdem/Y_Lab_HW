package org.example.frontend.DTO;

public class PersonDTO {
    private String email;
    private String password;
    private String name;

    public PersonDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
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
}
