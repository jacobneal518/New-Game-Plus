package com.example.newgameplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    Button homeButton;
    Button profileButton;
    FirebaseAuth mAuth;
    TextView usernameTV,fNameTV,lNameTV;

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
            Intent intent = new Intent(ProfilePage.this, AddProfileDetail.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        fNameTV = findViewById(R.id.firstNameTVProfile);
        lNameTV = findViewById(R.id.lastNameTVProfile);
        usernameTV=findViewById(R.id.userTVProfile);
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        String cleanEmail = email.replace(".", "_");//cleaned for firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(cleanEmail);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fNameTV.setText(dataSnapshot.child("firstName").getValue(String.class));
                    lNameTV.setText(dataSnapshot.child("lastName").getValue(String.class));
                    usernameTV.setText(dataSnapshot.child("userName").getValue(String.class));
                    String profilePicUrl = dataSnapshot.child("profilePic").getValue(String.class);

                    // Now you have the values, you can use them as needed
                    // For instance, set these values to TextViews or use them elsewhere in your app
                } else {
                    // Handle the case where the user data doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors that may occur while fetching data
            }
        });

        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homePageListener);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);
    }
}