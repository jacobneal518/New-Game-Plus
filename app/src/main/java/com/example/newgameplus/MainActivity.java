package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button homeButton,signIn,signOut;
    private FirebaseAuth mAuth;
    Boolean signedIn;

    //
    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            if(signedIn==true){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Not Signed In",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signedIn = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homePageListener);
        mAuth = FirebaseAuth.getInstance();
    }
}