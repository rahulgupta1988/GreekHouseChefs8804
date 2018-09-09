package com.sixd.greek.house.chefs.Cheff;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.ViewGroup;
import android.view.Window;
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
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemDishAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuPagerAdapterCheff;
import com.sixd.greek.house.chefs.CheffAdapter.MenuWeekIntervalAdapter;
import com.sixd.greek.house.chefs.HeaderActivty;
import com.sixd.greek.house.chefs.HomeActivityStudent;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.MenuPagerAdapter;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuBreakfastFagmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuDinnerFragmentCheff;
import com.sixd.greek.house.chefs.fragmentsCheff.MenuLunchFragmentCheff;
import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.model.MenuModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.List;

import dao_db.AllWeekIntervalList;
import dao_db.CalenderData;
import dao_db.CalenderDataDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.UserLoginInfo;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuActivityCheff extends HeaderActivtyCheff {

    PopupWindow PopUp;

    Context mContext;
    MaterialDialog materialDialog=null;
    TabLayout menu_tabs;
    ViewPager menuPager;
    TextView user_name;
    ImageView profile_icon;
    RelativeLayout submit_rl;
    RelativeLayout header_lay;

    String menu_position="";
    LinearLayout linear;
    String Week_number="",year="";

    String tabposition="",new_pos="";
    int new_pos_new=-1;


    String compare_check_tab="";

    Dialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;


        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        intent = getIntent();
        tabposition = intent.getStringExtra("tabposition");


        setHeaderTitele("Submit Menu");
        initViews();
    }

    public void initViews()
    {

        dialog=new Dialog(MenuActivityCheff.this);

        linear=(LinearLayout)findViewById(R.id.linear);
        header_lay=(RelativeLayout)findViewById(R.id.header_lay);
        submit_rl=(RelativeLayout)findViewById(R.id.submit_rl);
        user_name=(TextView)findViewById(R.id.user_name);
        profile_icon=(ImageView)findViewById(R.id.profile_icon);
        setUserInfo();

        initTabMenu();



        MenuPagerAdapterCheff menuPagerAdapterCheff=new MenuPagerAdapterCheff(getSupportFragmentManager());
        menuPager = (ViewPager) findViewById(R.id.menuPager);
        menuPager.setAdapter(menuPagerAdapterCheff);
        menu_tabs.setupWithViewPager(menuPager);
        menuPager.setCurrentItem(Integer.parseInt(tabposition));


        date_sel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initgetWeekIntervalAPI();
            }
        });



        download_menu_chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Log.i("respo_chef_menudoc_URL=",""+MenuCalendarManager.menu_doc_URL);

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



        submit_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (menu_position.equalsIgnoreCase("Breakfast")) {
                    Toast.makeText(mContext, "Submit " + menu_position, Toast.LENGTH_LONG).show();
                } else if (menu_position.equalsIgnoreCase("Lunch")) {
                    Toast.makeText(mContext, "Submit " + menu_position, Toast.LENGTH_LONG).show();
                } else if (menu_position.equalsIgnoreCase("Dinner")) {
                    Toast.makeText(mContext, "Submit " + menu_position, Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    public void setUserInfo(){

        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        if(userDatas!=null)
        {
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





    public void initTabMenu()
    {
        menu_tabs = (TabLayout)findViewById(R.id.menu_tabs);
        menu_tabs.addTab(menu_tabs.newTab().setText("Breakfast"));
        menu_tabs.addTab(menu_tabs.newTab().setText("Lunch"));
        menu_tabs.addTab(menu_tabs.newTab().setText("Dinner"));

        menu_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                final int position = tab.getPosition();

               /* Toast.makeText(mContext, "Entry Final POS=" + new_pos_new+" position="+position,
                        Toast.LENGTH_LONG).show();*/


                if (position == 0)
                {

                    TabCondationPopUp();

                    if (compare_check_tab.equalsIgnoreCase("0"))
                    {
                        menuPager.setCurrentItem(new_pos_new);
                       // Toast.makeText(mContext, "if=" + new_pos_new, Toast.LENGTH_LONG).show();

                      //  Toast.makeText(mContext, "POPUP 0", Toast.LENGTH_LONG).show();



                        Window window = dialog.getWindow();
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);
                       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.alertdialogpage);
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
                                /*header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                menu_position="Breakfast";
                                new_pos_new = 0;
                                menuPager.setCurrentItem(position);
                                dialog.dismiss();*/

                                {
                                    /*new_pos = menu_position;

                                    new_pos_new=-1;*/
                                  //  Toast.makeText(mContext,"goes="+new_pos_new,Toast.LENGTH_SHORT).show();

                                    DaoSession daoSession = BaseManager.getDBSessoin(MenuActivityCheff.this);
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
                                            Log.i("respo_size_model=", "" + menuList.size());

                                            Constant.ListElementsMenutitle_dinner.add(menuList.get(0).getMenuTitle());
                                            Constant.ListElementsMenutitle2_dinner.add(menuList.get(1).getMenuTitle());
                                            Constant.ListElementsMenutitle3_dinner.add(menuList.get(2).getMenuTitle());
                                            Constant.ListElementsMenutitle4_dinner.add(menuList.get(3).getMenuTitle());
                                            Constant.ListElementsMenutitle5_dinner.add(menuList.get(4).getMenuTitle());
                                            Constant.ListElementsMenutitle6_dinner.add(menuList.get(5).getMenuTitle());
                                            Constant.ListElementsMenutitle7_dinner.add(menuList.get(6).getMenuTitle());
                                        }
                                    }


                                    MenuPagerAdapterCheff menuPagerAdapterCheff = new MenuPagerAdapterCheff(getSupportFragmentManager());
                                    menuPager = (ViewPager) findViewById(R.id.menuPager);
                                    menuPager.setAdapter(menuPagerAdapterCheff);
                                    menu_tabs.setupWithViewPager(menuPager);

                                    /*if (new_pos.equalsIgnoreCase("Breakfast")) {
                                        menuPager.setCurrentItem(0);
                                    } else if (new_pos.equalsIgnoreCase("Lunch")) {
                                        menuPager.setCurrentItem(1);
                                    } else if (new_pos.equalsIgnoreCase("Dinner")) {
                                        menuPager.setCurrentItem(2);
                                    }*/

                                     header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                     menu_position="Breakfast";
                                     new_pos_new = 0;
                                     menuPager.setCurrentItem(position);
                                     dialog.dismiss();


                                    //  Toast.makeText(mContext,"tabposition="+tabposition+"new_pos="+new_pos,Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                /*new_pos_new = 0;*/
                              //  Toast.makeText(mContext, "Cancel from 0=" + new_pos_new, Toast.LENGTH_LONG).show();

                                dialog.dismiss();
                            }
                        });

                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();






                        /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle("Continue?");
                        alertDialog.setMessage("Are you sure you want to continue?");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                menu_position="Breakfast";
                                new_pos_new = 0;
                                menuPager.setCurrentItem(position);
                            }
                        });
                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(mContext, "new_pos_new=" + new_pos_new, Toast.LENGTH_SHORT).show();
                            *//*menuPager.setCurrentItem(0);*//*
                                if (new_pos_new == 0)
                                {
                                    header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                    menu_position="Breakfast";
                                    new_pos_new = 0;
                                    menuPager.setCurrentItem(0);
                                }
                                else if (new_pos_new == 1)
                                {
                                    header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                    menu_position="Lunch";
                                    new_pos_new = 0;
                                    menuPager.setCurrentItem(1);
                                }
                                else if (new_pos_new == 2)
                                {
                                    Toast.makeText(mContext, "new_pos_new_dinner1=" + new_pos_new, Toast.LENGTH_SHORT).show();
                                    header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                    menu_position="Dinner";
                                    new_pos_new = 0;
                                    menuPager.setCurrentItem(2);
                                    Toast.makeText(mContext, "new_pos_new_dinner2=" + new_pos_new, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.show();*/
                    }
                    else
                    {
                       // Toast.makeText(mContext, "else=" + new_pos_new, Toast.LENGTH_LONG).show();

                        new_pos_new=0;
                        header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                        menu_position="Breakfast";
                    }

                }
                else if (position == 1)
                {

                    TabCondationPopUp();

                    if (compare_check_tab.equalsIgnoreCase("0"))
                    {

                      //  Toast.makeText(mContext, "POPUP 1", Toast.LENGTH_LONG).show();


                        menuPager.setCurrentItem(new_pos_new);


                        Window window = dialog.getWindow();
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);
                       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.alertdialogpage);
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
                              /*  header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                menu_position = "Lunch";
                                new_pos_new = 1;
                                menuPager.setCurrentItem(position);
                                dialog.dismiss();*/

                                {
                                   /* new_pos = menu_position;

                                    new_pos_new=-1;*/
                                //    Toast.makeText(mContext,"goes="+new_pos_new,Toast.LENGTH_SHORT).show();

                                    DaoSession daoSession = BaseManager.getDBSessoin(MenuActivityCheff.this);
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
                                            Log.i("respo_size_model=", "" + menuList.size());

                                            Constant.ListElementsMenutitle_dinner.add(menuList.get(0).getMenuTitle());
                                            Constant.ListElementsMenutitle2_dinner.add(menuList.get(1).getMenuTitle());
                                            Constant.ListElementsMenutitle3_dinner.add(menuList.get(2).getMenuTitle());
                                            Constant.ListElementsMenutitle4_dinner.add(menuList.get(3).getMenuTitle());
                                            Constant.ListElementsMenutitle5_dinner.add(menuList.get(4).getMenuTitle());
                                            Constant.ListElementsMenutitle6_dinner.add(menuList.get(5).getMenuTitle());
                                            Constant.ListElementsMenutitle7_dinner.add(menuList.get(6).getMenuTitle());
                                        }
                                    }


                                    MenuPagerAdapterCheff menuPagerAdapterCheff = new MenuPagerAdapterCheff(getSupportFragmentManager());
                                    menuPager = (ViewPager) findViewById(R.id.menuPager);
                                    menuPager.setAdapter(menuPagerAdapterCheff);
                                    menu_tabs.setupWithViewPager(menuPager);

                                    /*if (new_pos.equalsIgnoreCase("Breakfast")) {
                                        menuPager.setCurrentItem(0);
                                    } else if (new_pos.equalsIgnoreCase("Lunch")) {
                                        menuPager.setCurrentItem(1);
                                    } else if (new_pos.equalsIgnoreCase("Dinner")) {
                                        menuPager.setCurrentItem(2);
                                    }*/


                                    header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                    menu_position = "Lunch";
                                    new_pos_new = 1;
                                    menuPager.setCurrentItem(position);
                                    dialog.dismiss();

                                    //  Toast.makeText(mContext,"tabposition="+tabposition+"new_pos="+new_pos,Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0)
                            {
                               /* new_pos_new = 1;*/
                              //  Toast.makeText(mContext, "Cancel from 1=" + new_pos_new, Toast.LENGTH_LONG).show();

                                dialog.dismiss();

                            }
                        });

                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();



                            /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                            alertDialog.setTitle("Continue?");
                            alertDialog.setMessage("Are you sure you want to continue?");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                    menu_position = "Lunch";
                                    new_pos_new = 1;
                                    menuPager.setCurrentItem(position);

                                }
                            });
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(mContext, "Cancel_new_pos_new1=" + new_pos_new, Toast.LENGTH_LONG).show();
                            *//*menuPager.setCurrentItem(0);*//*
                                    if (new_pos_new == 0)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                        menu_position="Breakfast";
                                        new_pos_new = 1;
                                        menuPager.setCurrentItem(0);
                                    }
                                    else if (new_pos_new == 1)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                        menu_position="Lunch";
                                        new_pos_new = 1;
                                        menuPager.setCurrentItem(1);
                                    }
                                    else if (new_pos_new == 2)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                        menu_position="Dinner";
                                        new_pos_new = 1;
                                        Toast.makeText(mContext, "Cancel_new_pos_new2=" + new_pos_new, Toast.LENGTH_LONG).show();
                                        menuPager.setCurrentItem(2);
                                    }
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.show();*/
                    }
                    else
                    {
                        new_pos_new=1;
                        header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                        menu_position="Lunch";
                    }
                }



                else if (position == 2)
                {

                    TabCondationPopUp();

                    if (compare_check_tab.equalsIgnoreCase("0"))
                    {
                        menuPager.setCurrentItem(new_pos_new);

                      //  Toast.makeText(mContext, "POPUP 2", Toast.LENGTH_LONG).show();

                        Window window = dialog.getWindow();
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);
                       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.alertdialogpage);
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
                                /*header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                menu_position="Dinner";
                                new_pos_new=2;
                                menuPager.setCurrentItem(position);
                                dialog.dismiss();*/

                                {
                                    /*new_pos = menu_position;

                                    new_pos_new=-1;*/
                                  //  Toast.makeText(mContext,"goes="+new_pos_new,Toast.LENGTH_SHORT).show();

                                    DaoSession daoSession = BaseManager.getDBSessoin(MenuActivityCheff.this);
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
                                            Log.i("respo_size_model=", "" + menuList.size());

                                            Constant.ListElementsMenutitle_dinner.add(menuList.get(0).getMenuTitle());
                                            Constant.ListElementsMenutitle2_dinner.add(menuList.get(1).getMenuTitle());
                                            Constant.ListElementsMenutitle3_dinner.add(menuList.get(2).getMenuTitle());
                                            Constant.ListElementsMenutitle4_dinner.add(menuList.get(3).getMenuTitle());
                                            Constant.ListElementsMenutitle5_dinner.add(menuList.get(4).getMenuTitle());
                                            Constant.ListElementsMenutitle6_dinner.add(menuList.get(5).getMenuTitle());
                                            Constant.ListElementsMenutitle7_dinner.add(menuList.get(6).getMenuTitle());
                                        }
                                    }


                                    MenuPagerAdapterCheff menuPagerAdapterCheff = new MenuPagerAdapterCheff(getSupportFragmentManager());
                                    menuPager = (ViewPager) findViewById(R.id.menuPager);
                                    menuPager.setAdapter(menuPagerAdapterCheff);
                                    menu_tabs.setupWithViewPager(menuPager);

                                   /* if (new_pos.equalsIgnoreCase("Breakfast")) {
                                        menuPager.setCurrentItem(0);
                                    } else if (new_pos.equalsIgnoreCase("Lunch")) {
                                        menuPager.setCurrentItem(1);
                                    } else if (new_pos.equalsIgnoreCase("Dinner")) {
                                        menuPager.setCurrentItem(2);
                                    }*/


                                    header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                    menu_position="Dinner";
                                    new_pos_new=2;
                                    menuPager.setCurrentItem(position);
                                    dialog.dismiss();
                                    //  Toast.makeText(mContext,"tabposition="+tabposition+"new_pos="+new_pos,Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


                        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                /*new_pos_new = 2;*/

                               // Toast.makeText(mContext, "Cancel from 2=" + new_pos_new, Toast.LENGTH_LONG).show();

                                dialog.dismiss();
                            }
                        });

                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                           /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                            alertDialog.setTitle("Continue?");
                            alertDialog.setMessage("Are you sure you want to continue?");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                    menu_position="Dinner";
                                    new_pos_new=2;
                                    menuPager.setCurrentItem(position);
                                }
                            });
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                    menu_position = "Breakfast";
                                    Toast.makeText(mContext, "new_pos_new=" + new_pos_new, Toast.LENGTH_SHORT).show();
                            *//*menuPager.setCurrentItem(0);*//*

                                    if (new_pos_new == 0)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.breakfast_banner_ic);
                                        menu_position="Breakfast";
                                        new_pos_new = 2;
                                        menuPager.setCurrentItem(0);
                                    }
                                    else if (new_pos_new == 1)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.lunch_banner_ic);
                                        menu_position="Lunch";
                                        new_pos_new = 2;
                                        menuPager.setCurrentItem(1);
                                    }
                                    else if (new_pos_new == 2)
                                    {
                                        header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                                        menu_position="Dinner";
                                        new_pos_new = 2;
                                        menuPager.setCurrentItem(2);
                                    }
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.show();*/
                    }

                    else
                    {
                        header_lay.setBackgroundResource(R.drawable.dinner_banner_ic);
                        menu_position="Dinner";
                        new_pos_new=2;
                    }





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
                    if (responce.equals("100"))
                    {
                        new_pos = menu_position;

                        new_pos_new=-1;

                      //  Toast.makeText(mContext,"goes="+new_pos_new,Toast.LENGTH_SHORT).show();

                        DaoSession daoSession = BaseManager.getDBSessoin(MenuActivityCheff.this);
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
                                Log.i("respo_size_model=", "" + menuList.size());

                                Constant.ListElementsMenutitle_dinner.add(menuList.get(0).getMenuTitle());
                                Constant.ListElementsMenutitle2_dinner.add(menuList.get(1).getMenuTitle());
                                Constant.ListElementsMenutitle3_dinner.add(menuList.get(2).getMenuTitle());
                                Constant.ListElementsMenutitle4_dinner.add(menuList.get(3).getMenuTitle());
                                Constant.ListElementsMenutitle5_dinner.add(menuList.get(4).getMenuTitle());
                                Constant.ListElementsMenutitle6_dinner.add(menuList.get(5).getMenuTitle());
                                Constant.ListElementsMenutitle7_dinner.add(menuList.get(6).getMenuTitle());
                            }
                        }


                        MenuPagerAdapterCheff menuPagerAdapterCheff = new MenuPagerAdapterCheff(getSupportFragmentManager());
                        menuPager = (ViewPager) findViewById(R.id.menuPager);
                        menuPager.setAdapter(menuPagerAdapterCheff);
                        menu_tabs.setupWithViewPager(menuPager);

                        if (new_pos.equalsIgnoreCase("Breakfast")) {
                            menuPager.setCurrentItem(0);
                        } else if (new_pos.equalsIgnoreCase("Lunch")) {
                            menuPager.setCurrentItem(1);
                        } else if (new_pos.equalsIgnoreCase("Dinner")) {
                            menuPager.setCurrentItem(2);
                        }


                        //  Toast.makeText(mContext,"tabposition="+tabposition+"new_pos="+new_pos,Toast.LENGTH_SHORT).show();

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
                    if (responce.equals("100"))
                    {

                        final List<AllWeekIntervalList> allWeekIntervalLists;
                        allWeekIntervalLists = new MenuCalendarManager(MenuActivityCheff.this).getAllWeekIntervalLists();
                        Log.i("respo_weekinterval_size", "" + allWeekIntervalLists.size());
                        if (allWeekIntervalLists.size() > 0)
                        {
                            PopUp = new PopupWindow(mContext);
                            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View layout = layoutInflater.inflate(R.layout.dialog_menu_date_cheff, null);
                            TextView cancel_check = (TextView) layout.findViewById(R.id.cancel_check);
                            ListView week_interval_list = (ListView) layout.findViewById(R.id.week_interval_list);

                            if (android.os.Build.VERSION.SDK_INT >= 24)
                            {
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
                                int newheight=screenHeight/2;
                                PopUp.setHeight(newheight - OFFSET_Y);
                                PopUp.showAtLocation(layout, Gravity.BOTTOM, 0, OFFSET_Y);

                              /*  int OFFSET_X = 0;
                                int OFFSET_Y = 0;
                                PopUp.setBackgroundDrawable(new BitmapDrawable());
                                // Displaying the popup at the specified location, + offsets.

                                // PopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

                                PopUp.showAtLocation((MenuActivityCheff.this).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 840);

                                //TextView done_check=(TextView)layout.findViewById(R.id.done_check);*/

                            }
                            else {

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

                            week_interval_list.setAdapter(new MenuWeekIntervalAdapter(MenuActivityCheff.this, allWeekIntervalLists));

                            week_interval_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Week_number = allWeekIntervalLists.get(position).getWeek().toString().trim();
                                    year = allWeekIntervalLists.get(position).getYear().toString().trim();

                                    {
                                        /* =================================Breakfast Start==============*/
                                        if (menu_position.equalsIgnoreCase("Breakfast"))
                                        {

                                          //  Toast.makeText(mContext, "back_menu_final=" + menu_position, Toast.LENGTH_SHORT).show();

                                            MenuBreakfastFagmentCheff menuBreakfastFagmentCheff = new MenuBreakfastFagmentCheff();
                                            String mon_cs_current = menuBreakfastFagmentCheff.mon_cs;
                                            String tue_cs_current = menuBreakfastFagmentCheff.tue_cs;
                                            String wed_cs_current = menuBreakfastFagmentCheff.wed_cs;
                                            String thu_cs_current = menuBreakfastFagmentCheff.thu_cs;
                                            String fri_cs_current = menuBreakfastFagmentCheff.fri_cs;
                                            String sat_cs_current = menuBreakfastFagmentCheff.sat_cs;
                                            String sun_cs_current = menuBreakfastFagmentCheff.sun_cs;


                                            List<MenuModel> menuList = null;
                                            menuList = MenuCalendarManager.breaffastList;
                                            Log.i("respo_size_model=", "" + menuList.size());


                                            StringBuilder first_MenuTitle = new StringBuilder();
                                            StringBuilder first_MenuTitle2 = new StringBuilder();
                                            StringBuilder first_MenuTitle3 = new StringBuilder();
                                            StringBuilder first_MenuTitle4 = new StringBuilder();
                                            StringBuilder first_MenuTitle5 = new StringBuilder();
                                            StringBuilder first_MenuTitle6 = new StringBuilder();
                                            StringBuilder first_MenuTitle7 = new StringBuilder();

                                            try {
                                                first_MenuTitle.setLength(0);
                                                first_MenuTitle2.setLength(0);
                                                first_MenuTitle3.setLength(0);
                                                first_MenuTitle4.setLength(0);
                                                first_MenuTitle5.setLength(0);
                                                first_MenuTitle6.setLength(0);
                                                first_MenuTitle7.setLength(0);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle.size(); i++) {
                                                    if (Constant.ListElementsMenutitle.size() == 1) {
                                                        first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                                                    } else {
                                                        if (i == (Constant.ListElementsMenutitle.size() - 1)) {
                                                            first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                                                        } else {
                                                            first_MenuTitle.append(Constant.ListElementsMenutitle.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            try {

                                                for (int i = 0; i < Constant.ListElementsMenutitle2.size(); i++) {
                                                    if (Constant.ListElementsMenutitle2.size() == 1) {
                                                        first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                                                    } else {
                                                        // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                                                        if (i == (Constant.ListElementsMenutitle2.size() - 1)) {
                                                            first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                                                        } else {
                                                            first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle3.size(); i++) {
                                                    if (Constant.ListElementsMenutitle3.size() == 1) {
                                                        first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                                                    } else {
                                                        //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                                                        if (i == (Constant.ListElementsMenutitle3.size() - 1)) {
                                                            first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                                                        } else {
                                                            first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle4.size(); i++) {
                                                    if (Constant.ListElementsMenutitle4.size() == 1) {
                                                        first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                                                    } else {
                                                        //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                                                        if (i == (Constant.ListElementsMenutitle4.size() - 1)) {
                                                            first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                                                        } else {
                                                            first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle5.size(); i++) {
                                                    if (Constant.ListElementsMenutitle5.size() == 1) {
                                                        first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                                                    } else {
                                                        //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                                                        if (i == (Constant.ListElementsMenutitle5.size() - 1)) {
                                                            first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                                                        } else {
                                                            first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle6.size(); i++) {
                                                    if (Constant.ListElementsMenutitle6.size() == 1) {
                                                        first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                                                    } else {
                                                        //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                                                        if (i == (Constant.ListElementsMenutitle6.size() - 1)) {
                                                            first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                                                        } else {
                                                            first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                for (int i = 0; i < Constant.ListElementsMenutitle7.size(); i++) {
                                                    if (Constant.ListElementsMenutitle7.size() == 1) {
                                                        first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                                                    } else {
                                                        // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                                                        if (i == (Constant.ListElementsMenutitle7.size() - 1)) {
                                                            first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                                                        } else {
                                                            first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }




           /* Toast.makeText(mContext, "new first_MenuTitle="+first_MenuTitle.toString().trim()+"\n"
                    +" old ="+menuList.get(0).getMenuTitle().toString().trim(), Toast.LENGTH_LONG).show();  //hi ""*/

                                            /*Toast.makeText(mContext, "mon_cs1=" + mon_cs_current + " tue_cs1=" + tue_cs_current +
                                                            " wed_cs1=" + wed_cs_current + " thu_cs1=" + thu_cs_current + " fri_cs1=" + fri_cs_current
                                                            + " sat_cs1=" + sat_cs_current + " sun_cs1=" + sun_cs_current,
                                                    Toast.LENGTH_LONG).show();

                                            Toast.makeText(mContext, "first_MenuTitle=" + first_MenuTitle
                                                            + " first_MenuTitle2=" + first_MenuTitle2 +
                                                            " first_MenuTitle3=" + first_MenuTitle3
                                                            + " first_MenuTitle4=" + first_MenuTitle4 + " first_MenuTitle5=" + first_MenuTitle5
                                                            + " first_MenuTitle6=" + first_MenuTitle6 + " first_MenuTitle7=" + first_MenuTitle7,
                                                    Toast.LENGTH_LONG).show();

                                            Toast.makeText(mContext, "first_MenuTitlelast=" + menuList.get(0).getMenuTitle()
                                                            + " first_MenuTitle2last=" + menuList.get(1).getMenuTitle() +
                                                            " first_MenuTitle3last=" + menuList.get(2).getMenuTitle()
                                                            + " first_MenuTitle4last=" + menuList.get(3).getMenuTitle()
                                                            + " first_MenuTitle5last=" + menuList.get(4).getMenuTitle()
                                                            + " first_MenuTitle6last=" + menuList.get(5).getMenuTitle()
                                                            + " first_MenuTitle7last=" + menuList.get(6).getMenuTitle(),
                                                    Toast.LENGTH_LONG).show();*/

                                            String compare_check = "0";

                                            if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                                                    && (first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                                                    && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                                                    && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                                                    && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                                                    && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                                                    && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                                                    && mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                                                    && (tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                                                    && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                                                    && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                                                    && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                                                    && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                                                    && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                                                    )
                                            {
                                                compare_check = "1";
                                            }
                                            else
                                            {
                                                compare_check = "0";
                                            }


                                           // Toast.makeText(mContext, "compare_check=" + compare_check, Toast.LENGTH_SHORT).show();


                                            if (compare_check.equalsIgnoreCase("0")) {
                                                ShowDialogDate();
                                            }
                                            else
                                            {
                                                PopUp.dismiss();
                                                initMenuCalenderAPI();
                                            }


                                        }

       /* =================================Breakfast end==============*/

          /* =================================Lunch Start==============*/

                                        else if (menu_position.equalsIgnoreCase("Lunch")) {
                                            {
                                             //   Toast.makeText(mContext, "back_menu_final=" + menu_position, Toast.LENGTH_SHORT).show();


                                                MenuLunchFragmentCheff menuLunchFragmentCheff = new MenuLunchFragmentCheff();
                                                String mon_cs_current = menuLunchFragmentCheff.mon_cs;
                                                String tue_cs_current = menuLunchFragmentCheff.tue_cs;
                                                String wed_cs_current = menuLunchFragmentCheff.wed_cs;
                                                String thu_cs_current = menuLunchFragmentCheff.thu_cs;
                                                String fri_cs_current = menuLunchFragmentCheff.fri_cs;
                                                String sat_cs_current = menuLunchFragmentCheff.sat_cs;
                                                String sun_cs_current = menuLunchFragmentCheff.sun_cs;



                                                List<MenuModel> menuList = null;
                                                menuList = MenuCalendarManager.lunchList;
                                                Log.i("respo_size_model=", "" + menuList.size());


                                                StringBuilder first_MenuTitle = new StringBuilder();
                                                StringBuilder first_MenuTitle2 = new StringBuilder();
                                                StringBuilder first_MenuTitle3 = new StringBuilder();
                                                StringBuilder first_MenuTitle4 = new StringBuilder();
                                                StringBuilder first_MenuTitle5 = new StringBuilder();
                                                StringBuilder first_MenuTitle6 = new StringBuilder();
                                                StringBuilder first_MenuTitle7 = new StringBuilder();

                                                try {
                                                    first_MenuTitle.setLength(0);
                                                    first_MenuTitle2.setLength(0);
                                                    first_MenuTitle3.setLength(0);
                                                    first_MenuTitle4.setLength(0);
                                                    first_MenuTitle5.setLength(0);
                                                    first_MenuTitle6.setLength(0);
                                                    first_MenuTitle7.setLength(0);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle_lunch.size() == 1) {
                                                            first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                                                        } else {
                                                            if (i == (Constant.ListElementsMenutitle_lunch.size() - 1)) {
                                                                first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                try {

                                                    for (int i = 0; i < Constant.ListElementsMenutitle2_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle2_lunch.size() == 1) {
                                                            first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                                                        } else {
                                                            // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                                                            if (i == (Constant.ListElementsMenutitle2_lunch.size() - 1)) {
                                                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle3_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle3_lunch.size() == 1) {
                                                            first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                                                        } else {
                                                            //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle3_lunch.size() - 1)) {
                                                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle4_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle4_lunch.size() == 1) {
                                                            first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                                                        } else {
                                                            //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                                                            if (i == (Constant.ListElementsMenutitle4_lunch.size() - 1)) {
                                                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle5_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle5_lunch.size() == 1) {
                                                            first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                                                        } else {
                                                            //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle5_lunch.size() - 1)) {
                                                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle6_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle6_lunch.size() == 1) {
                                                            first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                                                        } else {
                                                            //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle6_lunch.size() - 1)) {
                                                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle7_lunch.size(); i++) {
                                                        if (Constant.ListElementsMenutitle7_lunch.size() == 1) {
                                                            first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                                                        } else {
                                                            // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                                                            if (i == (Constant.ListElementsMenutitle7_lunch.size() - 1)) {
                                                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                                                            } else {
                                                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                String compare_check = "0";

                                                if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                                                        && (first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                                                        && mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                                                        && (tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                                                        && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                                                        && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                                                        && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                                                        && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                                                        && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                                                        ) {
                                                    compare_check = "1";
                                                } else {
                                                    compare_check = "0";
                                                }


                                             //   Toast.makeText(mContext, "compare_check=" + compare_check, Toast.LENGTH_SHORT).show();


                                                if (compare_check.equalsIgnoreCase("0")) {
                                                    ShowDialogDate();
                                                }
                                                else

                                                {
                                                    PopUp.dismiss();
                                                    initMenuCalenderAPI();
                                                }


                                            }
                                        }

           /* =================================Lunch end==============*/

          /* =================================Dinner Start==============*/

                                        else if (menu_position.equalsIgnoreCase("Dinner")) {
                                            {
                                               // Toast.makeText(mContext, "back_menu_final=" + menu_position, Toast.LENGTH_SHORT).show();


                                                MenuDinnerFragmentCheff menuDinnerFragmentCheff = new MenuDinnerFragmentCheff();
                                                String mon_cs_current = menuDinnerFragmentCheff.mon_cs;
                                                String tue_cs_current = menuDinnerFragmentCheff.tue_cs;
                                                String wed_cs_current = menuDinnerFragmentCheff.wed_cs;
                                                String thu_cs_current = menuDinnerFragmentCheff.thu_cs;
                                                String fri_cs_current = menuDinnerFragmentCheff.fri_cs;
                                                String sat_cs_current = menuDinnerFragmentCheff.sat_cs;
                                                String sun_cs_current = menuDinnerFragmentCheff.sun_cs;


                                                List<MenuModel> menuList = null;
                                                menuList = MenuCalendarManager.dinnerList;
                                                Log.i("respo_size_model=", "" + menuList.size());


                                                StringBuilder first_MenuTitle = new StringBuilder();
                                                StringBuilder first_MenuTitle2 = new StringBuilder();
                                                StringBuilder first_MenuTitle3 = new StringBuilder();
                                                StringBuilder first_MenuTitle4 = new StringBuilder();
                                                StringBuilder first_MenuTitle5 = new StringBuilder();
                                                StringBuilder first_MenuTitle6 = new StringBuilder();
                                                StringBuilder first_MenuTitle7 = new StringBuilder();

                                                try {
                                                    first_MenuTitle.setLength(0);
                                                    first_MenuTitle2.setLength(0);
                                                    first_MenuTitle3.setLength(0);
                                                    first_MenuTitle4.setLength(0);
                                                    first_MenuTitle5.setLength(0);
                                                    first_MenuTitle6.setLength(0);
                                                    first_MenuTitle7.setLength(0);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle_dinner.size() == 1) {
                                                            first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                                                        } else {
                                                            if (i == (Constant.ListElementsMenutitle_dinner.size() - 1)) {
                                                                first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                try {

                                                    for (int i = 0; i < Constant.ListElementsMenutitle2_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle2_dinner.size() == 1) {
                                                            first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                                                        } else {
                                                            // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                                                            if (i == (Constant.ListElementsMenutitle2_dinner.size() - 1)) {
                                                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle3_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle3_dinner.size() == 1) {
                                                            first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                                                        } else {
                                                            //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle3_dinner.size() - 1)) {
                                                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle4_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle4_dinner.size() == 1) {
                                                            first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                                                        } else {
                                                            //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                                                            if (i == (Constant.ListElementsMenutitle4_dinner.size() - 1)) {
                                                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle5_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle5_dinner.size() == 1) {
                                                            first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                                                        } else {
                                                            //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle5_dinner.size() - 1)) {
                                                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle6_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle6_dinner.size() == 1) {
                                                            first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                                                        } else {
                                                            //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                                                            if (i == (Constant.ListElementsMenutitle6_dinner.size() - 1)) {
                                                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                try {
                                                    for (int i = 0; i < Constant.ListElementsMenutitle7_dinner.size(); i++) {
                                                        if (Constant.ListElementsMenutitle7_dinner.size() == 1) {
                                                            first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                                                        } else {
                                                            // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                                                            if (i == (Constant.ListElementsMenutitle7_dinner.size() - 1)) {
                                                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                                                            } else {
                                                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i) + " | ");
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                String compare_check = "0";


                                                if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                                                        && (first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                                                        && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                                                        && mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                                                        && (tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                                                        && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                                                        && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                                                        && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                                                        && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                                                        && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                                                        ) {
                                                    compare_check = "1";
                                                } else {
                                                    compare_check = "0";
                                                }

                                             //   Toast.makeText(mContext, "compare_check=" + compare_check, Toast.LENGTH_SHORT).show();


                                                if (compare_check.equalsIgnoreCase("0")) {
                                                    ShowDialogDate();
                                                }
                                                else

                                                {
                                                    PopUp.dismiss();
                                                    initMenuCalenderAPI();
                                                }


                                            }
                                        }

          /* =================================Dinner end==============*/
                                    }


                                      /*  PopUp.dismiss();
                                        Week_number = allWeekIntervalLists.get(position).getWeek().toString().trim();
                                        year=allWeekIntervalLists.get(position).getYear().toString().trim();
                                        initMenuCalenderAPI();*/

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


                }

                else

                {

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
                    public void onFailure (String responce){
                        materialDialog.dismiss();
                    }
                }

                );
            }else {
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
    public void onBackPressed()
    {
        /* =================================Breakfast Start==============*/
        if (menu_position.equalsIgnoreCase("Breakfast"))
        {

          //  Toast.makeText(mContext, "back_menu_final="+menu_position, Toast.LENGTH_SHORT).show();

            MenuBreakfastFagmentCheff menuBreakfastFagmentCheff=new MenuBreakfastFagmentCheff();
            String mon_cs_current= menuBreakfastFagmentCheff.mon_cs;
            String tue_cs_current= menuBreakfastFagmentCheff.tue_cs;
            String wed_cs_current= menuBreakfastFagmentCheff.wed_cs;
            String thu_cs_current= menuBreakfastFagmentCheff.thu_cs;
            String fri_cs_current= menuBreakfastFagmentCheff.fri_cs;
            String sat_cs_current= menuBreakfastFagmentCheff.sat_cs;
            String sun_cs_current= menuBreakfastFagmentCheff.sun_cs;


            List<MenuModel> menuList = null;
            menuList = MenuCalendarManager.breaffastList;
            Log.i("respo_size_model=", "" + menuList.size());


            StringBuilder first_MenuTitle = new StringBuilder();
            StringBuilder first_MenuTitle2 = new StringBuilder();
            StringBuilder first_MenuTitle3 = new StringBuilder();
            StringBuilder first_MenuTitle4 = new StringBuilder();
            StringBuilder first_MenuTitle5 = new StringBuilder();
            StringBuilder first_MenuTitle6 = new StringBuilder();
            StringBuilder first_MenuTitle7 = new StringBuilder();

            try {
                first_MenuTitle.setLength(0);
                first_MenuTitle2.setLength(0);
                first_MenuTitle3.setLength(0);
                first_MenuTitle4.setLength(0);
                first_MenuTitle5.setLength(0);
                first_MenuTitle6.setLength(0);
                first_MenuTitle7.setLength(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                for (int i=0;i<Constant.ListElementsMenutitle.size();i++)
                {
                    if (Constant.ListElementsMenutitle.size()==1)
                    {
                        first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                    }
                    else
                    {
                        if (i==(Constant.ListElementsMenutitle.size()-1))
                        {
                            first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                        }
                        else
                        {
                            first_MenuTitle.append(Constant.ListElementsMenutitle.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                for (int i=0;i<Constant.ListElementsMenutitle2.size();i++)
                {
                    if (Constant.ListElementsMenutitle2.size()==1)
                    {
                        first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                    }
                    else
                    {
                        // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                        if (i==(Constant.ListElementsMenutitle2.size()-1))
                        {
                            first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                        }
                        else
                        {
                            first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                for (int i=0;i<Constant.ListElementsMenutitle3.size();i++)
                {
                    if (Constant.ListElementsMenutitle3.size()==1)
                    {
                        first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                    }
                    else
                    {
                        //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                        if (i==(Constant.ListElementsMenutitle3.size()-1))
                        {
                            first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                        }
                        else
                        {
                            first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                for (int i=0;i<Constant.ListElementsMenutitle4.size();i++)
                {
                    if (Constant.ListElementsMenutitle4.size()==1)
                    {
                        first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                    }
                    else
                    {
                        //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                        if (i==(Constant.ListElementsMenutitle4.size()-1))
                        {
                            first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                        }
                        else
                        {
                            first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                for (int i=0;i<Constant.ListElementsMenutitle5.size();i++)
                {
                    if (Constant.ListElementsMenutitle5.size()==1)
                    {
                        first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                    }
                    else
                    {
                        //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                        if (i==(Constant.ListElementsMenutitle5.size()-1))
                        {
                            first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                        }
                        else
                        {
                            first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                for (int i=0;i<Constant.ListElementsMenutitle6.size();i++)
                {
                    if (Constant.ListElementsMenutitle6.size()==1)
                    {
                        first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                    }
                    else
                    {
                        //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                        if (i==(Constant.ListElementsMenutitle6.size()-1))
                        {
                            first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                        }
                        else
                        {
                            first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                for (int i=0;i<Constant.ListElementsMenutitle7.size();i++)
                {
                    if (Constant.ListElementsMenutitle7.size()==1)
                    {
                        first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                    }
                    else
                    {
                        // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                        if (i==(Constant.ListElementsMenutitle7.size()-1))
                        {
                            first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                        }
                        else
                        {
                            first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i)+" | ");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            String compare_check="0";


            if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                  &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                   && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                    && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                     && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                    && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                     && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                   &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                    &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                    && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                    && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                    && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                    && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                    && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                     )
             {
                 compare_check="1";
             }
             else
             {
                 compare_check="0";
             }

            /*if (mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                    &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                    && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                    && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                    && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                    && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                    && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))
                    )
            {
                compare_check_cs="1";
            }
            else
            {
                compare_check_cs="0";
            }*/


         //   Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


            if (compare_check.equalsIgnoreCase("0"))
            {
                ShowDialogBackNew();
            }
            else
            {
                {
                    {
                /*===========breakfast===========*/

                        Constant.ListElementsMenutitle.clear();
                        Constant.ListElementsMenutitle2.clear();
                        Constant.ListElementsMenutitle3.clear();
                        Constant.ListElementsMenutitle4.clear();
                        Constant.ListElementsMenutitle5.clear();
                        Constant.ListElementsMenutitle6.clear();
                        Constant.ListElementsMenutitle7.clear();

                        Constant.ListElementsFinal_temp.clear();
                    }

                    {
                 /*===========lunch===========*/

                        Constant.ListElementsMenutitle_lunch.clear();
                        Constant.ListElementsMenutitle2_lunch.clear();
                        Constant.ListElementsMenutitle3_lunch.clear();
                        Constant.ListElementsMenutitle4_lunch.clear();
                        Constant.ListElementsMenutitle5_lunch.clear();
                        Constant.ListElementsMenutitle6_lunch.clear();
                        Constant.ListElementsMenutitle7_lunch.clear();

                        Constant.ListElementsFinal_temp_lunch.clear();
                    }

                    {
                 /*===========dinner===========*/

                        Constant.ListElementsMenutitle_dinner.clear();
                        Constant.ListElementsMenutitle2_dinner.clear();
                        Constant.ListElementsMenutitle3_dinner.clear();
                        Constant.ListElementsMenutitle4_dinner.clear();
                        Constant.ListElementsMenutitle5_dinner.clear();
                        Constant.ListElementsMenutitle6_dinner.clear();
                        Constant.ListElementsMenutitle7_dinner.clear();

                        Constant.ListElementsFinal_temp_dinner.clear();
                    }


                    Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                    startActivity(intent);
                    finish();
                }
            }


        }

       /* =================================Breakfast end==============*/

          /* =================================Lunch Start==============*/

        else if (menu_position.equalsIgnoreCase("Lunch"))
        {
            {
              //  Toast.makeText(mContext, "back_menu_final="+menu_position, Toast.LENGTH_SHORT).show();


                MenuLunchFragmentCheff menuLunchFragmentCheff=new MenuLunchFragmentCheff();
                String mon_cs_current= menuLunchFragmentCheff.mon_cs;
                String tue_cs_current= menuLunchFragmentCheff.tue_cs;
                String wed_cs_current= menuLunchFragmentCheff.wed_cs;
                String thu_cs_current= menuLunchFragmentCheff.thu_cs;
                String fri_cs_current= menuLunchFragmentCheff.fri_cs;
                String sat_cs_current= menuLunchFragmentCheff.sat_cs;
                String sun_cs_current= menuLunchFragmentCheff.sun_cs;



                List<MenuModel> menuList = null;
                menuList = MenuCalendarManager.lunchList;
                Log.i("respo_size_model=", "" + menuList.size());


                StringBuilder first_MenuTitle = new StringBuilder();
                StringBuilder first_MenuTitle2 = new StringBuilder();
                StringBuilder first_MenuTitle3 = new StringBuilder();
                StringBuilder first_MenuTitle4 = new StringBuilder();
                StringBuilder first_MenuTitle5 = new StringBuilder();
                StringBuilder first_MenuTitle6 = new StringBuilder();
                StringBuilder first_MenuTitle7 = new StringBuilder();

                try {
                    first_MenuTitle.setLength(0);
                    first_MenuTitle2.setLength(0);
                    first_MenuTitle3.setLength(0);
                    first_MenuTitle4.setLength(0);
                    first_MenuTitle5.setLength(0);
                    first_MenuTitle6.setLength(0);
                    first_MenuTitle7.setLength(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try
                {
                    for (int i=0;i<Constant.ListElementsMenutitle_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle_lunch.size()==1)
                        {
                            first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                        }
                        else
                        {
                            if (i==(Constant.ListElementsMenutitle_lunch.size()-1))
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                try {

                    for (int i=0;i<Constant.ListElementsMenutitle2_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle2_lunch.size()==1)
                        {
                            first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                        }
                        else
                        {
                            // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle2_lunch.size()-1))
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle3_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle3_lunch.size()==1)
                        {
                            first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                        }
                        else
                        {
                            //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle3_lunch.size()-1))
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle4_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle4_lunch.size()==1)
                        {
                            first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                        }
                        else
                        {
                            //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle4_lunch.size()-1))
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle5_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle5_lunch.size()==1)
                        {
                            first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                        }
                        else
                        {
                            //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle5_lunch.size()-1))
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle6_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle6_lunch.size()==1)
                        {
                            first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                        }
                        else
                        {
                            //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle6_lunch.size()-1))
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle7_lunch.size();i++)
                    {
                        if (Constant.ListElementsMenutitle7_lunch.size()==1)
                        {
                            first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                        }
                        else
                        {
                            // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                            if (i==(Constant.ListElementsMenutitle7_lunch.size()-1))
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                            }
                            else
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                String compare_check="0";


                if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                        &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                        && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                        && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                        && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                        && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                        && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                        &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                        &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                        && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                        && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                        && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                        && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                        && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                        )
                {
                    compare_check="1";
                }
                else
                {
                    compare_check="0";
                }



             //   Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


                if (compare_check.equalsIgnoreCase("0"))
                {
                    ShowDialogBackNew();
                }
                else
                {
                    {
                        {
                /*===========breakfast===========*/

                            Constant.ListElementsMenutitle.clear();
                            Constant.ListElementsMenutitle2.clear();
                            Constant.ListElementsMenutitle3.clear();
                            Constant.ListElementsMenutitle4.clear();
                            Constant.ListElementsMenutitle5.clear();
                            Constant.ListElementsMenutitle6.clear();
                            Constant.ListElementsMenutitle7.clear();

                            Constant.ListElementsFinal_temp.clear();
                        }

                        {
                 /*===========lunch===========*/

                            Constant.ListElementsMenutitle_lunch.clear();
                            Constant.ListElementsMenutitle2_lunch.clear();
                            Constant.ListElementsMenutitle3_lunch.clear();
                            Constant.ListElementsMenutitle4_lunch.clear();
                            Constant.ListElementsMenutitle5_lunch.clear();
                            Constant.ListElementsMenutitle6_lunch.clear();
                            Constant.ListElementsMenutitle7_lunch.clear();

                            Constant.ListElementsFinal_temp_lunch.clear();
                        }

                        {
                 /*===========dinner===========*/

                            Constant.ListElementsMenutitle_dinner.clear();
                            Constant.ListElementsMenutitle2_dinner.clear();
                            Constant.ListElementsMenutitle3_dinner.clear();
                            Constant.ListElementsMenutitle4_dinner.clear();
                            Constant.ListElementsMenutitle5_dinner.clear();
                            Constant.ListElementsMenutitle6_dinner.clear();
                            Constant.ListElementsMenutitle7_dinner.clear();

                            Constant.ListElementsFinal_temp_dinner.clear();
                        }


                        Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        }

           /* =================================Lunch end==============*/

          /* =================================Dinner Start==============*/

        else if (menu_position.equalsIgnoreCase("Dinner"))
        {
            {
              //  Toast.makeText(mContext, "back_menu_final="+menu_position, Toast.LENGTH_SHORT).show();


                MenuDinnerFragmentCheff menuDinnerFragmentCheff=new MenuDinnerFragmentCheff();
                String mon_cs_current= menuDinnerFragmentCheff.mon_cs;
                String tue_cs_current= menuDinnerFragmentCheff.tue_cs;
                String wed_cs_current= menuDinnerFragmentCheff.wed_cs;
                String thu_cs_current= menuDinnerFragmentCheff.thu_cs;
                String fri_cs_current= menuDinnerFragmentCheff.fri_cs;
                String sat_cs_current= menuDinnerFragmentCheff.sat_cs;
                String sun_cs_current= menuDinnerFragmentCheff.sun_cs;


                List<MenuModel> menuList = null;
                menuList = MenuCalendarManager.dinnerList;
                Log.i("respo_size_model=", "" + menuList.size());


                StringBuilder first_MenuTitle = new StringBuilder();
                StringBuilder first_MenuTitle2 = new StringBuilder();
                StringBuilder first_MenuTitle3 = new StringBuilder();
                StringBuilder first_MenuTitle4 = new StringBuilder();
                StringBuilder first_MenuTitle5 = new StringBuilder();
                StringBuilder first_MenuTitle6 = new StringBuilder();
                StringBuilder first_MenuTitle7 = new StringBuilder();

                try {
                    first_MenuTitle.setLength(0);
                    first_MenuTitle2.setLength(0);
                    first_MenuTitle3.setLength(0);
                    first_MenuTitle4.setLength(0);
                    first_MenuTitle5.setLength(0);
                    first_MenuTitle6.setLength(0);
                    first_MenuTitle7.setLength(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try
                {
                    for (int i=0;i<Constant.ListElementsMenutitle_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle_dinner.size()==1)
                        {
                            first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                        }
                        else
                        {
                            if (i==(Constant.ListElementsMenutitle_dinner.size()-1))
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                try {

                    for (int i=0;i<Constant.ListElementsMenutitle2_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle2_dinner.size()==1)
                        {
                            first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                        }
                        else
                        {
                            // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle2_dinner.size()-1))
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle3_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle3_dinner.size()==1)
                        {
                            first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                        }
                        else
                        {
                            //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle3_dinner.size()-1))
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle4_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle4_dinner.size()==1)
                        {
                            first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                        }
                        else
                        {
                            //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle4_dinner.size()-1))
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle5_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle5_dinner.size()==1)
                        {
                            first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                        }
                        else
                        {
                            //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle5_dinner.size()-1))
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle6_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle6_dinner.size()==1)
                        {
                            first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                        }
                        else
                        {
                            //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle6_dinner.size()-1))
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle7_dinner.size();i++)
                    {
                        if (Constant.ListElementsMenutitle7_dinner.size()==1)
                        {
                            first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                        }
                        else
                        {
                            // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                            if (i==(Constant.ListElementsMenutitle7_dinner.size()-1))
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                            }
                            else
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                String compare_check="0";


                if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                        &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                        && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                        && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                        && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                        && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                        && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                        &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                        &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                        && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                        && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                        && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                        && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                        && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                        )
                {
                    compare_check="1";
                }
                else
                {
                    compare_check="0";
                }

              //  Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


                if (compare_check.equalsIgnoreCase("0"))
                {
                    ShowDialogBackNew();
                }
                else
                {
                    {
                        {
                /*===========breakfast===========*/

                            Constant.ListElementsMenutitle.clear();
                            Constant.ListElementsMenutitle2.clear();
                            Constant.ListElementsMenutitle3.clear();
                            Constant.ListElementsMenutitle4.clear();
                            Constant.ListElementsMenutitle5.clear();
                            Constant.ListElementsMenutitle6.clear();
                            Constant.ListElementsMenutitle7.clear();

                            Constant.ListElementsFinal_temp.clear();
                        }

                        {
                 /*===========lunch===========*/

                            Constant.ListElementsMenutitle_lunch.clear();
                            Constant.ListElementsMenutitle2_lunch.clear();
                            Constant.ListElementsMenutitle3_lunch.clear();
                            Constant.ListElementsMenutitle4_lunch.clear();
                            Constant.ListElementsMenutitle5_lunch.clear();
                            Constant.ListElementsMenutitle6_lunch.clear();
                            Constant.ListElementsMenutitle7_lunch.clear();

                            Constant.ListElementsFinal_temp_lunch.clear();
                        }

                        {
                 /*===========dinner===========*/

                            Constant.ListElementsMenutitle_dinner.clear();
                            Constant.ListElementsMenutitle2_dinner.clear();
                            Constant.ListElementsMenutitle3_dinner.clear();
                            Constant.ListElementsMenutitle4_dinner.clear();
                            Constant.ListElementsMenutitle5_dinner.clear();
                            Constant.ListElementsMenutitle6_dinner.clear();
                            Constant.ListElementsMenutitle7_dinner.clear();

                            Constant.ListElementsFinal_temp_dinner.clear();
                        }


                        Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        }

          /* =================================Dinner end==============*/
    }


    public void ShowDialogBackNew()
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogpage);
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
                {
              /*  ===========breakfast===========*/

                    Constant.ListElementsMenutitle.clear();
                    Constant.ListElementsMenutitle2.clear();
                    Constant.ListElementsMenutitle3.clear();
                    Constant.ListElementsMenutitle4.clear();
                    Constant.ListElementsMenutitle5.clear();
                    Constant.ListElementsMenutitle6.clear();
                    Constant.ListElementsMenutitle7.clear();

                    Constant.ListElementsFinal_temp.clear();
                }

                {
                 /*===========lunch===========*/

                    Constant.ListElementsMenutitle_lunch.clear();
                    Constant.ListElementsMenutitle2_lunch.clear();
                    Constant.ListElementsMenutitle3_lunch.clear();
                    Constant.ListElementsMenutitle4_lunch.clear();
                    Constant.ListElementsMenutitle5_lunch.clear();
                    Constant.ListElementsMenutitle6_lunch.clear();
                    Constant.ListElementsMenutitle7_lunch.clear();

                    Constant.ListElementsFinal_temp_lunch.clear();
                }

                {
                /* ===========dinner===========*/

                    Constant.ListElementsMenutitle_dinner.clear();
                    Constant.ListElementsMenutitle2_dinner.clear();
                    Constant.ListElementsMenutitle3_dinner.clear();
                    Constant.ListElementsMenutitle4_dinner.clear();
                    Constant.ListElementsMenutitle5_dinner.clear();
                    Constant.ListElementsMenutitle6_dinner.clear();
                    Constant.ListElementsMenutitle7_dinner.clear();

                    Constant.ListElementsFinal_temp_dinner.clear();
                }


                Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                startActivity(intent);
                finish();
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


    public void ShowDialogDate()
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogpage);
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
                dialog.dismiss();
                PopUp.dismiss();
                initMenuCalenderAPI();
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



    public void TabCondationPopUp()
    {
        {
        /* =================================Breakfast Start==============*/
            if (new_pos_new == 0)
            {

              //  Toast.makeText(mContext, "old_new_pos_new="+new_pos_new, Toast.LENGTH_SHORT).show();

                MenuBreakfastFagmentCheff menuBreakfastFagmentCheff=new MenuBreakfastFagmentCheff();
                String mon_cs_current= menuBreakfastFagmentCheff.mon_cs;
                String tue_cs_current= menuBreakfastFagmentCheff.tue_cs;
                String wed_cs_current= menuBreakfastFagmentCheff.wed_cs;
                String thu_cs_current= menuBreakfastFagmentCheff.thu_cs;
                String fri_cs_current= menuBreakfastFagmentCheff.fri_cs;
                String sat_cs_current= menuBreakfastFagmentCheff.sat_cs;
                String sun_cs_current= menuBreakfastFagmentCheff.sun_cs;


                List<MenuModel> menuList = null;
                menuList = MenuCalendarManager.breaffastList;
                Log.i("respo_size_model=", "" + menuList.size());


                StringBuilder first_MenuTitle = new StringBuilder();
                StringBuilder first_MenuTitle2 = new StringBuilder();
                StringBuilder first_MenuTitle3 = new StringBuilder();
                StringBuilder first_MenuTitle4 = new StringBuilder();
                StringBuilder first_MenuTitle5 = new StringBuilder();
                StringBuilder first_MenuTitle6 = new StringBuilder();
                StringBuilder first_MenuTitle7 = new StringBuilder();

                try {
                    first_MenuTitle.setLength(0);
                    first_MenuTitle2.setLength(0);
                    first_MenuTitle3.setLength(0);
                    first_MenuTitle4.setLength(0);
                    first_MenuTitle5.setLength(0);
                    first_MenuTitle6.setLength(0);
                    first_MenuTitle7.setLength(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try
                {
                    for (int i=0;i<Constant.ListElementsMenutitle.size();i++)
                    {
                        if (Constant.ListElementsMenutitle.size()==1)
                        {
                            first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                        }
                        else
                        {
                            if (i==(Constant.ListElementsMenutitle.size()-1))
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle.get(i));
                            }
                            else
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                try {

                    for (int i=0;i<Constant.ListElementsMenutitle2.size();i++)
                    {
                        if (Constant.ListElementsMenutitle2.size()==1)
                        {
                            first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                        }
                        else
                        {
                            // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle2.size()-1))
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i));
                            }
                            else
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle3.size();i++)
                    {
                        if (Constant.ListElementsMenutitle3.size()==1)
                        {
                            first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                        }
                        else
                        {
                            //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle3.size()-1))
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i));
                            }
                            else
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle4.size();i++)
                    {
                        if (Constant.ListElementsMenutitle4.size()==1)
                        {
                            first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                        }
                        else
                        {
                            //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                            if (i==(Constant.ListElementsMenutitle4.size()-1))
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i));
                            }
                            else
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle5.size();i++)
                    {
                        if (Constant.ListElementsMenutitle5.size()==1)
                        {
                            first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                        }
                        else
                        {
                            //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle5.size()-1))
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i));
                            }
                            else
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle6.size();i++)
                    {
                        if (Constant.ListElementsMenutitle6.size()==1)
                        {
                            first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                        }
                        else
                        {
                            //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                            if (i==(Constant.ListElementsMenutitle6.size()-1))
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i));
                            }
                            else
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try {
                    for (int i=0;i<Constant.ListElementsMenutitle7.size();i++)
                    {
                        if (Constant.ListElementsMenutitle7.size()==1)
                        {
                            first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                        }
                        else
                        {
                            // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                            if (i==(Constant.ListElementsMenutitle7.size()-1))
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i));
                            }
                            else
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i)+" | ");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                compare_check_tab="0";


                if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                        &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                        && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                        && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                        && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                        && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                        && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                        &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                        &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                        && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                        && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                        && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                        && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                        && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                        )
                {
                    compare_check_tab="1";
                }
                else
                {
                    compare_check_tab="0";
                }

            /*if (mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                    &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                    && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                    && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                    && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                    && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                    && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))
                    )
            {
                compare_check_cs="1";
            }
            else
            {
                compare_check_cs="0";
            }*/


              //  Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


               /* if (compare_check.equalsIgnoreCase("0"))
                {
                    ShowDialogBackNew();
                }
                else
                {
                    {
                        {
                *//*===========breakfast===========*//*

                            Constant.ListElementsMenutitle.clear();
                            Constant.ListElementsMenutitle2.clear();
                            Constant.ListElementsMenutitle3.clear();
                            Constant.ListElementsMenutitle4.clear();
                            Constant.ListElementsMenutitle5.clear();
                            Constant.ListElementsMenutitle6.clear();
                            Constant.ListElementsMenutitle7.clear();

                            Constant.ListElementsFinal_temp.clear();
                        }

                        {
                 *//*===========lunch===========*//*

                            Constant.ListElementsMenutitle_lunch.clear();
                            Constant.ListElementsMenutitle2_lunch.clear();
                            Constant.ListElementsMenutitle3_lunch.clear();
                            Constant.ListElementsMenutitle4_lunch.clear();
                            Constant.ListElementsMenutitle5_lunch.clear();
                            Constant.ListElementsMenutitle6_lunch.clear();
                            Constant.ListElementsMenutitle7_lunch.clear();

                            Constant.ListElementsFinal_temp_lunch.clear();
                        }

                        {
                 *//*===========dinner===========*//*

                            Constant.ListElementsMenutitle_dinner.clear();
                            Constant.ListElementsMenutitle2_dinner.clear();
                            Constant.ListElementsMenutitle3_dinner.clear();
                            Constant.ListElementsMenutitle4_dinner.clear();
                            Constant.ListElementsMenutitle5_dinner.clear();
                            Constant.ListElementsMenutitle6_dinner.clear();
                            Constant.ListElementsMenutitle7_dinner.clear();

                            Constant.ListElementsFinal_temp_dinner.clear();
                        }


                        Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                        startActivity(intent);
                        finish();
                    }
                }*/


            }

       /* =================================Breakfast end==============*/

          /* =================================Lunch Start==============*/

            else if (new_pos_new == 1)
            {
                {
                  //  Toast.makeText(mContext, "old_new_pos_new="+new_pos_new, Toast.LENGTH_SHORT).show();


                    MenuLunchFragmentCheff menuLunchFragmentCheff=new MenuLunchFragmentCheff();
                    String mon_cs_current= menuLunchFragmentCheff.mon_cs;
                    String tue_cs_current= menuLunchFragmentCheff.tue_cs;
                    String wed_cs_current= menuLunchFragmentCheff.wed_cs;
                    String thu_cs_current= menuLunchFragmentCheff.thu_cs;
                    String fri_cs_current= menuLunchFragmentCheff.fri_cs;
                    String sat_cs_current= menuLunchFragmentCheff.sat_cs;
                    String sun_cs_current= menuLunchFragmentCheff.sun_cs;



                    List<MenuModel> menuList = null;
                    menuList = MenuCalendarManager.lunchList;
                    Log.i("respo_size_model=", "" + menuList.size());


                    StringBuilder first_MenuTitle = new StringBuilder();
                    StringBuilder first_MenuTitle2 = new StringBuilder();
                    StringBuilder first_MenuTitle3 = new StringBuilder();
                    StringBuilder first_MenuTitle4 = new StringBuilder();
                    StringBuilder first_MenuTitle5 = new StringBuilder();
                    StringBuilder first_MenuTitle6 = new StringBuilder();
                    StringBuilder first_MenuTitle7 = new StringBuilder();

                    try {
                        first_MenuTitle.setLength(0);
                        first_MenuTitle2.setLength(0);
                        first_MenuTitle3.setLength(0);
                        first_MenuTitle4.setLength(0);
                        first_MenuTitle5.setLength(0);
                        first_MenuTitle6.setLength(0);
                        first_MenuTitle7.setLength(0);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try
                    {
                        for (int i=0;i<Constant.ListElementsMenutitle_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle_lunch.size()==1)
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                            }
                            else
                            {
                                if (i==(Constant.ListElementsMenutitle_lunch.size()-1))
                                {
                                    first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle.append(Constant.ListElementsMenutitle_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    try {

                        for (int i=0;i<Constant.ListElementsMenutitle2_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle2_lunch.size()==1)
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                            }
                            else
                            {
                                // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                                if (i==(Constant.ListElementsMenutitle2_lunch.size()-1))
                                {
                                    first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle2.append(Constant.ListElementsMenutitle2_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle3_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle3_lunch.size()==1)
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                            }
                            else
                            {
                                //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle3_lunch.size()-1))
                                {
                                    first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle3.append(Constant.ListElementsMenutitle3_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle4_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle4_lunch.size()==1)
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                            }
                            else
                            {
                                //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                                if (i==(Constant.ListElementsMenutitle4_lunch.size()-1))
                                {
                                    first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle4.append(Constant.ListElementsMenutitle4_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle5_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle5_lunch.size()==1)
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                            }
                            else
                            {
                                //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle5_lunch.size()-1))
                                {
                                    first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle5.append(Constant.ListElementsMenutitle5_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle6_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle6_lunch.size()==1)
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                            }
                            else
                            {
                                //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle6_lunch.size()-1))
                                {
                                    first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle6.append(Constant.ListElementsMenutitle6_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle7_lunch.size();i++)
                        {
                            if (Constant.ListElementsMenutitle7_lunch.size()==1)
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                            }
                            else
                            {
                                // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                                if (i==(Constant.ListElementsMenutitle7_lunch.size()-1))
                                {
                                    first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i));
                                }
                                else
                                {
                                    first_MenuTitle7.append(Constant.ListElementsMenutitle7_lunch.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    compare_check_tab="0";


                    if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                            &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                            && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                            && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                            && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                            && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                            && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                            &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                            &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                            && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                            && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                            && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                            && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                            && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                            )
                    {
                        compare_check_tab="1";
                    }
                    else
                    {
                        compare_check_tab="0";
                    }



                  //  Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


                    /*if (compare_check.equalsIgnoreCase("0"))
                    {
                        ShowDialogBackNew();
                    }
                    else
                    {
                        {
                            {
                *//*===========breakfast===========*//*

                                Constant.ListElementsMenutitle.clear();
                                Constant.ListElementsMenutitle2.clear();
                                Constant.ListElementsMenutitle3.clear();
                                Constant.ListElementsMenutitle4.clear();
                                Constant.ListElementsMenutitle5.clear();
                                Constant.ListElementsMenutitle6.clear();
                                Constant.ListElementsMenutitle7.clear();

                                Constant.ListElementsFinal_temp.clear();
                            }

                            {
                 *//*===========lunch===========*//*

                                Constant.ListElementsMenutitle_lunch.clear();
                                Constant.ListElementsMenutitle2_lunch.clear();
                                Constant.ListElementsMenutitle3_lunch.clear();
                                Constant.ListElementsMenutitle4_lunch.clear();
                                Constant.ListElementsMenutitle5_lunch.clear();
                                Constant.ListElementsMenutitle6_lunch.clear();
                                Constant.ListElementsMenutitle7_lunch.clear();

                                Constant.ListElementsFinal_temp_lunch.clear();
                            }

                            {
                 *//*===========dinner===========*//*

                                Constant.ListElementsMenutitle_dinner.clear();
                                Constant.ListElementsMenutitle2_dinner.clear();
                                Constant.ListElementsMenutitle3_dinner.clear();
                                Constant.ListElementsMenutitle4_dinner.clear();
                                Constant.ListElementsMenutitle5_dinner.clear();
                                Constant.ListElementsMenutitle6_dinner.clear();
                                Constant.ListElementsMenutitle7_dinner.clear();

                                Constant.ListElementsFinal_temp_dinner.clear();
                            }


                            Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                            startActivity(intent);
                            finish();
                        }
                    }
*/

                }
            }

           /* =================================Lunch end==============*/

          /* =================================Dinner Start==============*/

            else if (new_pos_new == 2)
            {
                {
                  //  Toast.makeText(mContext, "old_new_pos_new="+new_pos_new, Toast.LENGTH_SHORT).show();


                    MenuDinnerFragmentCheff menuDinnerFragmentCheff=new MenuDinnerFragmentCheff();
                    String mon_cs_current= menuDinnerFragmentCheff.mon_cs;
                    String tue_cs_current= menuDinnerFragmentCheff.tue_cs;
                    String wed_cs_current= menuDinnerFragmentCheff.wed_cs;
                    String thu_cs_current= menuDinnerFragmentCheff.thu_cs;
                    String fri_cs_current= menuDinnerFragmentCheff.fri_cs;
                    String sat_cs_current= menuDinnerFragmentCheff.sat_cs;
                    String sun_cs_current= menuDinnerFragmentCheff.sun_cs;


                    List<MenuModel> menuList = null;
                    menuList = MenuCalendarManager.dinnerList;
                    Log.i("respo_size_model=", "" + menuList.size());


                    StringBuilder first_MenuTitle = new StringBuilder();
                    StringBuilder first_MenuTitle2 = new StringBuilder();
                    StringBuilder first_MenuTitle3 = new StringBuilder();
                    StringBuilder first_MenuTitle4 = new StringBuilder();
                    StringBuilder first_MenuTitle5 = new StringBuilder();
                    StringBuilder first_MenuTitle6 = new StringBuilder();
                    StringBuilder first_MenuTitle7 = new StringBuilder();

                    try {
                        first_MenuTitle.setLength(0);
                        first_MenuTitle2.setLength(0);
                        first_MenuTitle3.setLength(0);
                        first_MenuTitle4.setLength(0);
                        first_MenuTitle5.setLength(0);
                        first_MenuTitle6.setLength(0);
                        first_MenuTitle7.setLength(0);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try
                    {
                        for (int i=0;i<Constant.ListElementsMenutitle_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle_dinner.size()==1)
                            {
                                first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                            }
                            else
                            {
                                if (i==(Constant.ListElementsMenutitle_dinner.size()-1))
                                {
                                    first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle.append(Constant.ListElementsMenutitle_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    try {

                        for (int i=0;i<Constant.ListElementsMenutitle2_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle2_dinner.size()==1)
                            {
                                first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                            }
                            else
                            {
                                // first_MenuTitle2.append(Constant.ListElementsMenutitle2.get(i)+" | ");
                                if (i==(Constant.ListElementsMenutitle2_dinner.size()-1))
                                {
                                    first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle2.append(Constant.ListElementsMenutitle2_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle3_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle3_dinner.size()==1)
                            {
                                first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                            }
                            else
                            {
                                //first_MenuTitle3.append(Constant.ListElementsMenutitle3.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle3_dinner.size()-1))
                                {
                                    first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle3.append(Constant.ListElementsMenutitle3_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle4_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle4_dinner.size()==1)
                            {
                                first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                            }
                            else
                            {
                                //  first_MenuTitle4.append(Constant.ListElementsMenutitle4.get(i)+" | ");
                                if (i==(Constant.ListElementsMenutitle4_dinner.size()-1))
                                {
                                    first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle4.append(Constant.ListElementsMenutitle4_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle5_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle5_dinner.size()==1)
                            {
                                first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                            }
                            else
                            {
                                //first_MenuTitle5.append(Constant.ListElementsMenutitle5.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle5_dinner.size()-1))
                                {
                                    first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle5.append(Constant.ListElementsMenutitle5_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle6_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle6_dinner.size()==1)
                            {
                                first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                            }
                            else
                            {
                                //first_MenuTitle6.append(Constant.ListElementsMenutitle6.get(i)+" | ");

                                if (i==(Constant.ListElementsMenutitle6_dinner.size()-1))
                                {
                                    first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle6.append(Constant.ListElementsMenutitle6_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    try {
                        for (int i=0;i<Constant.ListElementsMenutitle7_dinner.size();i++)
                        {
                            if (Constant.ListElementsMenutitle7_dinner.size()==1)
                            {
                                first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                            }
                            else
                            {
                                // first_MenuTitle7.append(Constant.ListElementsMenutitle7.get(i) + " | ");

                                if (i==(Constant.ListElementsMenutitle7_dinner.size()-1))
                                {
                                    first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i));
                                }
                                else
                                {
                                    first_MenuTitle7.append(Constant.ListElementsMenutitle7_dinner.get(i)+" | ");
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    compare_check_tab="0";


                    if (first_MenuTitle.toString().trim().equalsIgnoreCase(menuList.get(0).getMenuTitle().toString().trim())
                            &&(first_MenuTitle2.toString().trim().equalsIgnoreCase(menuList.get(1).getMenuTitle().toString().trim()))
                            && (first_MenuTitle3.toString().trim().equalsIgnoreCase(menuList.get(2).getMenuTitle().toString().trim()))
                            && (first_MenuTitle4.toString().trim().equalsIgnoreCase(menuList.get(3).getMenuTitle().toString().trim()))
                            && (first_MenuTitle5.toString().trim().equalsIgnoreCase(menuList.get(4).getMenuTitle().toString().trim()))
                            && (first_MenuTitle6.toString().trim().equalsIgnoreCase(menuList.get(5).getMenuTitle().toString().trim()))
                            && (first_MenuTitle7.toString().trim().equalsIgnoreCase(menuList.get(6).getMenuTitle().toString().trim()))

                            &&mon_cs_current.toString().trim().equalsIgnoreCase(menuList.get(0).getMenucalender_cs().toString().trim())
                            &&(tue_cs_current.toString().trim().equalsIgnoreCase(menuList.get(1).getMenucalender_cs().toString().trim()))
                            && (wed_cs_current.toString().trim().equalsIgnoreCase(menuList.get(2).getMenucalender_cs().toString().trim()))
                            && (thu_cs_current.toString().trim().equalsIgnoreCase(menuList.get(3).getMenucalender_cs().toString().trim()))
                            && (fri_cs_current.toString().trim().equalsIgnoreCase(menuList.get(4).getMenucalender_cs().toString().trim()))
                            && (sat_cs_current.toString().trim().equalsIgnoreCase(menuList.get(5).getMenucalender_cs().toString().trim()))
                            && (sun_cs_current.toString().trim().equalsIgnoreCase(menuList.get(6).getMenucalender_cs().toString().trim()))

                            )
                    {
                        compare_check_tab="1";
                    }
                    else
                    {
                        compare_check_tab="0";
                    }

                 //   Toast.makeText(mContext, "compare_check="+compare_check, Toast.LENGTH_SHORT).show();


                /*    if (compare_check.equalsIgnoreCase("0"))
                    {
                        ShowDialogBackNew();
                    }
                    else
                    {
                        {
                            {
                *//*===========breakfast===========*//*

                                Constant.ListElementsMenutitle.clear();
                                Constant.ListElementsMenutitle2.clear();
                                Constant.ListElementsMenutitle3.clear();
                                Constant.ListElementsMenutitle4.clear();
                                Constant.ListElementsMenutitle5.clear();
                                Constant.ListElementsMenutitle6.clear();
                                Constant.ListElementsMenutitle7.clear();

                                Constant.ListElementsFinal_temp.clear();
                            }

                            {
                 *//*===========lunch===========*//*

                                Constant.ListElementsMenutitle_lunch.clear();
                                Constant.ListElementsMenutitle2_lunch.clear();
                                Constant.ListElementsMenutitle3_lunch.clear();
                                Constant.ListElementsMenutitle4_lunch.clear();
                                Constant.ListElementsMenutitle5_lunch.clear();
                                Constant.ListElementsMenutitle6_lunch.clear();
                                Constant.ListElementsMenutitle7_lunch.clear();

                                Constant.ListElementsFinal_temp_lunch.clear();
                            }

                            {
                 *//*===========dinner===========*//*

                                Constant.ListElementsMenutitle_dinner.clear();
                                Constant.ListElementsMenutitle2_dinner.clear();
                                Constant.ListElementsMenutitle3_dinner.clear();
                                Constant.ListElementsMenutitle4_dinner.clear();
                                Constant.ListElementsMenutitle5_dinner.clear();
                                Constant.ListElementsMenutitle6_dinner.clear();
                                Constant.ListElementsMenutitle7_dinner.clear();

                                Constant.ListElementsFinal_temp_dinner.clear();
                            }


                            Intent intent=new Intent(MenuActivityCheff.this,HomeActivityCheff.class);
                            startActivity(intent);
                            finish();
                        }
                    }*/


                }
            }

          /* =================================Dinner end==============*/
        }
    }



}
