package com.sixd.greek.house.chefs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sixd.greek.house.chefs.fragments.MenuBreakfastFagment;
import com.sixd.greek.house.chefs.fragments.MenuDinnerFragment;
import com.sixd.greek.house.chefs.fragments.MenuLunchFragment;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuPagerAdapter extends FragmentPagerAdapter {

    public MenuPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new MenuBreakfastFagment();
        }
        else if (position == 1)
        {
            fragment = new MenuLunchFragment();
        }
        else if (position == 2)
        {
            fragment = new MenuDinnerFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Breakfast";
        }
        else if (position == 1)
        {
            title = "Lunch";
        }
        else if (position == 2)
        {
            title = "Dinner";
        }
        return title;
    }
}
