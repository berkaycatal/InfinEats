package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyFavoriteListsActivity extends AppCompatActivity {
    private MyFavoriteListAdapter favoriteAdapter;
    private MyFavoriteListsController favoriteController;
    private ListView listView;

    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        favoriteController = new MyFavoriteListsController();

        listView = findViewById(R.id.list_favorites);
        favoriteAdapter = new MyFavoriteListAdapter(this, favoriteController);
        listView.setAdapter(favoriteAdapter);
        fetchFoodItems();

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFavoritesDialog();
            }
        });
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFavoriteListsActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference("users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                    //TextView view = findViewById(R.id.created_at);
                    //view.setText(User.getCreatedLocalDate(user).toString());
                }
            }
        });
    }
    private void fetchFoodItems() {
        favoriteController.fetchFoodItems(new MyFavoriteListsController.DataStatus() {
            @Override
            public void dataLoaded(ArrayList<Favorites> favorites) {
                favoriteAdapter.setFavoriteLists(favorites);
            }

            @Override
            public void dataIsInserted() {}

            @Override
            public void dataIsUpdated() {}

            @Override
            public void dataIsDeleted() {}
        });
    }
    private void showAddFavoritesDialog() {
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
            Restaurant restaurant = new Restaurant("Prototype", "0", new ArrayList<>());
            ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
            restaurants.add(restaurant);
            restaurants.remove(restaurant);
            Favorites fav = new Favorites(name, restaurants);
            favoriteController.addFavorites(fav);
        });


        builder.setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }


}