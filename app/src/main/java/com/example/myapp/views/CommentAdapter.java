package com.example.myapp.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.models.Comment;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> commentList;

    public CommentAdapter(Context context, ArrayList<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    // Implement the BaseAdapter methods similarly to the FoodItemAdapter

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Comment> getFoodItems(){return commentList;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_comment, parent, false);
        }
        Comment review = commentList.get(position);
        TextView tvContent = convertView.findViewById(R.id.tv_content);
        tvContent.setText(review.getContent());
        TextView tvRating = convertView.findViewById(R.id.tv_rating);
        tvRating.setText(String.valueOf(review.getRating()));
        return convertView;

    }
}

