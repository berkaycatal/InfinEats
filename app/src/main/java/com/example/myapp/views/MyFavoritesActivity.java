package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyFavoritesActivity extends AppCompatActivity {

    private MyFavoriteAdapter favoriteAdapter;
    private MyFavoriteController favoriteController;
    private ListView listView;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);
        String listName = getIntent().getStringExtra("listId");
        System.out.println("user Id:" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        favoriteController = new MyFavoriteController(this);

        listView = findViewById(R.id.list_favorites);
        favoriteAdapter = new MyFavoriteAdapter(this, favoriteController);
        listView.setAdapter(favoriteAdapter);
        fetchRestaurants();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapp.MyFavoritesActivity.this, MyFavoriteListsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapp.MyFavoritesActivity.this, AddRestaurantActivity.class);
                intent.putExtra("listId", listName);
                startActivity(intent);
            }
        });

    }

    private void fetchRestaurants() {
        favoriteController.fetchRestaurants(new MyFavoriteController.DataStatus() {
            @Override
            public void dataLoaded(Favorites favorites) {
                favoriteAdapter.setFavoriteList(favorites.getFavoriteLists());
            }

            @Override
            public void dataIsInserted() {
            }

            @Override
            public void dataIsUpdated() {
            }

            @Override
            public void dataIsDeleted() {
            }
        });
    }
}

