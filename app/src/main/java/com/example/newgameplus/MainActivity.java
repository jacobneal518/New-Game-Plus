package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {

    Button homeButton,signIn,signOut, tempButton,profileButton;

    Boolean signedIn;
    private FirebaseAuth mAuth;
    DatabaseReference mdatabase;

    //
    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            FirebaseUser currUser= mAuth.getCurrentUser();
            if(currUser!=null){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Not Signed In",Toast.LENGTH_LONG).show();
            }

        }
    };
    View.OnClickListener profilePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            FirebaseUser currUser= mAuth.getCurrentUser();
            if(currUser!=null){
                Intent intent = new Intent(MainActivity.this, ProfilePage.class);
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
    View.OnClickListener tempListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                // User is signed in, you can get the user details like email, UID, etc.
                String userEmail = currentUser.getEmail();
                Toast.makeText(getApplicationContext(),"User Email is "+userEmail,Toast.LENGTH_LONG).show();
                // ... other user details
            } else {
                Toast.makeText(getApplicationContext(),"Something went oopsie",Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signedIn = false;
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser= mAuth.getCurrentUser();
        if(currUser!=null){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homePageListener);
        signIn = findViewById(R.id.signInButtonMain);
        signIn.setOnClickListener(signInListener);
        tempButton=findViewById(R.id.tempButton);
        tempButton.setOnClickListener(tempListener);
        profileButton=findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);

        mdatabase = FirebaseDatabase.getInstance().getReference("users");
        }
   /* @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
                                                               wanted this to sign out user whenever app closed,maybe onDestroy?
        if (currentUser != null) {
            auth.signOut();
            // Additional clean-up or actions after signing out
        }
    }*/
    public void onResume(){
        super.onResume();
        FirebaseUser currUser= mAuth.getCurrentUser();
        if(currUser!=null){
            currUser.reload();
        }
    }
}
