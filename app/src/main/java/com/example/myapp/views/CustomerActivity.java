package com.example.myapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.models.Restaurant;
import com.example.myapp.controllers.RestaurantController;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    private ListView listView;
    private RestaurantAdapter restaurantAdapter;
    private RestaurantController restaurantController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        listView = findViewById(R.id.list_comment);
        restaurantController = new RestaurantController();

        restaurantController.fetchRestaurants(new RestaurantController.OnRestaurantDataChangedListener() {
            @Override
            public void onRestaurantDataChanged(ArrayList<Restaurant> restaurants) {
                restaurantAdapter = new RestaurantAdapter(CustomerActivity.this, restaurants);
                listView.setAdapter(restaurantAdapter);
            }
        });

        findViewById(R.id.log_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantController.signOut();
                startActivity(new Intent(CustomerActivity.this, MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.my_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, MyProfileActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}