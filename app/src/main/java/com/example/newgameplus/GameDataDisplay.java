package com.example.newgameplus;

import android.graphics.drawable.Drawable;

public class GameDataDisplay {
    String name;
    String rating;

    String releaseYear;

    public GameDataDisplay(String gameName, String gameRating, String gameReleaseYear){
        name = gameName;
        rating = gameRating;
        releaseYear = gameReleaseYear;
    }

    public String getName(){
        return name;
    }

    public String getRating(){
        return rating;
    }

    public String getReleaseYear(){
        return releaseYear;
    }

}
