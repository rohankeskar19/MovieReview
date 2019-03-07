package com.example.rohan.moviereview;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.rohan.moviereview.Models.News;


public class NewsActivity extends AppCompatActivity {

    ImageView image;
    TextView titleTextView,descriptionTextView,contentTextView,linkToArticle,publishedAt,author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        image = findViewById(R.id.thumbnailImage);
        titleTextView = findViewById(R.id.newsTitle);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        contentTextView = findViewById(R.id.contentTextView);
        linkToArticle = findViewById(R.id.linkToArticle);
        publishedAt = findViewById(R.id.publishedAt);
        author = findViewById(R.id.authorTextView);
        titleTextView.setSelected(true);

        News news;

        Intent intent = getIntent();
        if (intent.hasExtra("news")){
            news = (News) intent.getSerializableExtra("news");;
            titleTextView.setText(news.getTitle());
            Glide.with(this).load(news.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(image);
            descriptionTextView.setText(news.getDescription());
            contentTextView.setText(news.getContent());
            linkToArticle.setText("Link to Article : " + news.getUrlToArticle());
            publishedAt.setText("Published At : " + news.getPublishedAt());
            author.setText("Author : " + news.getAuthor());
        }



    }
}
