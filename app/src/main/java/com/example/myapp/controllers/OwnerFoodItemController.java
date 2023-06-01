package com.example.myapp.controllers;

import androidx.annotation.NonNull;

import com.example.myapp.models.FoodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class OwnerFoodItemController {
    private DatabaseReference foodReference;
    private ArrayList<FoodItem> foodItemList;

    public OwnerFoodItemController(String userId) {
        this.foodItemList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.foodReference = database.getReference("restaurants").child(userId).child("foodItems");
    }

    public void addFoodItem(String name, double price, String key, String userId) {
        FoodItem foodItem = new FoodItem(name, price, key, userId);
        foodReference.child(key).setValue(foodItem);
    }

    public void updateFoodItem(FoodItem foodItem) {
        Map<String, Object> values = foodItem.toMap();
        foodReference.child(foodItem.getKey()).updateChildren(values);
    }

    public void deleteFoodItem(FoodItem foodItem) {
        foodReference.child(foodItem.getKey()).removeValue();
    }

    public interface DataStatus {
        void dataLoaded(ArrayList<FoodItem> foodItems);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }

    public void fetchFoodItems(final DataStatus dataStatus) {
        foodReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItemList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    FoodItem foodItem = itemSnapshot.getValue(FoodItem.class);
                    foodItemList.add(foodItem);
                }
                dataStatus.dataLoaded(foodItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public DatabaseReference getFoodReference(){
        return foodReference;
    }

    public static class OnFoodItemDataChangedListener {
    }
}

