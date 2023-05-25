package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileController {
    private DatabaseReference profileReference;

    public ProfileController(Context context) {
        profileReference = FirebaseDatabase.getInstance().getReference("profiles");
    }

    public void fetchProfiles(final ProfileController.OnProfileDataChangedListener listener) {
        final ArrayList<Profile> profilelist = new ArrayList<>();
        profileReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Profile profile = snapshot.getValue(Profile.class);
                profilelist.add(profile);
                listener.onRestaurantDataChanged(profilelist);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Implement other ChildEventListener methods...
        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public interface OnProfileDataChangedListener {
        void onRestaurantDataChanged(ArrayList<Profile> profiles);
    }

    public void createProfile(Profile profile) {
        profileReference.child(profile.getId()).setValue(profile);
    }

    public static class MyFavoriteListsAdapter extends BaseAdapter {

        private Context context;

        private ArrayList<String> favoritesList;
        private OwnerFoodItemController foodController;



        public MyFavoriteListsAdapter(Context context, OwnerFoodItemController foodController) {
            this.context = context;
            this.favoritesList = new ArrayList<>();
            this.foodController = foodController;
        }

        @Override
        public int getCount() {
            return favoritesList.size();
        }

        @Override
        public Object getItem(int position) {
            return favoritesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public ArrayList<String> getFavoritesList(){return favoritesList;}
        public void setFoodItems(ArrayList<String> favorites) {
            this.favoritesList = favorites;
            notifyDataSetChanged();
        }

        private void onDeleteButtonClicked(String favorite) {
            //favoritesController.deleteFavorite(favorite);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_my_list, parent, false);
            }

            String favorite = favoritesList.get(position);
            TextView tvFoodName = convertView.findViewById(R.id.tv_name);

            Button btnDelete = convertView.findViewById(R.id.btn_delete);

            tvFoodName.setText(favorite);


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteButtonClicked(favorite);
                }
            });


            ;
            return convertView;
        }

    }
}

