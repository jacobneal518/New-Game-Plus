package com.example.newgameplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProfileDetail extends AppCompatActivity {
    Button backButton, signOut, submitButton, photoButton;
    EditText firstNameET, lastNameET, userNameET;
    ImageView tempProfile;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storRef;
    private Uri imageUri;
    StorageReference photoStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_detail);
        tempProfile = findViewById(R.id.tempProfile);
        mAuth = FirebaseAuth.getInstance();
        backButton = findViewById(R.id.detailBack);
        backButton.setOnClickListener(backButtonListen);
        signOut = findViewById(R.id.signOutButton);
        signOut.setOnClickListener(signOutButtonListen);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitButtonListener);
        photoButton = findViewById(R.id.submitPhotoButton);
        photoButton.setOnClickListener(photoButtonListen);
        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        userNameET = findViewById(R.id.customerUsernameET);
        storRef =FirebaseStorage.getInstance().getReference().child("profile_images");


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
        }
    };
    View.OnClickListener photoButtonListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choosePicture();
        }
    };

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri = data.getData();
            tempProfile.setImageURI(imageUri);
            uploadPic(imageUri);

        }
    }

    private void uploadPic(Uri uploadUri) {
        String email = mAuth.getCurrentUser().getEmail();
        String cleanEmail = email.replace(".", "_");
        StorageReference imageRef = storRef.child(imageUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(uploadUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String imgUrl = uri.toString();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("users").child(cleanEmail).child("profilePic").setValue(imgUrl)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getApplicationContext(),"Profile Pic Saved",Toast.LENGTH_LONG).show();
                        })
                        .addOnFailureListener(exception -> {
                            Toast.makeText(getApplicationContext(),"Ruh-Roh, it didn't save :(",Toast.LENGTH_LONG).show();
                        });

            });
        }).addOnFailureListener(exception -> {
            Toast.makeText(getApplicationContext(),"Ruh-Roh, it didnt save :(",Toast.LENGTH_LONG).show();
        });

        /*        Old method

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("user").child(cleanEmail).child("profImgUri").setValue(uploadUri)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(),"Profile Pic Saved",Toast.LENGTH_LONG).show();

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),"Ruh-Roh, it didnt save :(",Toast.LENGTH_LONG).show();

                });*/
    };

    }
