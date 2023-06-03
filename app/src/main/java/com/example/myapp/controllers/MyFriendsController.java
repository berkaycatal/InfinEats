package com.example.myapp.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.models.Favorites;
import com.example.myapp.models.Friends;
import com.example.myapp.models.Restaurant;
import com.example.myapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFriendsController {
    private DatabaseReference friendsReference;
    private Friends friends;

    private String friendsName;

    private String userId;

    private ArrayList<User> friendsList;

    public MyFriendsController(AppCompatActivity context) {

        this.friendsName = context.getIntent().getStringExtra("listId");
        this.userId = context.getIntent().getStringExtra("userId");

        System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //not sure on path
        //friendsReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId).child("userID").child(friendsName).child("list");
        friendsReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId).child(friendsName).child("list");

    }


    public void deleteFriend(User user) {
        friendsReference.child(user.getId()).removeValue();
    }

    public interface DataStatus {
        void dataLoaded(Friends friends);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }
    public interface UserDataStatus {
        void dataLoaded(User user);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }


    public void fetchFriends(final MyFriendsController.DataStatus dataStatus) {
        friendsList = new ArrayList<>();
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendsList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    System.out.println("SnapshotValue");
                    System.out.println(itemSnapshot.getValue());
                    User user = itemSnapshot.getValue(User.class);
                    friendsList.add(user);
                }
                Friends friends1 = new Friends(friendsName, friendsList);
                dataStatus.dataLoaded(friends1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public DatabaseReference getFriendsReference(){
        return friendsReference;
    }

    //added by our dear friend we 3 of us feed :)
    public void fetchUser(String userId, final UserDataStatus userDataStatus) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("profiles").child(userId);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userDataStatus.dataLoaded(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addFriend(User user) {
        friendsReference.child(user.getId()).setValue(user);
    }

}
