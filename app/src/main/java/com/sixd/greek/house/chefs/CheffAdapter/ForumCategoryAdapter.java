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

import dao_db.AllFormCategory;
import dao_db.MenuCategory;



public class ForumCategoryAdapter extends BaseAdapter {
    Context mcontext;
    List<AllFormCategory> allFormCategories;

    public ForumCategoryAdapter(Context context, List<AllFormCategory> allFormCategories)
    {
        this.mcontext = context;
        this.allFormCategories = allFormCategories;
    }

    @Override
    public int getCount() {
        return allFormCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return allFormCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView forum_category_txt,post_and_topics_txt;
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.forum_category_adapter, null);


        image_view=(ImageView)
                convertView.findViewById(R.id.image_view);
        forum_category_txt=(TextView)convertView.findViewById(R.id.forum_category_txt);
        post_and_topics_txt=(TextView)convertView.findViewById(R.id.post_and_topics_txt);


        if (allFormCategories.get(pos).getCategory_name() != null
                && !allFormCategories.get(pos).getCategory_name().equalsIgnoreCase("")
                && !allFormCategories.get(pos).getCategory_name().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=allFormCategories.get(pos).getCategory_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            forum_category_txt.setText(result.toString().trim());
        }
        else
        {
            forum_category_txt.setText("");
        }

        if (allFormCategories.get(pos).getTotal_topics() != null
                && !allFormCategories.get(pos).getTotal_topics().equalsIgnoreCase("")
                && !allFormCategories.get(pos).getTotal_topics().equalsIgnoreCase("null"))
        {
            /*post_and_topics_txt.setText(allFormCategories.get(pos).getTotal_comments() +" Posts"+""+" - "+
                    allFormCategories.get(pos).getTotal_topics() +" Topics");*/

            post_and_topics_txt.setText(allFormCategories.get(pos).getTotal_topics() +" Topics");
        }
        else
        {
            post_and_topics_txt.setText("0 Posts");
        }


        //get first letter of each String item

        String firstLetter = String.valueOf(allFormCategories.get(pos).getCategory_name().toString().trim().toUpperCase().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(allFormCategories.get(pos));
        //int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        image_view.setImageDrawable(drawable);


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