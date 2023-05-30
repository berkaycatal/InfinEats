package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecentlyVisitedActivity extends AppCompatActivity {

    private ListView listView;
    private RestaurantAdapter restaurantAdapter;
    private VisitedController visitedController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited);
        listView = findViewById(R.id.list_restaurant);
        visitedController = new VisitedController();

        visitedController.fetchRestaurants(new VisitedController.OnRestaurantDataChangedListener() {
            @Override
            public void onRestaurantDataChanged(ArrayList<Restaurant> restaurants) {
                restaurantAdapter = new RestaurantAdapter(RecentlyVisitedActivity.this, restaurants);
                listView.setAdapter(restaurantAdapter);
            }
        });

        Button btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("içerdema");
                Intent intent = new Intent(com.example.myapp.RecentlyVisitedActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
