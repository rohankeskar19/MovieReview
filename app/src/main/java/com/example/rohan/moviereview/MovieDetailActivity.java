package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Helpers.BoxStyleRecyclerViewAdapter;
import com.example.rohan.moviereview.Helpers.ParallaxTransformer;
import com.example.rohan.moviereview.Helpers.RecyclerViewCastAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewImagesAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewVideosAdapter;
import com.example.rohan.moviereview.Helpers.ViewPagerYoutubeAdapter;
import com.example.rohan.moviereview.Helpers.YoutubeApiClient;
import com.example.rohan.moviereview.Models.Cast;
import com.example.rohan.moviereview.Models.Image;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.YoutubeVideo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailActivity extends YouTubeBaseActivity{


    private ArrayList<YoutubeVideo> videoURLS = new ArrayList<>();
    private ArrayList<Cast> actors = new ArrayList<>();
    private ArrayList<Movie> relatedMovies = new ArrayList<>();
    private ArrayList<Image> imgURL = new ArrayList<>();

    Movie movie;

    TextView movieTitleTextView,basicInfoTextView,genreTextView,overviewTextView;
    ImageView posterImageView;
    RequestQueue requestQueue;
    RecyclerView recyclerView,recyclerViewRelated,recyclerViewImages,recyclerViewVideos;
    LinearLayout relatedMoviesLayout,imagesLinearLayout;
    boolean videoIsAvailable;
    TabLayout tabLayout;

    private static final String TAG = "MovieDetailActivity";
    LinearLayout linearLayout1;

    boolean fullScreen;

    ImageView playButton;

    RelativeLayout shimmerFrameLayout;
    ViewPager viewPager;

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        linearLayout1 = findViewById(R.id.linearLayout2);
        requestQueue = Volley.newRequestQueue(this);

        tabLayout = findViewById(R.id.tab_layout);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        viewPager = findViewById(R.id.videosViewPager);

        imagesLinearLayout = findViewById(R.id.images);


        fullScreen = false;

        recyclerView = findViewById(R.id.recyclerviewCast);
        recyclerViewRelated = findViewById(R.id.recyclerviewRelated);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        basicInfoTextView = findViewById(R.id.basicDataTextView);
        genreTextView = findViewById(R.id.genreTextView);
        overviewTextView = findViewById(R.id.overviewTextView);
        posterImageView = findViewById(R.id.posterImage);
        relatedMoviesLayout = findViewById(R.id.related);
        Intent intent = getIntent();

        recyclerViewImages = findViewById(R.id.recyclerViewImages);




        if(intent.hasExtra("movie")){
            movie = (Movie) intent.getSerializableExtra("movie");
        }
        Log.d(TAG, "onCreate: Movie ID : " + movie.getId());
        videoIsAvailable = false;
        String url = "https://api.themoviedb.org/3/movie/"+ movie.getId() + "?api_key="+ getString(R.string.API_KEY);
        String videosUrl = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key="+ getString(R.string.API_KEY);
        String castUrl = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/credits?api_key="+ getString(R.string.API_KEY);
        String similarUrl = "https://api.themoviedb.org/3/movie/"+ movie.getId() + "/similar?api_key="+ getString(R.string.API_KEY);
        String imagesURL = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/images?api_key="+ getString(R.string.API_KEY);
        setupBasicData();
        fetchData(url);
        fetchVideos(videosUrl);
        fetchCast(castUrl);
        fetchRelated(similarUrl);
        fetchImages(imagesURL);

    }

    private void fetchRelated(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray movies = response.getJSONArray("results");
                    processMovieJSON(movies);
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

    private void processMovieJSON(JSONArray movies){
        try{
            for(int i = 0;i < movies.length(); i++){
                JSONObject movie = movies.getJSONObject(i);
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
                relatedMovies.add(movieObject);
            }
            setupRecyclerView1();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupRecyclerView1(){
        if (relatedMovies.size() == 0){
            relatedMoviesLayout.setVisibility(View.GONE);
        }
        BoxStyleRecyclerViewAdapter recyclerViewAdapter = new BoxStyleRecyclerViewAdapter(relatedMovies,this);
        recyclerViewRelated.setAdapter(recyclerViewAdapter);
        recyclerViewRelated.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void fetchCast(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray cast = response.getJSONArray("cast");
                    processCastJSON(cast);
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

    private void processCastJSON(JSONArray cast){
        try{
            for(int i = 0; i < cast.length(); i++){
                JSONObject celeb = cast.getJSONObject(i);
                Cast cast1 = new Cast(celeb.getString("character"),celeb.getString("name"),celeb.getString("profile_path"),celeb.getInt("id"));
                actors.add(cast1);
            }
            setupRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupBasicData(){

        String posterUrl = "https://image.tmdb.org/t/p/w500/" + movie.getPosterUrl();
        Glide.with(this).load(posterUrl).transition(DrawableTransitionOptions.withCrossFade()).into(posterImageView);
        String title = movie.getTitle();


        movieTitleTextView.setText(movie.getTitle());
        String[] release = movie.getRelease().split("-");
        String rated;
        if(movie.isAdult()){
            rated = "Rated";
        }else{
            rated = "Not Rated";
        }

        String basicInfo = release[0] + "\t" + rated ;
        movieTitleTextView.setText(title);
        basicInfoTextView.setText(basicInfo);
        overviewTextView.setText(movie.getOverview());
    }

    private void setupData(final JSONObject movie1, JSONArray genres){
        try{

            String title = movie1.getString("title");
            String overview = movie1.getString("overview");

            String[] release = movie1.getString("release_date").split("-");

            boolean adult = movie1.getBoolean("adult");
            String rated;
            int duration = movie1.getInt("runtime");
            String duration1 = Integer.toString(duration);
            if(adult){
                rated = "Rated";
            }else{
                rated = "Not Rated";
            }
            final int hours = duration / 60; //since both are ints, you get an int
            final int minutes = duration % 60;
            final String time = hours + " h " + minutes + "m";
            String basicInfo = release[0] + "\t" + rated + "\t" + time;

            String genre = "";
            for (int i = 0; i < genres.length(); i++){
                JSONObject jsonObject = genres.getJSONObject(i);
                genre += jsonObject.getString("name") + ", ";
            }

            movie.setDuration(duration);
            genre = genre.substring(0,genre.length() - 2);
            final String genre1 = genre;
            genreTextView.setText(genre);
            movieTitleTextView.setText(title);
            basicInfoTextView.setText(basicInfo);
            overviewTextView.setText(overview);
            int budget = movie1.getInt("budget");
            final String budget1 = Integer.toString(budget);
            int revenue = movie1.getInt("revenue");
            final String revenue1 = Integer.toString(revenue);




            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MovieDetailActivity.this,MovieDescriptionActivity.class);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MovieDetailActivity.this,posterImageView,ViewCompat.getTransitionName(posterImageView));
                    intent1.putExtra("movie",movie);
                    intent1.putExtra("duration",time);
                    intent1.putExtra("genre",genre1);
                    intent1.putExtra("budget",budget1);
                    intent1.putExtra("revenue",revenue1);
                    intent1.putExtra("genre",genre1);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(intent1,activityOptionsCompat.toBundle());
                    }
                    else{
                        startActivity(intent1);
                    }
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void fetchImages(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseImages(response.getJSONArray("posters"),response.getJSONArray("backdrops"));
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

    private void parseImages(JSONArray posters,JSONArray backdrops){
        try{
            for (int i = 0; i < posters.length(); i++){
                Image image = new Image(posters.getJSONObject(i).getString("file_path"),"poster");
                imgURL.add(image);
            }
            for (int i = 0; i < backdrops.length(); i++){
                Image image = new Image(backdrops.getJSONObject(i).getString("file_path"),"backdrop");
                imgURL.add(image);
            }
            setupImageRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private void setupImageRecyclerView(){
        shimmerFrameLayout.setVisibility(View.GONE);
        if (imgURL.size() == 0){
            imagesLinearLayout.setVisibility(View.GONE);
        }
        else{

            RecyclerViewImagesAdapter recyclerViewImagesAdapter = new RecyclerViewImagesAdapter(this,imgURL);
            recyclerViewImages.setAdapter(recyclerViewImagesAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            recyclerViewImages.setLayoutManager(linearLayoutManager);
        }

    }

    private void fetchData(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject movie = response;
                    JSONArray genres = movie.getJSONArray("genres");
                    setupData(movie,genres);
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

    private void setupRecyclerView(){
        RecyclerViewCastAdapter recyclerViewCastAdapter = new RecyclerViewCastAdapter(this,actors);
        recyclerView.setAdapter(recyclerViewCastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void setupVideo(final ArrayList<YoutubeVideo> urls){
        if (urls.size() == 0){

            viewPager.setVisibility(View.GONE);

        }
        if(urls.size() != 0) {
            ViewPagerYoutubeAdapter viewPagerYoutubeAdapter = new ViewPagerYoutubeAdapter(this,urls);
            viewPager.setAdapter(viewPagerYoutubeAdapter);

            tabLayout.setupWithViewPager(viewPager);

        }


        relativeLayout.setVisibility(View.GONE);



    }


    private void fetchVideos(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray videos = response.getJSONArray("results");

                    parseVideoJSON(videos);
                }catch (Exception e){
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

    private void parseVideoJSON(JSONArray videos){
        try{
        for (int i = 0; i < videos.length(); i++){

                if(videos.getJSONObject(i).getString("site").equals("YouTube")){
                    String videoURL = videos.getJSONObject(i).getString("key");
                    String title = videos.getJSONObject(i).getString("name");
                    videoURLS.add(new YoutubeVideo(videoURL,title));
                }



        }
        setupVideo(videoURLS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
