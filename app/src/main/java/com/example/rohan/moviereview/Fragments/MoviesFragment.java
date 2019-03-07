package com.example.rohan.moviereview.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.BoxStyleRecyclerViewAdapter;
import com.example.rohan.moviereview.Helpers.ParallaxTransformer;
import com.example.rohan.moviereview.Helpers.ViewPagerMovieAdapter;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.SeeAllActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    //Urls
    String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";
    String POPULAR_MOVIE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";
    String TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";
    String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";


    private static final String TAG = "MoviesFragment";
    RequestQueue requestQueue;
    ViewPager movieViewPager;
    RecyclerView popularRecyclerView,topRatedRecyclerView,upComingRecyclerView;
    TabLayout tabLayout;

    private ArrayList<Movie> mMoviesNowPlaying = new ArrayList<>();
    private ArrayList<Movie> mMoviesPopular = new ArrayList<>();
    private ArrayList<Movie> mMoviesTopRated = new ArrayList<>();
    private ArrayList<Movie> mMoviesUpComing = new ArrayList<>();

    private TextView popularSeeAllTextView,topRatedSeeAllTextView,upcomingSeeAllTextView;



    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Starting");
        v = inflater.inflate(R.layout.fragment_movies, container, false);
        popularRecyclerView = v.findViewById(R.id.recyclerviewPopular);
        topRatedRecyclerView = v.findViewById(R.id.recyclerviewTopRated);
        upComingRecyclerView = v.findViewById(R.id.recyclerviewUpComing);




        popularSeeAllTextView = v.findViewById(R.id.popularSeeAll);
        topRatedSeeAllTextView = v.findViewById(R.id.topRatedSeeAll);
        upcomingSeeAllTextView = v.findViewById(R.id.upcomingSeeAll);

        tabLayout = v.findViewById(R.id.tab_layout);
        movieViewPager = v.findViewById(R.id.movieViewPager);
        init();
        return v;

    }


    private void init(){
        popularSeeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SeeAllActivity.class);
                intent.putExtra("list-name","popular");
                startActivity(intent);
            }
        });

        topRatedSeeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SeeAllActivity.class);
                intent.putExtra("list-name","toprated");
                startActivity(intent);
            }
        });

        upcomingSeeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SeeAllActivity.class);
                intent.putExtra("list-name","upcoming");
                startActivity(intent);
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        String local = getContext().getResources().getConfiguration().locale.getCountry();
        mMoviesNowPlaying.clear();
        mMoviesPopular.clear();
        mMoviesTopRated.clear();
        mMoviesUpComing.clear();
        Log.d(TAG, "onCreate: " + local);
        fetchData();
    }

    private void fetchData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NOW_PLAYING_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");


                    parseData(jsonArray,0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, POPULAR_MOVIE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    parseData(jsonArray,1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, TOP_RATED_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    parseData(jsonArray,2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, UPCOMING_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("results");
                    parseData(jsonArray,3);
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
        requestQueue.add(jsonObjectRequest1);
        requestQueue.add(jsonObjectRequest2);
        requestQueue.add(jsonObjectRequest3);
    }

    private void parseData(JSONArray response,int arrayChoice){

        try {
            for(int i = 0; i < response.length(); i++){

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
                if(arrayChoice == 0) {
                    mMoviesNowPlaying.add(movieObject);
                }
                else if(arrayChoice == 1){
                    mMoviesPopular.add(movieObject);
                }
                else if(arrayChoice == 2){
                    mMoviesTopRated.add(movieObject);
                }
                else if(arrayChoice == 3){
                    mMoviesUpComing.add(movieObject);
                }

            }

            if(arrayChoice == 0) {
                setupViewPager();
            }
            else if(arrayChoice == 1){
                setupRecyclerView();
            }
            else if(arrayChoice == 2){
                setupTopRatedRecyclerView();
            }
            else if(arrayChoice == 3){
                setupUpcomingRecyclerView();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    private void setupViewPager(){
        Log.d(TAG, "setupViewPager: " + mMoviesNowPlaying.toString());
        ViewPagerMovieAdapter viewPagerAdapter = new ViewPagerMovieAdapter(getContext(),mMoviesNowPlaying);
        movieViewPager.setAdapter(viewPagerAdapter);
        movieViewPager.setPageTransformer(false,new ParallaxTransformer());
        tabLayout.setupWithViewPager(movieViewPager);
    }

    private void setupRecyclerView(){
        Log.d(TAG, "setupViewPager: " + mMoviesPopular.toString());
        Log.d(TAG, "setupRecyclerView: size " + mMoviesPopular.size());
        BoxStyleRecyclerViewAdapter recyclerViewAdapter = new BoxStyleRecyclerViewAdapter(mMoviesPopular,getContext());

        popularRecyclerView.setAdapter(recyclerViewAdapter);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayout.HORIZONTAL,false));
    }

    private void setupTopRatedRecyclerView(){
        Log.d(TAG, "setupViewPager: " + mMoviesTopRated.toString());

        BoxStyleRecyclerViewAdapter recyclerViewAdapter = new BoxStyleRecyclerViewAdapter(mMoviesTopRated,getContext());
        topRatedRecyclerView.setAdapter(recyclerViewAdapter);
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayout.HORIZONTAL,false));
    }

    private void setupUpcomingRecyclerView(){
        Log.d(TAG, "setupViewPager: " + mMoviesUpComing.toString());
        BoxStyleRecyclerViewAdapter recyclerViewAdapter = new BoxStyleRecyclerViewAdapter(mMoviesUpComing,getContext());
        upComingRecyclerView.setAdapter(recyclerViewAdapter);
        upComingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayout.HORIZONTAL,false));

    }

}
