package com.sixd.greek.house.chefs.CheffAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.sixd.greek.house.chefs.fragments.MenuBreakfastFagment;
import com.sixd.greek.house.chefs.fragments.MenuDinnerFragment;
import com.sixd.greek.house.chefs.fragments.MenuLunchFragment;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuBreakfastFagmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuDinnerFragmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuLunchFragmentCheff;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuPagerAdapterCheff extends FragmentPagerAdapter {

    public MenuPagerAdapterCheff(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;

        Log.i("","");


        if (position == 0)
        {
            fragment = new MenuBreakfastFagmentCheff();
        }
        else if (position == 1)
        {
            fragment = new MenuLunchFragmentCheff();
        }
        else if (position == 2)
        {
            fragment = new MenuDinnerFragmentCheff();
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
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
