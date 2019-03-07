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
import com.example.rohan.moviereview.Models.YoutubeVideo;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.VideoActivity;

import java.util.ArrayList;

public class RecyclerViewVideosAdapter extends RecyclerView.Adapter<RecyclerViewVideosAdapter.ViewHolder> {

    private ArrayList<YoutubeVideo> videos = new ArrayList<>();
    private Context mContext;


    public RecyclerViewVideosAdapter(ArrayList<YoutubeVideo> videos, Context mContext) {
        this.videos = videos;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_videos,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        String url = "https://img.youtube.com/vi/" + videos.get(i).getUrl() + "/mqdefault.jpg";
        Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.thumbnailImage);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoActivity.class);
                intent.putExtra("videoURL",videos.get(i).getUrl());
                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        ImageView thumbnailImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            thumbnailImage = itemView.findViewById(R.id.thumbnail);
        }
    }
}
