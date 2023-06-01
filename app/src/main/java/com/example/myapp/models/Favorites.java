package com.example.myapp.models;

import java.util.ArrayList;

public class Favorites {
    private ArrayList<Restaurant> favoriteLists;
    private String listName;

    // Empty constructor (required for Firebase)
    public Favorites() {
    }

    // Constructor with parameters
    public Favorites(String listName, ArrayList<Restaurant> favoriteLists) {
        this.listName = listName;
        this.favoriteLists = favoriteLists;
    }

    // Getters and setters for the attributes
    public ArrayList<Restaurant> getFavoriteLists() {
        return favoriteLists;
    }

    public void setFavoriteLists(ArrayList<Restaurant> favoriteLists) {
        this.favoriteLists = favoriteLists;
    }

    public String getListName() {
        return listName;
    }

    public int getCount(){return favoriteLists.size();}

    public void setListName(String listName) {
        this.listName = listName;
    }


}

