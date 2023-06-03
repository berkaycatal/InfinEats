package com.example.myapp.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.models.Friends;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddFriendController {

    private DatabaseReference friendsReference;
    private ArrayList<Friends> friendsList;

    private String userId;

    public AddFriendController(AppCompatActivity context) {
        this.friendsList = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        friendsReference = FirebaseDatabase.getInstance().getReference("profiles")
                .child(userId)
                .child("friends");
    }



    public void fetchFriends(final OnFriendDataChangedListener listener) {
        friendsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Friends friend = snapshot.getValue(Friends.class);
                friendsList.add(friend);
                listener.onFriendDataChanged(friendsList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface OnFriendDataChangedListener {
        void onFriendDataChanged(ArrayList<Friends> friends);
    }

    public String getUserId() {
        return userId;
    }
    public void putFriend(Friends friend){ //hard coded
        friendsReference.child(friend.getListName()).setValue(friend);
    }
}
