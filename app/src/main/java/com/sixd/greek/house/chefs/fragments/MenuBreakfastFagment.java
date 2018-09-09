package com.sixd.greek.house.chefs.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.Menu_BreakfastListAdapter;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuBreakfastFagment extends Fragment {
    Context mContext;
    View view;
    RecyclerView breakfast_list;
    LinearLayout linear;
    RelativeLayout download_lay;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_breakfast_view, container, false);
        breakfast_list=(RecyclerView)view.findViewById(R.id.breakfast_list);
        linear=(LinearLayout)view.findViewById(R.id.linear);
        download_lay=(RelativeLayout)view.findViewById(R.id.download_lay);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        breakfast_list.setLayoutManager(layoutManager);
        breakfast_list.setItemAnimator(new DefaultItemAnimator());
        breakfast_list.setAdapter(new Menu_BreakfastListAdapter(mContext, 1));


        download_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.i("respo_stu_menudoc_URL=",""+MenuCalendarManager.menu_doc_URL);

                if (MenuCalendarManager.menu_doc_URL.length() > 0)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(MenuCalendarManager.menu_doc_URL));
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(linear, "Menu is not available!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });

                    snackbar.setActionTextColor(Color.RED);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        });



        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("onAttach", "onAttach");
        mContext = (Context) activity;
    }
}
