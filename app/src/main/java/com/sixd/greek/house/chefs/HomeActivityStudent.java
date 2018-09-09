package com.sixd.greek.house.chefs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.adapter.MenuPagerAdapter;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.EventManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllEvents;
import dao_db.UserLoginInfo;

/**
 * Created by Praveen on 14-Jul-17.
 */

public class HomeActivityStudent extends HeaderActivty implements View.OnClickListener{

    String Week_number = "", year = "";

    Context mContext;
    CircularImageView profile_icon;
    TextView user_name;
    MaterialDialog materialDialog=null;
    ImageView menu_icon,rate_icon,late_plate_icon,craving_icon,
            event_icon,social_icon,meal_attan_icon,contact_icon,allergy_icon;
    LinearLayout linear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        linear=(LinearLayout)findViewById(R.id.linear);

        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Home");
        initViews();
    }

    public void initViews(){
        profile_icon=(CircularImageView)findViewById(R.id.profile_icon);
        user_name=(TextView)findViewById(R.id.user_name);
        setUserInfo();
        menu_icon=(ImageView)findViewById(R.id.menu_icon);
        rate_icon=(ImageView)findViewById(R.id.rate_icon);

        craving_icon=(ImageView)findViewById(R.id.craving_icon);
        event_icon=(ImageView)findViewById(R.id.event_icon);
        social_icon=(ImageView)findViewById(R.id.social_icon);

        contact_icon=(ImageView)findViewById(R.id.contact_icon);
        allergy_icon=(ImageView)findViewById(R.id.allergy_icon);
        // set click listener
        menu_icon.setOnClickListener(this);
        rate_icon.setOnClickListener(this);

        craving_icon.setOnClickListener(this);
        event_icon.setOnClickListener(this);
        social_icon.setOnClickListener(this);

        contact_icon.setOnClickListener(this);
        allergy_icon.setOnClickListener(this);


        String is_lateplate_inhouse="",is_lateplate_outofhouse="";
        List<UserLoginInfo> userLoginInfos;
        userLoginInfos = new LoginManager(HomeActivityStudent.this).getUserInfo();
        try
        {
            is_lateplate_inhouse=userLoginInfos.get(0).getIs_lateplate();
            is_lateplate_outofhouse=userLoginInfos.get(0).getIs_lateplate_outofhouse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       /* is_lateplate_inhouse="1";  //ram1
        is_lateplate_outofhouse="1"; //ram1*/

        if (is_lateplate_inhouse.equalsIgnoreCase("1")&&is_lateplate_outofhouse.equalsIgnoreCase("1"))
        {
            late_plate_icon=(ImageView)findViewById(R.id.late_plate_icon);
            late_plate_icon.setVisibility(View.VISIBLE);
            late_plate_icon.setOnClickListener(this);
            meal_attan_icon=(ImageView)findViewById(R.id.meal_attan_icon);
            meal_attan_icon.setVisibility(View.VISIBLE);
            meal_attan_icon.setOnClickListener(this);
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
        {
            /*late_plate_icon=(ImageView)findViewById(R.id.late_plate_icon);
            late_plate_icon.setVisibility(View.VISIBLE);
            late_plate_icon.setOnClickListener(this);
            meal_attan_icon=(ImageView)findViewById(R.id.meal_attan_icon);
            meal_attan_icon.setVisibility(View.VISIBLE);
            meal_attan_icon.setOnClickListener(this);*/
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("1"))
        {
           /* late_plate_icon=(ImageView)findViewById(R.id.late_plate_icon);
            late_plate_icon.setVisibility(View.VISIBLE);
            late_plate_icon.setOnClickListener(this);*/

            meal_attan_icon=(ImageView)findViewById(R.id.meal_attan_icon);
            meal_attan_icon.setVisibility(View.VISIBLE);
            meal_attan_icon.setOnClickListener(this);
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("1")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
        {
            late_plate_icon=(ImageView)findViewById(R.id.late_plate_icon);
            late_plate_icon.setVisibility(View.VISIBLE);
            late_plate_icon.setOnClickListener(this);

            /*meal_attan_icon=(ImageView)findViewById(R.id.meal_attan_icon);
            meal_attan_icon.setVisibility(View.VISIBLE);
            meal_attan_icon.setOnClickListener(this);*/
        }






    }


    public void setUserInfo(){

        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        if(userDatas!=null){
            if(userDatas.get(0)!=null){

                String userprofileimage = userDatas.get(0).getImage_url();
                String userName = userDatas.get(0).getFirst_name()+" "+userDatas.get(0).getLast_name();
                user_name.setText("Hello "+userName);
                Glide.with(mContext)
                        .load(userprofileimage)
                        .placeholder(R.drawable.profile_ic)
                        .error(R.drawable.profile_ic)
                        .crossFade()
                        .fitCenter()
                        .into(profile_icon);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.menu_icon:
               /* Intent menu_intent=new Intent(mContext,MenuActivity.class);
                startActivity(menu_intent);
                finish();*/

                initMenuCalenderAPI();

                break;

            case R.id.rate_icon:
                Intent rate_intent=new Intent(mContext,RateMyMealActivity.class);
                startActivity(rate_intent);
                finish();
                break;

            case R.id.late_plate_icon:
                Intent late_plate_intent=new Intent(mContext,LatePlateRequestActivity.class);
                startActivity(late_plate_intent);
                finish();
                break;

            case R.id.craving_icon:
                Intent craving_intent=new Intent(mContext,CravingActivity.class);
                startActivity(craving_intent);
                finish();
                break;

            case R.id.event_icon:
                {
                    initeventAPI();
                }
                break;

            case R.id.social_icon:
                Intent social_intent=new Intent(mContext,SocialMediaActivity.class);
                startActivity(social_intent);
                finish();
                break;

            case R.id.meal_attan_icon:
                Intent meal_attan_intent=new Intent(mContext,OutofHouseMealAttandanceActivity.class);
                startActivity(meal_attan_intent);
                finish();
                break;

            case R.id.contact_icon:
                Intent contact_intent=new Intent(mContext,ContactUsActivity.class);
                startActivity(contact_intent);
                finish();
                break;

            case R.id.allergy_icon:
                Intent allergy_intent=new Intent(mContext,AllergyActivity.class);
                startActivity(allergy_intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {

        ShowDialogExit();


         /*materialDialog= new MaterialDialog.Builder(this)
                .title("Exit")
                .content("Are you sure, you want to Exit?")
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .contentColor(Color.BLACK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                      *//* finish();*//*
                        finishAffinity();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        materialDialog.dismiss();
                    }
                }).build();

        materialDialog.show();*/

    }



    public void initMenuCalenderAPI() {

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new MenuCalendarManager(mContext).initMenucalenderAPI(Week_number, year, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {

                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        Intent menu_intent=new Intent(mContext,MenuActivity.class);
                        startActivity(menu_intent);
                        finish();
                    }
                    else
                    {
                        // Toast.makeText(mContext, "" + responce, Toast.LENGTH_SHORT).show();

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

                        if(responce.contains("Received data"))
                        {
                            String sessionToken = new LoginManager(mContext).getSessionToken();
                            String error_content = "" + Constant.BASEURL + "&param=" +
                                    Constant.API.MENU_CALENDER_API + "&Week_number=" + Week_number + "&year=" + year
                                    + "&token=" + sessionToken.toString();
                            initGetErrorhtmlAPI(error_content);
                        }
                    }

                }

                @Override
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });

        } else {
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



    public void ShowDialogExit()
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exitdialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        int width = ViewGroup.LayoutParams.FILL_PARENT;
        int height = ViewGroup.LayoutParams.FILL_PARENT;
        dialog.getWindow().setLayout(width, height);
        TextView btn_cancel_ll=(TextView)window.findViewById(R.id.btn_cancel_ll);
        TextView btn_ok_ll=(TextView)window.findViewById(R.id.btn_ok_ll);

        btn_ok_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                finishAffinity();
            }
        });


        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });



        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }




    public void initeventAPI()
    {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new EventManager(mContext).initEventAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {

                        List<AllEvents> allEventses;
                        List<AllEvents> temp_ll_AllEvents;
                        allEventses = new EventManager(HomeActivityStudent.this).getAllEvents();
                        temp_ll_AllEvents = new ArrayList<AllEvents>();
                        temp_ll_AllEvents.clear();
                        for (int x = 0; x < allEventses.size(); x++) {
                            if (allEventses.get(x).getIs_student_calender().equalsIgnoreCase("0")) {
                                temp_ll_AllEvents.add(allEventses.get(x));
                            }
                        }

                        if (temp_ll_AllEvents.size() > 0)
                        {
                            Intent event_intent=new Intent(mContext,EventActivity.class);
                            startActivity(event_intent);
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Event is not available!", Snackbar.LENGTH_LONG)
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

                    } else {
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

                        if(responce.contains("Received data"))
                        {
                            String sessionToken=new LoginManager(mContext).getSessionToken();
                            String error_content=""+Constant.BASEURL + "&param=" +
                                    Constant.API.EVENT_API
                                    +"&token="+sessionToken.toString();
                            initGetErrorhtmlAPI(error_content);
                        }


                    }
                }

                @Override
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });
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



    public void initGetErrorhtmlAPI(String content)
    {
        String error_content="";
        error_content=content;

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new CravingCheffManager(mContext).initGetErrorhtmlAPI(error_content, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {

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
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });
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
            snackbar.show();        }
    }


}
