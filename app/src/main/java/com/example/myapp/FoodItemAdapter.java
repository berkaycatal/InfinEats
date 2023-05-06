package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FoodItem> foodItemList;

    private DatabaseReference reference;



    public FoodItemAdapter(Context context, DatabaseReference reference) {
        this.context = context;
        this.foodItemList = new ArrayList<FoodItem>();
        this.reference =reference;
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FoodItem meal = snapshot.getValue(FoodItem.class);
                foodItemList.add(meal);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getValue(FoodItem.class).getKey();
                for (FoodItem meal: foodItemList){
                    if (meal.getKey().equals(key)){
                        int index = foodItemList.indexOf(meal);
                        foodItemList.set(index, snapshot.getValue(FoodItem.class));

                        break;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getValue(FoodItem.class).getKey();
                for (FoodItem meal: foodItemList){
                    if (meal.getKey().equals(key)){
                        foodItemList.remove(meal);
                        break;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getCount() {
        return foodItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public List<FoodItem> getFoodItems(){return foodItemList;}
    private void onDeleteButtonClicked(FoodItem foodItem){
        System.out.println(foodItem);
        reference.child(foodItem.getKey()).removeValue();

    }
    private void onEditButtonClicked(FoodItem foodItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.edit);
        Activity activity = (Activity) context;
        View view = activity.getLayoutInflater().inflate(R.layout.edit_food_dialog, null);
        builder.setView(view);

        final EditText nameEditText = view.findViewById(R.id.name_edit_text);
        final EditText priceEditText = view.findViewById(R.id.price_edit_text);

        nameEditText.setText(foodItem.getName());
        priceEditText.setText(String.format("%.2f", foodItem.getPrice()));

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                String priceText = priceEditText.getText().toString();

                if (name.isEmpty() || priceText.isEmpty()) {
                    return;
                }

                double price = Double.parseDouble(priceText);
                foodItem.setName(name);
                foodItem.setPrice(price);

                Map<String,Object> values = foodItem.toMap();

                reference.child(foodItem.getKey()).updateChildren(values);
            }
        });

        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_food, parent, false);
        }

        FoodItem foodItem = foodItemList.get(position);

        TextView tvFoodName = convertView.findViewById(R.id.tv_food_name);
        TextView tvFoodPrice = convertView.findViewById(R.id.tv_food_price);
        Button btnDelete = convertView.findViewById(R.id.btn_delete);
        Button btnEdit = convertView.findViewById(R.id.btn_edit);
        tvFoodName.setText(foodItem.getName());
        tvFoodPrice.setText(String.format("%.2f", foodItem.getPrice()));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked(foodItem);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditButtonClicked(foodItem);
            }
        });

        ;
        return convertView;
    }

}
