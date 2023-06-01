package com.example.myapp.models;

import com.example.myapp.models.FoodItem;

import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private String id;
    private String name;
    private List<FoodItem> foodItems;

    public Restaurant() {
        // Default constructor required for Firebase
    }
    public Restaurant(String name, String id, List<FoodItem> foodItems) {
        this.id = id;
        this.name = name;
        this.foodItems = foodItems;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(HashMap<String, FoodItem> foodItems) {

    }
}

