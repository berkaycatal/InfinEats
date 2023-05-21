package com.example.myapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserController {
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    public UserController() {
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("users");
    }
    public void createUser(User user, OnUserCreatedListener listener) {
        // Save the user to the database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    // Call `listener.onUserCreated()` once the task is complete.
                    listener.onUserCreated();
                }
                else{
                    // Handle any errors
                }
            }
        });
    }

    public void getCurrentUser(OnGetUserListener listener) {
        // Fetch the current user from the database.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                    // Call `listener.onGetUser(user)` once the task is complete.
                    listener.onGetUser(user);
                }
                else{
                    // Handle any errors
                }
            }
        });
    }

    public interface OnUserCreatedListener {
        void onUserCreated();
    }

    public interface OnGetUserListener {
        void onGetUser(User user);
    }
}


