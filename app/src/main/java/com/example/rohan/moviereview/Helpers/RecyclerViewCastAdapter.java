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
import com.example.rohan.moviereview.Models.Cast;
import com.example.rohan.moviereview.PersonDetailsActivity;
import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewCastAdapter extends RecyclerView.Adapter<RecyclerViewCastAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Cast> actors = new ArrayList<>();

    public RecyclerViewCastAdapter(Context mContext, ArrayList<Cast> actors) {
        this.mContext = mContext;
        this.actors = actors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_cast_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        viewHolder.name.setText(actors.get(i).getName());
        viewHolder.character.setText(actors.get(i).getCharacter());
        String imageUrl ="https://image.tmdb.org/t/p/w500/" + actors.get(i).getProfileurl();
        Glide.with(mContext).load(imageUrl).transition(DrawableTransitionOptions.withCrossFade()).into(viewHolder.profileImage);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PersonDetailsActivity.class);
                intent.putExtra("cast",actors.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        TextView name,character;
        ImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            name = itemView.findViewById(R.id.nameTextView);
            character = itemView.findViewById(R.id.characterTextView);
            profileImage = itemView.findViewById(R.id.profileImageView);
        }
    }
}
