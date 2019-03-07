package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.RecyclerViewDynamicAdapter;
import com.example.rohan.moviereview.Helpers.SearchResultsAdapter;
import com.example.rohan.moviereview.Models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class SeeAllActivity extends AppCompatActivity {

    Button viewType;

    String urlType;
    String URL;
    int totalPages,currentPage;
    ArrayList<Movie> mMovies = new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    boolean isScrolling = false;
    int currentItems,scrolledOutItems,totalItems;
    String url = "";
    ProgressBar progressBar;
    RecyclerViewDynamicAdapter recyclerViewDynamicAdapter;
    LinearLayout linearLayout;
    private static final String TAG = "SeeAllActivity";
    LinkedHashSet<String> genreTypes = new LinkedHashSet<>();
    RecyclerView.LayoutManager manager;
    boolean linearLayoutManager = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        viewType = findViewById(R.id.btnViewType);

        progressBar = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerview);
        linearLayout = findViewById(R.id.linearLayout);
        currentPage = 1;
        Intent intent = getIntent();

        if(intent.hasExtra("list-name")){
            urlType = intent.getStringExtra("list-name");
        }


        if(urlType.equals("popular")){
            url = "https://api.themoviedb.org/3/movie/popular?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=";

        }
        else if(urlType.equals("toprated")){
            url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=";

        }
        else if(urlType.equals("upcoming")){
            url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=";
        }
        URL = url + currentPage;
        fetchData(URL,true);

        viewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linearLayoutManager){
                    manager = new GridLayoutManager(SeeAllActivity.this,3);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            viewType.setBackground(getDrawable(R.drawable.ic_list));
                        }
                    }
                    recyclerViewDynamicAdapter = new RecyclerViewDynamicAdapter(mMovies,SeeAllActivity.this,false);
                    linearLayoutManager = false;
                }
                else{
                    manager = new LinearLayoutManager(SeeAllActivity.this);
                    recyclerView.setLayoutManager(manager);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            viewType.setBackground(getDrawable(R.drawable.ic_view_column));
                        }
                    }
                    recyclerViewDynamicAdapter = new RecyclerViewDynamicAdapter(mMovies,SeeAllActivity.this,true);

                    linearLayoutManager = true;
                }
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(recyclerViewDynamicAdapter);


            }
        });

    }




    private void fetchData(String url, final boolean initialFetch){
        Log.d(TAG, "fetchData: url: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    totalPages = response.getInt("total_pages");
                    JSONArray results = response.getJSONArray("results");
                    parseData(results,initialFetch);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void parseData(JSONArray response,boolean initialFetch){
        try {
            for (int i = 0; i < response.length(); i++) {


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
            if (initialFetch) {
                setupRecyclerView();
            }
            else {
                setupRecyclerView2();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setupRecyclerView2(){
        progressBar.setVisibility(View.GONE);
        recyclerViewDynamicAdapter.notifyDataSetChanged();
    }
    private void setupRecyclerView(){
        manager = new LinearLayoutManager(this);
        recyclerViewDynamicAdapter = new RecyclerViewDynamicAdapter(mMovies,this,true);
        recyclerView.setAdapter(recyclerViewDynamicAdapter);

        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();

                if (isScrolling && currentItems + scrolledOutItems == totalItems && currentPage < totalPages){

                    Log.d(TAG, "onScrolled: before splitting " + URL);
                    String length = Integer.toString(currentPage);
                    Log.d(TAG, "onScrolled: Current Page " + currentPage);
                    Log.d(TAG, "onScrolled: Length " + length);
                    URL = URL.substring(0,URL.length() - length.length());
                    currentPage++;
                    Log.d(TAG, "onScrolled: After splitting " + URL);
                    URL = URL + currentPage;
                    Log.d(TAG, "onScrolled: After appending " + URL);
                    progressBar.setVisibility(View.VISIBLE);
                    fetchData(URL,false);
                }
            }
        });
    }


}
