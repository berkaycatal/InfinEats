package com.example.myapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.controllers.AddFriendController;
import com.example.myapp.R;
import com.example.myapp.models.Friends;

import java.util.ArrayList;

public class AddFriendAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Friends> friendsList;
    private AddFriendController friendController;

    public AddFriendAdapter(Context context, ArrayList<Friends> friendsList, AddFriendController friendController) {
        this.context = context;
        this.friendsList = friendsList;
        this.friendController = friendController;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_add_friend, parent, false);
        }

        Friends friend = friendsList.get(position);
        TextView tvFriendName = convertView.findViewById(R.id.tv_friend_name);
        tvFriendName.setText(friend.getFirstName());

        convertView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendController.putFriend(friend);
            }
        });

        return convertView;
    }
}
