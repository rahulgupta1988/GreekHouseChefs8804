package com.sixd.greek.house.chefs.Cheff;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuWeekIntervalAdapter;
import com.sixd.greek.house.chefs.EventActivity;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuBreakfastFagmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuDinnerFragmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuLunchFragmentCheff;
import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.EventManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.model.MenuModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllEvents;
import dao_db.AllFormCategory;
import dao_db.AllLatePlateChefList;
import dao_db.AllWeekIntervalList;
import dao_db.CalenderData;
import dao_db.CalenderDataDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.UserLoginInfo;

/**
 * Created by Praveen on 14-Jul-17.
 */

public class HomeActivityCheff extends HeaderActivtyCheff implements View.OnClickListener{

    RelativeLayout submit_rl;

    PopupWindow PopUp;

    Context mContext;
    CircularImageView profile_icon;
    TextView user_name;
    ImageView submit_menu_icon,menu_development_icon,submit_budget_icon,late_plate_numbers_icon,chef_forum_icon,
            submit_recipe_icon,school_calender_icon,event_icon,contact_mgmt_icon,outhouse_plate_numbers_icon,
            craving_icon;
    Intent intent;
    LinearLayout linear;

    String Week_number="",year="";
    ScrollView scroll_list;

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


        scroll_list=(ScrollView)findViewById(R.id.scroll_list);
        scroll_list.setScrollbarFadingEnabled(false);
        scroll_list.setVerticalScrollBarEnabled(true);
        scroll_list.setVerticalFadingEdgeEnabled(false);

        submit_rl=(RelativeLayout)findViewById(R.id.submit_rl);

        profile_icon=(CircularImageView)findViewById(R.id.profile_icon);
        user_name=(TextView)findViewById(R.id.user_name);
        setUserInfo();
        submit_menu_icon=(ImageView)findViewById(R.id.submit_menu_icon);
        menu_development_icon=(ImageView)findViewById(R.id.menu_development_icon);
        submit_budget_icon=(ImageView)findViewById(R.id.submit_budget_icon);
        late_plate_numbers_icon=(ImageView)findViewById(R.id.late_plate_numbers_icon);
        chef_forum_icon=(ImageView)findViewById(R.id.chef_forum_icon);
        submit_recipe_icon=(ImageView)findViewById(R.id.submit_recipe_icon);
        school_calender_icon=(ImageView)findViewById(R.id.school_calender_icon);
        event_icon=(ImageView)findViewById(R.id.event_icon);
        contact_mgmt_icon=(ImageView)findViewById(R.id.contact_mgmt_icon);
        outhouse_plate_numbers_icon=(ImageView)findViewById(R.id.outhouse_plate_numbers_icon);
        craving_icon=(ImageView)findViewById(R.id.craving_icon);
        // set click listener

        submit_menu_icon.setOnClickListener(this);
        menu_development_icon.setOnClickListener(this);
        submit_budget_icon.setOnClickListener(this);
        late_plate_numbers_icon.setOnClickListener(this);
        chef_forum_icon.setOnClickListener(this);
        submit_recipe_icon.setOnClickListener(this);
        school_calender_icon.setOnClickListener(this);
        event_icon.setOnClickListener(this);
        contact_mgmt_icon.setOnClickListener(this);
        outhouse_plate_numbers_icon.setOnClickListener(this);
        craving_icon.setOnClickListener(this);

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
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.submit_menu_icon:

                {
                    initgetWeekIntervalAPI();
                }

                break;

            case R.id.menu_development_icon:
                {
                    initMenuDevelopmentAPI();
                }
                break;

            case R.id.submit_budget_icon:
          /*  {
                Snackbar snackbar1 = Snackbar
                        .make(linear, "Coming Soon!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar1.setActionTextColor(Color.RED);
                View sbView1 = snackbar1.getView();
                TextView textView1 = (TextView) sbView1.findViewById(android.support.design.R.id.snackbar_text);
                textView1.setTextColor(Color.YELLOW);
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/uwch.ttf");
                textView1.setTypeface(font);
                snackbar1.show();
            }*/

                Intent submit_budget=new Intent(mContext,SubmitBudgetCheff.class);
                startActivity(submit_budget);
                finish();

                break;

            case R.id.late_plate_numbers_icon:
                {
                    initGetlatePlateAPI();
                }
                break;

            case R.id.chef_forum_icon:

            {
                initgetForumCategoryAPI();
            }

             /* {
                Snackbar snackbar1 = Snackbar
                        .make(linear, "Coming Soon!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar1.setActionTextColor(Color.RED);
                View sbView1 = snackbar1.getView();
                TextView textView1 = (TextView) sbView1.findViewById(android.support.design.R.id.snackbar_text);
                textView1.setTextColor(Color.YELLOW);
                Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/uwch.ttf");
                textView1.setTypeface(font);
                snackbar1.show();
              }*/
                break;

            case R.id.submit_recipe_icon:
                Intent allergy_intent=new Intent(mContext,SubmitRecipeActivityCheff.class);
                startActivity(allergy_intent);
                finish();
                break;

            case R.id.school_calender_icon:
                {
                    initeventAPISchool();
                }
                break;

            case R.id.event_icon:
                {
                    initeventAPI();
                }
                break;

            case R.id.contact_mgmt_icon:
                intent=new Intent(mContext,ContactUsActivityCheff.class);
                startActivity(intent);
                finish();
                break;

            case R.id.outhouse_plate_numbers_icon:
             {
                initGetOutHouseNumbersAPI();
             }
                break;

            case R.id.craving_icon:
            {
                intent=new Intent(mContext,CravingCheffActivity.class);
                startActivity(intent);
                finish();
            }
            break;


        }
    }



    public void initGetOutHouseNumbersAPI()
    {
        String param="GetlatePlateOutOfMeal";

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new LatePlateManagerCheff(mContext).initGetlatePlateAPI(param,new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        List<AllLatePlateChefList> allLatePlateChefLists;
                        allLatePlateChefLists = new LatePlateManagerCheff(HomeActivityCheff.this).getAllLatePlateChefLists();
                        Log.i("respo_LatePlatChef_size", "" + allLatePlateChefLists.size());

                        if (allLatePlateChefLists.size()>0)
                        {
                            Intent rate_intent = new Intent(mContext, OutHouseNumbersActivity.class);
                            startActivity(rate_intent);
                            finish();
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Out house numbers is not available!", Snackbar.LENGTH_LONG)
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
                            String error_content=""+Constant.BASEURL + "&param=GetlatePlateOutOfMeal"
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
            snackbar.show();        }
    }





    public void initGetlatePlateAPI()
    {
        String param="GetlatePlate";

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new LatePlateManagerCheff(mContext).initGetlatePlateAPI(param,new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        List<AllLatePlateChefList> allLatePlateChefLists;
                        allLatePlateChefLists = new LatePlateManagerCheff(HomeActivityCheff.this).getAllLatePlateChefLists();
                        Log.i("respo_LatePlatChef_size", "" + allLatePlateChefLists.size());

                        if (allLatePlateChefLists.size()>0)
                        {
                            Intent rate_intent = new Intent(mContext, LatePlateChefActivity.class);
                            startActivity(rate_intent);
                            finish();
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Late plate numbers is not available!", Snackbar.LENGTH_LONG)
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
                            String error_content=""+Constant.BASEURL + "&param=GetlatePlate"
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
            snackbar.show();        }
    }


    MaterialDialog materialDialog=null;
    @Override
    public void onBackPressed()
    {
        /* materialDialog= new MaterialDialog.Builder(this)
                .title("Exit")
                .content("Are you sure, you want to Exit?")
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .contentColor(Color.BLACK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       *//*finish();*//*
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

        ShowDialogExit();


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
                        allEventses = new EventManager(HomeActivityCheff.this).getAllEvents();
                        temp_ll_AllEvents = new ArrayList<AllEvents>();
                        temp_ll_AllEvents.clear();
                        for (int x = 0; x < allEventses.size(); x++) {
                            if (allEventses.get(x).getIs_student_calender().equalsIgnoreCase("0")) {
                                temp_ll_AllEvents.add(allEventses.get(x));
                            }
                        }

                        if (temp_ll_AllEvents.size() > 0) {
                            intent = new Intent(mContext, EventActivityCheff.class);
                            startActivity(intent);
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
            snackbar.show();        }
    }


    public void initeventAPISchool()
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
                    if (responce.equals("100"))
                    {
                        List<AllEvents> allEventses;
                        List<AllEvents> temp_ll_AllEvents;
                        allEventses = new EventManager(HomeActivityCheff.this).getAllEvents();
                        temp_ll_AllEvents = new ArrayList<AllEvents>();
                        temp_ll_AllEvents.clear();
                        for (int x = 0; x < allEventses.size(); x++)
                        {
                            if (allEventses.get(x).getIs_student_calender().equalsIgnoreCase("1"))
                            {
                                temp_ll_AllEvents.add(allEventses.get(x));
                            }
                        }

                        if(temp_ll_AllEvents.size()>0)
                        {
                            intent=new Intent(mContext,SchoolCalActivityCheff.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "School Calendar is not available!", Snackbar.LENGTH_LONG)
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
            snackbar.show();        }
    }



    public void initMenuDevelopmentAPI()
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

            new MenuDevelopmentManager(mContext).initMenuDevelopmentAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {
                        final List<MenuCategory> getMenuCategories;
                        final List<MenuCategory> temp_ll_MenuCategory;
                        getMenuCategories = new MenuDevelopmentManager(HomeActivityCheff.this).getmMenuCategories();
                        temp_ll_MenuCategory = new ArrayList<MenuCategory>();
                        temp_ll_MenuCategory.clear();
                        Log.i("respo_menulist_size", "" + getMenuCategories.size());

                        for (int x = 0; x < getMenuCategories.size(); x++) {
                            if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase("null")) {
                                temp_ll_MenuCategory.add(getMenuCategories.get(x));
                            }
                        }

                        if (temp_ll_MenuCategory.size() > 0) {
                            intent = new Intent(mContext, MenuDevCategoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Menu Category is not available!", Snackbar.LENGTH_LONG)
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

                        if(responce.contains("Received data"))
                        {
                            String sessionToken=new LoginManager(mContext).getSessionToken();
                            String error_content=""+Constant.BASEURL + "&param=" +
                                    Constant.API.MENU_DEVELOPMENT_CATEGORY_API
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



   /* public void initGetMasterAllMenuItemsAPI()
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
            new MenuCalendarManager(mContext).initGetMasterAllMenuItemsAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    if (responce.equals("100"))
                    {
                        *//*initMenuCalenderAPI();*//*
                    }
                    else
                    {
                        materialDialog.dismiss();
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
    }*/




    public void initMenuCalenderAPI()
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
            new MenuCalendarManager(mContext).initMenucalenderAPI(Week_number, year, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {

                    materialDialog.dismiss();
                    if (responce.equals("100")) {

                        DaoSession daoSession = BaseManager.getDBSessoin(HomeActivityCheff.this);
                        daoSession.getCalenderDataDao().deleteAll();
                        CalenderDataDao calenderDataDao = daoSession.getCalenderDataDao();
                        CalenderData calenderData = new CalenderData(Week_number, year);
                        calenderDataDao.insertOrReplace(calenderData);


                        {
                            /*=========== breaffastList=============*/

                            Constant.ListElementsMenutitle.clear();
                            Constant.ListElementsMenutitle2.clear();
                            Constant.ListElementsMenutitle3.clear();
                            Constant.ListElementsMenutitle4.clear();
                            Constant.ListElementsMenutitle5.clear();
                            Constant.ListElementsMenutitle6.clear();
                            Constant.ListElementsMenutitle7.clear();

                            Constant.ListElementsFinal_temp.clear();

                            {
                                List<MenuModel> menuList = null;
                                menuList = MenuCalendarManager.breaffastList;
                                Log.i("respo_sise_model=", "" + menuList.size());

                                Constant.ListElementsMenutitle.add(menuList.get(0).getMenuTitle());
                                Constant.ListElementsMenutitle2.add(menuList.get(1).getMenuTitle());
                                Constant.ListElementsMenutitle3.add(menuList.get(2).getMenuTitle());
                                Constant.ListElementsMenutitle4.add(menuList.get(3).getMenuTitle());
                                Constant.ListElementsMenutitle5.add(menuList.get(4).getMenuTitle());
                                Constant.ListElementsMenutitle6.add(menuList.get(5).getMenuTitle());
                                Constant.ListElementsMenutitle7.add(menuList.get(6).getMenuTitle());
                            }
                        }


                        {
                            /*===========lunchList=============*/

                            Constant.ListElementsMenutitle_lunch.clear();
                            Constant.ListElementsMenutitle2_lunch.clear();
                            Constant.ListElementsMenutitle3_lunch.clear();
                            Constant.ListElementsMenutitle4_lunch.clear();
                            Constant.ListElementsMenutitle5_lunch.clear();
                            Constant.ListElementsMenutitle6_lunch.clear();
                            Constant.ListElementsMenutitle7_lunch.clear();

                            Constant.ListElementsFinal_temp_lunch.clear();

                            {
                                List<MenuModel> menuList = null;
                                menuList = MenuCalendarManager.lunchList;
                                Log.i("respo_sise_model=", "" + menuList.size());

                                Constant.ListElementsMenutitle_lunch.add(menuList.get(0).getMenuTitle());
                                Constant.ListElementsMenutitle2_lunch.add(menuList.get(1).getMenuTitle());
                                Constant.ListElementsMenutitle3_lunch.add(menuList.get(2).getMenuTitle());
                                Constant.ListElementsMenutitle4_lunch.add(menuList.get(3).getMenuTitle());
                                Constant.ListElementsMenutitle5_lunch.add(menuList.get(4).getMenuTitle());
                                Constant.ListElementsMenutitle6_lunch.add(menuList.get(5).getMenuTitle());
                                Constant.ListElementsMenutitle7_lunch.add(menuList.get(6).getMenuTitle());
                            }
                        }


                        {
                            /*===========dinnerList=============*/

                            Constant.ListElementsMenutitle_dinner.clear();
                            Constant.ListElementsMenutitle2_dinner.clear();
                            Constant.ListElementsMenutitle3_dinner.clear();
                            Constant.ListElementsMenutitle4_dinner.clear();
                            Constant.ListElementsMenutitle5_dinner.clear();
                            Constant.ListElementsMenutitle6_dinner.clear();
                            Constant.ListElementsMenutitle7_dinner.clear();

                            Constant.ListElementsFinal_temp_dinner.clear();

                            {
                                List<MenuModel> menuList = null;
                                menuList = MenuCalendarManager.dinnerList;
                                Log.i("respo_sise_model=", "" + menuList.size());

                                Constant.ListElementsMenutitle_dinner.add(menuList.get(0).getMenuTitle());
                                Constant.ListElementsMenutitle2_dinner.add(menuList.get(1).getMenuTitle());
                                Constant.ListElementsMenutitle3_dinner.add(menuList.get(2).getMenuTitle());
                                Constant.ListElementsMenutitle4_dinner.add(menuList.get(3).getMenuTitle());
                                Constant.ListElementsMenutitle5_dinner.add(menuList.get(4).getMenuTitle());
                                Constant.ListElementsMenutitle6_dinner.add(menuList.get(5).getMenuTitle());
                                Constant.ListElementsMenutitle7_dinner.add(menuList.get(6).getMenuTitle());
                            }
                        }


                        intent = new Intent(mContext, MenuActivityCheff.class);
                        intent.putExtra("tabposition", "0");
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




    public void initgetWeekIntervalAPI()
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
            new MenuCalendarManager(mContext).initgetWeekIntervalAPI(new CallBackManager() {

                                                                         @Override
                                                                         public void onSuccess(String responce) {

                                                                             materialDialog.dismiss();
                                                                             if (responce.equals("100")) {
                                                                                 final List<AllWeekIntervalList> allWeekIntervalLists;
                                                                                 allWeekIntervalLists = new MenuCalendarManager(HomeActivityCheff.this).getAllWeekIntervalLists();
                                                                                 Log.i("respo_weekinterval_size", "" + allWeekIntervalLists.size());

                                                                                 if (allWeekIntervalLists.size() > 0) {
                                                                                     PopUp = new PopupWindow(mContext);
                                                                                     LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                                     View layout = layoutInflater.inflate(R.layout.dialog_menu_date_cheff, null);
                                                                                     TextView cancel_check = (TextView) layout.findViewById(R.id.cancel_check);
                                                                                     ListView week_interval_list = (ListView) layout.findViewById(R.id.week_interval_list);


                                                                                     if (android.os.Build.VERSION.SDK_INT >= 24) {
                                                                                         Rect rc = new Rect();
                                                                                         submit_rl.getWindowVisibleDisplayFrame(rc);
                                                                                         int[] xy = new int[2];
                                                                                         submit_rl.getLocationInWindow(xy);
                                                                                         rc.offset(xy[0], xy[1]);
                                                                                         PopUp.setContentView(layout);
                                                                                         DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
                                                                                         int height = displaymetrics.heightPixels;
                                                                                         int width = displaymetrics.widthPixels;
                                                                                         PopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                         PopUp.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                                                                                         PopUp.setFocusable(true);
                                                                                         PopUp.setOutsideTouchable(false);
                                                                                         // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.


                                                                                         int OFFSET_X = 0;
                                                                                         int OFFSET_Y = -50;
                                                                                         PopUp.setBackgroundDrawable(new BitmapDrawable());
                                                                                         WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                                                                                         int screenHeight = wm.getDefaultDisplay().getHeight();
                                                                                         int newheight = screenHeight / 2;
                                                                                         PopUp.setHeight(newheight - OFFSET_Y);
                                                                                         PopUp.showAtLocation(layout, Gravity.BOTTOM, 0, OFFSET_Y);

/* int OFFSET_X = 0;
int OFFSET_Y = 0;
PopUp.setBackgroundDrawable(new BitmapDrawable());
// Displaying the popup at the specified location, + offsets.

// PopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

PopUp.showAtLocation((HomeActivityCheff.this).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 840);

//TextView done_check=(TextView)layout.findViewById(R.id.done_check);*/

                                                                                     } else {

                                                                                         Rect rc = new Rect();
                                                                                         submit_rl.getWindowVisibleDisplayFrame(rc);
                                                                                         int[] xy = new int[2];
                                                                                         submit_rl.getLocationInWindow(xy);
                                                                                         rc.offset(xy[0], xy[1]);
                                                                                         PopUp.setContentView(layout);
                                                                                         DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
                                                                                         int height = displaymetrics.heightPixels;
                                                                                         int width = displaymetrics.widthPixels;
                                                                                         PopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                         PopUp.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                                                                                         PopUp.setFocusable(true);
                                                                                         PopUp.setOutsideTouchable(false);
                                                                                         // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
                                                                                         int OFFSET_X = 0;
                                                                                         int OFFSET_Y = 0;
                                                                                         PopUp.setBackgroundDrawable(new BitmapDrawable());
                                                                                         // Displaying the popup at the specified location, + offsets.
                                                                                         PopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

                                                                                         //TextView done_check=(TextView)layout.findViewById(R.id.done_check);
                                                                                     }

                                                                                     cancel_check.setOnClickListener(new View.OnClickListener() {
                                                                                         @Override
                                                                                         public void onClick(View v) {
                                                                                             PopUp.dismiss();
                                                                                         }
                                                                                     });

                                                                                     week_interval_list.setAdapter(new MenuWeekIntervalAdapter(HomeActivityCheff.this, allWeekIntervalLists));

                                                                                     week_interval_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                                         @Override
                                                                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                                                             Week_number = allWeekIntervalLists.get(position).getWeek().toString().trim();
                                                                                             year = allWeekIntervalLists.get(position).getYear().toString().trim();

                    /* initGetMasterAllMenuItemsAPI();*/

                                                                                             initMenuCalenderAPI();

                                                                                         }
                                                                                     });


                                                                                 } else {
                                                                                     Snackbar snackbar = Snackbar
                                                                                             .make(linear, "Week Interval not available!", Snackbar.LENGTH_LONG)
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

                                                                                 if(responce.contains("Received data"))
                                                                                 {
                                                                                     String sessionToken = new LoginManager(mContext).getSessionToken();
                                                                                     String error_content = "" + Constant.BASEURL + "&param=" +
                                                                                             Constant.API.WEEK_INTERVAL_API
                                                                                             + "&token=" + sessionToken.toString();
                                                                                     initGetErrorhtmlAPI(error_content);

                                                                                 }

                                                                             }

                                                                         }

                                                                         @Override
                                                                         public void onFailure(String responce) {
                                                                             materialDialog.dismiss();
                                                                         }
                                                                     }

            );
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



    public void initgetForumCategoryAPI()
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
            new ForumManager(mContext).initgetForumCategoryAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {
                        List<AllFormCategory> allFormCategories;
                        allFormCategories = new ForumManager(HomeActivityCheff.this).getAllFormCategories();
                        Log.i("respo_ForumCateg_size", "" + allFormCategories.size());

                        if (allFormCategories.size() > 0) {
                            Intent forum_intent = new Intent(mContext, ForumCategoryActivity.class);
                            startActivity(forum_intent);
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Forum Category is not available!", Snackbar.LENGTH_LONG)
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

                        if(responce.contains("Received data"))
                        {
                            String sessionToken=new LoginManager(mContext).getSessionToken();
                            String error_content=""+Constant.BASEURL + "&param=" +
                                    Constant.API.GET_FORUM_CATEGPRY_API
                                    +"&token="+sessionToken.toString();
                            initGetErrorhtmlAPI(error_content);
                        }

                    }
                }

                @Override
                public void onFailure(String responce) {
                    materialDialog.dismiss();

                    ConnectivityManager connec =
                            (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    // Check for network connections
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
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
