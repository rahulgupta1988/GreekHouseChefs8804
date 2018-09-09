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

import dao_db.AllLunchAllergyList;
import dao_db.AllPastBudgetInfo;
import dao_db.MenuCategory;



public class SubmitBudgetAdapter extends BaseAdapter {
    Context mcontext;
    List<AllPastBudgetInfo> getAllPastBudgetInfo;

    public SubmitBudgetAdapter(Context context,List<AllPastBudgetInfo> getAllPastBudgetInfo)
    {
        this.mcontext = context;
        this.getAllPastBudgetInfo = getAllPastBudgetInfo;
    }

    @Override
    public int getCount() {
        return getAllPastBudgetInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return getAllPastBudgetInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView allergy_name_txt;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.submit_budget_adapter, null);

        allergy_name_txt=(TextView)convertView.findViewById(R.id.allergy_name_txt);

        if (getAllPastBudgetInfo.get(pos).getInterval() != null
                && !getAllPastBudgetInfo.get(pos).getInterval().equalsIgnoreCase("")
                && !getAllPastBudgetInfo.get(pos).getInterval().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=getAllPastBudgetInfo.get(pos).getInterval().toString().trim();
            allergy_name_txt.setText(Sub_cat_item_name.toString().trim());
        }
        else
        {
            allergy_name_txt.setText("");
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