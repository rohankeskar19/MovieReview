package com.example.rohan.moviereview.Models;

import java.io.Serializable;

public class TV implements Serializable {
    String title;
    int id;
    String posterPath;
    String airDate;
    String language;
    String backdropPath;
    String overview;
    Double voteCount;
    Double voteAverage;
    Double popularity;

    public TV(String title, int id, String posterPath, String airDate, String language, String backdropPath, String overview, Double voteCount, Double voteAverage, Double popularity) {
        this.title = title;
        this.id = id;
        this.posterPath = posterPath;
        this.airDate = airDate;
        this.language = language;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    public String getOriginalName() {
        return title;
    }

    public void setOriginalName(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "TV{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", posterPath='" + posterPath + '\'' +
                ", airDate='" + airDate + '\'' +
                ", language='" + language + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", overview='" + overview + '\'' +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                '}';
    }
}
