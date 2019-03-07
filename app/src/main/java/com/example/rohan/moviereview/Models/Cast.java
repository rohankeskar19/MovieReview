package com.example.rohan.moviereview.Models;

import java.io.Serializable;

public class Cast implements Serializable {
    String character;
    String name;
    String profileurl;
    int id;


    public Cast(String character, String name, String profileurl, int id) {
        this.character = character;
        this.name = name;
        this.profileurl = profileurl;
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
