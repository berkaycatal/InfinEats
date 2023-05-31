package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private ListView listView;
    private CommentAdapter commentAdapter;
    private CommentController commentController;
    private String restaurantId;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        listView = findViewById(R.id.list_comment);
        restaurantId = getIntent().getStringExtra("restaurantId");
        foodId = getIntent().getStringExtra("foodId");

        commentController = new CommentController();

        commentController.fetchComments(foodId, new CommentController.OnCommentDataChangedListener() {
            @Override
            public void onCommentDataChanged(ArrayList<Comment> comments) {
                commentAdapter = new CommentAdapter(CommentActivity.this, comments);
                listView.setAdapter(commentAdapter);
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    showAddCommentDialog();


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

    private void showAddCommentDialog() {
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
            //in order not to get nullPointer exception
            try {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userId = auth.getCurrentUser().getUid();
                double rating = Double.parseDouble(ratingText);
                //keeps the rating below 5
                if (rating > 5){
                    Toast.makeText(CommentActivity.this,"Your rating is more than 5!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = new Comment(userId, foodId, contentText, rating);

                // Use CommentController to save the comment.
                commentController.addComment(comment, new CommentController.OnCommentAddedListener() {
                    @Override
                    public void onCommentAdded(boolean isSuccess) {
                        if (isSuccess) {
                            commentController.saveToRecents(restaurantId);
                            Toast.makeText(CommentActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CommentActivity.this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }catch (Exception e){System.out.println("no user registered");}

        });

        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }

}

