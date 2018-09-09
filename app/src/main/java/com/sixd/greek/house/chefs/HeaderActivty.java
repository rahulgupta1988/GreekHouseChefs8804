package com.sixd.greek.house.chefs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.EventManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.LogoutManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllEvents;
import dao_db.DaoSession;
import dao_db.UserLoginInfo;
import dao_db.UserLoginInfoDao;

/**
 * Created by Praveen on 18-Jul-17.
 */

public class HeaderActivty extends AppCompatActivity{

    String Week_number = "", year = "";

    DrawerLayout drawer_layout;
    ImageView line_menu_icon,logout_icon;
    TextView header_txt;
    MaterialDialog logoutconfirm=null;
    LinearLayout container_lay;
    RelativeLayout date_sel_lay;
    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout linear;
// nav list views
    RelativeLayout menu_item,rate_item,request_late_item,craving_item,event_item,
        social_item,outhouse_item,allergy_item,contact_item,change_pass_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
        mContext=this;
        linear=(LinearLayout)findViewById(R.id.linear);

        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        navigationDrwaerInit();
        logout();
    }

    public void logout(){

        logoutconfirm=new MaterialDialog.Builder(this)
                .title("Logout")
                .content("Are you sure, you want to logout?")
                .cancelable(false)
                .positiveText("OK")
                .negativeText("Cancel")
                .contentColor(Color.BLACK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ConnectivityManager connec =
                                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                        // Check for network connections
                        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                            initLogout();
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
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        logoutconfirm.dismiss();
                    }
                }).build();

        logout_icon=(ImageView)findViewById(R.id.logout_icon);
        logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*logoutconfirm.show();*/
                ShowDialogLogout();
            }
        });

    }


    public void ShowDialogLogout()
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logputdialog);
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
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    dialog.dismiss();
                    initLogout();
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


    public void initLogout()
    {
        materialDialog.show();
        new LogoutManager(this).initLogoutAPI(new CallBackManager() {
            @Override
            public void onSuccess(String responce) {
                materialDialog.dismiss();
                if (responce.equals("100"))
                {
                    //Toast.makeText(mContext,""+responce.toString(),Toast.LENGTH_SHORT).show();
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    UserLoginInfoDao userLoginInfoDao = daoSession.getUserLoginInfoDao();
                    userLoginInfoDao.deleteAll();
                    Intent intent = new Intent(HeaderActivty.this, UserSelectionActivity.class);
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


    public void setHeaderTitele(String title){
        date_sel_lay=(RelativeLayout)findViewById(R.id.date_sel_lay);
        header_txt=(TextView)findViewById(R.id.header_txt);
        header_txt.setText("" + title);

        line_menu_icon=(ImageView)findViewById(R.id.line_menu_icon);


       /* line_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HeaderActivty.this,HomeActivityStudent.class);
                startActivity(intent);
                finish();
            }
        });*/
        container_lay=(LinearLayout)findViewById(R.id.container_lay);
        container_lay.removeAllViews();
        if(title.equals("Menu"))
        {
            date_sel_lay.setVisibility(View.VISIBLE);
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.menu_activity, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Rate My Meal")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_ratemymeal, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Late Plate Request")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_lateplaterequest, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Craving Requests"))
        {
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_craving, null);
            container_lay.addView(layout);
        }
        else if(title.equals("Events")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_events, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Social Media")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_socialmedia, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Out of House Meal Attendance"))
        {
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_outofhousemealattandance, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Contact Us")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_contactus, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Allergy")){
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.activity_allergy, null);
            container_lay.addView(layout);
        }

        else if(title.equals("Change Password"))
        {
            RelativeLayout layout = (RelativeLayout) View.inflate(this,
                    R.layout.change_password, null);
            container_lay.addView(layout);
        }

        else{

            String is_lateplate_inhouse="",is_lateplate_outofhouse="";
            List<UserLoginInfo> userLoginInfos;
            userLoginInfos = new LoginManager(HeaderActivty.this).getUserInfo();
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
                RelativeLayout layout = (RelativeLayout) View.inflate(this,
                        R.layout.home_view_student_first, null);
                container_lay.addView(layout);
            }
            else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
            {
                RelativeLayout layout = (RelativeLayout) View.inflate(this,
                        R.layout.home_view_student_second, null);
                container_lay.addView(layout);
            }
            else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("1"))
            {
                RelativeLayout layout = (RelativeLayout) View.inflate(this,
                        R.layout.home_view_student_third, null);
                container_lay.addView(layout);
            }
            else if (is_lateplate_inhouse.equalsIgnoreCase("1")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
            {
                RelativeLayout layout = (RelativeLayout) View.inflate(this,
                        R.layout.home_view_student_fourth, null);
                container_lay.addView(layout);
            }
        }
    }

    public void navigationDrwaerInit(){
        drawer_layout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer_layout.closeDrawers();
        line_menu_icon=(ImageView)findViewById(R.id.line_menu_icon);
        line_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer_layout.isDrawerOpen(GravityCompat.START)){
                    drawer_layout.closeDrawers();
                }
                else{
                    drawer_layout.openDrawer(GravityCompat.START);
                }
            }
        });





        setUserInfo_nav();
    }

    public void setUserInfo_nav(){

        ImageView profile_icon=(ImageView)findViewById(R.id.profile_icon_nav);
        TextView user_name=(TextView)findViewById(R.id.user_name_nav);

        try
        {
            List<UserLoginInfo> userDatas = null;
            userDatas = new LoginManager(getApplicationContext()).getUserInfo();
            if(userDatas!=null)
            {
                if(userDatas.get(0)!=null)
                {

                    String userprofileimage = userDatas.get(0).getImage_url();
                    String userName = userDatas.get(0).getFirst_name()+" "+userDatas.get(0).getLast_name();
                    user_name.setText("Hello "+userName);
                    Glide.with(this)
                            .load(userprofileimage)
                            .placeholder(R.drawable.profile_ic)
                            .error(R.drawable.profile_ic)
                            .crossFade()
                            .fitCenter()
                            .into(profile_icon);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }





        setnavList();

    }

    public void setnavList(){
        menu_item=(RelativeLayout)findViewById(R.id.menu_item);
        rate_item=(RelativeLayout)findViewById(R.id.rate_item);
        craving_item=(RelativeLayout)findViewById(R.id.craving_item);
        event_item=(RelativeLayout)findViewById(R.id.event_item);
        social_item=(RelativeLayout)findViewById(R.id.social_item);
        allergy_item=(RelativeLayout)findViewById(R.id.allergy_item);
        contact_item=(RelativeLayout)findViewById(R.id.contact_item);
        change_pass_item=(RelativeLayout)findViewById(R.id.change_pass_item);

        menu_item.setOnClickListener(new OnClicklisten());
        rate_item.setOnClickListener(new OnClicklisten());
        craving_item.setOnClickListener(new OnClicklisten());
        event_item.setOnClickListener(new OnClicklisten());
        social_item.setOnClickListener(new OnClicklisten());
        allergy_item.setOnClickListener(new OnClicklisten());
        contact_item.setOnClickListener(new OnClicklisten());
        change_pass_item.setOnClickListener(new OnClicklisten());

        String is_lateplate_inhouse="",is_lateplate_outofhouse="";
        List<UserLoginInfo> userLoginInfos;
        userLoginInfos = new LoginManager(HeaderActivty.this).getUserInfo();
        try
        {
            is_lateplate_inhouse=userLoginInfos.get(0).getIs_lateplate();
            is_lateplate_outofhouse=userLoginInfos.get(0).getIs_lateplate_outofhouse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.i("respo_lateplate_inhouse","="+is_lateplate_inhouse+" & is_lateplate_outofhouse="+is_lateplate_outofhouse);

        if (is_lateplate_inhouse.equalsIgnoreCase("1")&&is_lateplate_outofhouse.equalsIgnoreCase("1"))
        {
            request_late_item=(RelativeLayout)findViewById(R.id.request_late_item);
            request_late_item.setVisibility(View.VISIBLE);
            request_late_item.setOnClickListener(new OnClicklisten());
            outhouse_item=(RelativeLayout)findViewById(R.id.outhouse_item);
            request_late_item.setVisibility(View.VISIBLE);
            outhouse_item.setOnClickListener(new OnClicklisten());
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
        {
            request_late_item=(RelativeLayout)findViewById(R.id.request_late_item);
            request_late_item.setVisibility(View.GONE);
            request_late_item.setOnClickListener(new OnClicklisten());
            outhouse_item=(RelativeLayout)findViewById(R.id.outhouse_item);
            outhouse_item.setVisibility(View.GONE);
            outhouse_item.setOnClickListener(new OnClicklisten());
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("0")&&is_lateplate_outofhouse.equalsIgnoreCase("1"))
        {
            request_late_item=(RelativeLayout)findViewById(R.id.request_late_item);
            request_late_item.setVisibility(View.GONE);
            request_late_item.setOnClickListener(new OnClicklisten());
            outhouse_item=(RelativeLayout)findViewById(R.id.outhouse_item);
            request_late_item.setVisibility(View.VISIBLE);
            outhouse_item.setOnClickListener(new OnClicklisten());
        }
        else if (is_lateplate_inhouse.equalsIgnoreCase("1")&&is_lateplate_outofhouse.equalsIgnoreCase("0"))
        {
            request_late_item=(RelativeLayout)findViewById(R.id.request_late_item);
            request_late_item.setVisibility(View.VISIBLE);
            request_late_item.setOnClickListener(new OnClicklisten());
            outhouse_item=(RelativeLayout)findViewById(R.id.outhouse_item);
            outhouse_item.setVisibility(View.GONE);
            outhouse_item.setOnClickListener(new OnClicklisten());
        }



    }

    public class OnClicklisten implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.menu_item:
                     /* Intent menu_intent=new Intent(mContext,MenuActivity.class);
                startActivity(menu_intent);
                finish();*/
                    drawer_layout.closeDrawers();
                    initMenuCalenderAPI();

                    break;

                case R.id.rate_item:
                    drawer_layout.closeDrawers();
                    Intent rate_intent=new Intent(mContext,RateMyMealActivity.class);
                    startActivity(rate_intent);
                    finish();
                    break;

                case R.id.request_late_item:
                    drawer_layout.closeDrawers();
                    Intent late_plate_intent=new Intent(mContext,LatePlateRequestActivity.class);
                    startActivity(late_plate_intent);
                    finish();
                    break;

                case R.id.craving_item:
                    drawer_layout.closeDrawers();
                    Intent craving_intent=new Intent(mContext,CravingActivity.class);
                    startActivity(craving_intent);
                    finish();
                    break;

                case R.id.event_item:
                    {
                        drawer_layout.closeDrawers();
                        initeventAPI();
                    }
                    break;

                case R.id.social_item:
                    drawer_layout.closeDrawers();
                    Intent social_intent=new Intent(mContext,SocialMediaActivity.class);
                    startActivity(social_intent);
                    finish();
                    break;

                case R.id.outhouse_item:
                    drawer_layout.closeDrawers();
                    Intent meal_attan_intent=new Intent(mContext,OutofHouseMealAttandanceActivity.class);
                    startActivity(meal_attan_intent);
                    finish();
                    break;

                case R.id.allergy_item:
                    drawer_layout.closeDrawers();
                    Intent allergy_intent=new Intent(mContext,AllergyActivity.class);
                    startActivity(allergy_intent);
                    finish();
                    break;

                case R.id.contact_item:
                    drawer_layout.closeDrawers();
                    Intent contact_intent=new Intent(mContext,ContactUsActivity.class);
                    startActivity(contact_intent);
                    finish();
                    break;

                case R.id.change_pass_item:
                    drawer_layout.closeDrawers();
                    Intent change_password=new Intent(mContext,ChangePasswordActivityStudent.class);
                    startActivity(change_password);
                    finish();
                    break;

            }
        }
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
                        allEventses = new EventManager(HeaderActivty.this).getAllEvents();
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


    public void initMenuCalenderAPI() {

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            materialDialog.show();
            new MenuCalendarManager(mContext).initMenucalenderAPI(Week_number, year, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {

                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        Intent menu_intent = new Intent(mContext, MenuActivity.class);
                        startActivity(menu_intent);
                        finish();
                    }
                    else
                    {
                        // Toast.makeText(mContext, "" + responce, Toast.LENGTH_SHORT).show();

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



}
