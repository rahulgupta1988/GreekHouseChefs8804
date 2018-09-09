package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sixd.greek.house.chefs.utils.Constant;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class SocialMediaActivity extends HeaderActivty {

    Context mContext;
    ImageView insta_ic,fb_ic,twit_ic;
    LinearLayout linear;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setHeaderTitele("Social Media");
        initViews();
    }

    public void initViews(){
        linear=(LinearLayout)findViewById(R.id.linear);

        insta_ic=(ImageView)findViewById(R.id.insta_ic);
        fb_ic=(ImageView)findViewById(R.id.fb_ic);
        twit_ic=(ImageView)findViewById(R.id.twit_ic);

        insta_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {

                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Constant.SocialURL.INSTAGRAM_URL));
                    startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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

        fb_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Constant.SocialURL.FACEBOOK_URL));
                    startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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

        twit_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Constant.SocialURL.TWITTER_URL));
                    startActivity(intent);
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SocialMediaActivity.this,HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }
}
