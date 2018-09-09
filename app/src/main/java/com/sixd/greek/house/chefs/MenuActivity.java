package com.sixd.greek.house.chefs;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.CheffAdapter.MenuWeekIntervalAdapter;
import com.sixd.greek.house.chefs.adapter.MenuPagerAdapter;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.List;

import dao_db.AllWeekIntervalList;
import dao_db.UserLoginInfo;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuActivity extends HeaderActivty {
    Context mContext;
    MaterialDialog materialDialog = null;
    TabLayout menu_tabs;
    ViewPager menuPager;
    TextView user_name;
    ImageView profile_icon;
    RelativeLayout download_lay;
    RelativeLayout header_lay;
    LinearLayout linear;
    String Week_number = "", year = "";

    String new_pos="";
    String menu_position="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        materialDialog = new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();


        setHeaderTitele("Menu");
        initViews();
    }

    public void initViews() {
        linear = (LinearLayout) findViewById(R.id.linear);
        header_lay = (RelativeLayout) findViewById(R.id.header_lay);
        download_lay = (RelativeLayout) findViewById(R.id.download_lay);
        user_name = (TextView) findViewById(R.id.user_name);
        profile_icon = (ImageView) findViewById(R.id.profile_icon);
        setUserInfo();
        initTabMenu();

        /*initMenuCalenderAPI();*/

        MenuPagerAdapter menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager());
        menuPager = (ViewPager) findViewById(R.id.menuPager);
        menuPager.setAdapter(menuPagerAdapter);
        menu_tabs.setupWithViewPager(menuPager);


        date_sel_lay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                initgetWeekIntervalAPI();
            }
        });

      /*  download_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
        });*/
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

    public void initTabMenu() {
        menu_tabs = (TabLayout) findViewById(R.id.menu_tabs);
        menu_tabs.addTab(menu_tabs.newTab().setText("Breakfast"));
        menu_tabs.addTab(menu_tabs.newTab().setText("Lunch"));
        menu_tabs.addTab(menu_tabs.newTab().setText("Dinner"));

        menu_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                int position = tab.getPosition();
                if (position == 0)
                {
                    menu_position="Breakfast";
                    header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                }

                else if (position == 1)
                {
                    menu_position="Lunch";
                    header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                }

                else if (position == 2)
                {
                    menu_position="Dinner";
                    header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

                        new_pos = menu_position;

                        MenuPagerAdapter menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager());
                        menuPager = (ViewPager) findViewById(R.id.menuPager);
                        menuPager.setAdapter(menuPagerAdapter);
                        menu_tabs.setupWithViewPager(menuPager);


                        if (new_pos.equalsIgnoreCase("Breakfast")) {
                            menuPager.setCurrentItem(0);
                        } else if (new_pos.equalsIgnoreCase("Lunch")) {
                            menuPager.setCurrentItem(1);
                        } else if (new_pos.equalsIgnoreCase("Dinner")) {
                            menuPager.setCurrentItem(2);
                        }

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

    public void initgetWeekIntervalAPI() {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            materialDialog.show();
            new MenuCalendarManager(mContext).initgetWeekIntervalAPI(new CallBackManager() {

                 @Override
                 public void onSuccess(String responce) {

                     materialDialog.dismiss();
                     if (responce.equals("100")) {

                         final List<AllWeekIntervalList> allWeekIntervalLists;
                         allWeekIntervalLists = new MenuCalendarManager(MenuActivity.this).getAllWeekIntervalLists();
                         Log.i("respo_weekinterval_size", "" + allWeekIntervalLists.size());

                         if (allWeekIntervalLists.size() > 0)
                         {
                             if (android.os.Build.VERSION.SDK_INT >=24)
                             {
                                 final PopupWindow PopUp = new PopupWindow(mContext);
                                 LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                 View layout = layoutInflater.inflate(R.layout.dialog_menu_date_cheff, null);

                                 Rect rc = new Rect();
                                 download_lay.getWindowVisibleDisplayFrame(rc);
                                 int[] xy = new int[2];
                                 download_lay.getLocationInWindow(xy);
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
                                /* int OFFSET_X = 0;
                                 int OFFSET_Y = 0;*/


                                 int OFFSET_X = 0;
                                 int OFFSET_Y = -50;
                                 PopUp.setBackgroundDrawable(new BitmapDrawable());
                                 WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                                 int screenHeight = wm.getDefaultDisplay().getHeight();
                                 int newheight=screenHeight/2;
                                 PopUp.setHeight(newheight - OFFSET_Y);
                                 PopUp.showAtLocation(layout, Gravity.BOTTOM, 0, OFFSET_Y);



                                 // Displaying the popup at the specified location, + offsets.
                               //  PopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

                                // PopUp.showAtLocation((MenuActivity.this).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , 840);


                                 //TextView done_check=(TextView)layout.findViewById(R.id.done_check);
                                 ListView week_interval_list = (ListView) layout.findViewById(R.id.week_interval_list);


                                 TextView cancel_check = (TextView) layout.findViewById(R.id.cancel_check);
                                 cancel_check.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         PopUp.dismiss();
                                     }
                                 });

                                 week_interval_list.setAdapter(new MenuWeekIntervalAdapter(MenuActivity.this, allWeekIntervalLists));
                                 week_interval_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         PopUp.dismiss();
                                         Week_number = "";
                                         year = "";
                                         Week_number = allWeekIntervalLists.get(position).getWeek();
                                         year = allWeekIntervalLists.get(position).getYear();

                                         initMenuCalenderAPI();

                                     }
                                 });

                             }
                             else {
                                 final PopupWindow PopUp = new PopupWindow(mContext);
                                 LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                 View layout = layoutInflater.inflate(R.layout.dialog_menu_date_cheff, null);

                                 Rect rc = new Rect();
                                 download_lay.getWindowVisibleDisplayFrame(rc);
                                 int[] xy = new int[2];
                                 download_lay.getLocationInWindow(xy);
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
                                 ListView week_interval_list = (ListView) layout.findViewById(R.id.week_interval_list);


                                 TextView cancel_check = (TextView) layout.findViewById(R.id.cancel_check);
                                 cancel_check.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         PopUp.dismiss();
                                     }
                                 });

                                 week_interval_list.setAdapter(new MenuWeekIntervalAdapter(MenuActivity.this, allWeekIntervalLists));
                                 week_interval_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         PopUp.dismiss();
                                         Week_number = "";
                                         year = "";
                                         Week_number = allWeekIntervalLists.get(position).getWeek();
                                         year = allWeekIntervalLists.get(position).getYear();

                                         initMenuCalenderAPI();

                                     }
                                 });

                             }

                         } else {
                             Snackbar snackbar = Snackbar
                                     .make(linear, "Week interval not available!", Snackbar.LENGTH_LONG)
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
                       //  Toast.makeText(mContext, "" + responce, Toast.LENGTH_SHORT).show();

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
             }

            );
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MenuActivity.this, HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }

}
