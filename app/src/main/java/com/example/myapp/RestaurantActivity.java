package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantActivity extends AppCompatActivity {
    private CustomerFoodItemAdapter foodAdapter;
    private DatabaseReference restaurantReference;

    private String restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        restaurantId = getIntent().getStringExtra("restaurantId");
        restaurantReference = database.getReference("restaurants").child(restaurantId).child("foodItems");;
        setContentView(R.layout.activity_restaurant);
        ListView listView = findViewById(R.id.list_customer_food);
            foodAdapter = new CustomerFoodItemAdapter(this, restaurantReference); // passing MainActivity instance as the OnFoodItemEditClickListener parameter
        listView.setAdapter(foodAdapter);
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, CustomerActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RestaurantActivity.this, CustomerActivity.class);
        startActivity(intent);
        finish();
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}

