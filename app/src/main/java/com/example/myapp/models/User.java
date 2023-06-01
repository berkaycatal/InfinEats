package com.example.myapp.models;

import androidx.annotation.NonNull;

import com.example.myapp.enums.UserType;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.Map;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserType userType;
    private Map<String, String> createdAt;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(UserType userType, String id, String firstName, String lastName, String email, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.userType = userType;
        this.createdAt = ServerValue.TIMESTAMP;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }



    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public Map<String, String> getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long time) {

    }

    public static Date getCreatedLocalDate(User user){
        System.out.println(user.createdAt);
        return new Date();
    }

    @Override //usage of non-null makes sure this method is not null
    public @NonNull String toString(){
        return firstName + lastName + username + userType + email;
    }
}

