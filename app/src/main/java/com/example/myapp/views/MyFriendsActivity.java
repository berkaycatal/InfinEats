package com.example.myapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.controllers.MyFriendsController;
import com.example.myapp.models.Friends;
import com.example.myapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MyFriendsActivity extends AppCompatActivity {

    private MyFriendsAdapter friendsAdapter;
    private MyFriendsController friendsController;
    private ListView listView;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("user Id:" + userId);

        friendsController = new MyFriendsController(this);

        listView = findViewById(R.id.list_friends); // list_friend id is 1001
        friendsAdapter = new MyFriendsAdapter(this, friendsController);
        listView.setAdapter(friendsAdapter);

        fetchFriends();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the activity you want to navigate to on back button click
                Intent intent = new Intent(MyFriendsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the activity you want to navigate to on add button click
                Intent intent = new Intent(MyFriendsActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchFriends() {
        friendsController.fetchFriends(new MyFriendsController.DataStatus() {
            @Override
            public void dataLoaded(Friends friends) { // Change this line to accept Friends
                ArrayList<Friends> friendsList = new ArrayList<>();
                friendsList.add(friends);
                friendsAdapter.setFriendsList(friendsList);
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });
    }

}
