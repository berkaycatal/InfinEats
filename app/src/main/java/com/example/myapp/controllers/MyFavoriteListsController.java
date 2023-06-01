package com.example.myapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MyFavoriteListsController {
    private DatabaseReference favoritesReference;
    private ArrayList<Favorites> favoriteLists;

    private String userId;

    public MyFavoriteListsController() {
        this.favoriteLists = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        favoritesReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId).child("favorites");
    }

    public void addFavorites(Favorites favorites) {

        favoritesReference.child(favorites.getListName()).setValue(favorites);
        favoritesReference.child(favorites.getListName()).child("list").setValue(favorites.getFavoriteLists());
    }

    public void updateFavorites(Favorites fav) {

    }

    public void deleteFavorites(Favorites favorites) {
        favoritesReference.child(favorites.getListName()).removeValue();
    }

    public interface DataStatus {
        void dataLoaded(ArrayList<Favorites> foodItems);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }

    public void fetchFoodItems(final DataStatus dataStatus) {
        favoritesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteLists.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    System.out.println(itemSnapshot.getKey());
                    System.out.println(itemSnapshot.getValue());
                    Favorites favorites = itemSnapshot.getValue(Favorites.class);
                    favoriteLists.add(favorites);
                }
                dataStatus.dataLoaded(favoriteLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getUserId(){return userId;}
    public DatabaseReference getFavoritesReference(){
        return favoritesReference;
    }

    public static class OnFoodItemDataChangedListener {
    }
}

