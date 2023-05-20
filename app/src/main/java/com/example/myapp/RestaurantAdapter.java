package com.example.myapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RestaurantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Restaurant> restaurantList;
    private DatabaseReference reference;


    public RestaurantAdapter(Context context, DatabaseReference reference) {
        this.context = context;
        this.restaurantList = new ArrayList<Restaurant>();
        this.reference = reference;
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                restaurantList.add(restaurant);
                notifyDataSetChanged();
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
        });
    }

    // Implement the BaseAdapter methods similarly to the FoodItemAdapter

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_restaurant, parent, false);
        }

        Restaurant restaurant = restaurantList.get(position);
        TextView tvRestaurantName = convertView.findViewById(R.id.tv_restaurant_name);
        tvRestaurantName.setText(restaurant.getName());
        convertView.findViewById(R.id.btn_view_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantActivity.class);
                context.startActivity(intent);
                intent.putExtra("restaurantId", restaurant.getId());
                context.startActivity(intent);
            }
        });
        return convertView;

    }
}

