package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AddProfileDetail extends AppCompatActivity {
    Button backButton,signOut;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_detail);
        backButton = findViewById(R.id.detailBack);
        backButton.setOnClickListener(backButtonListen);
        signOut = findViewById(R.id.signOutButton);
        signOut.setOnClickListener(signOutButtonListen);


    }

    View.OnClickListener backButtonListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddProfileDetail.this, ProfilePage.class);
            startActivity(intent);
                }
            };
    View.OnClickListener signOutButtonListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(AddProfileDetail.this, MainActivity.class);
            startActivity(intent);
        }
    };
}