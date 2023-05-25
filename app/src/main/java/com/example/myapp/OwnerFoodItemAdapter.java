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

import androidx.appcompat.app.AlertDialog;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class OwnerFoodItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FoodItem> foodItemList;
    private OwnerFoodItemController foodController;



    public OwnerFoodItemAdapter(Context context, OwnerFoodItemController foodController) {
        this.context = context;
        this.foodItemList = new ArrayList<FoodItem>();
        this.foodController = foodController;
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
    public ArrayList<FoodItem> getFoodItems(){return foodItemList;}
    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItemList = foodItems;
        notifyDataSetChanged();
    }

    private void onDeleteButtonClicked(FoodItem foodItem) {
        foodController.deleteFoodItem(foodItem);
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

                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                try {
                    Number num = format.parse(priceText);
                    double price = num.doubleValue();
                    foodItem.setName(name);
                    foodItem.setPrice(price);

                    foodController.updateFoodItem(foodItem);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
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
