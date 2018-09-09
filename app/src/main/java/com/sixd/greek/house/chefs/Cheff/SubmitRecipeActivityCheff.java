package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.CheffAdapter.MenuPagerAdapterCheff;
import com.sixd.greek.house.chefs.HeaderActivty;
import com.sixd.greek.house.chefs.HomeActivityStudent;
import com.sixd.greek.house.chefs.ManagerCheff.SubmitRecipeManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.MenuPagerAdapter;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.List;

import dao_db.UserLoginInfo;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class SubmitRecipeActivityCheff extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    TextView submit_recipe_rl;
    LinearLayout linear;
    String Dish_name="",Dish_time="",Ingrediats="",Method="";
    EditText dish_ed_txt,time_ed_txt,ingredients_ed_txt,method_ed_txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Submit Recipe");
        initViews();
    }

    public void initViews()
    {
        linear=(LinearLayout)findViewById(R.id.linear);
        submit_recipe_rl=(TextView)findViewById(R.id.submit_recipe_rl);

        dish_ed_txt=(EditText)findViewById(R.id.dish_ed_txt);
        time_ed_txt=(EditText)findViewById(R.id.time_ed_txt);
        ingredients_ed_txt=(EditText)findViewById(R.id.ingredients_ed_txt);
        method_ed_txt=(EditText)findViewById(R.id.method_ed_txt);
       // initMenuCalenderAPI();

        submit_recipe_rl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dish_name=dish_ed_txt.getText().toString().trim();
                Dish_time=time_ed_txt.getText().toString().trim();
                Ingrediats=ingredients_ed_txt.getText().toString().trim();
                Method=method_ed_txt.getText().toString().trim();

                if(Dish_name.length()>0)
                {
                    if(Dish_time.length()>0)
                    {
                        if(Ingrediats.length()>0)
                        {
                            if(Method.length()>0)
                            {
                                ConnectivityManager connec =
                                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                                // Check for network connections
                                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                                {
                                    initaddRecipeAPI();
                                }
                                else
                                {
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
                            else{
                               // Toast.makeText(mContext,"Please enter method!",Toast.LENGTH_SHORT).show();

                                Snackbar snackbar = Snackbar
                                        .make(linear, "Please enter method!", Snackbar.LENGTH_LONG)
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
                        else{
                            //Toast.makeText(mContext,"Please enter ingredients!",Toast.LENGTH_SHORT).show();

                            Snackbar snackbar = Snackbar
                                    .make(linear, "Please enter ingredients!", Snackbar.LENGTH_LONG)
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
                    else{
                        //Toast.makeText(mContext,"Please enter time!",Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar
                                .make(linear, "Please enter time!", Snackbar.LENGTH_LONG)
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
                else{
                  //  Toast.makeText(mContext,"Please enter dish name!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Please enter dish name!", Snackbar.LENGTH_LONG)
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

    public void initaddRecipeAPI()
    {
            materialDialog.show();
            new SubmitRecipeManager(mContext).initaddRecipeAPI(Dish_name,Dish_time,Ingrediats,Method,
                    new CallBackManager() {

            @Override
            public void onSuccess(String responce)
            {
                materialDialog.dismiss();
                if(responce.equals("100"))
                {
                    Toast.makeText(mContext, "Recipe submitted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SubmitRecipeActivityCheff.this, HomeActivityCheff.class);
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
    public void onBackPressed() {
        Intent intent=new Intent(SubmitRecipeActivityCheff.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }


}
