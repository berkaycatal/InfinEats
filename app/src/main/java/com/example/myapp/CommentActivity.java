package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentActivity extends AppCompatActivity {
    private CommentAdapter reviewAdapter;
    private DatabaseReference reviewReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        String foodId = getIntent().getStringExtra("foodId");
        String restaurantId = getIntent().getStringExtra("restaurantId");

        reviewReference = database.getReference("reviews").child(foodId);
        //Restaurant restaurant = new Restaurant(userId, foodAdapter.getFoodItems());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ListView listView = findViewById(R.id.list_comment);

        reviewAdapter = new CommentAdapter(this, reviewReference); // passing MainActivity instance as the OnFoodItemEditClickListener parameter
        listView.setAdapter(reviewAdapter);
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reviewReference.child(userId).get() != null){reviewReference.child(userId).setValue(null);}
                showAddFoodDialog();
            }
        });
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, RestaurantActivity.class);
                if (restaurantId != null) {
                    intent.putExtra("restaurantId", restaurantId);
                    startActivity(intent);
                }
            }
        });
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View view = getLayoutInflater().inflate(R.layout.edit_comment_dialog, null);
        builder.setView(view);

        final EditText contentEditText = view.findViewById(R.id.content_edit_text);
        final EditText ratingEditText = view.findViewById(R.id.rating_edit_text);

        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String contentText = contentEditText.getText().toString();
            String ratingText = ratingEditText.getText().toString();

            if (contentText.isEmpty() || ratingText.isEmpty()) {
                return;
            }
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String userId = auth.getCurrentUser().getUid();
            double rating = Double.parseDouble(ratingText);
            String key = reviewReference.push().getKey();
            String foodId = getIntent().getStringExtra("foodId");
            Comment comment = new Comment(userId, foodId, contentText, rating);
            reviewReference.child(userId).setValue(comment);
        });

        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }


}
