package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AddRestaurantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Restaurant> restaurantList;
    private DatabaseReference reference;
    private AddRestaurantController controller;

    public AddRestaurantAdapter(Context context, ArrayList<Restaurant> restaurantList, AddRestaurantController controller) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.controller = controller;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_add_favorite, parent, false);
        }

        Restaurant restaurant = restaurantList.get(position);
        TextView tvRestaurantName = convertView.findViewById(R.id.tv_restaurant_name);
        tvRestaurantName.setText(restaurant.getName());
        System.out.println(restaurant.getFoodItems() + restaurant.getId());
        convertView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                controller.putRestaurant(restaurant);
            }
        });
        return convertView;

    }
}
