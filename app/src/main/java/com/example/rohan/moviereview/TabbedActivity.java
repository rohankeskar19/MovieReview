package com.example.rohan.moviereview;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.EditText;

import com.example.rohan.moviereview.Fragments.HomeFragment;

import com.example.rohan.moviereview.Fragments.MoviesFragment;
import com.example.rohan.moviereview.Fragments.NewsFragment;
import com.example.rohan.moviereview.Fragments.TVFragment;
import com.example.rohan.moviereview.Fragments.LatestFragment;
import com.example.rohan.moviereview.Helpers.ViewPagerAdapter;

public class TabbedActivity extends AppCompatActivity {

    private static final String TAG = "TabbedActivity";

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    EditText searchEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        setTitle("Activity 1");
        tabLayout = findViewById(R.id.tabs);
        appBarLayout = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new HomeFragment(),"Home");
        viewPagerAdapter.addFragment(new MoviesFragment(),"Movies");
        viewPagerAdapter.addFragment(new TVFragment(),"TV");
        viewPagerAdapter.addFragment(new LatestFragment(),"Latest");
        viewPagerAdapter.addFragment(new NewsFragment(),"News");

        viewPager.setOffscreenPageLimit(4);

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        searchEditText = findViewById(R.id.search_edit_text);

        searchEditText.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(TabbedActivity.this,SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return false;
            }
        });

    }


}
