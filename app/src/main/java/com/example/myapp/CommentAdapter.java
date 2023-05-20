package com.example.myapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> commentList;
    private DatabaseReference reference;

    public CommentAdapter(Context context, DatabaseReference reference) {
        this.context = context;
        this.commentList = new ArrayList<>();
        this.reference = reference;
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                commentList.add(comment);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Comment comment = snapshot.getValue(Comment.class);

                for (Comment review: commentList){
                    if (review.getUserId() == comment.getUserId()){
                        commentList.remove(review);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

