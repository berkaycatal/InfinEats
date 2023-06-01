package com.example.myapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@IgnoreExtraProperties
public class FoodItem {
    private String key;
    private String name;
    private double price;

    private String restaurantId;

    public FoodItem(){

    }
    public FoodItem(String name, double price, String key, String restaurantId) {
        this.name = name;
        this.price = price;
        this.key = key;
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }
    public String getKey() {return this.key;}
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getRestaurantId(){return restaurantId;}

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FoodItem foodItem = (FoodItem) obj;
        return Double.compare(foodItem.price, price) == 0 &&
                Objects.equals(name, foodItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("name", name);
        result.put("price", price);
        return result;
    }
}
