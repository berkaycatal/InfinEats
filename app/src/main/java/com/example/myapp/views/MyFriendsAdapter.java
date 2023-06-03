package com.example.myapp.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.controllers.MyFriendsController;
import com.example.myapp.R;
import com.example.myapp.models.Friends;
import com.example.myapp.models.User;

import java.util.ArrayList;

public class MyFriendsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friends> friendsList;
    private MyFriendsController friendsController;

    public MyFriendsAdapter(Context context, MyFriendsController friendsController) {
        this.context = context;
        this.friendsList = new ArrayList<>();
        this.friendsController = friendsController;
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Friends> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<Friends> friendsList) {
        this.friendsList = friendsList;
        notifyDataSetChanged();
    }

    private void onDeleteButtonClicked(User user) {
        friendsController.deleteFriend(user);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_friends, parent, false);
        }

        User user = friendsList.get(position);

        TextView tvName = convertView.findViewById(R.id.tv_friend_name);
        tvName.setText(user.getFirstName());

        convertView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked(user);
                friendsList.remove(user);
            }
        });

        convertView.findViewById(R.id.btn_view_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //certainly not sure, it should be VisitorProfileActivity but I do not have that yet
                Intent intent = new Intent(context, MyProfileActivity.class);
                intent.putExtra("userId", user.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
