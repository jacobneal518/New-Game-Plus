package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button signUp,signIn,homeButton,signOutButton;
    EditText usernameET,passwordET;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        signIn = findViewById(R.id.signInButton);
        signIn.setOnClickListener(signInButtonListener);
        signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(signUpButtonListener);
        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homeButtonListener);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        signOutButton =findViewById(R.id.signOut);
        signOutButton.setOnClickListener(signOutButtonListener);
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    View.OnClickListener homeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener signInButtonListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            if(email!=null&&password!=null){
                if(isValidEmail(usernameET.getText().toString())==true){
                    signIn(email,password);
                }else{
                    Toast.makeText(getApplicationContext(),"Username must be an email",Toast.LENGTH_LONG).show();
                }

        }
        }
    };
    View.OnClickListener signOutButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                mAuth.signOut();
                // Additional clean-up or actions after signing out
            }
        }
    };
    View.OnClickListener signUpButtonListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = usernameET.getText().toString();
            String password = passwordET.getText().toString();

            if(isValidEmail(email) && isValidPassword(password)) {
                signUp(email,password);
            } else {
                // Displays a message if email or password is invalid
                if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(),"Username must be an email",Toast.LENGTH_LONG).show();
                }
                if (!isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(),"Password is not complex enough",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    //fake credentials for testing: jordanm.anderson@bruins.com
    //pass is FakePassForProj42!
    //or smth like that
    public void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignIn", "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Sign in Succesful",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void signUp(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success, save new user in Freebase
                            Log.d("Sign Up", "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Signup Succesful",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
// If sign up fails, display a message to the user.
                            Exception e = task.getException();
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void onStart(){
        super.onStart();
        FirebaseUser currUser= mAuth.getCurrentUser();
        if(currUser!=null){
            currUser.reload();
        }

    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private boolean isValidPassword(String password){
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
}