package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private UserController userController;
    private RestaurantController restaurantController;

    private ProfileController profileController;

    private EditText firstNameEditText, lastNameEditText, emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button submitButton;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        userController = new UserController();
        restaurantController = new RestaurantController();
        profileController = new ProfileController(this);

        // Initialize Firebase authentication and database references
        mAuth = FirebaseAuth.getInstance();

        //mRef = FirebaseDatabase.getInstance().getReference("users");

        //restaurantReference =  FirebaseDatabase.getInstance().getReference("restaurants");

        // Initialize views
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        submitButton = findViewById(R.id.submit_button);

        // Set OnClickListener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input values
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(firstName)) {
                    firstNameEditText.setError("First name is required");
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    lastNameEditText.setError("Last name is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError("Username is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordEditText.setError("Confirm password is required");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    return;
                }

                // Create new user object

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String userId = task.getResult().getUser().getUid();
                            UserType type = (UserType) getIntent().getSerializableExtra("userType");
                            User user = new User(type, userId, firstName, lastName, email, username);

                            if (user.getUserType() == UserType.Owner) {
                                Restaurant restaurant = new Restaurant(firstName, userId, new ArrayList<>());
                                restaurantController.createRestaurant(restaurant);
                            }
                            if (user.getUserType() == UserType.Customer) {
                                Profile profile = new Profile(firstName,lastName, userId, email);
                                profileController.createProfile(profile);
                            }
                            userController.createUser(user, new UserController.OnUserCreatedListener() {
                                @Override
                                public void onUserCreated() {
                                    onAuthSuccessful();
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User is NOT registered", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
    private void onAuthSuccessful(){
        userController.getCurrentUser(new UserController.OnGetUserListener() {
            @Override
            public void onGetUser(User user) {
                if (user == null){
                    mAuth.signOut();
                    return;
                }

                if (user.getUserType() == UserType.Owner){
                    startActivity(new Intent(RegisterActivity.this, OwnerActivity.class));
                }
                else if (user.getUserType() == UserType.Customer){
                    startActivity(new Intent(RegisterActivity.this, CustomerActivity.class));
                }
            }
        });
    }
}
