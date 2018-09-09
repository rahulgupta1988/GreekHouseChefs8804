package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sixd.greek.house.chefs.R;

import java.util.List;

import dao_db.AllWeekIntervalList;


public class MenuWeekIntervalAdapter extends BaseAdapter {
    Context mcontext;
    List<AllWeekIntervalList> allWeekIntervalLists;

    public MenuWeekIntervalAdapter(Context context, List<AllWeekIntervalList> allWeekIntervalLists)
    {
        this.mcontext = context;
        this.allWeekIntervalLists = allWeekIntervalLists;
    }

    @Override
    public int getCount() {
        return allWeekIntervalLists.size();
    }

    @Override
    public Object getItem(int i) {
        return allWeekIntervalLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView week_interval_txt;
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.week_interval, null);


        image_view=(ImageView)
                convertView.findViewById(R.id.image_view);
        week_interval_txt=(TextView)convertView.findViewById(R.id.week_interval_txt);

        String Interval=allWeekIntervalLists.get(pos).getInterval();
        String week=allWeekIntervalLists.get(pos).getWeek();
        String year=allWeekIntervalLists.get(pos).getYear();

        if (allWeekIntervalLists.get(pos).getInterval() != null
                && !allWeekIntervalLists.get(pos).getInterval().equalsIgnoreCase("")
                && !allWeekIntervalLists.get(pos).getInterval().equalsIgnoreCase("null"))
        {
            week_interval_txt.setText(allWeekIntervalLists.get(pos).getInterval().toString().trim());
        }
        else
        {
            week_interval_txt.setText("");
        }



        return convertView;
    }


    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

}