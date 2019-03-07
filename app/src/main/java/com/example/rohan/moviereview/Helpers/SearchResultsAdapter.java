package com.example.rohan.moviereview.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.rohan.moviereview.Models.Cast;
import com.example.rohan.moviereview.Models.Movie;
import com.example.rohan.moviereview.Models.People;
import com.example.rohan.moviereview.Models.TV;
import com.example.rohan.moviereview.MovieDetailActivity;
import com.example.rohan.moviereview.PersonDetailsActivity;
import com.example.rohan.moviereview.R;
import com.example.rohan.moviereview.TVDetailActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>{

    ArrayList<Object> searchResults = new ArrayList<>();
    Context mContext;
    String imgUrl = "https://image.tmdb.org/t/p/w300/";
    private static final String TAG = "SearchResultsAdapter";
    public SearchResultsAdapter(ArrayList<Object> mMovies, Context mContext) {
        this.searchResults = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_search_list_item,viewGroup,false);

       ViewHolder v1 = new ViewHolder(v);

       return v1;
   }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        switch (getItemViewType(i)){
            case 1:
                final Movie movie = (Movie) searchResults.get(i);
                viewHolder.title.setText(movie.getTitle() + " (Movie)");
                viewHolder.overview.setText(movie.getOverview());
                String[] release = movie.getRelease().split("-");
                viewHolder.releaseDate.setText(release[0]);
                String url = imgUrl + movie.getPosterUrl();
                Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.posterImageView);
                viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,MovieDetailActivity.class);
                        intent.putExtra("movie",movie);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 2:
                final TV tv = (TV) searchResults.get(i);
                viewHolder.title.setText(tv.getOriginalName() + " (TV show)");
                viewHolder.overview.setText(tv.getOverview());
                String[] release1 = tv.getAirDate().split("-");
                viewHolder.releaseDate.setText(release1[0]);
                String url1 = imgUrl + tv.getPosterPath();
                Glide.with(mContext).load(url1).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.posterImageView);
                viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,TVDetailActivity.class);
                        intent.putExtra("tv",tv);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 3:
                final People people = (People) searchResults.get(i);
                viewHolder.title.setText(people.getTitle() + " (Person)");
                String popularity = Double.toString(people.getPopularity());
                viewHolder.releaseDate.setText("Popularity : " + popularity);
                ArrayList<String> knownFor = people.getKnownForTitles();
                String knownForTitles = "";

                for (int j = 0; j < knownFor.size(); j++){
                    knownForTitles += knownFor.get(j) + ", ";
                }
                Log.d(TAG, "onBindViewHolder: " + knownForTitles);
                if (knownForTitles != "") {
                    knownForTitles = knownForTitles.substring(0, knownForTitles.length() - 2);
                }
                String url2 = imgUrl + people.getProfilePath();
                viewHolder.overview.setText(knownForTitles);
                Glide.with(mContext).load(url2).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.posterImageView);
                viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cast cast = new Cast("",people.getTitle(),"",people.getId());
                        Intent intent = new Intent(mContext,PersonDetailsActivity.class);
                        intent.putExtra("cast",cast);
                        Log.d(TAG, "onClick: " + cast.getId());
                        mContext.startActivity(intent);
                    }
                });

                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (searchResults.get(position) instanceof Movie){
            return 1;
        }
        else if (searchResults.get(position) instanceof TV){
            return 2;
        }
        else if(searchResults.get(position) instanceof People){
            return 3;
        }
        else{
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView posterImageView;
        TextView title,overview,releaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            title = itemView.findViewById(R.id.movieTitleTextView);
            overview = itemView.findViewById(R.id.overviewTextView);
            releaseDate = itemView.findViewById(R.id.releasedateTextView);
        }
    }

}
