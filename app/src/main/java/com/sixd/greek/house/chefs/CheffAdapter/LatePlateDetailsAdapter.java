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
import dao_db.MenuCategory;



public class LatePlateDetailsAdapter extends BaseAdapter {
    Context mcontext;
    List<AllLunchAllergyList> getAllLunchAllergyLists;

    public LatePlateDetailsAdapter(Context context, List<AllLunchAllergyList> getAllLunchAllergyLists)
    {
        this.mcontext = context;
        this.getAllLunchAllergyLists = getAllLunchAllergyLists;
    }

    @Override
    public int getCount() {
        return getAllLunchAllergyLists.size();
    }

    @Override
    public Object getItem(int i) {
        return getAllLunchAllergyLists.get(i);
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
        convertView = inflater.inflate(R.layout.late_plate_details, null);

        allergy_name_txt=(TextView)convertView.findViewById(R.id.allergy_name_txt);

        if (getAllLunchAllergyLists.get(pos).getAllergy_name() != null
                && !getAllLunchAllergyLists.get(pos).getAllergy_name().equalsIgnoreCase("")
                && !getAllLunchAllergyLists.get(pos).getAllergy_name().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=getAllLunchAllergyLists.get(pos).getAllergy_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            allergy_name_txt.setText(result.toString().trim());
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