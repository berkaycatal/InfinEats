package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AddRestaurantActivity extends AppCompatActivity {

    private ListView listView;
    private AddRestaurantAdapter restaurantAdapter;
    private AddRestaurantController restaurantController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        listView = findViewById(R.id.list_favorites);
        String listId = getIntent().getStringExtra("listId");
        restaurantController = new AddRestaurantController(this);

        restaurantController.fetchRestaurants(new AddRestaurantController.OnRestaurantDataChangedListener() {
            @Override
            public void onRestaurantDataChanged(ArrayList<Restaurant> restaurants) {
                restaurantAdapter = new AddRestaurantAdapter(AddRestaurantActivity.this, restaurants, restaurantController);
                listView.setAdapter(restaurantAdapter);
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRestaurantActivity.this, MyFavoritesActivity.class);
                intent.putExtra("userId", FirebaseAuth.getInstance().getUid());
                intent.putExtra("listId", listId);
                startActivity(intent);


            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}

