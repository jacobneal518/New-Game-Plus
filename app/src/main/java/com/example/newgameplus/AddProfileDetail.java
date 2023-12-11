package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProfileDetail extends AppCompatActivity {
    Button backButton,signOut,submitButton;
    EditText firstNameET,lastNameET,userNameET;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_detail);
        mAuth = FirebaseAuth.getInstance();
        backButton = findViewById(R.id.detailBack);
        backButton.setOnClickListener(backButtonListen);
        signOut = findViewById(R.id.signOutButton);
        signOut.setOnClickListener(signOutButtonListen);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitButtonListener);
        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        userNameET = findViewById(R.id.customerUsernameET);


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
    View.OnClickListener submitButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mAuth.getCurrentUser().getEmail();
            String cleanEmail = email.replace(".", "_");//cleaned for firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users");
            ref.child(cleanEmail).child("firstName").setValue(firstNameET.getText().toString());
            ref.child(cleanEmail).child("lastName").setValue(lastNameET.getText().toString());
            ref.child(cleanEmail).child("userName").setValue(userNameET.getText().toString());
            ref.child(cleanEmail).child("profilePic").setValue("");
        }
    };
}