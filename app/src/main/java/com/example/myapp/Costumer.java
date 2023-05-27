package com.example.myapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Costumer {
    private String name,surname, id, email;
    private ArrayList<Restaurant> recentlyVisited;
    private ArrayList<RestaurantsList> restaurantLists;

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

    public ArrayList<Restaurant> getRecentlyVisited() {
        return recentlyVisited;
    }

    public void setRecentlyVisited(HashMap<Integer, Restaurant> recentlyVisited) {
        this.recentlyVisited.clear();
        for(Restaurant r : recentlyVisited.values()){
            this.recentlyVisited.add(r);
        }
    }

    public ArrayList<RestaurantsList> getRestaurantLists() {
        return restaurantLists;
    }

    public void setRestaurantLists(HashMap<Integer, RestaurantsList> restaurantLists) {
        this.restaurantLists.clear();
        for(RestaurantsList rl : restaurantLists.values()){
            this.restaurantLists.add(rl);
        }
    }

    public void addVisit(Restaurant restaurant){
        this.recentlyVisited.add(restaurant);
    }
    public void addList(RestaurantsList restaurantsList){
        this.restaurantLists.add(restaurantsList);
    }
}
