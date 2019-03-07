package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.Image;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewImagesAdapter extends RecyclerView.Adapter<RecyclerViewImagesAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Image> imageURL;
    private String url = "https://image.tmdb.org/t/p/w500/";
    private static final String TAG = "RecyclerViewImagesAdapt";

    public RecyclerViewImagesAdapter(Context mContext, ArrayList<Image> imageURL) {
        this.mContext = mContext;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (getItemViewType(i)){
            case 1:
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_images_poster,viewGroup,false));

            case 2:
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_images_backrop,viewGroup,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String imageURL1 = url + imageURL.get(i).getUrl();

        Glide.with(mContext).load(imageURL1).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.image);
    }


    @Override
    public int getItemViewType(int position) {
        if (imageURL.get(position).getImageType().equals("poster")){
            return 1;
        }
        else if (imageURL.get(position).getImageType().equals("backdrop")){
            return 2;
        }
        else return 0;
    }

    @Override
    public int getItemCount() {
        return imageURL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            image = itemView.findViewById(R.id.image);
        }
    }

}
