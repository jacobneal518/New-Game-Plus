package com.example.newgameplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    public WebView webView;
    HashMap<String, String> gameURLs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        webView = findViewById(R.id.webView);

        gameURLs = new HashMap<String, String>();
        gameURLs.put("God of War", "https://store.steampowered.com/app/1593500/God_of_War/");

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