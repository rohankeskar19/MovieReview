package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.rohan.moviereview.Helpers.RecyclerViewCastAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewImagesAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewProductionAdapter;
import com.example.rohan.moviereview.Helpers.RecyclerViewTVAdapter;
import com.example.rohan.moviereview.Helpers.ViewPagerYoutubeAdapter;
import com.example.rohan.moviereview.Models.Cast;
import com.example.rohan.moviereview.Models.Image;
import com.example.rohan.moviereview.Models.Season;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.Models.YoutubeVideo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.GONE;

public class TVDetailActivity extends AppCompatActivity {

    TV tv;
    RequestQueue requestQueue;
    ViewPager videoViewPager;
    LinearLayout imagesLayout,castLayout,productionCompaniesLayout,creatorsLayout,relatedTVShowsLayout,posterLinearLayout;
    RecyclerView imagesRecyclerView,creatorsRecyclerView,productionRecyclerView,castRecyclerView,similarRecyclerView;
    TextView homePageTextView,titleTextView,genreTextView,overviewTextView;
    ImageView posterImageView;
    TabLayout tabLayout;
    RelativeLayout shimmerLayout;

    View view;

    ArrayList<YoutubeVideo> videoURL = new ArrayList<>();
    ArrayList<TV> relatedTVShows = new ArrayList<>();
    ArrayList<Cast> mCasts = new ArrayList<>();
    ArrayList<Cast> mCreators = new ArrayList<>();
    ArrayList<String> productionCompaniesNames = new ArrayList<>();
    ArrayList<String> productionCompaniesURLs = new ArrayList<>();
    ArrayList<Image> mImages = new ArrayList<>();
    ArrayList<Season> mSeasons = new ArrayList<>();


    private static final String TAG = "TVDetailActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        view = findViewById(R.id.view);

        homePageTextView = findViewById(R.id.homepageTextView);
        titleTextView = findViewById(R.id.tvTitleTextView);
        genreTextView = findViewById(R.id.genreTextView);
        overviewTextView = findViewById(R.id.overviewTextView);

        posterImageView = findViewById(R.id.posterImage);

        videoViewPager = findViewById(R.id.videosViewPager);
        tabLayout = findViewById(R.id.tab_layout);

        imagesRecyclerView = findViewById(R.id.recyclerviewImages);
        creatorsRecyclerView = findViewById(R.id.recyclerviewCreatedBy);
        productionRecyclerView = findViewById(R.id.recyclerviewProductionCompanies);
        castRecyclerView = findViewById(R.id.recyclerviewCast);
        similarRecyclerView = findViewById(R.id.recyclerviewSimilar);

        shimmerLayout = findViewById(R.id.shimmerLayout);

        imagesLayout = findViewById(R.id.imagesLinearLayout);
        castLayout = findViewById(R.id.cast);
        productionCompaniesLayout = findViewById(R.id.productionLinearLayout);
        creatorsLayout = findViewById(R.id.createdByLinearLayout);
        relatedTVShowsLayout = findViewById(R.id.relatedLinearLayout);
        posterLinearLayout = findViewById(R.id.linearLayout2);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();

        if (intent.hasExtra("tv")){
            tv = (TV) intent.getSerializableExtra("tv");
        }

        Log.d(TAG, "onCreate: id " + tv.getId());

        String DETAIL_URL = "https://api.themoviedb.org/3/tv/"+ tv.getId() + "?api_key=" + getString(R.string.API_KEY) + "&language=en-US";
        String VIDEO_URL = "https://api.themoviedb.org/3/tv/" + tv.getId() + "/videos?api_key=" + getString(R.string.API_KEY) + "&language=en-US";
        String CAST_URL = "https://api.themoviedb.org/3/tv/" + tv.getId() + "/credits?api_key=" + getString(R.string.API_KEY) + "&language=en-US";
        String IMAGES_URL = "https://api.themoviedb.org/3/tv/" + tv.getId() + "/images?api_key=" + getString(R.string.API_KEY);
        String SIMILAR_SHOWS = "https://api.themoviedb.org/3/tv/" + tv.getId() + "/similar?api_key=" + getString(R.string.API_KEY) + "&language=en-US";

        fetchDetailData(DETAIL_URL);
        fetchCastData(CAST_URL);
        fetchVideoData(VIDEO_URL);
        fetchImages(IMAGES_URL);
        fetchSimilar(SIMILAR_SHOWS);
    }


    private void fetchDetailData(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJSON1(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchVideoData(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseVideos(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchCastData(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseCast(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchImages(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseImages(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchSimilar(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseSimilar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void parseJSON1(JSONObject jsonObject){
        try{
            JSONArray genresJSON = jsonObject.getJSONArray("genres");
            String genreString = "";
            for (int i = 0; i < genresJSON.length(); i++){
                JSONObject genre = genresJSON.getJSONObject(i);

                genreString += genre.getString("name") + ", ";

            }
            JSONArray creatorsJSON = jsonObject.getJSONArray("created_by");

            for (int j = 0; j < creatorsJSON.length(); j++){
                JSONObject creator = creatorsJSON.getJSONObject(j);
                Cast cast = new Cast("",creator.getString("name"),creator.getString("profile_path"),creator.getInt("id"));
                mCreators.add(cast);
            }

            JSONArray productionsJSON = jsonObject.getJSONArray("production_companies");

            for (int z = 0; z < productionsJSON.length(); z++){
                JSONObject company = productionsJSON.getJSONObject(z);
                productionCompaniesNames.add(company.getString("name"));
                productionCompaniesURLs.add(company.getString("logo_path"));
            }

            String homepage = jsonObject.getString("homepage");
            if (genreString.length() > 2) {
                genreString = genreString.substring(0, genreString.length() - 2);
            }
            String imgUrl = "https://image.tmdb.org/t/p/w500/" + tv.getPosterPath();

            if(homepage.equals("")){
                homePageTextView.setVisibility(View.GONE);
            }else{
                homePageTextView.setText("Home Page : " + homepage);
            }

            Log.d(TAG, "parseJSON: TV Title " + tv.getOriginalName());
            titleTextView.setText(tv.getOriginalName());
            genreTextView.setText(genreString);
            overviewTextView.setText(tv.getOverview());
            Glide.with(this).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(posterImageView);
            setupCreatorsRecyclerView();
            setupProductionRecyclerView();


            JSONArray seasons = jsonObject.getJSONArray("seasons");

            for (int i = 0; i < seasons.length(); i++){
                JSONObject jsonObject1 = seasons.getJSONObject(i);
                Season season = new Season(jsonObject1.getString("air_date"),jsonObject1.getInt("episode_count"),jsonObject1.getInt("id"),jsonObject1.getString("name"),jsonObject1.getString("overview"),jsonObject1.getString("poster_path"),jsonObject1.getInt("season_number"));
                mSeasons.add(season);
            }

            final String firstAirDate = jsonObject.getString("first_air_date");
            final String lastAirDate = jsonObject.getString("last_air_date");
            final boolean inProduction = jsonObject.getBoolean("in_production");

            posterLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TVDetailActivity.this,TVDescriptionActivity.class);
                    intent.putExtra("first-air-date",firstAirDate);
                    intent.putExtra("last-air-date",lastAirDate);
                    intent.putExtra("in-production",inProduction);
                    intent.putExtra("tv",tv);
                    intent.putExtra("season-list",mSeasons);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TVDetailActivity.this,posterImageView,ViewCompat.getTransitionName(posterImageView));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(intent,activityOptionsCompat.toBundle());
                    }
                    else{
                        startActivity(intent);
                    }

                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseCast(JSONObject jsonObject){
        try{
            JSONArray castJSON = jsonObject.getJSONArray("cast");
            for (int i = 0; i < castJSON.length(); i++){
                JSONObject castJSONObject = castJSON.getJSONObject(i);

                Cast cast = new Cast(castJSONObject.getString("character"),castJSONObject.getString("name"),castJSONObject.getString("profile_path"),castJSONObject.getInt("id"));
                mCasts.add(cast);
            }
            setupCastRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseVideos(JSONObject jsonObject){
        try{
            JSONArray videoJSON = jsonObject.getJSONArray("results");

            for (int i = 0; i < videoJSON.length(); i++){
                JSONObject video = videoJSON.getJSONObject(i);
                YoutubeVideo youtubeVideo = new YoutubeVideo(video.getString("key"),video.getString("name"));
                videoURL.add(youtubeVideo);
            }
            setupVideoViewPager();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseImages(JSONObject jsonObject){
        try{
            JSONArray postersArray = jsonObject.getJSONArray("posters");
            JSONArray backdropsArray = jsonObject.getJSONArray("backdrops");

            for (int i = 0; i < postersArray.length(); i++){
                Image image = new Image(postersArray.getJSONObject(i).getString("file_path"),"poster");
                mImages.add(image);
            }
            for (int j = 0; j < backdropsArray.length(); j++){
                Image image = new Image(backdropsArray.getJSONObject(j).getString("file_path"),"backdrop");
                mImages.add(image);
            }

            setupImagesRecyclerView();
        }
        catch (Exception e){

        }
    }

    private void parseSimilar(JSONObject jsonObject){
        try{
            JSONArray similarJSON = jsonObject.getJSONArray("results");

            for (int i = 0; i < similarJSON.length(); i++){
                JSONObject tv = similarJSON.getJSONObject(i);

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

                relatedTVShows.add(tv1);
            }
            setupSimilarRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setupCastRecyclerView(){
        if(mCasts.size() == 0){
            castLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupCastRecyclerView: No Cast " + mCasts.toString());
        }
        RecyclerViewCastAdapter recyclerViewCastAdapter = new RecyclerViewCastAdapter(this,mCasts);
        castRecyclerView.setAdapter(recyclerViewCastAdapter);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
    private void setupCreatorsRecyclerView(){
        if (mCreators.size() == 0){
            creatorsLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupCreatorsRecyclerView: No Creators " + mCreators.toString());
        }
        RecyclerViewCastAdapter recyclerViewCastAdapter = new RecyclerViewCastAdapter(this,mCreators);
        creatorsRecyclerView.setAdapter(recyclerViewCastAdapter);
        creatorsRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }


    private void setupVideoViewPager(){
        if (videoURL.size() == 0){
            videoViewPager.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupVideoViewPager: No Videos " + videoURL.toString());
        }
        ViewPagerYoutubeAdapter viewPagerYoutubeAdapter = new ViewPagerYoutubeAdapter(this,videoURL);
        videoViewPager.setAdapter(viewPagerYoutubeAdapter);
        tabLayout.setupWithViewPager(videoViewPager);
    }

    private void setupImagesRecyclerView(){
        if (mImages.size() == 0){
            imagesLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupImagesRecyclerView: No Images " + mImages.toString());
        }
        Log.d(TAG, "setupImagesRecyclerView: " + mImages.toString());
        RecyclerViewImagesAdapter recyclerViewImagesAdapter = new RecyclerViewImagesAdapter(this,mImages);
        imagesRecyclerView.setAdapter(recyclerViewImagesAdapter);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void setupSimilarRecyclerView(){
        shimmerLayout.setVisibility(GONE);
        if (relatedTVShows.size() == 0){
            relatedTVShowsLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupSimilarRecyclerView: No Related Movies " + relatedTVShows.toString());
        }
        RecyclerViewTVAdapter recyclerViewTVAdapter = new RecyclerViewTVAdapter(relatedTVShows,this,1);
        similarRecyclerView.setAdapter(recyclerViewTVAdapter);
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void setupProductionRecyclerView(){
        if (productionCompaniesURLs.size() == 0 || productionCompaniesNames.size() == 0){
            productionCompaniesLayout.setVisibility(View.GONE);
            Log.d(TAG, "setupProductionRecyclerView: No Production Companies " + productionCompaniesNames.toString());
        }
        RecyclerViewProductionAdapter recyclerViewProductionAdapter = new RecyclerViewProductionAdapter(this,productionCompaniesNames,productionCompaniesURLs);
        productionRecyclerView.setAdapter(recyclerViewProductionAdapter);
        productionRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

}
