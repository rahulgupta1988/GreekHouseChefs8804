package com.sixd.greek.house.chefs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sixd.greek.house.chefs.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Praveen on 24-Jul-17.
 */

public class RateMyMealListAdapter  extends RecyclerView.Adapter<RateMyMealListAdapter.MenuHolder> {


    Context mContext;

    public RateMyMealListAdapter(Context mContext, int mealtype) {
        this.mContext = mContext;
    }

    @Override
    public RateMyMealListAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new RateMyMealListAdapter.MenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RateMyMealListAdapter.MenuHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        TextView day_txt, item_text;

        public MenuHolder(View convertView) {
            super(convertView);
            day_txt = (TextView) convertView.findViewById(R.id.day_txt);
            item_text = (TextView) convertView.findViewById(R.id.item_text);
        }

    }

    String getDayName(String dateStr) {
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(dateStr);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEE");
            String goal = outFormat.format(date);
            return goal.toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
