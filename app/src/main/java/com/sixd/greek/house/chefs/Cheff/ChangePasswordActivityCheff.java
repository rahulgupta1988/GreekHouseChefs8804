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

public class ChangePasswordActivityCheff extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    TextView submit_password_rl;
    LinearLayout linear;
    String old_password="",new_password="",confirm_password="";
    EditText old_pass_ed_txt,new_pass_ed_txt,confirmpass_ed_txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Change Password");
        initViews();
    }

    public void initViews()
    {
        linear=(LinearLayout)findViewById(R.id.linear);
        submit_password_rl=(TextView)findViewById(R.id.submit_password_rl);

        old_pass_ed_txt=(EditText)findViewById(R.id.old_pass_ed_txt);
        new_pass_ed_txt=(EditText)findViewById(R.id.new_pass_ed_txt);
        confirmpass_ed_txt=(EditText)findViewById(R.id.confirmpass_ed_txt);
       // initMenuCalenderAPI();

        submit_password_rl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                old_password=old_pass_ed_txt.getText().toString().trim();
                new_password=new_pass_ed_txt.getText().toString().trim();
                confirm_password=confirmpass_ed_txt.getText().toString().trim();

                if(old_password.length()>0)
                {
                    if(new_password.length()>0)
                    {
                        if(confirm_password.length()>0)
                        {
                            if (new_pass_ed_txt.getText().toString().trim().equals(confirmpass_ed_txt.getText().toString().trim()))
                            {
                                ConnectivityManager connec =
                                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                                // Check for network connections
                                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                                {
                                    initchangePasswordAPI();
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
                            else
                            {
                                // Toast.makeText(mContext,"Password do not match!",Toast.LENGTH_SHORT).show();

                                Snackbar snackbar = Snackbar
                                        .make(linear, "Password do not match!", Snackbar.LENGTH_LONG)
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
                            // Toast.makeText(mContext,"Please enter confirm password!",Toast.LENGTH_SHORT).show();

                            Snackbar snackbar = Snackbar
                                    .make(linear, "Please enter confirm password!", Snackbar.LENGTH_LONG)
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
                        // Toast.makeText(mContext,"Please enter new password!",Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar
                                .make(linear, "Please enter new password!", Snackbar.LENGTH_LONG)
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
                    // Toast.makeText(mContext,"Please enter old password!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Please enter old password!", Snackbar.LENGTH_LONG)
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

    public void initchangePasswordAPI()
    {
            materialDialog.show();
            new SubmitRecipeManager(mContext).initchangePasswordAPI(old_password, new_password, confirm_password,
                    new CallBackManager() {

                        @Override
                        public void onSuccess(String responce) {
                            materialDialog.dismiss();
                            if (responce.equals("100")) {
                                Toast.makeText(mContext, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivityCheff.this, HomeActivityCheff.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(linear, "" + responce, Snackbar.LENGTH_LONG)
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
        Intent intent=new Intent(ChangePasswordActivityCheff.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }


}
