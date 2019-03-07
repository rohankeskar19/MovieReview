package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.TVDetailActivity;

import java.util.ArrayList;

public class RecyclerViewLatestAdapter extends RecyclerView.Adapter<RecyclerViewLatestAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Object> response = new ArrayList<>();
    String url = "https://image.tmdb.org/t/p/w500/";
    public RecyclerViewLatestAdapter(Context mContext, ArrayList<Object> response) {
        this.mContext = mContext;
        this.response = response;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_latest,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (response.get(i) instanceof Movie){
            final Movie movie = (Movie) response.get(i);
            String posterURL = url + movie.getPosterUrl();
            Glide.with(mContext).load(posterURL).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.posterImage);
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,MovieDetailActivity.class);
                    intent.putExtra("movie",movie);
                    mContext.startActivity(intent);
                }
            });

        }
        else if (response.get(i) instanceof TV){
            final TV tv = (TV) response.get(i);
            String posterURL = url + tv.getPosterPath();
            Glide.with(mContext).load(posterURL).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.posterImage);
            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,TVDetailActivity.class);
                    intent.putExtra("tv",tv);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        ImageView posterImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImage = itemView.findViewById(R.id.posterImageView);
        }
    }

}
