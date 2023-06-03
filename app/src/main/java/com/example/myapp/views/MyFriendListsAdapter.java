package com.example.myapp.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.controllers.MyFriendListsController;
import com.example.myapp.R;
import com.example.myapp.models.Friends;

import java.util.ArrayList;

public class MyFriendListsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friends> friendsLists;
    private MyFriendListsController friendsController;

    public MyFriendListsAdapter(Context context, MyFriendListsController friendsController) {
        this.context = context;
        this.friendsLists = new ArrayList<>();
        this.friendsController = friendsController;
    }

    @Override
    public int getCount() {
        return friendsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Friends> getFriendsLists() {
        return friendsLists;
    }

    public void setFriendsLists(ArrayList<Friends> friendsLists) {
        this.friendsLists = friendsLists;
        notifyDataSetChanged();
    }

    private void onDeleteButtonClicked(Friends friends) {
        friendsController.deleteFriends(friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_list, parent, false);
        }

        Friends friends = friendsLists.get(position);

        TextView tvName = convertView.findViewById(R.id.tv_name);
        tvName.setText(friends.getListName());

        convertView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked(friends);
            }
        });

        convertView.findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyFriendsActivity.class);
                intent.putExtra("listId", friends.getListName());
                intent.putExtra("userId", friendsController.getUserId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
