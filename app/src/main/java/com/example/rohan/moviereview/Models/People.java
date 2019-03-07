package com.example.rohan.moviereview.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class People implements Serializable {
    String title;
    String profilePath;
    int id;
    Double popularity;
    ArrayList<String> knownForTitles;


    public People(String title, String profilePath, int id, Double popularity, ArrayList<String> knownForTitles) {
        this.title = title;
        this.profilePath = profilePath;
        this.id = id;
        this.popularity = popularity;
        this.knownForTitles = knownForTitles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public ArrayList<String> getKnownForTitles() {
        return knownForTitles;
    }

    public void setKnownForTitles(ArrayList<String> knownForTitles) {
        this.knownForTitles = knownForTitles;
    }
}
