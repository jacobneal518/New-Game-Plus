package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    public WebView webView;
    HashMap<String, String> gameURLs;
    Button homeButton;
    Button profileButton;

    View.OnClickListener homePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(GameActivity.this, HomePage.class);
            startActivity(intent);
        }
    };

    View.OnClickListener profilePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //opens DB activity
            Intent intent = new Intent(GameActivity.this, ProfilePage.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        homeButton = findViewById(R.id.homeButtonLogin);
        profileButton = findViewById(R.id.profileButton);
        homeButton.setOnClickListener(homePageListener);
        profileButton.setOnClickListener(profilePageListener);

        webView = findViewById(R.id.webView);

        gameURLs = new HashMap<String, String>();
        gameURLs.put("God of War", "https://store.steampowered.com/app/1593500/God_of_War/");
        gameURLs.put("Lethal Company", "https://store.steampowered.com/app/1966720/Lethal_Company/");
        gameURLs.put("Minecraft Dungeons", "https://store.steampowered.com/app/1672970/Minecraft_Dungeons/");
        gameURLs.put("Red Dead Redemption 2", "https://store.steampowered.com/app/1174180/Red_Dead_Redemption_2/");
        gameURLs.put("Elden Ring", "https://store.steampowered.com/app/1245620/ELDEN_RING/");
        gameURLs.put("Fortnite", "https://www.fortnite.com/?lang=en-US");
        gameURLs.put("Minecraft", "https://www.minecraft.net/en-us");
        gameURLs.put("Dead By Daylight", "https://store.steampowered.com/app/381210/Dead_by_Daylight/");

        String receivedGame = "google.com";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receivedGame = bundle.getString("key");
        }
        String url = gameURLs.get(receivedGame);
        updateWebsite(url);
    }

    /**
     * Updates website displayed
     * @param url
     */
    public void updateWebsite(String url){

        Log.i("console", "Tried to update website to: " + url);
        WebView myWebview = webView;
        myWebview.getSettings().setJavaScriptEnabled(true);
        myWebview.getSettings().setDomStorageEnabled(true);
        myWebview.setWebViewClient(new WebViewClient());
        myWebview.loadUrl(url);

        myWebview.clearView();
        myWebview.measure(100, 100);
        myWebview.getSettings().setUseWideViewPort(true);
        myWebview.getSettings().setLoadWithOverviewMode(true);
    }
}