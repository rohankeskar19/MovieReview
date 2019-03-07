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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.YoutubeVideo;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.VideoActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerYoutubeAdapter extends PagerAdapter{


    Context mContext;
    List<YoutubeVideo> urls = new ArrayList<>();
    private static final String TAG = "ViewPagerYoutubeAdapter";
    YouTubePlayer.OnInitializedListener onInitializedListener;

    public ViewPagerYoutubeAdapter(Context mContext, List<YoutubeVideo> urls) {
        this.mContext = mContext;
        this.urls = urls;
        Log.d(TAG, "ViewPagerYoutubeAdapter: " + urls.size());
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.video_view_pager_list_item,container,false);

        ImageView thumbNailImage = v.findViewById(R.id.thumbnailImageView);
        TextView title = v.findViewById(R.id.videoNameTextView);
        String url = "https://img.youtube.com/vi/" + urls.get(position).getUrl() + "/0.jpg";

        title.setText(urls.get(position).getTitle());
        Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(thumbNailImage);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,VideoActivity.class);
                intent.putExtra("videoURL",urls.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });




        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem: Starting");
        container.removeView((RelativeLayout)object);

    }

}
