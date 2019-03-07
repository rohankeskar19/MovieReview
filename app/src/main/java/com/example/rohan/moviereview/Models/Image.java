package com.example.rohan.moviereview.Models;

import java.io.Serializable;

public class Image implements Serializable {

    String url;
    String imageType;


    public Image(String url, String imageType) {
        this.url = url;
        this.imageType = imageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
