package com.example.rohan.moviereview.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rohan.moviereview.R;

import java.util.ArrayList;

public class RecyclerViewReviewsAdapter extends RecyclerView.Adapter<RecyclerViewReviewsAdapter.ViewHolder> {

    ArrayList<String> authors,content;
    Context mContext;

    public RecyclerViewReviewsAdapter(ArrayList<String> authors, ArrayList<String> content, Context mContext) {
        this.authors = authors;
        this.content = content;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.authorName.setText(authors.get(i));
        viewHolder.content.setText(content.get(i));
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        TextView authorName,content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
