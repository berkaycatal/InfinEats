package com.example.myapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.controllers.CustomerFoodItemController;
import com.example.myapp.models.FoodItem;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    private ListView listView;
    private CustomerFoodItemAdapter foodAdapter;
    private CustomerFoodItemController foodItemController;
    private String restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        listView = findViewById(R.id.list_customer_food);
        restaurantId = getIntent().getStringExtra("restaurantId");
        foodItemController = new CustomerFoodItemController();

        foodItemController.fetchFoodItems(restaurantId, new CustomerFoodItemController.OnFoodItemDataChangedListener() {
            @Override
            public void onFoodItemDataChanged(ArrayList<FoodItem> foodItems) {
                foodAdapter = new CustomerFoodItemAdapter(RestaurantActivity.this, foodItems);
                listView.setAdapter(foodAdapter);
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantActivity.this, CustomerActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RestaurantActivity.this, CustomerActivity.class));
        finish();
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}

