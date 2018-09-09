package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.AddMenuAdapterBreakFast;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import android.text.format.DateFormat;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class AddMenuCheffActivityBreakFast extends HeaderActivtyCheff {

    ImageView line_menu_icon;


    TextView add_menu_day_txt;
    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout linear;
    Intent intent;
    /*List<GetMasterAllMenuItems> searchItems;*/

    ListView add_menu_list;
    TextView add_menu_txt,submit_add_menu;
    EditText menu_ed_txt;
    String menu_title="";

    PopupWindow changeSortPopUp;
    //List<MenuItemDishes> searchItems = new ArrayList<MenuItemDishes>();
   /* List<GetMasterAllMenuItems> temp_ll_MenuItemDishes;*/
   /* SearchItemAdapter searchItemAdapter;*/
    String value_check="",date_value="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;

        intent = getIntent();
        value_check = intent.getStringExtra("value");
        date_value = intent.getStringExtra("date");

      /*  searchItemAdapter = new SearchItemAdapter();*/
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Add Menu");
        initViews();
    }

    public void initViews()
    {


        line_menu_icon=(ImageView)findViewById(R.id.line_menu_icon);
        line_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer_layout.isDrawerOpen(GravityCompat.START))
                {
                    drawer_layout.closeDrawers();
                    if (changeSortPopUp != null) {
                        if (changeSortPopUp.isShowing()) {
                            changeSortPopUp.dismiss();
                            if (getCurrentFocus() != null) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                }
                else{
                    if (changeSortPopUp != null) {
                        if (changeSortPopUp.isShowing()) {
                            changeSortPopUp.dismiss();
                            if (getCurrentFocus() != null) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                    drawer_layout.openDrawer(GravityCompat.START);
                }
            }
        });







       /* temp_ll_MenuItemDishes=new ArrayList<GetMasterAllMenuItems>();*/

        add_menu_day_txt=(TextView)findViewById(R.id.add_menu_day_txt);

        String day_val=(getDate(date_value));

        if (day_val.equalsIgnoreCase("1"))
        {
            add_menu_day_txt.setText("BreakFast - Sunday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("2"))
        {
            add_menu_day_txt.setText("BreakFast - Monday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("3"))
        {
            add_menu_day_txt.setText("BreakFast - Tuesday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("4"))
        {
            add_menu_day_txt.setText("BreakFast - Wednesday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("5"))
        {
            add_menu_day_txt.setText("BreakFast - Thursday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("6"))
        {
            add_menu_day_txt.setText("BreakFast - Friday "+"("+date_value+")");
        }
        else if (day_val.equalsIgnoreCase("7"))
        {
            add_menu_day_txt.setText("BreakFast -Saturday "+"("+date_value+")");
        }


        add_menu_txt=(TextView)findViewById(R.id.add_menu_txt);
        submit_add_menu=(TextView)findViewById(R.id.submit_add_menu);
        menu_ed_txt=(EditText)findViewById(R.id.menu_ed_txt);
        linear=(LinearLayout)findViewById(R.id.linear);

        add_menu_list=(ListView)findViewById(R.id.add_menu_list);
        add_menu_list.setDivider(null);

      /*  searchItems = new MenuCalendarManager(AddMenuCheffActivityBreakFast.this).getMasterAllMenuItemses();
        Log.i("respo_MasterAllMenusize", ""+searchItems.size());

        temp_ll_MenuItemDishes.clear();
        for(int x=0;x<searchItems.size();x++)
        {
            temp_ll_MenuItemDishes.add(searchItems.get(x));
        }*/

        Log.i("respo_value_check", ""+value_check);
        if (value_check.equalsIgnoreCase("ed"))
        {
            Log.i("respo_ed_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed2"))
        {
            Log.i("respo_ed2_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed3"))
        {
            Log.i("respo_ed3_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed4"))
        {
            Log.i("respo_ed4_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed5"))
        {
            Log.i("respo_ed5_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed6"))
        {
            Log.i("respo_ed6_size", ""+Constant.ListElementsFinal_temp.size());
            try
            {
                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (value_check.equalsIgnoreCase("ed7"))
        {
            Log.i("respo_ed7_size", ""+Constant.ListElementsFinal_temp.size());
            try {

                if (Constant.ListElementsFinal_temp.size() != 0)
                {
                    if (Constant.ListElementsFinal_temp.size() == 1)
                    {
                        if (Constant.ListElementsFinal_temp.get(0) != null
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                        {
                            add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                        }
                    }
                    else
                    {
                        add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    /*======================add menu===============================*/

        add_menu_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!menu_ed_txt.getText().toString().trim().equalsIgnoreCase("")) {
                    if (getCurrentFocus() != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }



                    menu_title = "";
                    menu_title = menu_ed_txt.getText().toString().trim();

                    if (value_check.equalsIgnoreCase("ed"))
                    {
                        Log.i("respo_ed_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed2"))
                    {
                        Log.i("respo_ed2_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed2_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed2_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed2_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed3"))
                    {
                        Log.i("respo_ed3_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed3_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed3_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed3_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed4"))
                    {
                        Log.i("respo_ed4_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed4_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed4_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed4_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed5"))
                    {
                        Log.i("respo_ed5_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed5_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed5_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed5_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed6"))
                    {
                        Log.i("respo_ed6_size_before", "" + Constant.ListElementsFinal_temp.size());
                        try {

                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed6_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed6_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed6_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (value_check.equalsIgnoreCase("ed7"))
                    {
                        Log.i("respo_ed7_size_before", ""+Constant.ListElementsFinal_temp.size());
                        try {


                            if (Constant.ListElementsFinal_temp.size() == 1)
                            {
                                if (Constant.ListElementsFinal_temp.get(0) != null
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase("null")
                                        && !Constant.ListElementsFinal_temp.get(0).equalsIgnoreCase(" "))
                                {
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed7_size_after1", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                    menu_ed_txt.setText("");
                                }
                                else
                                {
                                    Constant.ListElementsFinal_temp.clear();
                                    Constant.ListElementsFinal_temp.add(menu_title);
                                    Log.i("respo_ed7_size_after2", "" + Constant.ListElementsFinal_temp.size());
                                    add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext, Constant.ListElementsFinal_temp, add_menu_list, value_check));
                                    menu_ed_txt.setText("");
                                }
                            }
                            else
                            {
                                Constant.ListElementsFinal_temp.add(menu_title);
                                Log.i("respo_ed7_size_after3", "" + Constant.ListElementsFinal_temp.size());
                                add_menu_list.setAdapter(new AddMenuAdapterBreakFast(mContext,Constant.ListElementsFinal_temp,add_menu_list,value_check));
                                menu_ed_txt.setText("");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                else
                {
                   // Toast.makeText(mContext, "Please write menu!", Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Please write menu!", Snackbar.LENGTH_LONG)
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


          /*   ===============ADD menu FINISH====================*/


     /*   ===============submit menu final====================*/

        submit_add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (Constant.ListElementsMenutitle.size() != 0)
                {
                    Toast.makeText(mContext, "Menu has been added succesfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Please add menu!", Toast.LENGTH_SHORT).show();
                }*/

                Log.i("respo_value_check", "" + value_check);
                if (value_check.equalsIgnoreCase("ed")) {
                    Log.i("respo_ed_size", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++)
                            {
                                Constant.ListElementsMenutitle.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed2")) {
                    Log.i("respo_ed2_size", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed2_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle2.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle2.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle2.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed3")) {
                    Log.i("respo_ed3_size", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed3_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle3.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle3.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle3.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed4")) {
                    Log.i("respo_ed4_size", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed4_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle4.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle4.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle4.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed5")) {
                    Log.i("respo_ed5_size", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed5_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle5.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle5.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle5.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed6")) {
                    Log.i("respo_ed6_size_submit", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed6_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle6.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle6.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle6.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (value_check.equalsIgnoreCase("ed7")) {
                    Log.i("respo_ed7_size_submit", "" + Constant.ListElementsFinal_temp.size());
                    try {
                        if (Constant.ListElementsFinal_temp.size() != 0) {
                            Log.i("respo_ed7_size_submit", "" + Constant.ListElementsFinal_temp.size());
                            Constant.ListElementsMenutitle7.clear();
                            for (int i = 0; i < Constant.ListElementsFinal_temp.size(); i++) {
                                Constant.ListElementsMenutitle7.add(Constant.ListElementsFinal_temp.get(i));
                            }
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        } else {
                            Constant.ListElementsMenutitle7.clear();
                            Constant.ListElementsFinal_temp.clear();
                            Intent intent = new Intent(AddMenuCheffActivityBreakFast.this, MenuActivityCheff.class);
                            intent.putExtra("tabposition","0");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });


 /*   ===============Submit menu FINISH====================*/


        /*{
            menu_ed_txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i("beforeTextChanged", "beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i("onTextChanged", "onTextChanged");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i("afterTextChanged", "afterTextChanged");

                    if (s.toString().length() >= 3) {
                        searchItems.clear();
                        for (GetMasterAllMenuItems user : temp_ll_MenuItemDishes) {

                            //if (user.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            if (Pattern.compile(Pattern.quote(s.toString()), Pattern.CASE_INSENSITIVE).matcher(user.getMenu_title()).find()) {
                                //Log.i("name marh", "" + user.getName());
                                searchItems.add(user);
                            }
                        }
                        Log.i("searchItems", "" + searchItems.toString());

                        if (changeSortPopUp != null) {
                            if (!changeSortPopUp.isShowing()) {
                                if (searchItems.size() > 0) {
                                    showSearchList();
                                } else
                                    changeSortPopUp.dismiss();
                            } else {
                                searchItemAdapter.notifyDataSetChanged();
                                if(searchItems.size()==0)
                                {
                                    changeSortPopUp.dismiss();
                                }

                            }
                        } else {
                            if (searchItems.size() > 0)
                            {
                                showSearchList();
                            }

                        }
                    } else {
                        if (changeSortPopUp != null)
                            changeSortPopUp.dismiss();
                    }
                }
            });
        }*/

    }


   /* public void showSearchList()
    {

        changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.searchlistview, null);
        final ListView searhitemlist = (ListView) layout.findViewById(R.id.searhitemlist);
        searchItemAdapter = new SearchItemAdapter();
        searhitemlist.setAdapter(searchItemAdapter);




        Rect rc = new Rect();
        menu_ed_txt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        menu_ed_txt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // Creating the PopupWindow

        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(false);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        *//*int OFFSET_X = 15;
        int OFFSET_Y = 40;*//*

        int OFFSET_X,OFFSET_Y;

       *//*  OFFSET_X = -20;
         OFFSET_Y = 95;*//*

         OFFSET_X = 15;
         OFFSET_Y = 40;

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());


        if (android.os.Build.VERSION.SDK_INT >=24) {
            OFFSET_X = 0;
            OFFSET_Y = 0;

            // Displaying the popup at the specified location, + offsets.
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, 60,400);
        }
        else
        {
            // Displaying the popup at the specified location, + offsets.
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        }





    }*/



 /*   public class SearchItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            HolderView holder;
            if (convertView == null) {
                holder = new HolderView();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitem, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.searchitemlay = (LinearLayout) convertView.findViewById(R.id.searchitemlay);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }

            final int pos = position;

            String name = searchItems.get(pos).getMenu_title();
            holder.name.setText(name);

            holder.searchitemlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                 // Toast.makeText(getApplicationContext(),"val="+searchItems.get(pos).getMenu_title(),Toast.LENGTH_LONG).show();

                    if (getCurrentFocus() != null)
                    {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                    menu_ed_txt.setText("");
                    menu_ed_txt.append(searchItems.get(pos).getMenu_title());
                    changeSortPopUp.dismiss();
                }
            });

            return convertView;
        }

        public class HolderView {

            TextView name;
            LinearLayout searchitemlay;


        }
    }*/

    String getDate(String dateStr){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            return String.valueOf(day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    public void onBackPressed()
    {
        Constant.ListElementsFinal_temp.clear();
        Intent intent=new Intent(AddMenuCheffActivityBreakFast.this,MenuActivityCheff.class);
        intent.putExtra("tabposition","0");
        startActivity(intent);
        finish();
    }




}

