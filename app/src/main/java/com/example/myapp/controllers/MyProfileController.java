package com.example.myapp.controllers;

// MyProfileController.java


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myapp.models.Profile;
import com.example.myapp.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileController {

    private DatabaseReference profileReference;

    private FirebaseAuth auth;

    public MyProfileController(){
        profileReference = FirebaseDatabase.getInstance().getReference("profiles");
        auth = FirebaseAuth.getInstance();
    }
    public Task<Profile> getUserProfile(String userId) {
        // Create a TaskCompletionSource object
        TaskCompletionSource<Profile> taskCompletionSource = new TaskCompletionSource<>();

        // Add listener to fetch data from database
        profileReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get Profile object from snapshot
                    Profile profile = snapshot.getValue(Profile.class);
                    // Set the result of the task
                    taskCompletionSource.setResult(profile);
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






    public void showEditProfileDialog(Context context, String userId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Profile");

        View view = View.inflate(context, R.layout.edit_profile_dialog, null);
        final EditText nameEditText = view.findViewById(R.id.editName);
        final EditText surnameEditText = view.findViewById(R.id.editSurname);

        builder.setView(view);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String nameText = nameEditText.getText().toString();
            String surnameText = surnameEditText.getText().toString();

            if (nameText.isEmpty() || surnameText.isEmpty()) {
                return;
            }

            // Update user profile in Firebase
            profileReference.child(userId).child("name").setValue(nameText);
            profileReference.child(userId).child("surname").setValue(surnameText);
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }
}

