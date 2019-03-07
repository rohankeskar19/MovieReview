package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.TVDetailActivity;

import java.util.ArrayList;

public class RecyclerViewTVAdapter extends RecyclerView.Adapter<RecyclerViewTVAdapter.ViewHolder>{


    ArrayList<TV> mTVs = new ArrayList<>();
    Context mContext;
    String imgUrl = "https://image.tmdb.org/t/p/w500/";
    int choice;

    public RecyclerViewTVAdapter(ArrayList<TV> mTVs, Context mContext,int choice) {
        this.mTVs = mTVs;
        this.mContext = mContext;
        this.choice = choice;
    }

    @NonNull
    @Override
    public RecyclerViewTVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_list_item,viewGroup,false);
        RecyclerViewTVAdapter.ViewHolder viewHolder = new RecyclerViewTVAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewTVAdapter.ViewHolder viewHolder, final int i) {
        //Getting data from the object into strings
        String url = imgUrl + mTVs.get(i).getPosterPath();
        String title = mTVs.get(i).getOriginalName();
        String rating = Double.toString(mTVs.get(i).getVoteAverage());

        if (choice == 2){
            viewHolder.linearLayout.setVisibility(View.GONE);
        }
        else if (choice == 1){
            viewHolder.voting.setText(rating);
        }


        //Loading data into view
        try {
            Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.posterImageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        viewHolder.title.setText(title);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TVDetailActivity.class);
                intent.putExtra("tv",mTVs.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTVs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        LinearLayout linearLayout;
        ImageView posterImageView;
        TextView title,voting;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImageView = itemView.findViewById(R.id.posterImageViewPopular);
            title = itemView.findViewById(R.id.tvTitleTextView);
            voting = itemView.findViewById(R.id.ratingTextView);
        }
    }


}
