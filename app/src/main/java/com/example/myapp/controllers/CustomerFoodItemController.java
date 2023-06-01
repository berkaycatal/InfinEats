package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerFoodItemController {
    private DatabaseReference foodItemReference;

    public CustomerFoodItemController() {
        foodItemReference = FirebaseDatabase.getInstance().getReference("restaurants");
    }

    public void fetchFoodItems(String restaurantId, final OnFoodItemDataChangedListener listener) {
        final ArrayList<FoodItem> foodItemList = new ArrayList<>();
        foodItemReference.child(restaurantId).child("foodItems").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodItem foodItem = snapshot.getValue(FoodItem.class);
                foodItemList.add(foodItem);
                listener.onFoodItemDataChanged(foodItemList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Implement other ChildEventListener methods...
        });
    }

    public interface OnFoodItemDataChangedListener {
        void onFoodItemDataChanged(ArrayList<FoodItem> foodItems);
    }
}
