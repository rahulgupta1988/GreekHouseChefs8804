package com.sixd.greek.house.chefs.fragmentsCheff;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.Cheff.AddMenuCheffActivityLunch;
import com.sixd.greek.house.chefs.Cheff.HomeActivityCheff;
import com.sixd.greek.house.chefs.Cheff.MenuActivityCheff;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.model.MenuModel;
import com.sixd.greek.house.chefs.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao_db.CalenderData;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuLunchFragmentCheff extends Fragment
{

    String Is_draft="";


    String mon_meal="",tue_meal="",wed_meal="",thu_meal="",fri_meal="",sat_meal="",sun_meal="";
    public static String mon_cs="",tue_cs="",wed_cs="",thu_cs="",fri_cs="",sat_cs="",sun_cs="";
    String meal_type="lunch",week_number="",week_year ="",menu_calendar_id="",menu_is_draft="";

    List<CalenderData> calenderDatas;

    Context mContext;
    View view;
    RecyclerView breakfast_list;

    TextView submit_data,submit_data_final;
    TextView item_text,item_text2,item_text3,item_text4,item_text5,item_text6,item_text7;
    TextView day_txt,day_txt2,day_txt3,day_txt4,day_txt5,day_txt6,day_txt7;
    TextView month_txt,month_txt2,month_txt3,month_txt4,month_txt5,month_txt6,month_txt7;
    ImageView ch_ic,ch_ic2,ch_ic3,ch_ic4,ch_ic5,ch_ic6,ch_ic7;
    ImageView edit_menu,edit_menu2,edit_menu3,edit_menu4,edit_menu5,edit_menu6,edit_menu7;
    List<MenuModel> menuList=null;

  /*  String first_MenuTitle="",first_MenuTitle2="",first_MenuTitle3="",first_MenuTitle4="",first_MenuTitle5="",
            first_MenuTitle6="",first_MenuTitle7="";*/

    StringBuilder first_MenuTitle = new StringBuilder();
    StringBuilder first_MenuTitle2 = new StringBuilder();
    StringBuilder first_MenuTitle3 = new StringBuilder();
    StringBuilder first_MenuTitle4 = new StringBuilder();
    StringBuilder first_MenuTitle5 = new StringBuilder();
    StringBuilder first_MenuTitle6 = new StringBuilder();
    StringBuilder first_MenuTitle7 = new StringBuilder();

    MaterialDialog materialDialog=null;
    LinearLayout linear;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.menulunch_view_cheff, container, false);

        materialDialog=new MaterialDialog.Builder(mContext)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();
        linear=(LinearLayout)view.findViewById(R.id.linear);

       // Toast.makeText(mContext,"LunchFragment",Toast.LENGTH_LONG).show();

        //  Toast.makeText(mContext,"hii1",Toast.LENGTH_LONG).show();


          /* breakfast_list=(RecyclerView)view.findViewById(R.id.breakfast_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        breakfast_list.setLayoutManager(layoutManager);
        breakfast_list.setItemAnimator(new DefaultItemAnimator());
        breakfast_list.setAdapter(new Menu_BreakfastListAdapterCheff(mContext,1));*/

        menuList= MenuCalendarManager.lunchList;
        menu_calendar_id=MenuCalendarManager.menu_calendar_id;
        menu_is_draft=MenuCalendarManager.menu_is_draft;

        calenderDatas = new MenuCalendarManager(mContext).geCalenderDatas();


        Log.i("respo_size_model_fina1=",""+menuList.size());
        Log.i("respo_size_cons_final1=",""+Constant.ListElementsMenutitle_lunch.size());

        String today_date="";
        SimpleDateFormat formatter;
        formatter= new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        today_date = mdformat.format(calendar.getTime());

        submit_data=(TextView)view.findViewById(R.id.submit_data);
        submit_data_final=(TextView)view.findViewById(R.id.submit_data_final);
        item_text=(TextView)view.findViewById(R.id.item_text);
        item_text2=(TextView)view.findViewById(R.id.item_text2);
        item_text3=(TextView)view.findViewById(R.id.item_text3);
        item_text4=(TextView)view.findViewById(R.id.item_text4);
        item_text5=(TextView)view.findViewById(R.id.item_text5);
        item_text6=(TextView)view.findViewById(R.id.item_text6);
        item_text7=(TextView)view.findViewById(R.id.item_text7);

        day_txt=(TextView)view.findViewById(R.id.day_txt);
        day_txt2=(TextView)view.findViewById(R.id.day_txt2);
        day_txt3=(TextView)view.findViewById(R.id.day_txt3);
        day_txt4=(TextView)view.findViewById(R.id.day_txt4);
        day_txt5=(TextView)view.findViewById(R.id.day_txt5);
        day_txt6=(TextView)view.findViewById(R.id.day_txt6);
        day_txt7=(TextView)view.findViewById(R.id.day_txt7);

        month_txt=(TextView)view.findViewById(R.id.month_txt);
        month_txt2=(TextView)view.findViewById(R.id.month_txt2);
        month_txt3=(TextView)view.findViewById(R.id.month_txt3);
        month_txt4=(TextView)view.findViewById(R.id.month_txt4);
        month_txt5=(TextView)view.findViewById(R.id.month_txt5);
        month_txt6=(TextView)view.findViewById(R.id.month_txt6);
        month_txt7=(TextView)view.findViewById(R.id.month_txt7);

        ch_ic=(ImageView)view.findViewById(R.id.ch_ic);
        ch_ic2=(ImageView)view.findViewById(R.id.ch_ic2);
        ch_ic3=(ImageView)view.findViewById(R.id.ch_ic3);
        ch_ic4=(ImageView)view.findViewById(R.id.ch_ic4);
        ch_ic5=(ImageView)view.findViewById(R.id.ch_ic5);
        ch_ic6=(ImageView)view.findViewById(R.id.ch_ic6);
        ch_ic7=(ImageView)view.findViewById(R.id.ch_ic7);


        edit_menu=(ImageView)view.findViewById(R.id.edit_menu);
        edit_menu2=(ImageView)view.findViewById(R.id.edit_menu2);
        edit_menu3=(ImageView)view.findViewById(R.id.edit_menu3);
        edit_menu4=(ImageView)view.findViewById(R.id.edit_menu4);
        edit_menu5=(ImageView)view.findViewById(R.id.edit_menu5);
        edit_menu6=(ImageView)view.findViewById(R.id.edit_menu6);
        edit_menu7=(ImageView)view.findViewById(R.id.edit_menu7);

        try
        {
            first_MenuTitle.setLength(0);
            first_MenuTitle2.setLength(0);
            first_MenuTitle3.setLength(0);
            first_MenuTitle4.setLength(0);
            first_MenuTitle5.setLength(0);
            first_MenuTitle6.setLength(0);
            first_MenuTitle7.setLength(0);
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

        String first_char=((menuList.get(0).getDaynameStart()));
        String first_day=(getDate(menuList.get(0).getDate()));
        String first_month=(getMonthName(menuList.get(0).getDate()));
        String cs_val=menuList.get(0).getMenucalender_cs();
        String server_date=menuList.get(0).getDate().toString().trim();

        item_text.setText(first_MenuTitle);
        day_txt.setText(first_char);
        month_txt.setText(first_month+" "+first_day);

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

        String first_char2=((menuList.get(1).getDaynameStart()));
        String first_day2=(getDate(menuList.get(1).getDate()));
        String first_month2=(getMonthName(menuList.get(1).getDate()));
        String cs_val2=menuList.get(1).getMenucalender_cs();
        String server_date2=menuList.get(1).getDate().toString().trim();

        item_text2.setText(first_MenuTitle2);
        day_txt2.setText(first_char2);
        month_txt2.setText(first_month2+" "+first_day2);

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

        String first_char3=((menuList.get(2).getDaynameStart()));
        String first_day3=(getDate(menuList.get(2).getDate()));
        String first_month3=(getMonthName(menuList.get(2).getDate()));
        String cs_val3=menuList.get(2).getMenucalender_cs();
        String server_date3=menuList.get(2).getDate().toString().trim();

        item_text3.setText(first_MenuTitle3);
        day_txt3.setText(first_char3);
        month_txt3.setText(first_month3+" "+first_day3);

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

        String first_char4=((menuList.get(3).getDaynameStart()));
        String first_day4=(getDate(menuList.get(3).getDate()));
        String first_month4=(getMonthName(menuList.get(3).getDate()));
        String cs_val4=menuList.get(3).getMenucalender_cs();
        String server_date4=menuList.get(3).getDate().toString().trim();

        item_text4.setText(first_MenuTitle4);
        day_txt4.setText(first_char4);
        month_txt4.setText(first_month4+" "+first_day4);

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

        String first_char5=((menuList.get(4).getDaynameStart()));
        String first_day5=(getDate(menuList.get(4).getDate()));
        String first_month5=(getMonthName(menuList.get(4).getDate()));
        String cs_val5=menuList.get(4).getMenucalender_cs();
        String server_date5=menuList.get(4).getDate().toString().trim();

        item_text5.setText(first_MenuTitle5);
        day_txt5.setText(first_char5);
        month_txt5.setText(first_month5+" "+first_day5);


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

        String first_char6=((menuList.get(5).getDaynameStart()));
        String first_day6=(getDate(menuList.get(5).getDate()));
        String first_month6=(getMonthName(menuList.get(5).getDate()));
        String cs_val6=menuList.get(5).getMenucalender_cs();
        String server_date6=menuList.get(5).getDate().toString().trim();

        item_text6.setText(first_MenuTitle6);
        day_txt6.setText(first_char6);
        month_txt6.setText(first_month6+" "+first_day6);


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

        String first_char7=((menuList.get(6).getDaynameStart()));
        String first_day7=(getDate(menuList.get(6).getDate()));
        String first_month7=(getMonthName(menuList.get(6).getDate()));
        String cs_val7=menuList.get(6).getMenucalender_cs();
        String server_date7=menuList.get(6).getDate().toString().trim();


        item_text7.setText(first_MenuTitle7);
        day_txt7.setText(first_char7);
        month_txt7.setText(first_month7+" "+first_day7);



        if (cs_val.equalsIgnoreCase("1"))
        {
            ch_ic.setSelected(true);
        }
        else
        {
            ch_ic.setSelected(false);
        }

        if (cs_val2.equalsIgnoreCase("1"))
        {
            ch_ic2.setSelected(true);
        }
        else
        {
            ch_ic2.setSelected(false);
        }

        if (cs_val3.equalsIgnoreCase("1"))
        {
            ch_ic3.setSelected(true);
        }
        else
        {
            ch_ic3.setSelected(false);
        }

        if (cs_val4.equalsIgnoreCase("1"))
        {
            ch_ic4.setSelected(true);
        }
        else
        {
            ch_ic4.setSelected(false);
        }

        if (cs_val5.equalsIgnoreCase("1"))
        {
            ch_ic5.setSelected(true);
        }
        else
        {
            ch_ic5.setSelected(false);
        }

        if (cs_val6.equalsIgnoreCase("1"))
        {
            ch_ic6.setSelected(true);
        }
        else
        {
            ch_ic6.setSelected(false);
        }

        if (cs_val7.equalsIgnoreCase("1"))
        {
            ch_ic7.setSelected(true);
        }
        else
        {
            ch_ic7.setSelected(false);
        }



      /*  ch_ic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic.isSelected())
                {
                    ch_ic.setSelected(false);
                    mon_cs="0";
                }
                else{
                    ch_ic.setSelected(true);
                    mon_cs="1";
                }

               // Toast.makeText(mContext,"mon_cs="+mon_cs,Toast.LENGTH_LONG).show();
            }
        });

        ch_ic2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic2.isSelected())
                {
                    ch_ic2.setSelected(false);
                    tue_cs="0";
                }
                else{
                    ch_ic2.setSelected(true);
                    tue_cs="1";
                }
            }
        });

        ch_ic3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic3.isSelected())
                {
                    ch_ic3.setSelected(false);
                    wed_cs="0";
                }
                else{
                    ch_ic3.setSelected(true);
                    wed_cs="1";
                }
            }
        });

        ch_ic4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic4.isSelected())
                {
                    ch_ic4.setSelected(false);
                    thu_cs="0";
                }
                else{
                    ch_ic4.setSelected(true);
                    thu_cs="1";
                }
            }
        });

        ch_ic5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic5.isSelected())
                {
                    ch_ic5.setSelected(false);
                    fri_cs="0";
                }
                else{
                    ch_ic5.setSelected(true);
                    fri_cs="1";
                }
            }
        });

        ch_ic6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic6.isSelected())
                {
                    ch_ic6.setSelected(false);
                    sat_cs="0";
                }
                else{
                    ch_ic6.setSelected(true);
                    sat_cs="1";
                }
            }
        });

        ch_ic7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(ch_ic7.isSelected())
                {
                    ch_ic7.setSelected(false);
                    sun_cs="0";
                }
                else{
                    ch_ic7.setSelected(true);
                    sun_cs="1";
                }
            }
        });*/



             /* =====================start===========*/

        ch_ic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {

                    if (item_text.getText().toString().trim()!= null
                            && !item_text.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text.getText().toString().trim().equalsIgnoreCase("null"))

                    {

                        if(ch_ic.isSelected())
                        {
                            ch_ic.setSelected(false);
                            mon_cs="0";
                        }
                        else{
                            ch_ic.setSelected(true);
                            mon_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

                // Toast.makeText(mContext,"mon_cs="+mon_cs,Toast.LENGTH_LONG).show();
            }
        });

        ch_ic2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text2.getText().toString().trim()!= null
                            && !item_text2.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text2.getText().toString().trim().equalsIgnoreCase("null"))

                    {

                        if(ch_ic2.isSelected())
                        {
                            ch_ic2.setSelected(false);
                            tue_cs="0";
                        }
                        else{
                            ch_ic2.setSelected(true);
                            tue_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });

        ch_ic3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text3.getText().toString().trim()!= null
                            && !item_text3.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text3.getText().toString().trim().equalsIgnoreCase("null"))

                    {

                        if(ch_ic3.isSelected())
                        {
                            ch_ic3.setSelected(false);
                            wed_cs="0";
                        }
                        else{
                            ch_ic3.setSelected(true);
                            wed_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });

        ch_ic4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text4.getText().toString().trim()!= null
                            && !item_text4.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text4.getText().toString().trim().equalsIgnoreCase("null"))

                    {
                        if(ch_ic4.isSelected())
                        {
                            ch_ic4.setSelected(false);
                            thu_cs="0";
                        }
                        else{
                            ch_ic4.setSelected(true);
                            thu_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });

        ch_ic5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text5.getText().toString().trim()!= null
                            && !item_text5.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text5.getText().toString().trim().equalsIgnoreCase("null"))

                    {
                        if(ch_ic5.isSelected())
                        {
                            ch_ic5.setSelected(false);
                            fri_cs="0";
                        }
                        else{
                            ch_ic5.setSelected(true);
                            fri_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

            }
        });

        ch_ic6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text6.getText().toString().trim()!= null
                            && !item_text6.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text6.getText().toString().trim().equalsIgnoreCase("null"))

                    {
                        if(ch_ic6.isSelected())
                        {
                            ch_ic6.setSelected(false);
                            sat_cs="0";
                        }
                        else{
                            ch_ic6.setSelected(true);
                            sat_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });

        ch_ic7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    if (item_text7.getText().toString().trim()!= null
                            && !item_text7.getText().toString().trim().equalsIgnoreCase("")
                            && !item_text7.getText().toString().trim().equalsIgnoreCase("null"))

                    {
                        if(ch_ic7.isSelected())
                        {
                            ch_ic7.setSelected(false);
                            sun_cs="0";
                        }
                        else{
                            ch_ic7.setSelected(true);
                            sun_cs="1";
                        }
                    }
                    else {
                        Toast.makeText(mContext,"Please set the menu first!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });

 /* =====================End===========*/



        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic.setVisibility(View.VISIBLE);
                edit_menu.setVisibility(View.VISIBLE);
            }
            else
            {
                ch_ic.setVisibility(View.GONE);
                edit_menu.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date2;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic2.setVisibility(View.VISIBLE);
                edit_menu2.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic2.setVisibility(View.GONE);
                edit_menu2.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date3;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic3.setVisibility(View.VISIBLE);
                edit_menu3.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic3.setVisibility(View.GONE);
                edit_menu3.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date4;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic4.setVisibility(View.VISIBLE);
                edit_menu4.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic4.setVisibility(View.GONE);
                edit_menu4.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date5;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic5.setVisibility(View.VISIBLE);
                edit_menu5.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic5.setVisibility(View.GONE);
                edit_menu5.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date6;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic6.setVisibility(View.VISIBLE);
                edit_menu6.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic6.setVisibility(View.GONE);
                edit_menu6.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date7;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                ch_ic7.setVisibility(View.VISIBLE);
                edit_menu7.setVisibility(View.VISIBLE);
            }
            else {
                ch_ic7.setVisibility(View.GONE);
                edit_menu7.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        edit_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();

                    for (int i=0;i<Constant.ListElementsMenutitle_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }

                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed");
                    intent.putExtra("date",menuList.get(0).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

            }
        });
        edit_menu2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();
                    for (int i=0;i<Constant.ListElementsMenutitle2_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle2_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }
                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed2");
                    intent.putExtra("date",menuList.get(1).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

            }
        });
        edit_menu3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();
                    for (int i=0;i<Constant.ListElementsMenutitle3_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle3_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }
                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed3");
                    intent.putExtra("date",menuList.get(2).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });
        edit_menu4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();
                    for (int i=0;i<Constant.ListElementsMenutitle4_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle4_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }
                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed4");
                    intent.putExtra("date",menuList.get(3).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });
        edit_menu5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();


                    for (int i=0;i<Constant.ListElementsMenutitle5_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle5_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }

                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed5");
                    intent.putExtra("date",menuList.get(4).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

            }
        });
        edit_menu6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();

                    for (int i=0;i<Constant.ListElementsMenutitle6_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle6_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }

                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);


                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value", "ed6");
                    intent.putExtra("date",menuList.get(5).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });
        edit_menu7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Constant.ListElementsFinal_temp_lunch.clear();
                    for (int i=0;i<Constant.ListElementsMenutitle7_lunch.size();i++)
                    {
                        String s = Constant.ListElementsMenutitle7_lunch.get(i);
                        String[] array = s.split("\\|");
                        for(String word : array)
                        {
                            System.out.println(word);
                            Constant.ListElementsFinal_temp_lunch.add(word);

                        }
                    }

                    Log.i("respo_temp_size=",""+Constant.ListElementsFinal_temp_lunch);

                    Intent intent=new Intent(mContext,AddMenuCheffActivityLunch.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("value","ed7");
                    intent.putExtra("date",menuList.get(6).getDate());
                    mContext.startActivity(intent);
                    ((MenuActivityCheff) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }

            }
        });




        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    Is_draft="0";

                    mon_meal=item_text.getText().toString().trim();
                    tue_meal=item_text2.getText().toString().trim();
                    wed_meal=item_text3.getText().toString().trim();
                    thu_meal=item_text4.getText().toString().trim();
                    fri_meal=item_text5.getText().toString().trim();
                    sat_meal=item_text6.getText().toString().trim();
                    sun_meal=item_text7.getText().toString().trim();

                    if(ch_ic.isSelected())
                    {
                        mon_cs="1";
                    }
                    else
                    {
                        mon_cs="0";
                    }

                    if(ch_ic2.isSelected())
                    {
                        tue_cs="1";
                    }
                    else
                    {
                        tue_cs="0";
                    }

                    if(ch_ic3.isSelected())
                    {
                        wed_cs="1";
                    }
                    else
                    {
                        wed_cs="0";
                    }

                    if(ch_ic4.isSelected())
                    {
                        thu_cs="1";
                    }
                    else
                    {
                        thu_cs="0";
                    }

                    if(ch_ic5.isSelected())
                    {
                        fri_cs="1";
                    }
                    else
                    {
                        fri_cs="0";
                    }

                    if(ch_ic6.isSelected())
                    {
                        sat_cs="1";
                    }
                    else
                    {
                        sat_cs="0";
                    }

                    if(ch_ic7.isSelected())
                    {
                        sun_cs="1";
                    }
                    else
                    {
                        sun_cs="0";
                    }

                    try
                    {
                        week_number=calenderDatas.get(0).getWeek_number().toString().trim();
                        week_year=calenderDatas.get(0).getWeek_year().toString().trim();
                    }
                    catch (Exception e)
                    {
                        week_number="";
                        week_year="";
                        e.printStackTrace();
                    }

          /* Log.i("respo_week_number=",week_number+"&week_year="+week_year);*/
                    //Toast.makeText(mContext,"week_number="+week_number+"&week_year="+week_year,Toast.LENGTH_LONG).show();

                    Log.i("respo_meal_type=", meal_type);
                    Log.i("respo_menu_calendar_id=", menu_calendar_id);

                    Log.i("respo_mon_meal=", mon_meal);
                    Log.i("respo_tue_meal=", tue_meal);
                    Log.i("respo_wed_meal=", wed_meal);
                    Log.i("respo_thu_meal=", thu_meal);
                    Log.i("respo_fri_meal=", fri_meal);
                    Log.i("respo_sat_meal=", sat_meal);
                    Log.i("respo_sun_meal=", sun_meal);

                    Log.i("respo_mon_cs=", mon_cs);
                    Log.i("respo_tue_cs=", tue_cs);
                    Log.i("respo_wed_cs=", wed_cs);
                    Log.i("respo_thu_cs=", thu_cs);
                    Log.i("respo_fri_cs=", fri_cs);
                    Log.i("respo_sat_cs=", sat_cs);
                    Log.i("respo_sun_cs=", sun_cs);


                    initaddEditMenuCalenderAPI();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });


        submit_data_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (menu_is_draft.equalsIgnoreCase("0"))
                {
                    ShowDialogSubmitFinalMenu();
                }
                else
                {
                    Toast.makeText(mContext,"You have already sent this week's menu for approval.",Toast.LENGTH_LONG).show();
                }
            }
        });



        if(ch_ic.isSelected())
        {
            mon_cs="1";
        }
        else
        {
            mon_cs="0";
        }

        if(ch_ic2.isSelected())
        {
            tue_cs="1";
        }
        else
        {
            tue_cs="0";
        }

        if(ch_ic3.isSelected())
        {
            wed_cs="1";
        }
        else
        {
            wed_cs="0";
        }

        if(ch_ic4.isSelected())
        {
            thu_cs="1";
        }
        else
        {
            thu_cs="0";
        }

        if(ch_ic5.isSelected())
        {
            fri_cs="1";
        }
        else
        {
            fri_cs="0";
        }

        if(ch_ic6.isSelected())
        {
            sat_cs="1";
        }
        else
        {
            sat_cs="0";
        }

        if(ch_ic7.isSelected())
        {
            sun_cs="1";
        }
        else
        {
            sun_cs="0";
        }



        return view;
    }



    String getMonthName(String dateStr){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(dateStr);
            SimpleDateFormat outFormat = new SimpleDateFormat("MMM");
            String goal = outFormat.format(date);
            return goal.toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    String getDate(String dateStr){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return String.valueOf(day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }



    public void initaddEditMenuCalenderAPI()
    {
        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(mContext.CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new MenuCalendarManager(mContext).initaddEditMenuCalenderAPI(meal_type,week_number,week_year,mon_meal,mon_cs,
                    tue_meal,tue_cs,wed_meal,wed_cs,thu_meal,thu_cs,fri_meal,fri_cs,sat_meal,sat_cs,sun_meal,sun_cs,
                    menu_calendar_id,Is_draft,new CallBackManager()
                    {

                        @Override
                        public void onSuccess(String responce) {

                            materialDialog.dismiss();
                            if (responce.equals("100"))
                            {
                                if (Is_draft.equalsIgnoreCase("0"))
                                {
                                    Toast.makeText(mContext,"Thank you for adding your menu!",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(mContext,"Thank you for submitting your menu!",Toast.LENGTH_LONG).show();
                                }

                                Constant.ListElementsMenutitle_lunch.clear();
                                Constant.ListElementsMenutitle2_lunch.clear();
                                Constant.ListElementsMenutitle3_lunch.clear();
                                Constant.ListElementsMenutitle4_lunch.clear();
                                Constant.ListElementsMenutitle5_lunch.clear();
                                Constant.ListElementsMenutitle6_lunch.clear();
                                Constant.ListElementsMenutitle7_lunch.clear();

                                intent = new Intent(mContext, HomeActivityCheff.class);
                                startActivity(intent);
                                getActivity().finish();
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


                                if(responce.contains("Received data"))
                                {
                                    String sessionToken=new LoginManager(mContext).getSessionToken();
                                    String error_content=""+Constant.BASEURL + "&param="+
                                            Constant.API.ADD_MENU_CALENDER_API +"&meal_type="+meal_type+"&week_number="+week_number
                                            + "&week_year="+week_year+"&mon_meal=" + mon_meal+ "&mon_cs=" + mon_cs
                                            + "&tue_meal="+tue_meal+"&tue_cs="+tue_cs+"&wed_meal="+wed_meal+"&wed_cs="+wed_cs
                                            + "&thu_meal="+thu_meal+"&thu_cs="+thu_cs+"&fri_meal="+fri_meal+"&fri_cs="+fri_cs
                                            + "&sat_meal="+sat_meal+"&sat_cs="+sat_cs+"&sun_meal="+sun_meal+"&sun_cs="+sun_cs
                                            + "&menu_calendar_id="+menu_calendar_id+ "&Is_draft="+Is_draft
                                            + "&token="+sessionToken.toString();

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


    public void ShowDialogSubmitFinalMenu()
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.submit_final_menu);
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

                Is_draft="1";

                mon_meal=item_text.getText().toString().trim();
                tue_meal=item_text2.getText().toString().trim();
                wed_meal=item_text3.getText().toString().trim();
                thu_meal=item_text4.getText().toString().trim();
                fri_meal=item_text5.getText().toString().trim();
                sat_meal=item_text6.getText().toString().trim();
                sun_meal=item_text7.getText().toString().trim();

                if(ch_ic.isSelected())
                {
                    mon_cs="1";
                }
                else
                {
                    mon_cs="0";
                }

                if(ch_ic2.isSelected())
                {
                    tue_cs="1";
                }
                else
                {
                    tue_cs="0";
                }

                if(ch_ic3.isSelected())
                {
                    wed_cs="1";
                }
                else
                {
                    wed_cs="0";
                }

                if(ch_ic4.isSelected())
                {
                    thu_cs="1";
                }
                else
                {
                    thu_cs="0";
                }

                if(ch_ic5.isSelected())
                {
                    fri_cs="1";
                }
                else
                {
                    fri_cs="0";
                }

                if(ch_ic6.isSelected())
                {
                    sat_cs="1";
                }
                else
                {
                    sat_cs="0";
                }

                if(ch_ic7.isSelected())
                {
                    sun_cs="1";
                }
                else
                {
                    sun_cs="0";
                }

                try
                {
                    week_number=calenderDatas.get(0).getWeek_number().toString().trim();
                    week_year=calenderDatas.get(0).getWeek_year().toString().trim();
                }
                catch (Exception e)
                {
                    week_number="";
                    week_year="";
                    e.printStackTrace();
                }

          /* Log.i("respo_week_number=",week_number+"&week_year="+week_year);*/
                //Toast.makeText(mContext,"week_number="+week_number+"&week_year="+week_year,Toast.LENGTH_LONG).show();

                Log.i("respo_meal_type=", meal_type);
                Log.i("respo_menu_calendar_id=", menu_calendar_id);

                Log.i("respo_mon_meal=", mon_meal);
                Log.i("respo_tue_meal=", tue_meal);
                Log.i("respo_wed_meal=", wed_meal);
                Log.i("respo_thu_meal=", thu_meal);
                Log.i("respo_fri_meal=", fri_meal);
                Log.i("respo_sat_meal=", sat_meal);
                Log.i("respo_sun_meal=", sun_meal);

                Log.i("respo_mon_cs=", mon_cs);
                Log.i("respo_tue_cs=", tue_cs);
                Log.i("respo_wed_cs=", wed_cs);
                Log.i("respo_thu_cs=", thu_cs);
                Log.i("respo_fri_cs=", fri_cs);
                Log.i("respo_sat_cs=", sat_cs);
                Log.i("respo_sun_cs=", sun_cs);


                initaddEditMenuCalenderAPI();

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


    public void initGetErrorhtmlAPI(String content)
    {
        String error_content="";
        error_content=content;

        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(mContext.CONNECTIVITY_SERVICE);
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



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("onAttach", "onAttach");
        mContext = (Context) activity;
    }
}
