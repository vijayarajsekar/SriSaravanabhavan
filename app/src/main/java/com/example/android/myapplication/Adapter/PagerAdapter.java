package com.example.android.myapplication.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.myapplication.Fragments.ScreenBreakFast;
import com.example.android.myapplication.Fragments.ScreenDinner;
import com.example.android.myapplication.Fragments.ScreenLunch;

/**
 * Created by VijayarajSekar on 8/31/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"Breakfast", "Lunch", "Dinner"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new ScreenBreakFast();
        } else if (position == 1) {
            return new ScreenLunch();
        } else {
            return new ScreenDinner();
        }
    }
}
