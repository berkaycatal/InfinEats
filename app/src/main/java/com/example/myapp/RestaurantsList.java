package com.example.myapp;

import java.util.ArrayList;

public class RestaurantsList {
    private ArrayList<Restaurant> restaurants;
    private String userId, name;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void editSingleRestaurant(int index, Restaurant restaurant) {
        if(checkBounds(index)){
            restaurants.set(index, restaurant);
        }
    }

    public void deleteSingleRestaurant(int index){
        if(checkBounds(index)){
            restaurants.remove(index);
        }
    }

    public void addNewRestaurant(int index, Restaurant restaurant) {
        if (checkBounds(index)) {
            restaurants.add(index, restaurant);
        }
    }
    public void addNewRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    private boolean checkBounds(int index){
        return index >= 0 && index < restaurants.size();
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
