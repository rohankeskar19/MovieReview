package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Helpers.RecyclerViewExpandableAdapter;
import com.example.rohan.moviereview.Models.Season;
import com.example.rohan.moviereview.Models.TV;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TVDescriptionActivity extends AppCompatActivity {

    ImageView posterImageView,backdropImageView,backImageView;
    TextView tvTitleTextView,overviewTextView,firstAirDateTextView,lastAirDateTextView,inProductionTextView;
    RecyclerView seasonsExpandableRecyclerView;

    TV tv;
    ArrayList<Season> seasons = new ArrayList<>();
    String firstAirDate,lastAirDate;
    boolean inProduction;


    String imgUrl = "https://image.tmdb.org/t/p/w500/";
    String backdropUrl = "https://image.tmdb.org/t/p/original/";

    private static final String TAG = "TVDescriptionActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_description);

        posterImageView = findViewById(R.id.posterImage);
        backdropImageView = findViewById(R.id.backdropImageView);
        backImageView = findViewById(R.id.backImageView);


        tvTitleTextView = findViewById(R.id.tvTitle);
        overviewTextView = findViewById(R.id.tvOverview);
        firstAirDateTextView = findViewById(R.id.firstAirDate);
        lastAirDateTextView = findViewById(R.id.lastAirDate);
        inProductionTextView = findViewById(R.id.inProduction);


        seasonsExpandableRecyclerView = findViewById(R.id.seasonsRecyclerView);

        Intent intent = getIntent();

        if (intent.hasExtra("tv") && intent.hasExtra("first-air-date") && intent.hasExtra("last-air-date") && intent.hasExtra("in-production") && intent.hasExtra("season-list")){
            tv = (TV) intent.getSerializableExtra("tv");
            firstAirDate = intent.getStringExtra("first-air-date");
            lastAirDate = intent.getStringExtra("last-air-date");
            inProduction = intent.getBooleanExtra("in-production",false);
            seasons = (ArrayList<Season>) intent.getSerializableExtra("season-list");

        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVDescriptionActivity.super.onBackPressed();
            }
        });

        tvTitleTextView.setText(tv.getOriginalName());
        overviewTextView.setText(tv.getOverview());



        String firstAirDateMonth = getMonth(firstAirDate);
        String lastAirDateMonth = getMonth(lastAirDate);

        String[] firstAirDateArray = firstAirDate.split("-");
        String[] lastAirDateArray = lastAirDate.split("-");

        firstAirDateTextView.setText("First Aired : " + firstAirDateArray[2] + " " + firstAirDateMonth + " " + firstAirDateArray[0]);
        lastAirDateTextView.setText("Last Aired : " + lastAirDateArray[2] + " " + lastAirDateMonth + " " +  lastAirDateArray[0]);

        if (inProduction){
            inProductionTextView.setText("In Production : True");
        }else{
            inProductionTextView.setText("In Production : False");
        }

        Glide.with(this).load(imgUrl + tv.getPosterPath()).transition(DrawableTransitionOptions.withCrossFade(100)).into(posterImageView);
        Glide.with(this).load(backdropUrl + tv.getBackdropPath()).transition(DrawableTransitionOptions.withCrossFade(100)).into(backdropImageView);

        setupRecyclerView();

    }

    private String getMonth(String date){

        String[] dateArray = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,Integer.parseInt(dateArray[1]));

        String month = calendar.getTime().toString();
        String[] calendarArray = month.split(" ");

        return calendarArray[1];
    }


    private void setupRecyclerView(){
        Log.d(TAG, "setupRecyclerView: TV Description " + seasons.toString());
        RecyclerViewExpandableAdapter recyclerViewExpandableAdapter = new RecyclerViewExpandableAdapter(this,seasons);
        seasonsExpandableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seasonsExpandableRecyclerView.setAdapter(recyclerViewExpandableAdapter);

    }

}
