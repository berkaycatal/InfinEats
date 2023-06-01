package com.example.myapp.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddRestaurantController {

    private DatabaseReference favoritesReference;

    private DatabaseReference restaurantReference;

    public AddRestaurantController(AppCompatActivity context) {
        String favoritesName = context.getIntent().getStringExtra("listId");
        favoritesReference = FirebaseDatabase.getInstance().getReference("profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites").child(favoritesName).child("list");
        restaurantReference = FirebaseDatabase.getInstance().getReference("restaurants");
    }

    public void fetchRestaurants(final OnRestaurantDataChangedListener listener) {
        final ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                restaurantList.add(restaurant);
                listener.onRestaurantDataChanged(restaurantList);
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


    public interface OnRestaurantDataChangedListener {
        void onRestaurantDataChanged(ArrayList<Restaurant> restaurants);
    }

    public void putRestaurant(Restaurant restaurant) {
        favoritesReference.child(restaurant.getId()).setValue(restaurant);
    }
}
