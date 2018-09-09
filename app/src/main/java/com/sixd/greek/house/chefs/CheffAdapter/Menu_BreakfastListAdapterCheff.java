package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sixd.greek.house.chefs.Cheff.AddMenuCheffActivityBreakFast;
import com.sixd.greek.house.chefs.Cheff.MenuActivityCheff;
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

public class Menu_BreakfastListAdapterCheff extends RecyclerView.Adapter<Menu_BreakfastListAdapterCheff.MenuHolder> {


    Context mContext;
    List<MenuModel> menuList=null;
    public Menu_BreakfastListAdapterCheff(Context mContext,int mealtype){

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item_cheff, parent, false);
        return new MenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MenuHolder holder, final int position)
    {
        MenuModel menuItem=menuList.get(position);

        holder.item_text.setText(menuItem.getMenuTitle());
        holder.day_txt.setText(getDate(menuItem.getDate()));
        holder.month_txt.setText(getMonthName(menuItem.getDate()));

        String cs_val=menuItem.getMenucalender_cs();
        if (cs_val.equalsIgnoreCase("1"))
        {
            holder.ch_ic.setSelected(true);
        }
        else
        {
            holder.ch_ic.setSelected(false);
        }



        holder.ch_ic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if(holder.ch_ic.isSelected())
                {
                    holder.ch_ic.setSelected(false);
                    /*String allergy_id= AllergyListManager.allergyListModels.get(position).getAllergy_id();
                    allergyCall.removeAllergy(allergy_id);*/
                }
                else{
                    holder.ch_ic.setSelected(true);
                    /*String allergy_id=AllergyListManager.allergyListModels.get(position).getAllergy_id();
                    allergyCall.allergyID(allergy_id);*/
                }
            }
        });


        String server_date=menuItem.getDate().toString().trim();
        String today_date="";
        SimpleDateFormat formatter;
        formatter= new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        today_date = mdformat.format(calendar.getTime());

        Log.i("respo_today_date=",""+today_date+" server_date="+""+server_date);
        try {
            String str1 = today_date;
            Date date1 = formatter.parse(str1);
            String str2 = server_date;
            Date date2 = formatter.parse(str2);
            if (date1.compareTo(date2)<0)
            {
                holder.edit_menu.setVisibility(View.VISIBLE);
            }
            else {
                holder.edit_menu.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        holder.edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext,"edit menu &"+"position="+position+" menu_calendar_id="
                                +MenuCalendarManager.menu_calendar_id,
                        Toast.LENGTH_LONG).show();
                Intent intent=new Intent(mContext,AddMenuCheffActivityBreakFast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ((MenuActivityCheff) mContext).finish();
            }
        });



    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        TextView day_txt,month_txt,item_text;
        ImageView ch_ic,edit_menu;
        public MenuHolder(View convertView) {
            super(convertView);
            day_txt=(TextView)convertView.findViewById(R.id.day_txt);
            month_txt=(TextView)convertView.findViewById(R.id.month_txt);
            item_text=(TextView)convertView.findViewById(R.id.item_text);
            ch_ic=(ImageView)convertView.findViewById(R.id.ch_ic);
            edit_menu=(ImageView)convertView.findViewById(R.id.edit_menu);
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



