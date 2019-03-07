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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.News;
import com.example.rohan.moviereview.NewsActivity;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewNewsAdapter extends RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> {

    ArrayList<News> mNews;
    Context mContext;

    public RecyclerViewNewsAdapter(ArrayList<News> mNews, Context mContext) {
        this.mNews = mNews;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_item,viewGroup,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.titleTextView.setText(mNews.get(i).getTitle());
        Glide.with(mContext).load(mNews.get(i).getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.thumbnailImageView);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,NewsActivity.class);
                intent.putExtra("news",mNews.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        ImageView thumbnailImageView;
        TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImage);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            titleTextView.setSelected(true);
        }
    }
}
