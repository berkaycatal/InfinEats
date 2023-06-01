package com.example.myapp.views;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.models.FoodItem;
import com.example.myapp.R;

import java.util.ArrayList;

public class CustomerFoodItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FoodItem> foodList;

    public CustomerFoodItemAdapter(Context context, ArrayList<FoodItem> foodList) {
        this.context = context;
        this.foodList = foodList;
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

