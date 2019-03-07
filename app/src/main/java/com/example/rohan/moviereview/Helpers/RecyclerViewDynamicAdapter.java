package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewDynamicAdapter extends RecyclerView.Adapter{

    ArrayList<Movie> mMovies = new ArrayList<>();
    Context mContext;
    String imgUrl = "https://image.tmdb.org/t/p/w500/";
    boolean linearLayout;
    private static final String TAG = "RecyclerViewDynamicAdap";
    public RecyclerViewDynamicAdapter(ArrayList<Movie> mMovies, Context mContext, boolean linearLayout) {
        this.mMovies = mMovies;
        this.mContext = mContext;
        this.linearLayout = linearLayout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        ViewHolderGrid viewHolderGrid;
        ViewHolderLinear viewHolderLinear;
        if (getItemViewType(1) == 0){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.see_all_linear_list_item,viewGroup,false);
            viewHolderLinear = new ViewHolderLinear(v);
            return viewHolderLinear;
        }
        else if (getItemViewType(1) == 1){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.see_all_grid_list_item,viewGroup,false);
            viewHolderGrid = new ViewHolderGrid(v);
            return viewHolderGrid;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        String url = imgUrl + mMovies.get(i).getPosterUrl();
        String language = mMovies.get(i).getLanguage().toUpperCase();
        String rated = mMovies.get(i).isAdult() ? "Rated" : "Not Rated";
        String text = language + ", " + rated;
        String overview = mMovies.get(i).getOverview();
        Log.d(TAG, "onBindViewHolder: " + text);
        String voteAverage = Double.toString(mMovies.get(i).getVoteAverage());
        switch (viewHolder.getItemViewType()){
            case 0:
                ViewHolderLinear viewHolderLinear = (ViewHolderLinear) viewHolder;
                Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolderLinear.posterImageView);
                viewHolderLinear.title.setText(mMovies.get(i).getTitle());
                viewHolderLinear.duration.setText(text);
                viewHolderLinear.overview.setText(overview);
                viewHolderLinear.voteAverage.setText(voteAverage);
                Log.d(TAG, "onBindViewHolder: List Type");
                viewHolderLinear.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,MovieDetailActivity.class);
                        intent.putExtra("movie",mMovies.get(i));
                        mContext.startActivity(intent);
                    }
                });

                break;
            case 1:
                ViewHolderGrid viewHolderGrid = (ViewHolderGrid) viewHolder;
                Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolderGrid.posterImageView);
                viewHolderGrid.title.setText(mMovies.get(i).getTitle());
                viewHolderGrid.duration.setText(text);
                viewHolderGrid.voteAverage.setText(voteAverage);
                Log.d(TAG, "onBindViewHolder: Grid Type");
                viewHolderGrid.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,MovieDetailActivity.class);
                        intent.putExtra("movie",mMovies.get(i));
                        mContext.startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (linearLayout){
            return 0;
        }
        else {
            return 1;
        }


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolderLinear extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView posterImageView;
        TextView title,duration,voteAverage,overview;

        public ViewHolderLinear(@NonNull View itemView) {
            super(itemView);

            overview = itemView.findViewById(R.id.movieOverview);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImageView = itemView.findViewById(R.id.moviePoster);
            title = itemView.findViewById(R.id.movieTitle);
            duration = itemView.findViewById(R.id.movieDurationTextView);
            voteAverage = itemView.findViewById(R.id.movieVoteAverage);

        }
    }

    public class ViewHolderGrid extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView posterImageView;
        TextView title,duration,voteAverage;

        public ViewHolderGrid(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImageView = itemView.findViewById(R.id.moviePoster);
            title = itemView.findViewById(R.id.movieTitle);
            duration = itemView.findViewById(R.id.movieDurationTextView);
            voteAverage = itemView.findViewById(R.id.movieVoteAverage);

        }
    }

}
