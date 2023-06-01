package com.example.myapp.views;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.models.Restaurant;
import com.example.myapp.views.RestaurantActivity;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Restaurant> restaurantList;
    private DatabaseReference reference;


    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
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

