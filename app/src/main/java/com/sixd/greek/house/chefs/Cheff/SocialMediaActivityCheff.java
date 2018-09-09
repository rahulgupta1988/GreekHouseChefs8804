package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sixd.greek.house.chefs.HeaderActivty;
import com.sixd.greek.house.chefs.HomeActivityStudent;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.utils.Constant;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class SocialMediaActivityCheff extends HeaderActivtyCheff {

    LinearLayout linear;
    Context mContext;
    ImageView insta_ic,fb_ic,twit_ic;
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
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constant.SocialURL.INSTAGRAM_URL));
                startActivity(intent);

            }
        });

        fb_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constant.SocialURL.FACEBOOK_URL));
                startActivity(intent);

            }
        });

        twit_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constant.SocialURL.TWITTER_URL));
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SocialMediaActivityCheff.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }
}
