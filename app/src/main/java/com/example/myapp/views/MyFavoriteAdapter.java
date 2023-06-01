package com.example.myapp.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.controllers.MyFavoriteController;
import com.example.myapp.R;
import com.example.myapp.models.Restaurant;

import java.util.ArrayList;


public class MyFavoriteAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<Restaurant> favoriteList;

    private MyFavoriteController favoriteController;



    public MyFavoriteAdapter(Context context, MyFavoriteController resController) {
        this.context = context;
        this.favoriteList = new ArrayList<Restaurant>();
        this.favoriteController = resController;
    }

    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public ArrayList<Restaurant> getFavoriteList(){return favoriteList;}
    public void setFavoriteList(ArrayList<Restaurant> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }

    private void onDeleteButtonClicked(Restaurant restaurant) {
        favoriteController.deleteFavorites(restaurant);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_favorites, parent, false);
        }

        Restaurant restaurant = favoriteList.get(position);

        TextView tvName = convertView.findViewById(R.id.tv_restaurant_name);



        tvName.setText(restaurant.getName());


        convertView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClicked(restaurant); favoriteList.remove(restaurant);
            }
        });
        convertView.findViewById(R.id.btn_view_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantActivity.class);
                context.startActivity(intent);
                intent.putExtra("restaurantId", restaurant.getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

