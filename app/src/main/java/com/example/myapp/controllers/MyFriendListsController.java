package com.example.myapp.controllers;

import androidx.annotation.NonNull;

import com.example.myapp.models.Favorites;
import com.example.myapp.models.Friends;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFriendListsController {
    private DatabaseReference friendsReference;
    private ArrayList<Friends> friendsLists;

    private String userId;

    public MyFriendListsController() {
        this.friendsLists = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        //not sure on last child
        friendsReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId).child("friends");
    }

    public void addFriends(Friends friends) {

        friendsReference.child(friends.getListName()).setValue(friends);
        friendsReference.child(friends.getListName()).child("list").setValue(friends.getFriendsLists());

    }

    public void updateFriends(Friends fr) {

    }

    public void deleteFriends(Friends friends) {
        friendsReference.child(friends.getListName()).removeValue();
    }

    public interface DataStatus {
        void dataLoaded(ArrayList<Friends> friends);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }
//not sure
    public void fetchFriends(final MyFriendListsController.DataStatus dataStatus) {
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendsLists.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    System.out.println(itemSnapshot.getKey());
                    System.out.println(itemSnapshot.getValue());
                    Friends friends = itemSnapshot.getValue(Friends.class);
                    friendsLists.add(friends);
                }
                dataStatus.dataLoaded(friendsLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public String getUserId(){return userId;}
    public DatabaseReference getFriendsReference(){
        return friendsReference;
    }

    public static class OnFoodItemDataChangedListener {
    }

}
