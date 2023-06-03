package com.example.myapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.controllers.MyFriendListsController;
import com.example.myapp.R;
import com.example.myapp.models.Friends;

import java.util.ArrayList;

public class MyFriendListsActivity extends AppCompatActivity {
    private MyFriendListsAdapter friendListAdapter;
    private MyFriendListsController friendListsController;
    private ListView listView;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_lists);

        friendListsController = new MyFriendListsController();

        listView = findViewById(R.id.list_friend_lists);     //not sure
        friendListAdapter = new MyFriendListsAdapter(this, friendListsController);
        listView.setAdapter(friendListAdapter);
        fetchFriendLists();

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFriendListDialog();
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFriendListsActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchFriendLists() {
        friendListsController.fetchFriends(new MyFriendListsController.DataStatus() {
            @Override
            public void dataLoaded(ArrayList<Friends> friends) {
                friendListAdapter.setFriendsLists(friends);
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

    private void showAddFriendListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View view = getLayoutInflater().inflate(R.layout.edit_lists_dialog, null);
        builder.setView(view);

        final EditText nameEditText = view.findViewById(R.id.name_edit_text);

        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String name = nameEditText.getText().toString();

            if (name.isEmpty()) {
                return;
            }

            Friends friends = new Friends(name, new ArrayList<>());
            friendListsController.addFriends(friends);
        });

        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }
}
