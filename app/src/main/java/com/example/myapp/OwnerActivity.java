package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class OwnerActivity extends AppCompatActivity {
    private FoodItemAdapter foodAdapter;
    private DatabaseReference foodReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
<<<<<<< HEAD
        foodReference = database.getReference("food").child(userId);
=======
        foodReference = database.getReference("restaurants").child(userId);
>>>>>>> f67e84a (after meeting 08.05.23)

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        ListView listView = findViewById(R.id.list_view);

        foodAdapter = new FoodItemAdapter(this, foodReference); // passing MainActivity instance as the OnFoodItemEditClickListener parameter
        listView.setAdapter(foodAdapter);
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFoodDialog();
            }
        });
        findViewById(R.id.log_out_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOutButtonClicked();
            }
        });
        database.getReference("users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                    //TextView view = findViewById(R.id.created_at);
                    //view.setText(User.getCreatedLocalDate(user).toString());
                }
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
    private void onSignOutButtonClicked(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

}