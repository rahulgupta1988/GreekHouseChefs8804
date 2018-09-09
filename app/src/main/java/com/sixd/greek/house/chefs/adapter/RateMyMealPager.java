package com.sixd.greek.house.chefs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sixd.greek.house.chefs.fragments.RateLunchFragment;
import com.sixd.greek.house.chefs.fragments.RatedinnerFragment;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class RateMyMealPager extends FragmentPagerAdapter {

    public RateMyMealPager(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new RateLunchFragment();
        }
        else if (position == 1)
        {
            fragment = new RatedinnerFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Lunch";
        }
        else if (position == 1)
        {
            title = "Dinner";
        }
        return title;
    }
}

