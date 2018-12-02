package com.example.rohan.moviereview;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.SearchResultsAdapter;
import com.example.rohan.moviereview.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ArrayList<Movie> mMovies = new ArrayList<>();
    RecyclerView recyclerView;
    EditText searchEditText;
    String url = "https://api.themoviedb.org/3/search/movie?api_key=8098064858ea61ebc15f5dceabf6b02f&";
    RequestQueue myRequestQueue;
    TextView noResultTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Activity 1");

        noResultTextView = findViewById(R.id.noResultsTextView);
        recyclerView = findViewById(R.id.recyclerview);
        myRequestQueue = Volley.newRequestQueue(this);

        searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
    }

    private void init(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                String url = "https://api.themoviedb.org/3/search/movie?api_key=8098064858ea61ebc15f5dceabf6b02f&query=" + s;

                    Log.d(TAG, "afterTextChanged: " + url);
                    if(s.length() != 0) {
                        performSearch(url);
                    }


            }
        });
    }


    private void performSearch(String url){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        Log.d(TAG, "onResponse: " + jsonArray.toString());
                        if(jsonArray.length() == 0) {
                            noResultTextView.setVisibility(View.VISIBLE);
                        }
                        else{
                            noResultTextView.setVisibility(View.GONE);
                        }
                        processJSON(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                    noResultTextView.setVisibility(View.VISIBLE);
                }
            });

            myRequestQueue.add(jsonObjectRequest);

    }

    private void processJSON(JSONArray results){
        mMovies.clear();
        try {
            for (int i = 0; i < results.length(); i++) {

                JSONObject movie = results.getJSONObject(i);
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

            setupRecyclerView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupRecyclerView(){

        SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(mMovies,this);
        recyclerView.setAdapter(searchResultsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
