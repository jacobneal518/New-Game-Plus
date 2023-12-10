package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button homeButton,signIn,signOut;

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
    View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            //Log.i()
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signedIn = false;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homePageListener);
        signIn = findViewById(R.id.signInButtonMain);
        signIn.setOnClickListener(signInListener);
        }
}