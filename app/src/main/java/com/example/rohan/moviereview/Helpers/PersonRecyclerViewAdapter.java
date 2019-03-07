package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> imgURLs;
    private Context mContext;
    private String imgURL = "https://image.tmdb.org/t/p/original/";


    public PersonRecyclerViewAdapter(ArrayList<String> imgURLs, Context mContext) {
        this.imgURLs = imgURLs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_images_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String url = imgURL + imgURLs.get(i);
        Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.personImage);
    }

    @Override
    public int getItemCount() {
        return imgURLs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView personImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            personImage = itemView.findViewById(R.id.personImage);
        }
    }



}
