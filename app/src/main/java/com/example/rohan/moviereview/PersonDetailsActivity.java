package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Helpers.BoxStyleRecyclerViewAdapter;
import com.example.rohan.moviereview.Helpers.PersonRecyclerViewAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewTVAdapter;
import com.example.rohan.moviereview.Models.Cast;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.TV;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonDetailsActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private ArrayList<TV> tvs = new ArrayList<>();

    RecyclerView staggeredRecyclerView,movieRecyclerView,tvRecyclerView;

    TextView actorName,birthDate,biography;
    ImageView profileImageView;

    LinearLayout moviesLayout,tvLayout;

    RelativeLayout shimmerLayout;
    private static final String TAG = "PersonDetailsActivity";
    Cast actor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        requestQueue = Volley.newRequestQueue(this);

        staggeredRecyclerView = findViewById(R.id.staggeredRecyclerView);
        movieRecyclerView = findViewById(R.id.recyclerviewMovie);
        tvRecyclerView = findViewById(R.id.recyclerviewTV);

        moviesLayout = findViewById(R.id.filmographyLayout);
        tvLayout = findViewById(R.id.linearLayout2);


        shimmerLayout = findViewById(R.id.shimmerLayout);
        actorName = findViewById(R.id.personsNameTextView);
        biography = findViewById(R.id.biographyTextView);
        profileImageView = findViewById(R.id.profileImageView);
        birthDate = findViewById(R.id.birthDate);

        Intent intent = getIntent();
        if (intent.hasExtra("cast")){
            actor = (Cast) intent.getSerializableExtra("cast");
        }
        Log.d(TAG, "onCreate: " + actor.getId());
        String DETAILS_URL = "https://api.themoviedb.org/3/person/" + actor.getId() + "?api_key=" + getString(R.string.API_KEY) + "&language=en-US";
        String IMAGES_URL = "https://api.themoviedb.org/3/person/" + actor.getId() + "/images?api_key=" + getString(R.string.API_KEY);
        String CREDITS_URL = "https://api.themoviedb.org/3/person/" + actor.getId() + "/combined_credits?api_key=" + getString(R.string.API_KEY) + "&language=en-US";
        fetchData(DETAILS_URL,IMAGES_URL,CREDITS_URL);
    }


    private void fetchData(String detailsURl,String imagesURL,String creditsURL){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(detailsURl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String name = response.getString("name");
                    String birthDate1 = response.getString("birthday");
                    String biography1 = response.getString("biography");
                    String placeOfBirth = response.getString("place_of_birth");


                    String profileURL = response.getString("profile_path");
                    String url = "https://image.tmdb.org/t/p/w500/" + profileURL;

                    String[] birth = birthDate1.split("-");
                    String birthInfo = "No Data Available";
                    if (birth[0] != "null" && placeOfBirth != "null") {
                       birthInfo = birth[0] + " " + placeOfBirth;
                        Log.d(TAG, "onResponse: " + birth[0] + " " + placeOfBirth);
                    }
                    if (biography1.equals("")){
                        biography1 = "No Data Available";
                    }



                    birthDate.setText(birthInfo);
                    biography.setText(biography1);
                    actorName.setText(name);
                    Glide.with(PersonDetailsActivity.this).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(profileImageView);


                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(imagesURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseImages(response.getJSONArray("profiles"));
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(creditsURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseCredits(response.getJSONArray("cast"));

                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Error");
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
        requestQueue.add(jsonObjectRequest1);
        requestQueue.add(jsonObjectRequest2);
    }


    private void parseCredits(JSONArray jsonArray){
        try{
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject credit = jsonArray.getJSONObject(i);

                if(credit.getString("media_type").equals("movie")){
                    try{
                        int id = credit.getInt("id");
                        String title = credit.getString("title");
                        Log.d(TAG, "parseCredits: Title " + title);
                        String overview = credit.getString("overview");
                        String releaseDate = credit.getString("release_date");
                        Log.d(TAG, "parseCredits: releaseDate " + "  " + i + releaseDate);
                        double voteAverage = credit.getDouble("vote_average");
                        String language = credit.getString("original_language");
                        String backdropPath = credit.getString("backdrop_path");
                        String posterPath = credit.getString("poster_path");
                        boolean adult = credit.getBoolean("adult");
                        int popularity = credit.getInt("popularity");
                        Double voteCount  = credit.getDouble("vote_count");

                        Movie movie = new Movie(title,releaseDate,overview,adult,posterPath,language,id,voteCount,voteAverage,popularity,backdropPath);
                        mMovies.add(movie);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }else if (credit.getString("media_type").equals("tv")){
                    try{
                        int id = credit.getInt("id");
                        String title = credit.getString("name");
                        String overview = credit.getString("overview");
                        String airDate = credit.getString("first_air_date");
                        Double voteAverage = credit.getDouble("vote_average");
                        Double voteCount = credit.getDouble("vote_count");
                        String posterPath = credit.getString("poster_path");
                        String backdropPath = credit.getString("backdrop_path");
                        Double popularity = credit.getDouble("popularity");
                        String language = credit.getString("original_language");
                        TV tv = new TV(title,id,posterPath,airDate,language,backdropPath,overview,voteCount,voteAverage,popularity);

                        tvs.add(tv);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
            setupMovieRecyclerview();
            setupTVRecyclerview();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseImages(JSONArray jsonArray){
        try{
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String url = jsonObject.getString("file_path");
                if (url != null) {
                    imageUrls.add(url);
                }
            }
            if (imageUrls.size() > 1) {
                setupRecyclerView();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupRecyclerView(){
        PersonRecyclerViewAdapter personRecyclerViewAdapter = new PersonRecyclerViewAdapter(imageUrls,this);
        GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        staggeredRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        staggeredRecyclerView.setAdapter(personRecyclerViewAdapter);

    }

    private void setupMovieRecyclerview(){
        if (mMovies.size() == 0){
            moviesLayout.setVisibility(View.GONE);
        }
        Log.d(TAG, "setupMovieRecyclerview: " + mMovies.toString());
        BoxStyleRecyclerViewAdapter boxStyleRecyclerViewAdapter = new BoxStyleRecyclerViewAdapter(mMovies,this);
        movieRecyclerView.setAdapter(boxStyleRecyclerViewAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void setupTVRecyclerview(){
        shimmerLayout.setVisibility(View.GONE);
        Log.d(TAG, "setupTVRecyclerview: " + tvs.toString());
        if (tvs.size() == 0){
            tvLayout.setVisibility(View.GONE);
        }
        RecyclerViewTVAdapter recyclerViewTVAdapter = new RecyclerViewTVAdapter(tvs,this,1);
        tvRecyclerView.setAdapter(recyclerViewTVAdapter);
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }
}
