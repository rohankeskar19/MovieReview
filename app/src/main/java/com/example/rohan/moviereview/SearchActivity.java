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
import com.example.rohan.moviereview.Models.People;
import com.example.rohan.moviereview.Models.TV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ArrayList<Object> searchResults = new ArrayList<>();
    RecyclerView recyclerView;
    EditText searchEditText;
    String url;
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


                String url = "https://api.themoviedb.org/3/search/multi?api_key="+ getString(R.string.API_KEY) + "&query="+ s;

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
        searchResults.clear();
        try {
            for (int i = 0; i < results.length(); i++) {

                JSONObject searchObject = results.getJSONObject(i);

                if (searchObject.getString("media_type").equals("movie")){
                    Double voteCount = searchObject.getDouble("vote_count");
                    int id = searchObject.getInt("id");
                    Double voteAverage = searchObject.getDouble("vote_average");
                    String title = searchObject.getString("title");
                    int popularity = searchObject.getInt("popularity");
                    String posterPath = searchObject.getString("poster_path");
                    String language = searchObject.getString("original_language");
                    String overview = searchObject.getString("overview");
                    String releaseDate = searchObject.getString("release_date");
                    String backdropPath = searchObject.getString("backdrop_path");
                    boolean adult = searchObject.getBoolean("adult");


                    Movie movieObject = new Movie(title, releaseDate, overview, adult, posterPath, language, id, voteCount, voteAverage, popularity, backdropPath);
                    searchResults.add(movieObject);
                }
                else if(searchObject.getString("media_type").equals("tv")){
                    String name = searchObject.getString("original_name");
                    int id = searchObject.getInt("id");
                    Double voteCount = searchObject.getDouble("vote_count");
                    Double voteAverage = searchObject.getDouble("vote_average");
                    String posterPath = searchObject.getString("poster_path");
                    String airDate = searchObject.getString("first_air_date");
                    String originalLanguage = searchObject.getString("original_language");
                    String backdropPath = searchObject.getString("backdrop_path");
                    String overview = searchObject.getString("overview");
                    Double popularity = searchObject.getDouble("popularity");


                    TV tv = new TV(name,id,posterPath,airDate,originalLanguage,backdropPath,overview,voteCount,voteAverage,popularity);
                    searchResults.add(tv);

                }
                else if(searchObject.getString("media_type").equals("person")){
                    int id = searchObject.getInt("id");
                    String profilePath = searchObject.getString("profile_path");
                    String name = searchObject.getString("name");
                    Double popularity = searchObject.getDouble("popularity");

                    ArrayList<String> knownFor = new ArrayList<>();

                    JSONArray jsonArray = searchObject.getJSONArray("known_for");

                    for (int j = 0; j < jsonArray.length(); j++){
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        if (jsonObject.getString("media_type").equals("tv")){
                            Log.d(TAG, "processJSON: inside tv");
                            knownFor.add(jsonObject.getString("original_name"));
                        }
                        else if (jsonObject.getString("media_type").equals("movie")){
                            Log.d(TAG, "processJSON: inside movie");
                            knownFor.add(jsonObject.getString("original_title"));
                        
                        }


                    }
                    People people = new People(name,profilePath,id,popularity,knownFor);
                    searchResults.add(people);
                }





            }

            setupRecyclerView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupRecyclerView(){

        SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter(searchResults,this);
        recyclerView.setAdapter(searchResultsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
