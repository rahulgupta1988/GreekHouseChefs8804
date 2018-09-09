package com.sixd.greek.house.chefs.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.RateMyMealListAdapter;

/**
 * Created by Praveen on 26-Jul-17.
 */

public class RateLunchFragment extends Fragment {

    Context mContext;
    View view;
    RecyclerView dinner_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rateview_frag, container, false);
        dinner_list=(RecyclerView)view.findViewById(R.id.dinner_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        dinner_list.setLayoutManager(layoutManager);
        dinner_list.setItemAnimator(new DefaultItemAnimator());
        dinner_list.setAdapter(new RateMyMealListAdapter(mContext,2));
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("onAttach", "onAttach");
        mContext = (Context) activity;
    }
}

