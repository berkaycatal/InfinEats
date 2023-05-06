package com.example.myapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String userName;
    public User(){

    }
    public User(String nam){
        userName = nam;
    }
}
