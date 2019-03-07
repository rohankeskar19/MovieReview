package com.example.rohan.moviereview.Helpers;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.rohan.moviereview.R;

public class ParallaxTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View view, float v) {

        if (v >= -1 && v <= 1){
            view.findViewById(R.id.backdropImageView).setTranslationX(-v * view.getWidth() / 2);

        }
        else{
            view.setAlpha(1);
        }


    }
}
