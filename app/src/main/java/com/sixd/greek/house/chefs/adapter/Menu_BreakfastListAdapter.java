package com.sixd.greek.house.chefs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.model.MenuModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class Menu_BreakfastListAdapter extends RecyclerView.Adapter<Menu_BreakfastListAdapter.MenuHolder> {


    Context mContext;
    List<MenuModel> menuList=null;
    public Menu_BreakfastListAdapter(Context mContext,int mealtype){

        this.mContext=mContext;
        if(mealtype==1){
            menuList= MenuCalendarManager.breaffastList;
        }

        else if(mealtype==2){
            menuList= MenuCalendarManager.lunchList;
        }

        else if(mealtype==3){
            menuList= MenuCalendarManager.dinnerList;
        }
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new MenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        MenuModel menuItem=menuList.get(position);

        holder.item_text.setText(menuItem.getMenuTitle());
        holder.day_txt.setText((menuItem.getDaynameStart()));
        holder.month_txt.setText(getMonthName(menuItem.getDate())+" "+getDate(menuItem.getDate()));

        String cs_val=menuItem.getMenucalender_cs();

        if (cs_val.equalsIgnoreCase("1"))
        {
            holder.cs_circle.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.cs_circle.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        TextView day_txt,month_txt,item_text;
        ImageView cs_circle;
        public MenuHolder(View convertView) {
            super(convertView);
            day_txt=(TextView)convertView.findViewById(R.id.day_txt);
            month_txt=(TextView)convertView.findViewById(R.id.month_txt);
            item_text=(TextView)convertView.findViewById(R.id.item_text);
            cs_circle=(ImageView)convertView.findViewById(R.id.cs_circle);

        }

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



