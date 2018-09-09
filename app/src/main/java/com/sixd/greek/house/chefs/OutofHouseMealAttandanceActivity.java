package com.sixd.greek.house.chefs;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sixd.greek.house.chefs.Cheff.OutHouseNumbersActivity;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.CravingManager;
import com.sixd.greek.house.chefs.manager.LatePlateManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dao_db.AllLatePlateChefList;
import dao_db.CurrentWeekDinner;
import dao_db.CurrentWeekLunch;
import dao_db.NextWeekDinner;
import dao_db.NextWeekLunch;

/**
 * Created by Praveen on 20-Jul-17.
 */

public class OutofHouseMealAttandanceActivity extends HeaderActivty implements View.OnClickListener{

    TextView headr_outhouse,headr_outhouse1;

    LinearLayout current_week_ll,next_week_ll;
    String jsonStr="";

    ImageView ch1,ch2,ch3,ch4,ch5,ch6,ch7,
            ch11,ch21,ch31,ch41,ch51,ch61,ch71;
    TextView request_current_week,request_next_for_week,request_next_for_semester;
    Spinner week_sel;
    String []week_arr={"Current week","Next week"};
    String[] days = new String[7];
    SimpleDateFormat formatter;
    String strDate11="";
    String dayOfTheWeek;
    int hour;
    String timeString="",finalcurrenttime="";

    ArrayList<String>arrayList_lunch_next_week;
    ArrayList<String>arrayList_dinner_next_week;

    ArrayList<String>arrayList_lunch_cur_week;
    ArrayList<String>arrayList_dinner_cur_week;

    String week_type="",request_type="",spinner_item_val="",current_week_array="",next_week_array="",request_date ="",student_type ="out_house";
    LinearLayout linear;


    List<CurrentWeekLunch> getCurrentWeekLunches;
    List<CurrentWeekDinner> getCurrentWeekDinners;
    List<NextWeekLunch> getNextWeekLunches;
    List<NextWeekDinner> getNextWeekDinners;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHeaderTitele("Out of House Meal Attendance");

        linear=(LinearLayout)findViewById(R.id.linear);
        headr_outhouse1 = (TextView)findViewById(R.id.headr_outhouse1);
        String simple1 = "Please select next week option from drop down in case you want to submit request for the entire semester.";
        String colored1 = "*";
        SpannableStringBuilder builder1 = new SpannableStringBuilder(colored1+simple1);

        builder1.setSpan(new ForegroundColorSpan(Color.BLACK), simple1.length(), builder1.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        headr_outhouse1.setText(builder1);


        headr_outhouse = (TextView)findViewById(R.id.headr_outhouse);
        String simple = "Only to be filled by Out of house students.";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder(colored+simple);

        builder.setSpan(new ForegroundColorSpan(Color.BLACK), simple.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        headr_outhouse.setText(builder);

        week_sel=(Spinner)findViewById(R.id.week_sel);
        week_sel.setBackground(getResources().getDrawable(R.drawable.spinner_bg));

       // initLateplateDetailsForInhouseAndOutHouseAPI();

    }

    public void initViews()
    {



        current_week_ll=(LinearLayout)findViewById(R.id.current_week_ll);
        next_week_ll=(LinearLayout)findViewById(R.id.next_week_ll);

        ch1=(ImageView)findViewById(R.id.ch1);
        ch2=(ImageView)findViewById(R.id.ch2);
        ch3=(ImageView)findViewById(R.id.ch3);
        ch4=(ImageView)findViewById(R.id.ch4);
        ch5=(ImageView)findViewById(R.id.ch5);
        ch6=(ImageView)findViewById(R.id.ch6);
        ch7=(ImageView)findViewById(R.id.ch7);
        ch11=(ImageView)findViewById(R.id.ch11);
        ch21=(ImageView)findViewById(R.id.ch21);
        ch31=(ImageView)findViewById(R.id.ch31);
        ch41=(ImageView)findViewById(R.id.ch41);
        ch51=(ImageView)findViewById(R.id.ch51);
        ch61=(ImageView)findViewById(R.id.ch61);
        ch71=(ImageView)findViewById(R.id.ch71);
        ch71=(ImageView)findViewById(R.id.ch71);
        request_current_week=(TextView) findViewById(R.id.request_current_week);
        request_next_for_week=(TextView) findViewById(R.id.request_next_for_week);
        request_next_for_semester=(TextView) findViewById(R.id.request_next_for_semester);

        ch1.setOnClickListener(this);
        ch2.setOnClickListener(this);
        ch3.setOnClickListener(this);
        ch4.setOnClickListener(this);
        ch5.setOnClickListener(this);
        ch6.setOnClickListener(this);
        ch7.setOnClickListener(this);
        ch11.setOnClickListener(this);
        ch21.setOnClickListener(this);
        ch31.setOnClickListener(this);
        ch41.setOnClickListener(this);
        ch51.setOnClickListener(this);
        ch61.setOnClickListener(this);
        ch71.setOnClickListener(this);

        request_current_week.setOnClickListener(this);
        request_next_for_week.setOnClickListener(this);
        request_next_for_semester.setOnClickListener(this);

/*        week_sel=(Spinner)findViewById(R.id.week_sel);
        week_sel.setBackground(getResources().getDrawable(R.drawable.spinner_bg));*/

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,R.layout.list_v,
                week_arr);
        week_sel.setAdapter(adapter);
        week_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                getCurrentWeekLunches = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).
                        getCurrentWeekLunchList();
                getCurrentWeekDinners = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).
                        getCurrentWeekDinnersList();
                getNextWeekLunches = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).
                        getNextWeekLunchList();
                getNextWeekDinners = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).
                        getNextWeekDinnerList();

                Log.i("respo_CurWeekLunch_size", "" + getCurrentWeekLunches.size());
                Log.i("respo_CurWeekDinr_size", "" + getCurrentWeekDinners.size());
                Log.i("respo_NextWekLunch_size", "" + getNextWeekLunches.size());
                Log.i("respo_NextWeekDinr_size", "" + getNextWeekDinners.size());

                try {
                    arrayList_lunch_cur_week.clear();
                    arrayList_dinner_cur_week.clear();
                    arrayList_lunch_next_week.clear();
                    arrayList_dinner_next_week.clear();
                    spinner_item_val = adapter.getItem(i);

                    if (spinner_item_val.equalsIgnoreCase("Current week"))
                    {

                        current_week_ll.setVisibility(View.VISIBLE);
                        next_week_ll.setVisibility(View.GONE);

                        week_type="current_week";

                        arrayList_lunch_cur_week.clear();
                        arrayList_dinner_cur_week.clear();
                        arrayList_lunch_next_week.clear();
                        arrayList_dinner_next_week.clear();

                        ch1.setSelected(false);
                        ch2.setSelected(false);
                        ch3.setSelected(false);
                        ch4.setSelected(false);
                        ch5.setSelected(false);
                        ch6.setSelected(false);
                        ch7.setSelected(false);
                        ch11.setSelected(false);
                        ch21.setSelected(false);
                        ch31.setSelected(false);
                        ch41.setSelected(false);
                        ch51.setSelected(false);
                        ch61.setSelected(false);
                        ch71.setSelected(false);

                        if(dayOfTheWeek.equalsIgnoreCase("Sunday") ){
                            //Toast.makeText(getApplicationContext(), "Sundaycurrent", Toast.LENGTH_LONG).show();
                            int weekDaysCount=0;
                            weekDaysCount--;
                            Calendar now1 = Calendar.getInstance();
                            Calendar now = (Calendar) now1.clone();

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            //String [] days = new String[7];
                            int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
                            now.add(Calendar.WEEK_OF_YEAR , weekDaysCount);
                            now.add(Calendar.DAY_OF_MONTH , delta);
                            for (int x = 0; x < 7; x++)
                            {
                                days[x] = format.format(now.getTime());
                                now.add(Calendar.DAY_OF_MONTH , 1);
                            }


                            Log.i("imprcalendarCURRENTWEAK",""+Arrays.toString(days));

                        }
                        else {

                            //Toast.makeText(getApplicationContext(), "monday", Toast.LENGTH_LONG).show();

                            /* ============== Current week all dates================*/
                            Calendar now = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
                            now.add(Calendar.DAY_OF_MONTH, delta );
                            for (int x = 0; x < 7; x++)
                            {
                                days[x] = format.format(now.getTime());
                                now.add(Calendar.DAY_OF_MONTH, 1);
                            }
                            System.out.println(Arrays.toString(days));
                            //Log.i("first",""+days[0]);
                            Log.i("calendarCURRENTWEAK",""+Arrays.toString(days));

                        }


                        Log.i("respo_curweek_all_dates",""+Arrays.toString(days));

                        ////////////////////////// Logic Current/////////////////

                        if (getCurrentWeekLunches.get(0).getRequest_date().toString().trim().equalsIgnoreCase(days[0].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(0).getIs_checked().toString().trim();
                            Log.i("respo__lunch_mon",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch1.setSelected(false);
                            }
                            else
                            {
                                ch1.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(1).getRequest_date().toString().trim().equalsIgnoreCase(days[1].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(1).getIs_checked().toString().trim();
                            Log.i("respo__lunch_tue",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch2.setSelected(false);
                            }
                            else
                            {
                                ch2.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(2).getRequest_date().toString().trim().equalsIgnoreCase(days[2].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(2).getIs_checked().toString().trim();
                            Log.i("respo__lunch_wed",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch3.setSelected(false);
                            }
                            else
                            {
                                ch3.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(3).getRequest_date().toString().trim().equalsIgnoreCase(days[3].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(3).getIs_checked().toString().trim();
                            Log.i("respo__lunch_thu",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch4.setSelected(false);
                            }
                            else
                            {
                                ch4.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(4).getRequest_date().toString().trim().equalsIgnoreCase(days[4].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(4).getIs_checked().toString().trim();
                            Log.i("respo__lunch_fri",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch5.setSelected(false);
                            }
                            else
                            {
                                ch5.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(5).getRequest_date().toString().trim().equalsIgnoreCase(days[5].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(5).getIs_checked().toString().trim();
                            Log.i("respo__lunch_sat",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch6.setSelected(false);
                            }
                            else
                            {
                                ch6.setSelected(true);
                            }
                        }
                        if (getCurrentWeekLunches.get(6).getRequest_date().toString().trim().equalsIgnoreCase(days[6].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekLunches.get(6).getIs_checked().toString().trim();
                            Log.i("respo__lunch_sun",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch7.setSelected(false);
                            }
                            else
                            {
                                ch7.setSelected(true);
                            }
                        }

                        if (getCurrentWeekDinners.get(0).getRequest_date().toString().trim().equalsIgnoreCase(days[0].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(0).getIs_checked().toString().trim();
                            Log.i("respo__dinner_mon",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch11.setSelected(false);
                            }
                            else
                            {
                                ch11.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(1).getRequest_date().toString().trim().equalsIgnoreCase(days[1].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(1).getIs_checked().toString().trim();
                            Log.i("respo__dinner_tue",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch21.setSelected(false);
                            }
                            else
                            {
                                ch21.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(2).getRequest_date().toString().trim().equalsIgnoreCase(days[2].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(2).getIs_checked().toString().trim();
                            Log.i("respo__dinner_wed",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch31.setSelected(false);
                            }
                            else
                            {
                                ch31.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(3).getRequest_date().toString().trim().equalsIgnoreCase(days[3].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(3).getIs_checked().toString().trim();
                            Log.i("respo__dinner_thu",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch41.setSelected(false);
                            }
                            else
                            {
                                ch41.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(4).getRequest_date().toString().trim().equalsIgnoreCase(days[4].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(4).getIs_checked().toString().trim();
                            Log.i("respo__dinner_fri",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch51.setSelected(false);
                            }
                            else
                            {
                                ch51.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(5).getRequest_date().toString().trim().equalsIgnoreCase(days[5].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(5).getIs_checked().toString().trim();
                            Log.i("respo__dinner_sat",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch61.setSelected(false);
                            }
                            else
                            {
                                ch61.setSelected(true);
                            }
                        }
                        if (getCurrentWeekDinners.get(6).getRequest_date().toString().trim().equalsIgnoreCase(days[6].toString().trim()))
                        {
                            String ischecked_val=getCurrentWeekDinners.get(6).getIs_checked().toString().trim();
                            Log.i("respo__dinner_sun",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch71.setSelected(false);
                            }
                            else
                            {
                                ch71.setSelected(true);
                            }
                        }

                        //////if condition closed
                    }
                    else
                    {
                        //next week start spinner

                        week_type="next_week";

                        current_week_ll.setVisibility(View.GONE);
                        next_week_ll.setVisibility(View.VISIBLE);

                        arrayList_lunch_cur_week.clear();
                        arrayList_dinner_cur_week.clear();

                        arrayList_dinner_next_week.clear();
                        arrayList_lunch_next_week.clear();
                        ch1.setSelected(false);
                        ch2.setSelected(false);
                        ch3.setSelected(false);
                        ch4.setSelected(false);
                        ch5.setSelected(false);
                        ch6.setSelected(false);
                        ch7.setSelected(false);
                        ch11.setSelected(false);
                        ch21.setSelected(false);
                        ch31.setSelected(false);
                        ch41.setSelected(false);
                        ch51.setSelected(false);
                        ch61.setSelected(false);
                        ch71.setSelected(false);


                        if(dayOfTheWeek.equalsIgnoreCase("Sunday")){

                            //Toast.makeText(getApplicationContext(), "Sundayfuture", Toast.LENGTH_LONG).show();

                            Calendar now = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            //String [] days = new String[7];
                            int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
                            now.add(Calendar.DAY_OF_MONTH , delta);
                            for (int x = 0; x < 7; x++)
                            {
                                days [x] = format.format(now.getTime());
                                now.add(Calendar.DAY_OF_MONTH , 1);
                            }
                            Log.i("imprNEXTWEAK",""+Arrays.toString(days));

                        }
                        else {

                            int weekDaysCount=0;
                            weekDaysCount++;
                            Calendar now1 = Calendar.getInstance();
                            Calendar now = (Calendar) now1.clone();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            //String [] days = new String[7];
                            int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
                            now.add(Calendar.WEEK_OF_YEAR , weekDaysCount);
                            now.add(Calendar.DAY_OF_MONTH , delta);
                            for (int k = 0; k < 7; k++)
                            {
                                days [k] = format.format(now.getTime());
                                now.add(Calendar.DAY_OF_MONTH , 1);
                            }

                            //System.out.println(Arrays.toString(days));
                            Log.i("calendarNEXTWEAK",""+Arrays.toString(days));

                        }

                      /* ============== current week all dates================*/

                        Log.i("respo_curweek_all_dates",""+Arrays.toString(days));

                        ////////////////////////// Logic Current/////////////////

                        if (getNextWeekLunches.get(0).getRequest_date().toString().trim().equalsIgnoreCase(days[0].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(0).getIs_checked().toString().trim();
                            Log.i("respo_mon",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch1.setSelected(false);
                            }
                            else
                            {
                                ch1.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(1).getRequest_date().toString().trim().equalsIgnoreCase(days[1].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(1).getIs_checked().toString().trim();
                            Log.i("respo_tue",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch2.setSelected(false);
                            }
                            else
                            {
                                ch2.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(2).getRequest_date().toString().trim().equalsIgnoreCase(days[2].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(2).getIs_checked().toString().trim();
                            Log.i("respo_wed",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch3.setSelected(false);
                            }
                            else
                            {
                                ch3.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(3).getRequest_date().toString().trim().equalsIgnoreCase(days[3].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(3).getIs_checked().toString().trim();
                            Log.i("respo_thu",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch4.setSelected(false);
                            }
                            else
                            {
                                ch4.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(4).getRequest_date().toString().trim().equalsIgnoreCase(days[4].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(4).getIs_checked().toString().trim();
                            Log.i("respo_fri",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch5.setSelected(false);
                            }
                            else
                            {
                                ch5.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(5).getRequest_date().toString().trim().equalsIgnoreCase(days[5].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(5).getIs_checked().toString().trim();
                            Log.i("respo_sat",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch6.setSelected(false);
                            }
                            else
                            {
                                ch6.setSelected(true);
                            }
                        }
                        if (getNextWeekLunches.get(6).getRequest_date().toString().trim().equalsIgnoreCase(days[6].toString().trim()))
                        {
                            String ischecked_val=getNextWeekLunches.get(6).getIs_checked().toString().trim();
                            Log.i("respo_sun",""+ischecked_val);
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch7.setSelected(false);
                            }
                            else
                            {
                                ch7.setSelected(true);
                            }
                        }

                        if (getNextWeekDinners.get(0).getRequest_date().toString().trim().equalsIgnoreCase(days[0].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(0).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch11.setSelected(false);
                            }
                            else
                            {
                                ch11.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(1).getRequest_date().toString().trim().equalsIgnoreCase(days[1].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(1).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch21.setSelected(false);
                            }
                            else
                            {
                                ch21.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(2).getRequest_date().toString().trim().equalsIgnoreCase(days[2].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(2).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch31.setSelected(false);
                            }
                            else
                            {
                                ch31.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(3).getRequest_date().toString().trim().equalsIgnoreCase(days[3].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(3).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch41.setSelected(false);
                            }
                            else
                            {
                                ch41.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(4).getRequest_date().toString().trim().equalsIgnoreCase(days[4].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(4).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch51.setSelected(false);
                            }
                            else
                            {
                                ch51.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(5).getRequest_date().toString().trim().equalsIgnoreCase(days[5].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(5).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch61.setSelected(false);
                            }
                            else
                            {
                                ch61.setSelected(true);
                            }
                        }
                        if (getNextWeekDinners.get(6).getRequest_date().toString().trim().equalsIgnoreCase(days[6].toString().trim()))
                        {
                            String ischecked_val=getNextWeekDinners.get(6).getIs_checked().toString().trim();
                            if(ischecked_val.equalsIgnoreCase("0"))
                            {
                                ch71.setSelected(false);
                            }
                            else
                            {
                                ch71.setSelected(true);
                            }
                        }
                        //else condition end
                    }

                    //Toast.makeText(getApplicationContext(), "spinner_item_val=" + spinner_item_val, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ch1:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch1.isSelected()){
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch1.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[0]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{
                        try {
                            //Toast.makeText(OutofHouseMealAttandanceActivity.this,"hiunselected",Toast.LENGTH_LONG).show();

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);

                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }
                            else {

                                if (date1.compareTo(date2)<=0)
                                {

                                    ch1.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[0]);
                                    //System.out.println("date2 is Greater than my date1");


                                }

                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {

                    if(ch1.isSelected()){
                        ch1.setSelected(false);
                        arrayList_lunch_next_week.remove(days[0]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch1.setSelected(true);
                                arrayList_lunch_next_week.add(days[0]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
                break;

            case R.id.ch2:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch2.isSelected()){

                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch2.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[1]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }




                    else{

                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);


                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }
                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("tuesday",""+days[1]);
                                    ch2.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[1]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {

                    if(ch2.isSelected()){
                        ch2.setSelected(false);
                        arrayList_lunch_next_week.remove(days[1]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch2.setSelected(true);
                                arrayList_lunch_next_week.add(days[1]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case R.id.ch3:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch3.isSelected())
                    {

                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch3.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[2]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{

                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);


                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }
                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("wednesday",""+days[2]);
                                    ch3.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[2]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch3.isSelected()){
                        ch3.setSelected(false);
                        arrayList_lunch_next_week.remove(days[2]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch3.setSelected(true);
                                arrayList_lunch_next_week.add(days[2]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case R.id.ch4:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch4.isSelected()){


                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);



                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch4.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[3]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);

                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }
                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("thursday",""+days[3]);
                                    ch4.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[3]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                else
                {

                    if(ch4.isSelected()){
                        ch4.setSelected(false);
                        arrayList_lunch_next_week.remove(days[3]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch4.setSelected(true);
                                arrayList_lunch_next_week.add(days[3]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case R.id.ch5:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch5.isSelected()){


                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);


                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    arrayList_lunch_cur_week.remove(days[4]);
                                    ch5.setSelected(false);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{

                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);

                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }
                            else{
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("friday",""+days[4]);
                                    ch5.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[4]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                else
                {
                    if(ch5.isSelected()){
                        ch5.setSelected(false);
                        arrayList_lunch_next_week.remove(days[4]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch5.setSelected(true);
                                arrayList_lunch_next_week.add(days[4]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
                break;

            case R.id.ch6:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch6.isSelected()){
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);


                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch6.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[5]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);


                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }

                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("saturday",""+days[5]);
                                    ch6.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[5]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {

                    if(ch6.isSelected()){
                        ch6.setSelected(false);
                        arrayList_lunch_next_week.remove(days[5]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch6.setSelected(true);
                                arrayList_lunch_next_week.add(days[5]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case R.id.ch7:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch7.isSelected()){

                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry today lunch is over!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch7.setSelected(false);
                                    arrayList_lunch_cur_week.remove(days[6]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{

                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);



                            if(date1.compareTo(date2)==0){


                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry lunch is over for this date!",Toast.LENGTH_LONG).show();

                            }

                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    Log.i("sunday",""+days[6]);
                                    ch7.setSelected(true);
                                    arrayList_lunch_cur_week.add(days[6]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
                else
                {
                    if(ch7.isSelected()){
                        ch7.setSelected(false);
                        arrayList_lunch_next_week.remove(days[6]);
                    }

                    else {
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2) <= 0) {
                                ch7.setSelected(true);
                                arrayList_lunch_next_week.add(days[6]);
                                //System.out.println("date2 is Greater than my date1");
                            } else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this, "Sorry date is over!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;


            case R.id.ch11:

                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch11.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0 )
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  )
                                    {

                                        ch11.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[0]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 )
                                {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch11.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[0]);

                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0 )
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  )
                                    {

                                        ch11.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[0]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    ch11.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[0]);
                                    //System.out.println("date2 is Greater than my date1");

                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch11.isSelected()){
                        ch11.setSelected(false);
                        arrayList_dinner_next_week.remove(days[0]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[0];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch11.setSelected(true);
                                arrayList_dinner_next_week.add(days[0]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                }
                break;

            case R.id.ch21:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch21.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);

                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch21.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[1]);
                                        //System.out.println("date2 is Greater than my date1");

                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch21.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[1]);

                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch21.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[1]);
                                        //System.out.println("date2 is Greater than my date1");

                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }
                            else {

                                if (date1.compareTo(date2)<=0)
                                {
                                    ch21.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[1]);
                                    //System.out.println("date2 is Greater than my date1");

                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch21.isSelected()){
                        ch21.setSelected(false);
                        arrayList_dinner_next_week.remove(days[1]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[1];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch21.setSelected(true);
                                arrayList_dinner_next_week.add(days[1]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

            case R.id.ch31:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch31.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);
                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch31.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[2]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }


                                }                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch31.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[2]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch31.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[2]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }


                                }

                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }
                            else {

                                if (date1.compareTo(date2)<=0)
                                {
                                    ch31.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[2]);
                                    //System.out.println("date2 is Greater than my date1");

                                }

                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch31.isSelected()){
                        ch31.setSelected(false);
                        arrayList_dinner_next_week.remove(days[2]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[2];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch31.setSelected(true);
                                arrayList_dinner_next_week.add(days[2]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

            case R.id.ch41:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch41.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);
                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {

                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch41.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[3]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }


                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch41.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[3]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {

                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch41.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[3]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }


                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else {

                                if (date1.compareTo(date2)<=0)
                                {

                                    ch41.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[3]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch41.isSelected()){
                        ch41.setSelected(false);
                        arrayList_dinner_next_week.remove(days[3]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[3];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch41.setSelected(true);
                                arrayList_dinner_next_week.add(days[3]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

            case R.id.ch51:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch51.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);
                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch51.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[4]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch51.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[4]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch51.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[4]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else {

                                if (date1.compareTo(date2)<=0)
                                {
                                    ch51.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[4]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch51.isSelected())
                    {
                        ch51.setSelected(false);
                        arrayList_dinner_next_week.remove(days[4]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[4];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {

                                /*if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                        ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                        ||timeString.equalsIgnoreCase("7AM")  )*/
                                {

                                    ch51.setSelected(true);
                                    arrayList_dinner_next_week.add(days[4]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                               /* else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                }*/



                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;

            case R.id.ch61:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch61.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);
                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {

                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch61.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[5]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch61.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[5]);
                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {

                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch61.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[5]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    ch61.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[5]);
                                    //System.out.println("date2 is Greater than my date1");
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"sorry date is over",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch61.isSelected()){
                        ch61.setSelected(false);
                        arrayList_dinner_next_week.remove(days[5]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[5];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch61.setSelected(true);
                                arrayList_dinner_next_week.add(days[5]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;

            case R.id.ch71:
                if (spinner_item_val.equalsIgnoreCase("Current week"))
                {
                    if(ch71.isSelected())
                    {
                        try {
                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);
                            if (str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch71.setSelected(false);
                                        arrayList_dinner_cur_week.add(days[6]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                if(date1.compareTo(date2)>=0 ){
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry old date is over!",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    ch71.setSelected(false);
                                    arrayList_dinner_cur_week.remove(days[6]);

                                }
                            }


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);

                            if(str1.equalsIgnoreCase(str2))
                            {
                                if (date1.compareTo(date2)<=0)
                                {
                                    if(timeString.equalsIgnoreCase("1AM")||timeString.equalsIgnoreCase("2AM")||timeString.equalsIgnoreCase("3AM")
                                            ||timeString.equalsIgnoreCase("4AM") ||timeString.equalsIgnoreCase("5AM") ||timeString.equalsIgnoreCase("6AM")
                                            ||timeString.equalsIgnoreCase("7AM")  ){

                                        ch71.setSelected(true);
                                        arrayList_dinner_cur_week.add(days[6]);
                                        //System.out.println("date2 is Greater than my date1");
                                    }
                                    else {
                                        Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry dinner is over for this date!",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                if (date1.compareTo(date2)<=0)
                                {
                                    ch71.setSelected(true);
                                    arrayList_dinner_cur_week.add(days[6]);
                                    //System.out.println("date2 is Greater than my date1");

                                }
                                else {
                                    Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                                }

                            }


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    if(ch71.isSelected()){
                        ch71.setSelected(false);
                        arrayList_dinner_next_week.remove(days[6]);
                    }

                    else{
                        try {

                            String str1 = strDate11;
                            Date date1 = formatter.parse(str1);
                            String str2 = days[6];
                            Date date2 = formatter.parse(str2);
                            if (date1.compareTo(date2)<=0)
                            {
                                ch71.setSelected(true);
                                arrayList_dinner_next_week.add(days[6]);
                                //System.out.println("date2 is Greater than my date1");
                            }
                            else {
                                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Sorry date is over!",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;



            case R.id.request_next_for_semester:

            {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                JSONArray jsonArray2 = new JSONArray();

                JSONArray jsonArray3 = new JSONArray();
                JSONArray jsonArray4 = new JSONArray();
                JSONObject jsonObject1 = new JSONObject();

                Log.i("respo_lunc_curweek_size", "" + arrayList_lunch_cur_week.size());
                for (int i = 0; i < arrayList_lunch_cur_week.size(); i++) {
                    try {
                        jsonArray1.put(arrayList_lunch_cur_week.get(i));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("lunch", jsonArray1);
                    //Log.i("finalarray",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("respo_dinr_curweek_size",""+arrayList_dinner_cur_week.size());
                for (int j = 0; j < arrayList_dinner_cur_week.size(); j++) {
                    //Log.i("elememt",""+arrayList_dinner_cur_week.get(j));
                    try {
                        jsonArray2.put(arrayList_dinner_cur_week.get(j));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("dinner", jsonArray2);
                    Log.i("final_current_week", jsonObject.toString());
                    current_week_array = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Log.i("respo_lunc_nxtweek_size",""+arrayList_lunch_next_week.size());
                for (int i = 0; i < arrayList_lunch_next_week.size(); i++) {
                    try {
                        jsonArray3.put(arrayList_lunch_next_week.get(i));
                        //Log.i("completearray",jsonArray3.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_lunch_next_week.get(i));
                }
                try {
                    jsonObject1.put("lunch", jsonArray3);
                    //Log.i("finalarray",jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("reso_diner_nxtweek_size",""+arrayList_dinner_next_week.size());
                for (int i = 0; i < arrayList_dinner_next_week.size(); i++) {
                    try {
                        jsonArray4.put(arrayList_dinner_next_week.get(i));
                        //Log.i("completearray",jsonArray4.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_dinner_next_week.get(i));
                }
                try {
                    jsonObject1.put("dinner", jsonArray4);
                    Log.i("finalarrayfourth", jsonObject1.toString());
                    Log.i("final_next_week", jsonObject1.toString());
                    next_week_array = jsonObject1.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String val="is_weekly";

                submitForm(val);

            }


            break;


            case R.id.request_next_for_week:

            {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                JSONArray jsonArray2 = new JSONArray();

                JSONArray jsonArray3 = new JSONArray();
                JSONArray jsonArray4 = new JSONArray();
                JSONObject jsonObject1 = new JSONObject();

                Log.i("respo_lunc_curweek_size", "" + arrayList_lunch_cur_week.size());
                for (int i = 0; i < arrayList_lunch_cur_week.size(); i++) {
                    try {
                        jsonArray1.put(arrayList_lunch_cur_week.get(i));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("lunch", jsonArray1);
                    //Log.i("finalarray",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("respo_dinr_curweek_size",""+arrayList_dinner_cur_week.size());
                for (int j = 0; j < arrayList_dinner_cur_week.size(); j++) {
                    //Log.i("elememt",""+arrayList_dinner_cur_week.get(j));
                    try {
                        jsonArray2.put(arrayList_dinner_cur_week.get(j));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("dinner", jsonArray2);
                    Log.i("final_current_week", jsonObject.toString());
                    current_week_array = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Log.i("respo_lunc_nxtweek_size",""+arrayList_lunch_next_week.size());
                for (int i = 0; i < arrayList_lunch_next_week.size(); i++) {
                    try {
                        jsonArray3.put(arrayList_lunch_next_week.get(i));
                        //Log.i("completearray",jsonArray3.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_lunch_next_week.get(i));
                }
                try {
                    jsonObject1.put("lunch", jsonArray3);
                    //Log.i("finalarray",jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("reso_diner_nxtweek_size",""+arrayList_dinner_next_week.size());
                for (int i = 0; i < arrayList_dinner_next_week.size(); i++) {
                    try {
                        jsonArray4.put(arrayList_dinner_next_week.get(i));
                        //Log.i("completearray",jsonArray4.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_dinner_next_week.get(i));
                }
                try {
                    jsonObject1.put("dinner", jsonArray4);
                    Log.i("finalarrayfourth", jsonObject1.toString());
                    Log.i("final_next_week", jsonObject1.toString());
                    next_week_array = jsonObject1.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String val="is_onetime";
                submitForm(val);

            }

            break;




            case R.id.request_current_week:
            {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                JSONArray jsonArray2 = new JSONArray();

                JSONArray jsonArray3 = new JSONArray();
                JSONArray jsonArray4 = new JSONArray();
                JSONObject jsonObject1 = new JSONObject();

                Log.i("respo_lunc_curweek_size", "" + arrayList_lunch_cur_week.size());
                for (int i = 0; i < arrayList_lunch_cur_week.size(); i++) {
                    try {
                        jsonArray1.put(arrayList_lunch_cur_week.get(i));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("lunch", jsonArray1);
                    //Log.i("finalarray",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("respo_dinr_curweek_size",""+arrayList_dinner_cur_week.size());

                for (int j = 0; j < arrayList_dinner_cur_week.size(); j++)
                {
                    //Log.i("elememt",""+arrayList_dinner_cur_week.get(j));
                    try {
                        jsonArray2.put(arrayList_dinner_cur_week.get(j));
                        //Log.i("completearray",jsonArray1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("dinner", jsonArray2);
                    Log.i("final_current_week", jsonObject.toString());
                    current_week_array = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Log.i("respo_lunc_nxtweek_size",""+arrayList_lunch_next_week.size());
                for (int i = 0; i < arrayList_lunch_next_week.size(); i++) {
                    try {
                        jsonArray3.put(arrayList_lunch_next_week.get(i));
                        //Log.i("completearray",jsonArray3.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_lunch_next_week.get(i));
                }
                try {
                    jsonObject1.put("lunch", jsonArray3);
                    //Log.i("finalarray",jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("reso_diner_nxtweek_size",""+arrayList_dinner_next_week.size());
                for (int i = 0; i < arrayList_dinner_next_week.size(); i++) {
                    try {
                        jsonArray4.put(arrayList_dinner_next_week.get(i));
                        //Log.i("completearray",jsonArray4.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.i("elememt",""+arrayList_dinner_next_week.get(i));
                }
                try {
                    jsonObject1.put("dinner", jsonArray4);
                    Log.i("finalarrayfourth", jsonObject1.toString());
                    Log.i("final_next_week", jsonObject1.toString());
                    next_week_array = jsonObject1.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String val="is_onetime";
                submitForm(val);

            }
            break;

        }
    }


    private void submitForm(String requesttype_val)
    {

        request_type=requesttype_val;

        /////get all 14 checkbox balue and get all 14 dates 0-6 lunch and 0-6 dinner

     /*   *//*date 0 ki ch1 se binding hogi*//* ---  *//*date 6 ki ch7 se binding hogi*/
          /*   *//*date 0 ki ch11 se binding hogi*//* ---  *//*date 6 ki ch71 se binding hogi*/


        Log.i("imprcalendarCURRENTWEAK",""+Arrays.toString(days));

        String first_date=days[0].toString().trim();
        String second_date=days[1].toString().trim();
        String third_date=days[2].toString().trim();
        String fourth_date=days[3].toString().trim();
        String fifth_date=days[4].toString().trim();
        String six_date=days[5].toString().trim();
        String seven_date=days[6].toString().trim();

        String status1,status2,status3,status4,status5,status6,status7,status11,status21,status31,status41,status51,
                status61,status71;

        if(ch1.isSelected())
        {
            status1="1";
        }
        else
        {
            status1="0";
        }
        if(ch2.isSelected())
        {
            status2="1";
        }
        else
        {
            status2="0";
        }
        if(ch3.isSelected())
        {
            status3="1";
        }
        else
        {
            status3="0";
        }
        if(ch4.isSelected())
        {
            status4="1";
        }
        else
        {
            status4="0";
        }
        if(ch5.isSelected())
        {
            status5="1";
        }
        else
        {
            status5="0";
        }
        if(ch6.isSelected())
        {
            status6="1";
        }
        else
        {
            status6="0";
        }
        if(ch7.isSelected())
        {
            status7="1";
        }
        else
        {
            status7="0";
        }



     /*   =========================ch value dinner=======*/

        if(ch11.isSelected())
        {
            status11="1";
        }
        else
        {
            status11="0";
        }
        if(ch21.isSelected())
        {
            status21="1";
        }
        else
        {
            status21="0";
        }
        if(ch31.isSelected())
        {
            status31="1";
        }
        else
        {
            status31="0";
        }
        if(ch41.isSelected())
        {
            status41="1";
        }
        else
        {
            status41="0";
        }
        if(ch51.isSelected())
        {
            status51="1";
        }
        else
        {
            status51="0";
        }
        if(ch61.isSelected())
        {
            status61="1";
        }
        else
        {
            status61="0";
        }
        if(ch71.isSelected())
        {
            status71="1";
        }
        else
        {
            status71="0";
        }


        JSONObject lunch1 = new JSONObject();
        JSONObject lunch2 = new JSONObject();
        JSONObject lunch3 = new JSONObject();
        JSONObject lunch4 = new JSONObject();
        JSONObject lunch5 = new JSONObject();
        JSONObject lunch6 = new JSONObject();
        JSONObject lunch7 = new JSONObject();
        try {
            lunch1.put("date",""+first_date);
            lunch1.put("status",""+status1);

            lunch2.put("date",""+second_date);
            lunch2.put("status",""+status2);

            lunch3.put("date",""+third_date);
            lunch3.put("status",""+status3);

            lunch4.put("date",""+fourth_date);
            lunch4.put("status",""+status4);

            lunch5.put("date",""+fifth_date);
            lunch5.put("status",""+status5);

            lunch6.put("date",""+six_date);
            lunch6.put("status",""+status6);

            lunch7.put("date",""+seven_date);
            lunch7.put("status",""+status7);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(lunch1);
        jsonArray.put(lunch2);
        jsonArray.put(lunch3);
        jsonArray.put(lunch4);
        jsonArray.put(lunch5);
        jsonArray.put(lunch6);
        jsonArray.put(lunch7);



        JSONObject dinner1 = new JSONObject();
        JSONObject dinner2 = new JSONObject();
        JSONObject dinner3 = new JSONObject();
        JSONObject dinner4 = new JSONObject();
        JSONObject dinner5 = new JSONObject();
        JSONObject dinner6 = new JSONObject();
        JSONObject dinner7 = new JSONObject();
        try {
            dinner1.put("date",""+first_date);
            dinner1.put("status",""+status11);

            dinner2.put("date",""+second_date);
            dinner2.put("status",""+status21);

            dinner3.put("date",""+third_date);
            dinner3.put("status",""+status31);

            dinner4.put("date",""+fourth_date);
            dinner4.put("status",""+status41);

            dinner5.put("date",""+fifth_date);
            dinner5.put("status", ""+status51);

            dinner6.put("date",""+six_date);
            dinner6.put("status",""+status61);

            dinner7.put("date",""+seven_date);
            dinner7.put("status",""+status71);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.put(dinner1);
        jsonArray1.put(dinner2);
        jsonArray1.put(dinner3);
        jsonArray1.put(dinner4);
        jsonArray1.put(dinner5);
        jsonArray1.put(dinner6);
        jsonArray1.put(dinner7);
        try {
            JSONObject mainObj = new JSONObject();
            mainObj.put("lunch",jsonArray);
            mainObj.put("dinner",jsonArray1);

            jsonStr = mainObj.toString();
            System.out.println("jsonString: "+jsonStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        request_date=jsonStr.toString().trim();


  /*   ================make array==================*/

        //last

        if (spinner_item_val.equalsIgnoreCase("Current week"))
        {
           /* if(arrayList_lunch_cur_week.size()>0||arrayList_dinner_cur_week.size()>0)*/
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
                    new LatePlateManager(mContext).initaddLatePlateAPI(week_type,student_type,request_type,request_date, new CallBackManager() {
                        @Override
                        public void onSuccess(String responce)
                        {
                            materialDialog.dismiss();
                            if(responce.equals("100")){
                                Toast.makeText(mContext,"Meal request updated successfully!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OutofHouseMealAttandanceActivity.this,HomeActivityStudent.class);
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
                        public void onFailure(String responce) {

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
           /* else
            {
                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Please select lunch or dinner atleast one",Toast.LENGTH_LONG).show();
            }*/
        }
        else
        {
           // if(arrayList_lunch_next_week.size()>0||arrayList_dinner_next_week.size()>0)
            {
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    //Service Call for late plate
                    materialDialog.show();
                    new LatePlateManager(mContext).initaddLatePlateAPI(week_type,student_type,request_type,request_date, new CallBackManager() {
                        @Override
                        public void onSuccess(String responce)
                        {
                            materialDialog.dismiss();
                            if(responce.equals("100"))
                            {
                                Toast.makeText(mContext,"Meal request updated successfully!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(OutofHouseMealAttandanceActivity.this,HomeActivityStudent.class);
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
                        public void onFailure(String responce) {

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
          /*  else
            {
                Toast.makeText(OutofHouseMealAttandanceActivity.this,"Please select lunch or dinner atleast one!",Toast.LENGTH_LONG).show();
            }*/
        }

    }



    /*private boolean serviceCall() {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            user_name = inputUsername.getText().toString();
            password = inputPassword.getText().toString();

                    new TaskLoginData().execute();

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
            *//*Toast.makeText(getApplicationContext(),"Please check your Internet Connection",Toast.LENGTH_LONG).show();*//*
        }


        return true;
    }*/
   /* private boolean validateUserName() {
        if (inputUsername.getText().toString().trim().isEmpty()) {
            inputLayoutUsername.setError(getString(R.string.err_msg_user));

            return false;
        } else {
            inputLayoutUsername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));

            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
*/



    public void initLateplateDetailsForInhouseAndOutHouseAPI()
    {
        String Is_outhouse_lateplate="out_house";

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new LatePlateManagerCheff(mContext).initLateplateDetailsForInhouseAndOutHouseAPI(Is_outhouse_lateplate,
                    new CallBackManager() {

                        @Override
                        public void onSuccess(String responce) {
                            materialDialog.dismiss();
                            if (responce.equals("100"))
                            {

                                getCurrentWeekLunches = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).getCurrentWeekLunchList();
                                getCurrentWeekDinners = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).getCurrentWeekDinnersList();
                                getNextWeekLunches = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).getNextWeekLunchList();
                                getNextWeekDinners = new LatePlateManagerCheff(OutofHouseMealAttandanceActivity.this).getNextWeekDinnerList();


                                initViews();


                                arrayList_lunch_cur_week=new ArrayList<String>();
                                arrayList_dinner_cur_week=new ArrayList<String>();
                                arrayList_lunch_next_week=new ArrayList<String>();
                                arrayList_dinner_next_week=new ArrayList<String>();
                                formatter= new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd//dd/MM/yyyy
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");   //dd-MM-yyyy
                                //String strDate = "Attendence status for : " + mdformat.format(calendar.getTime());
                                strDate11 = mdformat.format(calendar.getTime());
                                Log.i("todaydate",""+strDate11.toString());
                                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                                Date d = new Date();
                                dayOfTheWeek = sdf.format(d);
                                hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                                if (hour == 0) {
                                    timeString =  "12AM (Midnight)";
                                    Log.i("midnight",""+timeString);

                                } else if (hour < 12) {
                                    timeString = hour +"AM";
                                    Log.i("AM",""+timeString);
                                    finalcurrenttime=timeString;
                                    Log.i("timeString",finalcurrenttime);

                                }
                                else if (hour == 12) {
                                    timeString = "12PM (Noon)";
                                    Log.i("Noon",""+timeString);

                                } else {
                                    timeString = hour-12 +"PM";
                                    Log.i("PM",""+timeString);
                                    finalcurrenttime=timeString;

                                    Log.i("timeString",finalcurrenttime);

                                }

                                Log.i("dayOfTheWeek",""+dayOfTheWeek.toString());




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
                                    String error_content=""+ Constant.BASEURL + "&param=" +
                                            Constant.API.LATE_OUT_INFO_API+ "&Is_outhouse_lateplate=out_house"
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





    @Override
    public void onBackPressed()
    {
        try
        {
            arrayList_lunch_cur_week.clear();
            arrayList_dinner_cur_week.clear();
            arrayList_lunch_next_week.clear();
            arrayList_dinner_next_week.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent=new Intent(OutofHouseMealAttandanceActivity.this,HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
       // Toast.makeText(mContext,"Resume Called!",Toast.LENGTH_SHORT).show();
        initLateplateDetailsForInhouseAndOutHouseAPI();
    }

}
