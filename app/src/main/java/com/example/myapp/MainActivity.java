package com.example.myapp;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    private FoodItemAdapter foodAdapter;
    private DatabaseReference foodReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        foodReference = database.getReference("food").child("berkay");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        foodAdapter = new FoodItemAdapter(this, foodReference); // passing MainActivity instance as the OnFoodItemEditClickListener parameter
        listView.setAdapter(foodAdapter);
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFoodDialog();
            }
        });

    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_food);

        View view = getLayoutInflater().inflate(R.layout.edit_food_dialog, null);
        builder.setView(view);

        final EditText nameEditText = view.findViewById(R.id.name_edit_text);
        final EditText priceEditText = view.findViewById(R.id.price_edit_text);

        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String priceText = priceEditText.getText().toString();

            if (name.isEmpty() || priceText.isEmpty()) {
                return;
            }

            double price = Double.parseDouble(priceText);
            String key = foodReference.push().getKey();
            FoodItem meal = new FoodItem(name, price, key);
            foodReference.child(key).setValue(meal);
        });

        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }

}