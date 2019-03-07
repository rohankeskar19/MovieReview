package com.example.rohan.moviereview.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.RecyclerViewLatestAdapter;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private static final String TAG = "TrendingFragment";

    RecyclerView latestMovieRecyclerView,latestTVRecyclerView;

    RequestQueue requestQueue;

    ArrayList<Movie> mMovies = new ArrayList<>();
    ArrayList<TV> mTVs = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: Starting");

        View v = inflater.inflate(R.layout.fragment_latest,container,false);

        latestMovieRecyclerView = v.findViewById(R.id.recyclerViewLatestMovies);
        latestTVRecyclerView = v.findViewById(R.id.recyclerViewLatestTV);


        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        String LATEST_MOVIE_URL = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + getString(R.string.API_KEY);
        String LATEST_TV_URL = "https://api.themoviedb.org/3/trending/tv/day?api_key=" + getString(R.string.API_KEY);

        fetchLatestMovies(LATEST_MOVIE_URL);
        fetchLatestTV(LATEST_TV_URL);

    }

    private void fetchLatestMovies(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseMovies(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void fetchLatestTV(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseTV(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void parseMovies(JSONObject response1){
        try {

            JSONArray response = response1.getJSONArray("results");
            for(int i = 0; i < response.length(); i++) {

                JSONObject movie = response.getJSONObject(i);

                Double voteCount = movie.getDouble("vote_count");
                int id = movie.getInt("id");
                Double voteAverage = movie.getDouble("vote_average");
                String title = movie.getString("title");
                int popularity = movie.getInt("popularity");
                String posterPath = movie.getString("poster_path");
                String language = movie.getString("original_language");
                String overview = movie.getString("overview");
                String releaseDate = movie.getString("release_date");
                String backdropPath = movie.getString("backdrop_path");
                boolean adult = movie.getBoolean("adult");

                Movie movieObject = new Movie(title, releaseDate, overview, adult, posterPath, language, id, voteCount, voteAverage, popularity, backdropPath);
                mMovies.add(movieObject);

            }

            setupMoviesRecyclerView();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseTV(JSONObject response1){
        try {
            JSONArray response = response1.getJSONArray("results");

            for (int i = 0; i < response.length(); i++) {
                JSONObject tv = response.getJSONObject(i);

                int id = tv.getInt("id");
                String title = tv.getString("name");
                String overview = tv.getString("overview");
                String airDate = tv.getString("first_air_date");
                Double voteAverage = tv.getDouble("vote_average");
                Double voteCount = tv.getDouble("vote_count");
                String posterPath = tv.getString("poster_path");
                String backdropPath = tv.getString("backdrop_path");
                Double popularity = tv.getDouble("popularity");
                String language = tv.getString("original_language");
                TV tv1 = new TV(title, id, posterPath, airDate, language, backdropPath, overview, voteCount, voteAverage, popularity);

                mTVs.add(tv1);


            }
            setupTVRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setupMoviesRecyclerView(){
        Object[] objects = mMovies.toArray();
        ArrayList<Object> mObjects = new ArrayList<>();
        for (int i = 0; i < objects.length; i++){
            mObjects.add(objects[i]);
        }
        RecyclerViewLatestAdapter recyclerViewLatestAdapter = new RecyclerViewLatestAdapter(getContext(),mObjects);
        latestMovieRecyclerView.setAdapter(recyclerViewLatestAdapter);
        latestMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void setupTVRecyclerView(){
        Object[] objects = mTVs.toArray();
        ArrayList<Object> mObjects = new ArrayList<>();
        for (int i = 0; i < objects.length; i++){
            mObjects.add(objects[i]);
        }
        RecyclerViewLatestAdapter recyclerViewLatestAdapter = new RecyclerViewLatestAdapter(getContext(),mObjects);
        latestTVRecyclerView.setAdapter(recyclerViewLatestAdapter);
        latestTVRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
