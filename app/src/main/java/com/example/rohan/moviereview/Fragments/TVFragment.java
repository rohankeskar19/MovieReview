package com.example.rohan.moviereview.Fragments;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.ParallaxTransformer;
import com.example.rohan.moviereview.Helpers.RecyclerViewTVAdapter;
import com.example.rohan.moviereview.Helpers.ViewPagerAdapterTV;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TVFragment extends Fragment {
    private static final String TAG = "TVFragment";
    ViewPager viewPager;
    TabLayout tabLayout;



    private RequestQueue requestQueue;
    private ArrayList<TV> onAirTodayList = new ArrayList<>();
    private ArrayList<TV> arrivingTodayList = new ArrayList<>();
    private ArrayList<TV> topRatedList = new ArrayList<>();
    private ArrayList<TV> poplarList = new ArrayList<>();

    RecyclerView arrivingTodayRecyclerView,topRatedRecyclerView,popularRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

         String onAirURL = "https://api.themoviedb.org/3/tv/on_the_air?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";
         String arrivingTodayURL = "https://api.themoviedb.org/3/tv/airing_today?api_key=" +  getString(R.string.API_KEY) + "&language=en-US&page=1";
         String topRatedURL = "https://api.themoviedb.org/3/tv/top_rated?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";
         String popularURL = "https://api.themoviedb.org/3/tv/popular?api_key=" + getString(R.string.API_KEY) + "&language=en-US&page=1";

        fetchPopular(popularURL);
        fetchOnAir(onAirURL);
        fetchArrivingToday(arrivingTodayURL);
        fetchTopRated(topRatedURL);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.tvViewPager);

        arrivingTodayRecyclerView = v.findViewById(R.id.recyclerViewArrivingToday);
        topRatedRecyclerView = v.findViewById(R.id.recyclerviewTopRatedTV);
        popularRecyclerView = v.findViewById(R.id.recyclerViewPopular);
        return v;
    }

    private void fetchPopular(String url){
        poplarList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseJSON(response.getJSONArray("results"),4);
                }
                catch (Exception e){
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

    private void fetchTopRated(String url){
        topRatedList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseJSON(response.getJSONArray("results"),3);
                }
                catch (Exception e){
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


    private void fetchOnAir(String url){
        onAirTodayList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseJSON(response.getJSONArray("results"),1);
                }
                catch (Exception e){
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

    private void fetchArrivingToday(String url){
        arrivingTodayList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseJSON(response.getJSONArray("results"),2);
                }
                catch (Exception e){
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

    private void parseJSON(JSONArray response,int choice){

        if (choice == 1) {
            try {
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

                    onAirTodayList.add(tv1);


                }
                setupViewPager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(choice == 2){
            try {
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

                    arrivingTodayList.add(tv1);


                }
                setupRecyclerViewArrivingToday();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (choice == 3){
            try {
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

                    topRatedList.add(tv1);


                }
                setupRecyclerViewTopRated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(choice == 4){
            try {
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

                    poplarList.add(tv1);


                }
                setupRecyclerViewPopular();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager(){
        Log.d(TAG, "setupViewPager: " + onAirTodayList.toString());
        ViewPagerAdapterTV viewPagerAdapterTV = new ViewPagerAdapterTV(getContext(), onAirTodayList);

        viewPager.setAdapter(viewPagerAdapterTV);
        viewPager.setPageTransformer(false,new ParallaxTransformer());
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupRecyclerViewArrivingToday(){
        RecyclerViewTVAdapter recyclerViewTVAdapter = new RecyclerViewTVAdapter(arrivingTodayList,getContext(),1);
        arrivingTodayRecyclerView.setAdapter(recyclerViewTVAdapter);
        arrivingTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void  setupRecyclerViewTopRated(){
        RecyclerViewTVAdapter recyclerViewTVAdapter = new RecyclerViewTVAdapter(topRatedList,getContext(),1);
        topRatedRecyclerView.setAdapter(recyclerViewTVAdapter);
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void setupRecyclerViewPopular(){
        RecyclerViewTVAdapter recyclerViewTVAdapter = new RecyclerViewTVAdapter(poplarList,getContext(),1);
        popularRecyclerView.setAdapter(recyclerViewTVAdapter);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }



}
