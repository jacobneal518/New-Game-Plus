package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfilePage extends AppCompatActivity {

    Button homeButton;
    Button profileButton;

    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(ProfilePage.this, HomePage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener profilePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(ProfilePage.this, ProfilePage.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homePageListener);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);
    }
}