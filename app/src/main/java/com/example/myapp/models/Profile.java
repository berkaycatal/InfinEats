package com.example.myapp.models;

public class Profile {
    private String name;
    private String surname;
    private String id;
    private String email;

    // Empty constructor (required for Firebase)
    public Profile() {
    }

    // Constructor with parameters
    public Profile(String name, String surname, String id, String email) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.email = email;
    }

    // Getters and setters for the attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

