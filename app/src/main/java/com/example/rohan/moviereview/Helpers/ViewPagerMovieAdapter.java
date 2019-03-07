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
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Fragments.MoviesFragment;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class ViewPagerMovieAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<Movie> mMovies;
    String posterImgUrl = "https://image.tmdb.org/t/p/w500/";
    String backdropImgUrl = "https://image.tmdb.org/t/p/original/";

    public ViewPagerMovieAdapter(Context mContext, ArrayList<Movie> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_list_item,container,false);

        ImageView backdropImage = v.findViewById(R.id.backdropImageView);
        ImageView posterImage = v.findViewById(R.id.posterImage);
        TextView titleTextView = v.findViewById(R.id.movieTitle);

        String backdropUrl = backdropImgUrl + mMovies.get(position).getBackdropPath();
        String posterUrl = posterImgUrl + mMovies.get(position).getPosterUrl();

        Glide.with(mContext).load(backdropUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(backdropImage);
        Glide.with(mContext).load(posterUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(posterImage);

        String title = mMovies.get(position).getTitle();
        titleTextView.setText(title);

        container.addView(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtra("movie",mMovies.get(position));
                mContext.startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }


}
