package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private DatabaseReference reference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        if(auth.getCurrentUser() != null){
            onAuthSuccessful();
        }
        ;
        findViewById(R.id.customer_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(UserType.Customer);
            }
        });

        findViewById(R.id.owner_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(UserType.Owner);

            }
        });
        EditText email = findViewById(R.id.email_edit_text);
        EditText password = findViewById(R.id.password_edit_text);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClicked(email.getText().toString(),password.getText().toString());
            }

        });


    }
    private void onSignUpButtonClicked(UserType type){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("userType", type);
        startActivity(intent);
    }
    private void onSignInButtonClicked(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    onAuthSuccessful();
                }
                else{
                    Toast.makeText(MainActivity.this, "Invalid Mail or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void onAuthSuccessful(){
        reference.child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    User user = task.getResult().getValue(User.class);
                    if (user == null){
                        auth.signOut();
                        return;
                    }
                    System.out.println(user.toString());

                    if (user.getUserType() == UserType.Owner){
                        startActivity(new Intent(MainActivity.this, OwnerActivity.class));
                    }
                }
            }
        });
    }

}
