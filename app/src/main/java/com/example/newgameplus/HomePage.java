package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    Button homeButton;
    Button profileButton;

    ArrayList<String> gameList;
    ArrayAdapter<String> adapter;
    ListView listView;

    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(HomePage.this, HomePage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener profilePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(HomePage.this, ProfilePage.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(homePageListener);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);

        listView = findViewById(R.id.gameListView);
        gameList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameList);
        listView.setAdapter(adapter);
        Log.i("ListView", "Finished initialization");
        //set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item's value
                String selectedItemValue = (String) parent.getItemAtPosition(position);

                //update the TextView with the selected item's value
                //String name = selectedItemValue.substring(0, selectedItemValue.indexOf("-") - 1);
                Intent intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
            }
        });

    }


}