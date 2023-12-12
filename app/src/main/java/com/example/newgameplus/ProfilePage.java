package com.example.newgameplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    ImageButton game1;
    ImageButton game2;
    ImageButton game3;
    FirebaseAuth mAuth;
    TextView usernameTV,fNameTV,lNameTV;
    ImageView profileImage;
    private ImageView profileImg;

    View.OnClickListener homePageListener = new View.OnClickListener() {//Changes to home page
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(ProfilePage.this, HomePage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener profilePageListener = new View.OnClickListener() {//Changes to profule page
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(ProfilePage.this, AddProfileDetail.class);
            startActivity(intent);
        }
    };

    View.OnClickListener game1Listener = new View.OnClickListener() {//hardcoded for example
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProfilePage.this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key", "Fortnite");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener game2Listener = new View.OnClickListener() {//hardcoded for example
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProfilePage.this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key", "Minecraft");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    View.OnClickListener game3Listener = new View.OnClickListener() {//hardcoded for example
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProfilePage.this, GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key", "Dead By Daylight");
            intent.putExtras(bundle);
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
                if (dataSnapshot.exists()) {//once the data snapshott comes in, this assigns things to where they need to show up
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

        game1 = findViewById(R.id.imageView8);
        game1.setOnClickListener(game1Listener);
        game2 = findViewById(R.id.imageView9);
        game2.setOnClickListener(game2Listener);
        game3 = findViewById(R.id.imageView10);
        game3.setOnClickListener(game3Listener);



    }
}