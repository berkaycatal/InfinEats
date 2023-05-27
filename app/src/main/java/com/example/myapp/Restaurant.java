package com.example.myapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    private String id, name;
    private ArrayList<FoodItem> foodItems;
    private ArrayList<Comment> comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void  setFoodItems(HashMap<Integer, FoodItem> foodItems){
        this.foodItems.clear();
        for(FoodItem f : foodItems.values()){
            this.foodItems.add(f);
        }
    }
    public void setComments(HashMap<Integer, Comment> comments ){
        this.comments.clear();
        for(Comment c : comments.values() ){
            this.comments.add(c);
        }
    }
}
