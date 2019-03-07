package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.TVDetailActivity;

import java.util.ArrayList;

public class ViewPagerAdapterTV extends PagerAdapter {


    private static final String TAG = "ViewPagerAdapterTV";
    private Context mContext;
    private ArrayList<TV> tvs = new ArrayList<>();
    String posterImgUrl = "https://image.tmdb.org/t/p/w500/";
    String backdropImgUrl = "https://image.tmdb.org/t/p/original/";
    public ViewPagerAdapterTV(Context mContext, ArrayList<TV> tvs) {
        this.mContext = mContext;
        this.tvs = tvs;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_list_item,container,false);

        TextView title = v.findViewById(R.id.movieTitle);
        ImageView posterImageView = v.findViewById(R.id.posterImage);
        ImageView backdropImage = v.findViewById(R.id.backdropImageView);

        title.setText(tvs.get(position).getOriginalName());
        String tvPosterUrl = posterImgUrl + tvs.get(position).getPosterPath();
        String tvBackdropUrl = backdropImgUrl + tvs.get(position).getBackdropPath();


        Glide.with(mContext).load(tvPosterUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(posterImageView);
        Glide.with(mContext).load(tvBackdropUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(backdropImage);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TVDetailActivity.class);
                intent.putExtra("tv",tvs.get(position));
                mContext.startActivity(intent);
            }
        });
        container.addView(v);


        return v;
    }

    @Override
    public int getCount() {
        return tvs.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return  view == o;
    }
}
