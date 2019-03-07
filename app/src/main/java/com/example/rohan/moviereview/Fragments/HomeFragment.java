package com.example.rohan.moviereview.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohan.moviereview.R;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: Starting");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }


}
