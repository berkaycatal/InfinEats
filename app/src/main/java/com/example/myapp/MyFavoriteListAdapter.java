package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyFavoriteListAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<Favorites> favoriteLists;

    private ArrayList<String> nameList;
    private MyFavoriteListsController favoriteController;




    public MyFavoriteListAdapter(Context context, MyFavoriteListsController favController) {
        this.context = context;
        this.favoriteLists = new ArrayList<Favorites>();
        this.favoriteController = favController;
    }

    @Override
    public int getCount() {
        return favoriteLists.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public ArrayList<Favorites> getFavoriteLists(){return favoriteLists;}
    public void setFavoriteLists(ArrayList<Favorites> favoriteLists) {
        this.favoriteLists = favoriteLists;
        notifyDataSetChanged();
    }

    private void onDeleteButtonClicked(Favorites favorites) {
        favoriteController.deleteFavorites(favorites);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_list, parent, false);
        }

        Favorites favorite = favoriteLists.get(position);

        TextView tvName = convertView.findViewById(R.id.tv_name);



        tvName.setText(favorite.getListName());


        convertView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked(favorite);
            }
        });
        convertView.findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MyFavoritesActivity.class);
                context.startActivity(intent);
                intent.putExtra("listId", favorite.getListName());
                intent.putExtra("userId", favoriteController.getUserId());
                context.startActivity(intent);
            }
        });



        return convertView;
    }
}
