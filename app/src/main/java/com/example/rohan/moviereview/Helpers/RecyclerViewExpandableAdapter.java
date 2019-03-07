package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.Season;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;
import java.util.Calendar;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class RecyclerViewExpandableAdapter extends ExpandableRecyclerView.Adapter<RecyclerViewExpandableAdapter.ChildViewHolder,RecyclerViewExpandableAdapter.GroupViewHolder,Object,String> {


    private Context mContext;
    private ArrayList<Season> mSeasons;
    private static final String TAG = "RecyclerViewExpandableA";

    public RecyclerViewExpandableAdapter(Context mContext, ArrayList<Season> mSeasons) {
        this.mContext = mContext;
        this.mSeasons = mSeasons;
    }

    @Override
    public int getChildItemViewType(int i, int i1) {
        return 1;
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, int position) {
        super.onBindChildViewHolder(holder, group, position);
        holder.seasonTitleTextView.setText(mSeasons.get(group).getName());
        holder.airDateTextView.setText(mSeasons.get(group).getAirDate());
        holder.episodeCountTextView.setText("Episodes : " + mSeasons.get(group).getEpisodeCount());
        if(mSeasons.get(group).getOverview().equals("null")){
            holder.overviewTextView.setText("");

        }else {
            holder.overviewTextView.setText(mSeasons.get(group).getOverview());
        }
        String imgUrl = "https://image.tmdb.org/t/p/w500/" + mSeasons.get(group).getPosterPath();
        Glide.with(mContext).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(100)).into(holder.posterImageView);
    }



    @Override
    public int getGroupItemCount() {
        return mSeasons.size() - 1;
    }

    @Override
    public int getChildItemCount(int i) {
        return 1;
    }

    @Override
    public String getGroupItem(int i) {
        return mSeasons.get(i).getName();
    }

    @Override
    public Object getChildItem(int i, int i1) {
        return mSeasons.get(i);
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int group) {
        super.onBindGroupViewHolder(holder, group);

        if (group < mSeasons.size()) {
            holder.seasonName.setText(mSeasons.get(group).getName());
            Log.d(TAG, "onBindGroupViewHolder: Group " + group);
        }




    }

    @Override
    protected GroupViewHolder onCreateGroupViewHolder(ViewGroup viewGroup) {
        return new GroupViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_list_item,viewGroup,false));
    }

    @Override
    protected ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        return new ChildViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seasons_list_item,viewGroup,false));
    }


    public class ChildViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImageView;
        TextView seasonTitleTextView,episodeCountTextView,airDateTextView,overviewTextView;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.posterImageView);
            seasonTitleTextView = itemView.findViewById(R.id.seasonNumberTextView);
            episodeCountTextView = itemView.findViewById(R.id.episodeCountTextView);
            airDateTextView = itemView.findViewById(R.id.airDateTextView);
            overviewTextView = itemView.findViewById(R.id.seasonOverviewTextView);
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{

        TextView seasonName;


        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            seasonName = itemView.findViewById(R.id.seasonName);
        }
    }



}
