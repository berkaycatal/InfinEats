package com.example.myapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.controllers.AddFriendController;
import com.example.myapp.R;
import com.example.myapp.models.Friends;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    private ListView listView;
    private AddFriendAdapter friendAdapter;
    private AddFriendController friendController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        listView = findViewById(R.id.list_friends);
        String listId = getIntent().getStringExtra("listId");
        friendController = new AddFriendController(this);

        friendController.fetchFriends(new AddFriendController.OnFriendDataChangedListener() {
            @Override
            public void onFriendDataChanged(ArrayList<Friends> friends) {
                friendAdapter = new AddFriendAdapter(AddFriendActivity.this, friends, friendController);
                listView.setAdapter(friendAdapter);
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {            //check intent
                Intent intent = new Intent(AddFriendActivity.this, MyFriendsActivity.class);
                intent.putExtra("userId", FirebaseAuth.getInstance().getUid());
                intent.putExtra("listId", listId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
