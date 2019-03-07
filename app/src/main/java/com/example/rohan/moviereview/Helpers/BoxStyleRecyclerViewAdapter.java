package com.example.rohan.moviereview.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class BoxStyleRecyclerViewAdapter extends RecyclerView.Adapter<BoxStyleRecyclerViewAdapter.ViewHolder> {

    ArrayList<Movie> mMovies = new ArrayList<>();
    Context mContext;
    String imgUrl = "https://image.tmdb.org/t/p/w500/";

    public BoxStyleRecyclerViewAdapter(ArrayList<Movie> mMovies, Context mContext) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BoxStyleRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_movie_list_item,viewGroup,false);
        BoxStyleRecyclerViewAdapter.ViewHolder viewHolder = new BoxStyleRecyclerViewAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BoxStyleRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        //Getting data from the object into strings
        String url = imgUrl + mMovies.get(i).getPosterUrl();
        String title = mMovies.get(i).getTitle();
        String rating = Double.toString(mMovies.get(i).getVoteAverage());




        //Loading data into view
        try {
            Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.posterImageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        viewHolder.title.setText(title);
        viewHolder.voting.setText(rating);
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtra("movie",mMovies.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView posterImageView;
        TextView title,voting;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImageView = itemView.findViewById(R.id.posterImageViewPopular);
            title = itemView.findViewById(R.id.movieTitleTextView);
            voting = itemView.findViewById(R.id.ratingTextView);
        }
    }

}
