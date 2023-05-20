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

public class CustomerFoodItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FoodItem> foodList;
    private DatabaseReference reference;

    public CustomerFoodItemAdapter(Context context, DatabaseReference reference) {
        this.context = context;
        this.foodList = new ArrayList<FoodItem>();
        this.reference = reference;
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodItem foodItem = snapshot.getValue(FoodItem.class);
                foodList.add(foodItem);
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
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<FoodItem> getFoodItems(){return foodList;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_food_customer, parent, false);
        }
        FoodItem meal = foodList.get(position);
        TextView tvFoodName = convertView.findViewById(R.id.tv_food_name);
        tvFoodName.setText(meal.getName());
        TextView tvFoodPrice = convertView.findViewById(R.id.tv_food_price);
        tvFoodPrice.setText(String.valueOf(meal.getPrice()));
        convertView.findViewById(R.id.btn_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("foodId", meal.getKey());
                intent.putExtra("restaurantId", meal.getRestaurantId());
                context.startActivity(intent);
            }
        });
        return convertView;

    }
}

