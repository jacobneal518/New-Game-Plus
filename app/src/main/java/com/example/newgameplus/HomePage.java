package com.example.newgameplus;
/*
Home page code
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    Button homeButton;
    Button profileButton;

    ArrayList<GameDataDisplay> gameList;
    ArrayAdapter<String> adapter;
    ListView listView;

    ArrayList<Integer> imageList;

    //go to home button
    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(HomePage.this, HomePage.class);
            startActivity(intent);
        }
    };

    //go to profile button
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

        homeButton = findViewById(R.id.homeButtonLogin);
        homeButton.setOnClickListener(homePageListener);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(profilePageListener);

        listView = findViewById(R.id.gameListView);
        gameList = new ArrayList<>();
        imageList = new ArrayList<>();
        //add valid games
        gameList.add(new GameDataDisplay("God of War", "M", "2018"));
        imageList.add(R.drawable.god_of_war);

        gameList.add(new GameDataDisplay("Lethal Company", "T", "2023"));
        imageList.add(R.drawable.lethal_company);

        gameList.add(new GameDataDisplay("Elden Ring", "M", "2022"));
        imageList.add(R.drawable.elden_ring);

        gameList.add(new GameDataDisplay("Red Dead Redemption 2", "M", "2018"));
        imageList.add(R.drawable.rdr2);

        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameList);
        //set custom adapter to allow for complex display
        ArrayAdapter<GameDataDisplay> adapter = new ArrayAdapter<GameDataDisplay>(this, android.R.layout.simple_list_item_1, gameList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.row_layout, parent, false);
                }

                GameDataDisplay dataModel = getItem(position);

                // Populate your custom layout with data from YourDataModel
                TextView textView1 = convertView.findViewById(R.id.textView1);
                TextView textView2 = convertView.findViewById(R.id.textView2);
                TextView textView3 = convertView.findViewById(R.id.textView3);
                // Find additional TextViews for additional columns

                textView1.setText(dataModel.getName());
                textView2.setText("Rating: " + dataModel.getRating());
                textView3.setText(dataModel.getReleaseYear());

                ImageView imageView = convertView.findViewById(R.id.imageView);
                imageView.setBackgroundResource(imageList.get(position));
                // Set text for additional columns

                return convertView;
            }
        };

        listView.setAdapter(adapter);
        Log.i("ListView", "Finished initialization");
        //set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected item's value
                GameDataDisplay selectedItemValue = (GameDataDisplay) parent.getItemAtPosition(position);

                //update the TextView with the selected item's value
                //String name = selectedItemValue.substring(0, selectedItemValue.indexOf("-") - 1);
                Intent intent = new Intent(HomePage.this, GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", selectedItemValue.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


}