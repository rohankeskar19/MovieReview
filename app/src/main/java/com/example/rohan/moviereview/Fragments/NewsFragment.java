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
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohan.moviereview.Helpers.RecyclerViewNewsAdapter;
import com.example.rohan.moviereview.Models.News;
import com.example.rohan.moviereview.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class NewsFragment extends Fragment {

    String URL = "https://newsapi.org/v2/top-headlines?sources=entertainment-weekly&apiKey=" + getString(R.string.API_KEY);

    private ArrayList<News> mNews = new ArrayList<>();

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.newsRecyclerView);
        progressBar = v.findViewById(R.id.progressBar);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        String URL = "https://newsapi.org/v2/top-headlines?sources=entertainment-weekly&apiKey=" + getString(R.string.API_KEY);


        fetchData(URL);
    }

    private void fetchData(String url) {
        mNews.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray response1 = response.getJSONArray("articles");

                    parseJSON(response1);

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

    private void parseJSON(JSONArray response){
        try{
            for (int i = 0; i < response.length(); i++){
                JSONObject news = response.getJSONObject(i);

                String title = news.getString("title");
                String desription = news.getString("description");
                String content = news.getString("content");
                String articleUrl = news.getString("url");
                String imageUrl = news.getString("urlToImage");
                String publishedAt = news.getString("publishedAt");
                String author = news.getString("author");
                String[] title1 = title.split("<em>");
                String title2 = Arrays.toString(title1);
                String[] title3 = title2.split("</em>");
                String title4 = Arrays.toString(title3);
                title4 = title4.substring(0,title4.length() - 2);
                title4 = title4.substring(3,title4.length());
                News news1 = new News(title4,desription,content,imageUrl,author,articleUrl,publishedAt);

                mNews.add(news1);
            }
            progressBar.setVisibility(View.GONE);
            setupRecyclerView();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setupRecyclerView(){
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(mNews,getContext());
        recyclerView.setAdapter(recyclerViewNewsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
