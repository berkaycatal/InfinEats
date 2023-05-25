package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentController {

    private DatabaseReference commentReference;

    private DatabaseReference visitedReference;

    private DatabaseReference restaurantReference;

    private FirebaseAuth auth;

    public CommentController() {
        restaurantReference = FirebaseDatabase.getInstance().getReference("restaurants");
        commentReference = FirebaseDatabase.getInstance().getReference("reviews");
        auth = FirebaseAuth.getInstance();
        visitedReference = FirebaseDatabase.getInstance().getReference("profiles").child(auth.getCurrentUser().getUid()).child("recentlyvisited");
    }

    public void fetchComments(String foodId, final OnCommentDataChangedListener listener) {
        final ArrayList<Comment> commentList = new ArrayList<>();
        commentReference.child(foodId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                commentList.add(comment);
                listener.onCommentDataChanged(commentList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Comment comment = snapshot.getValue(Comment.class);

                for (Comment review: commentList){
                    if (review.getUserId() == comment.getUserId()){
                        commentList.remove(review);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Implement other ChildEventListener methods...
        });
    }

    public interface OnCommentDataChangedListener {
        void onCommentDataChanged(ArrayList<Comment> comments);
    }
    public void addComment(Comment comment, OnCommentAddedListener listener) {
        String key = comment.getUserId();
        commentReference.child(comment.getKey()).child(key).setValue(comment).addOnCompleteListener(task -> {
            if (listener != null) {
                listener.onCommentAdded(task.isSuccessful());
            }
        });
    }
    private Task<Restaurant> getRestaurant(String restaurantId) {
        // Create a TaskCompletionSource object
        TaskCompletionSource<Restaurant> taskCompletionSource = new TaskCompletionSource<>();

        // Add listener to fetch data from database
        restaurantReference.child(restaurantId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get Profile object from snapshot
                    Restaurant restaurant = snapshot.getValue(Restaurant.class);
                    // Set the result of the task
                    taskCompletionSource.setResult(restaurant);
                } else {
                    // If snapshot does not exist, set the result of the task to null
                    taskCompletionSource.setResult(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // If an error occurred while fetching data, set the exception of the task
                taskCompletionSource.setException(error.toException());
            }
        });

        // Return the Task object
        return taskCompletionSource.getTask();
    }
    public void saveToRecents(String restaurantId){
        getRestaurant(restaurantId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Restaurant restaurant = task.getResult();
                visitedReference.child(restaurantId).setValue(restaurant);
            }
        });
    }

    public interface OnCommentAddedListener {
        void onCommentAdded(boolean isSuccess);
    }
}
