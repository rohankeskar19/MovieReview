package com.example.rohan.moviereview.Models;

import java.io.Serializable;
import java.util.Arrays;

public class Movie implements Serializable {

    String title;
    String release;
    String overview;
    boolean adult;
    String posterUrl;
    String language;
    int id;
    Double voteCount;
    Double voteAverage;
    int popularity;
    String backdropPath;
    int duration;

    public Movie(String title, String release, String overview, boolean adult, String posterUrl, String language, int id, Double voteCount, Double voteAverage, int popularity, String backdropPath) {
        this.title = title;
        this.release = release;
        this.overview = overview;
        this.adult = adult;
        this.posterUrl = posterUrl;
        this.language = language;
        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.backdropPath = backdropPath;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Double voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", release='" + release + '\'' +
                ", overview='" + overview + '\'' +
                ", adult=" + adult +
                ", posterUrl='" + posterUrl + '\'' +
                ", language='" + language + '\'' +
                ", id=" + id +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }
}
