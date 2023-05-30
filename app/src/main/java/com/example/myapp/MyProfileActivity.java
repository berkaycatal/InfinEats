package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MyProfileActivity extends AppCompatActivity {

    private MyProfileController profileController;
    private TextView tvName, tvSurname, tvEmail;
    private Button btnEditProfile, btnRecentlyVisited, btnFavoriteLists, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profileController = new MyProfileController();

        tvName = findViewById(R.id.tv_name);
        tvSurname = findViewById(R.id.tv_surname);
        tvEmail = findViewById(R.id.tv_email);

        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnRecentlyVisited = findViewById(R.id.btn_recently_visited);
        btnFavoriteLists = findViewById(R.id.btn_favorite_lists);
        btnBack = findViewById(R.id.back_button);


        btnEditProfile.setOnClickListener(v -> {
            profileController.showEditProfileDialog(this, FirebaseAuth.getInstance().getCurrentUser().getUid());
        });

        btnRecentlyVisited.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, RecentlyVisitedActivity.class);
            startActivity(intent);
        });

        btnFavoriteLists.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, MyFavoriteListsActivity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, CustomerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        profileController.getUserProfile(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Profile profile = task.getResult();
                if(profile != null){
                    tvName.setText(profile.getName());
                    tvSurname.setText(profile.getSurname());
                    tvEmail.setText(profile.getEmail());
                }
            } else {
                // Handle the error
                System.out.println("task is not succesfull...");
            }
        });


    }
}
