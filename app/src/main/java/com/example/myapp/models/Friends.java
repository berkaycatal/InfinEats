package com.example.myapp.models;

import java.util.ArrayList;

public class Friends extends User{ //not sure for extending
    private ArrayList<User> friendsLists;
    private String listName;

    // Empty constructor (required for Firebase)
    public Friends() {
    }

    // Constructor with parameters
    public Friends(String listName, ArrayList<User> friendsLists) {
        this.listName = listName;
        this.friendsLists = friendsLists;
    }

    // Getters and setters for the attributes
    public ArrayList<User> getFriendsLists() {
        return friendsLists;
    }


    public String getListName() {
        return listName;
    }
}
