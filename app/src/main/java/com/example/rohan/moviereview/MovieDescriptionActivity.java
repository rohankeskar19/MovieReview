package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Helpers.RecyclerViewReviewsAdapter;
import com.example.rohan.moviereview.Models.Movie;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MovieDescriptionActivity extends AppCompatActivity {

    ImageView posterImageView,backDropImageView,backImageView;
    TextView titleTextView,overviewTextView,durationTextView,releaseDateTextView,genreTextView,budgetTextView,revenueTextView,reviewsTextView;
    RecyclerView reviewsRecyclerView;
    String posterImgUrl = "https://image.tmdb.org/t/p/w500/";
    String backdropImgUrl = "https://image.tmdb.org/t/p/original/";
    Movie movie;
    String duration,genre,revenue,budget;
    RequestQueue requestQueue;


    ArrayList<String> authors = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        reviewsTextView = findViewById(R.id.reviewsTextView);

        backImageView = findViewById(R.id.backImageView);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        posterImageView = findViewById(R.id.posterImage);
        backDropImageView = findViewById(R.id.backdropImage);
        titleTextView = findViewById(R.id.movieTitle);
        overviewTextView = findViewById(R.id.movieOverview);
        durationTextView = findViewById(R.id.duration);
        releaseDateTextView = findViewById(R.id.releaseDate);
        genreTextView = findViewById(R.id.genre);
        budgetTextView = findViewById(R.id.budget);
        revenueTextView = findViewById(R.id.revenue);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();

        if(intent.hasExtra("movie")){
                movie = (Movie) intent.getSerializableExtra("movie");
                duration = intent.getStringExtra("duration");
                genre = intent.getStringExtra("genre");
                revenue = intent.getStringExtra("revenue");
                budget = intent.getStringExtra("budget");
                fetchReviews();
        }

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDescriptionActivity.super.onBackPressed();
            }
        });

        String backdorpUrl = backdropImgUrl + movie.getBackdropPath();
        String posterUrl = posterImgUrl + movie.getPosterUrl();

        Glide.with(this).load(backdorpUrl).transition(DrawableTransitionOptions.withCrossFade()).into(backDropImageView);
        Glide.with(this).load(posterUrl).transition(DrawableTransitionOptions.withCrossFade()).into(posterImageView);
//
//        String[] release = movie.getRelease().split("-");
//        String month = getMonth(movie.getRelease());
//
//        String date = release[2] + " " + month + " " + release[0];
//

        titleTextView.setText(movie.getTitle());
        overviewTextView.setText(movie.getOverview());
        durationTextView.setText("Duration: " + duration);
        releaseDateTextView.setText("Release: " + movie.getRelease());
        genreTextView.setText("Genre: " + genre);
        budgetTextView.setText("Budget: $" + budget);
        revenueTextView.setText("Revenue: $" + revenue);


    }


    private void fetchReviews(){
        String URL = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=" +  getString(R.string.API_KEY)  +"&language=en-US";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    parseJSON(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void parseJSON(JSONArray result){
        try{
            for(int i = 0; i < result.length(); i++){
                JSONObject object = result.getJSONObject(i);

                authors.add(object.getString("author"));
                content.add(object.getString("content"));

            }
            setupRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupRecyclerView(){
        if (authors.size() != 0) {
            RecyclerViewReviewsAdapter recyclerViewReviewsAdapter = new RecyclerViewReviewsAdapter(authors, content, this);
            reviewsRecyclerView.setAdapter(recyclerViewReviewsAdapter);
            reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            reviewsTextView.setText("No Reviews Yet");
            reviewsTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    private String getMonth(String date){

        String[] dateArray = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,Integer.parseInt(dateArray[2]));

        String month = calendar.getTime().toString();
        String[] calendarArray = month.split(" ");

        return calendarArray[1];
    }
}
