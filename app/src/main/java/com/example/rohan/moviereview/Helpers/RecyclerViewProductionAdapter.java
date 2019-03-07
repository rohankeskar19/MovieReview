package com.example.rohan.moviereview.Helpers;

import android.content.Context;
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
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewProductionAdapter extends RecyclerView.Adapter<RecyclerViewProductionAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> companyNames;
    private ArrayList<String> companyURLS;

    public RecyclerViewProductionAdapter(Context mContext, ArrayList<String> companyNames, ArrayList<String> companyURLS) {
        this.mContext = mContext;
        this.companyNames = companyNames;
        this.companyURLS = companyURLS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_production,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.companyNameTextView.setText(companyNames.get(i));
        String url = "https://image.tmdb.org/t/p/w500/" + companyURLS.get(i);
        Glide.with(mContext).load(url).transition(DrawableTransitionOptions.withCrossFade(100)).into(viewHolder.companyLogoImageView);
    }

    @Override
    public int getItemCount() {
        return companyNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        ImageView companyLogoImageView;
        TextView companyNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            companyLogoImageView = itemView.findViewById(R.id.companyLogo);
            companyNameTextView = itemView.findViewById(R.id.companyName);
        }
    }
}
