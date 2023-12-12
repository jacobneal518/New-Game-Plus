package com.example.newgameplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    ImageView profileImage;
    private ImageView profileImg;

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
        profileImg = findViewById(R.id.profilePicIV);
        fNameTV = findViewById(R.id.firstNameTVProfile);
        lNameTV = findViewById(R.id.lastNameTVProfile);
        usernameTV=findViewById(R.id.userTVProfile);
        mAuth = FirebaseAuth.getInstance();
        profileImg = findViewById(R.id.profilePicIV);
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
                    usernameTV.setText("@" + dataSnapshot.child("userName").getValue(String.class));
                    String profilePicUrl = dataSnapshot.child("profilePic").getValue(String.class);
                    Glide.with(getApplicationContext())//Picasso would NOT WORK ARGH
                            .load(profilePicUrl)
                            .into(profileImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //cant delete :/
            }
        });

        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homePageListener);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);
    }
}