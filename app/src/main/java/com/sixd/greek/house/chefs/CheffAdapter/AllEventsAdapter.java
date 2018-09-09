package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sixd.greek.house.chefs.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao_db.AllEvents;
import dao_db.MenuItemDishes;



public class AllEventsAdapter extends BaseAdapter {
    Context mcontext;
    List<AllEvents> temp_ll_AllEvents;

    public AllEventsAdapter(Context context, List<AllEvents> temp_ll_AllEvents)
    {
        this.mcontext = context;
        this.temp_ll_AllEvents = temp_ll_AllEvents;
    }

    @Override
    public int getCount() {
        return temp_ll_AllEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return temp_ll_AllEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        LinearLayout firstlayout,secondlayout;
        final int pos = i;
        TextView start_date_txt,start_month_txt,end_date_txt,end_month_txt,event_title,event_time,event_description;
        TextView start_date_txt1,start_month_txt1,event_title1,event_time1,event_description1;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.eventlist_item, null);

        String event_title_val=temp_ll_AllEvents.get(pos).getEvent_title();
        String event_start=temp_ll_AllEvents.get(pos).getEvent_start().toString().trim();
        String event_end=temp_ll_AllEvents.get(pos).getEvent_end().toString().trim();
        String start_time=temp_ll_AllEvents.get(pos).getStart_time();
        String end_time=temp_ll_AllEvents.get(pos).getEnd_time();
        String description=temp_ll_AllEvents.get(pos).getDescription();

        start_date_txt=(TextView)convertView.findViewById(R.id.start_date_txt);
        start_month_txt=(TextView)convertView.findViewById(R.id.start_month_txt);
        end_date_txt=(TextView)convertView.findViewById(R.id.end_date_txt);
        end_month_txt=(TextView)convertView.findViewById(R.id.end_month_txt);
        event_title=(TextView)convertView.findViewById(R.id.event_title);
        event_time=(TextView)convertView.findViewById(R.id.event_time);
        event_description=(TextView)convertView.findViewById(R.id.event_description);

        start_date_txt1=(TextView)convertView.findViewById(R.id.start_date_txt1);
        start_month_txt1=(TextView)convertView.findViewById(R.id.start_month_txt1);
        event_title1=(TextView)convertView.findViewById(R.id.event_title1);
        event_time1=(TextView)convertView.findViewById(R.id.event_time1);
        event_description1=(TextView)convertView.findViewById(R.id.event_description1);

        firstlayout=(LinearLayout)convertView.findViewById(R.id.firstlayout);
        secondlayout=(LinearLayout)convertView.findViewById(R.id.secondlayout);

        firstlayout.setVisibility(View.GONE);
        secondlayout.setVisibility(View.GONE);

        if (event_start.equalsIgnoreCase(event_end))
        {
            firstlayout.setVisibility(View.GONE);
            secondlayout.setVisibility(View.VISIBLE);


            event_title1.setText(event_title_val);
            start_date_txt1.setText(getDate(event_start));
            start_month_txt1.setText(getMonthName(event_start));
            event_time1.setText(start_time+" - "+end_time);
            event_description1.setText(description);

        }
        else
        {
            firstlayout.setVisibility(View.VISIBLE);
            secondlayout.setVisibility(View.GONE);

            event_title.setText(event_title_val);
            start_date_txt.setText(getDate(event_start));
            end_date_txt.setText(getDate(event_end));
            start_month_txt.setText(getMonthName(event_start));
            end_month_txt.setText(getMonthName(event_end));
            event_time.setText(start_time+" - "+end_time);
            event_description.setText(description);
        }






        return convertView;

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
}