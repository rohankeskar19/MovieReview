package com.example.rohan.moviereview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rohan.moviereview.Helpers.YoutubeApiClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {


    YouTubePlayerView youTubePlayerView;
    private static final String TAG = "VideoActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        youTubePlayerView = findViewById(R.id.youtubePlayerView);

        Intent intent = getIntent();

        if (intent.hasExtra("videoURL")){
            final String url = intent.getStringExtra("videoURL");

            youTubePlayerView.initialize(YoutubeApiClient.getApiKey(), new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.setFullscreen(true);
                    youTubePlayer.loadVideo(url);

                    youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                            if (!b) {
                                youTubePlayer.release();
                               finish();
                            }
                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(VideoActivity.this, "Failed to load video", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
