package com.example.myapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyFavoriteController {
    private DatabaseReference favoriteReference;
    private Favorites favorites;

    private String favoritesName;

    private String userId;

    private ArrayList<Restaurant> favoriteList;

    public MyFavoriteController(AppCompatActivity context) {

        this.favoritesName = context.getIntent().getStringExtra("listId");
        this.userId = context.getIntent().getStringExtra("userId");

        System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
        favoriteReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId).child("favorites").child(favoritesName).child("list");
    }

    public void addRestaurant(Restaurant restaurant) {

        favoriteReference.child(restaurant.getId()).setValue(favorites);
    }

    public void deleteFavorites(Restaurant restaurant) {
        favoriteReference.child(restaurant.getId()).removeValue();
    }

    public interface DataStatus {
        void dataLoaded(Favorites favorites);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }

    public void fetchRestaurants(final com.example.myapp.MyFavoriteController.DataStatus dataStatus) {
        favoriteList = new ArrayList<>();
        favoriteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    System.out.println("SnapshotValue");
                    System.out.println(itemSnapshot.getValue());
                    Restaurant restaurant = itemSnapshot.getValue(Restaurant.class);
                    favoriteList.add(restaurant);
                }
                Favorites favorites = new Favorites(favoritesName, favoriteList);
                dataStatus.dataLoaded(favorites);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public DatabaseReference getFoodReference(){
        return favoriteReference;
    }

    public static class OnFoodItemDataChangedListener {
    }
}



