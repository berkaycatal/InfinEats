package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VisitedController {

    private DatabaseReference restaurantReference;

    public VisitedController() {
        restaurantReference = FirebaseDatabase.getInstance().getReference("profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("recentlyvisited");
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

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public interface OnRestaurantDataChangedListener {
        void onRestaurantDataChanged(ArrayList<Restaurant> restaurants);
    }

    public void createRestaurant(Restaurant restaurant) {
        restaurantReference.child(restaurant.getId()).setValue(restaurant);
    }
}


