package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.RateManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.List;

import dao_db.UserLoginInfo;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class RateMyMealActivity extends HeaderActivty implements View.OnClickListener{

    TextView txt_lay2;

    Context mContext;
    MaterialDialog materialDialog=null;
    EditText rate_comment_txt;
    TextView user_name;
    ImageView start_one,start_two,start_three,start_four,start_five;
    ImageView profile_icon;
    RelativeLayout meal_tab,chef_tab;
    ImageView meal_ic,chef_ic,arrow_meal,arrow_chef;
    TextView meal_txt,chef_txt;
    TextView submit_rate;
    String rating_type="rating_meal";
    LinearLayout linear;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();
        setHeaderTitele("Rate My Meal");
        initViews();
    }

    public void initViews(){

        txt_lay2=(TextView)findViewById(R.id.txt_lay2);
        String simple = "This is an anonymous rating and is\n sent to management then communicated\n to your chef.";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder(colored+simple);

        builder.setSpan(new ForegroundColorSpan(Color.BLACK), simple.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt_lay2.setText(builder);


        linear=(LinearLayout)findViewById(R.id.linear);
        submit_rate=(TextView)findViewById(R.id.submit_rate);
        profile_icon = (ImageView) findViewById(R.id.profile_icon);
        user_name=(TextView)findViewById(R.id.user_name);
        rate_comment_txt=(EditText)findViewById(R.id.rate_comment_txt);
        start_one=(ImageView)findViewById(R.id.start_one);
        start_two=(ImageView)findViewById(R.id.start_two);
        start_three=(ImageView)findViewById(R.id.start_three);
        start_four=(ImageView)findViewById(R.id.start_four);
        start_five=(ImageView)findViewById(R.id.start_five);

        setUserInfo();

        start_one.setOnClickListener(this);
        start_two.setOnClickListener(this);
        start_three.setOnClickListener(this);
        start_four.setOnClickListener(this);
        start_five.setOnClickListener(this);


        meal_tab=(RelativeLayout)findViewById(R.id.meal_tab);
        chef_tab=(RelativeLayout)findViewById(R.id.chef_tab);
        meal_ic=(ImageView)findViewById(R.id.meal_ic);
        chef_ic=(ImageView)findViewById(R.id.chef_ic);
        meal_txt=(TextView)findViewById(R.id.meal_txt);
        chef_txt=(TextView)findViewById(R.id.chef_txt);

        arrow_meal=(ImageView)findViewById(R.id.arrow_meal);
        arrow_chef=(ImageView)findViewById(R.id.arrow_chef);

        meal_ic.setSelected(true);
        chef_ic.setSelected(false);

            meal_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        rating_type="rating_meal";
                        chef_ic.setSelected(false);
                        chef_txt.setTextColor(Color.parseColor("#686868"));
                        arrow_chef.setVisibility(View.GONE);

                        meal_ic.setSelected(true);
                        meal_txt.setTextColor(Color.parseColor("#ffffff"));
                        arrow_meal.setVisibility(View.VISIBLE);

                }
            });


            chef_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        rating_type="rating_chef";
                        meal_ic.setSelected(false);
                        meal_txt.setTextColor(Color.parseColor("#686868"));
                        arrow_meal.setVisibility(View.GONE);

                        chef_ic.setSelected(true);
                        chef_txt.setTextColor(Color.parseColor("#ffffff"));
                        arrow_chef.setVisibility(View.VISIBLE);

                }
            });

        submit_rate.setOnClickListener(new View.OnClickListener() {
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
                    initRateAPI();
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

    public void setUserInfo() {

        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        if (userDatas != null)
        {
            if (userDatas.get(0) != null)
            {
                String userName = userDatas.get(0).getChef_name();
                user_name.setText("Chef " + userName);

                try {
                    String userprofileimage =userDatas.get(0).getChef_image_url().toString().trim();
                    Picasso.with(mContext)
                            .load(userprofileimage)
                            .resize(100, 100)
                            .centerInside()
                            .placeholder(R.drawable.app_icon)
                            .error(R.drawable.app_icon)
                            .into(profile_icon);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(RateMyMealActivity.this,HomeActivityStudent.class);
        startActivity(intent);
        finish();

    }

    int rating_count=0;

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.start_one:
                     uncheckRate();
                     start_one.setImageResource(R.drawable.star_fiil);
                rating_count=1;
                break;
            case R.id.start_two:
                    uncheckRate();
                    start_one.setImageResource(R.drawable.star_fiil);
                    start_two.setImageResource(R.drawable.star_fiil);
                rating_count=2;
                break;
            case R.id.start_three:
                    uncheckRate();
                    start_one.setImageResource(R.drawable.star_fiil);
                    start_two.setImageResource(R.drawable.star_fiil);
                    start_three.setImageResource(R.drawable.star_fiil);
                rating_count=3;
                break;
            case R.id.start_four:
                    uncheckRate();
                    start_one.setImageResource(R.drawable.star_fiil);
                    start_two.setImageResource(R.drawable.star_fiil);
                    start_three.setImageResource(R.drawable.star_fiil);
                    start_four.setImageResource(R.drawable.star_fiil);
                rating_count=4;
                break;
            case R.id.start_five:
                      uncheckRate();
                      start_one.setImageResource(R.drawable.star_fiil);
                      start_two.setImageResource(R.drawable.star_fiil);
                      start_three.setImageResource(R.drawable.star_fiil);
                      start_four.setImageResource(R.drawable.star_fiil);
                      start_five.setImageResource(R.drawable.star_fiil);
                rating_count=5;
                break;
        }
    }



    public void uncheckRate(){
        start_one.setImageResource(R.drawable.star_blank);
        start_two.setImageResource(R.drawable.star_blank);
        start_three.setImageResource(R.drawable.star_blank);
        start_four.setImageResource(R.drawable.star_blank);
        start_five.setImageResource(R.drawable.star_blank);

    }

    public void setChefInfo(){

    }


    public void initRateAPI(){

        String rate_comment="";
        rate_comment =rate_comment_txt.getText().toString();

        if(rating_count==0)
        {

            Snackbar snackbar = Snackbar
                    .make(linear, "Please give a rate!", Snackbar.LENGTH_LONG)
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


            //Toast.makeText(mContext,"Please give a rate.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(rating_count<3){
            if(rate_comment.length()<=0)
            {

                Snackbar snackbar = Snackbar
                        .make(linear, "We’re sorry you weren’t 100% satisfied! Please share a few details with us so we can improve!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                textView.setMaxLines(3);
                snackbar.show();

                //Toast.makeText(mContext,"Rating below 3,comment is mandatory",Toast.LENGTH_SHORT).show();
                return;
            }

        }

        materialDialog.show();
        new RateManager(mContext).initRateAPI(rating_type, String.valueOf(rating_count), rate_comment, new CallBackManager() {
            @Override
            public void onSuccess(String responce)
            {
                materialDialog.dismiss();
                if(responce.equals("100"))
                {
                    Toast.makeText(mContext,"Rating added successfully!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RateMyMealActivity.this,HomeActivityStudent.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(linear,""+responce, Snackbar.LENGTH_LONG)
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

            @Override
            public void onFailure(String responce)
            {
                materialDialog.dismiss();

                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    Snackbar snackbar = Snackbar
                            .make(linear,""+responce, Snackbar.LENGTH_LONG)
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
    public void onVisibleBehindCanceled()
    {
        Intent intent=new Intent(RateMyMealActivity.this,HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }
}

