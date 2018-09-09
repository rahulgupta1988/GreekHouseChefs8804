package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;
import dao_db.MenuCategory;



public class ForumTopicsAdapter extends BaseAdapter {
    Context mcontext;
    List<AllCategoryTopics> allCategoryTopics;

    public ForumTopicsAdapter(Context context, List<AllCategoryTopics> allCategoryTopics)
    {
        this.mcontext = context;
        this.allCategoryTopics = allCategoryTopics;
    }

    @Override
    public int getCount() {
        return allCategoryTopics.size();
    }

    @Override
    public Object getItem(int i) {
        return allCategoryTopics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView created_by_txt,post_time_ago_txt,topic_name,total_comment_txt;
        String image_url="";
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.forum_topics_adapter, null);

        image_view=(ImageView)convertView.findViewById(R.id.image_view);
        created_by_txt=(TextView)convertView.findViewById(R.id.created_by_txt);
        post_time_ago_txt=(TextView)convertView.findViewById(R.id.post_time_ago_txt);
        topic_name=(TextView)convertView.findViewById(R.id.topic_name);
        total_comment_txt=(TextView)convertView.findViewById(R.id.total_comment_txt);

        if (allCategoryTopics.get(pos).getCreated_by() != null
                && !allCategoryTopics.get(pos).getCreated_by().equalsIgnoreCase("")
                && !allCategoryTopics.get(pos).getCreated_by().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=allCategoryTopics.get(pos).getCreated_by().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            created_by_txt.setText(result.toString().trim());
        }
        else
        {
            created_by_txt.setText("");
        }

        if (allCategoryTopics.get(pos).getCreated_on()!= null
                && !allCategoryTopics.get(pos).getCreated_on().equalsIgnoreCase("")
                && !allCategoryTopics.get(pos).getCreated_on().equalsIgnoreCase("null"))
        {
            post_time_ago_txt.setText(allCategoryTopics.get(pos).getCreated_on().toString().trim());

        }
        else
        {
            post_time_ago_txt.setText("");
        }

        if (allCategoryTopics.get(pos).getTopic_name() != null
                && !allCategoryTopics.get(pos).getTopic_name().equalsIgnoreCase("")
                && !allCategoryTopics.get(pos).getTopic_name().equalsIgnoreCase("null"))
        {
           // topic_name.setText(allCategoryTopics.get(pos).getTopic_name().toString().trim());
            String Created_on=allCategoryTopics.get(pos).getTopic_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Created_on);
            topic_name.setText(result.toString().trim());
        }
        else
        {
            topic_name.setText("");
        }


        if (allCategoryTopics.get(pos).getTotal_comments() != null
                && !allCategoryTopics.get(pos).getTotal_comments().equalsIgnoreCase("")
                && !allCategoryTopics.get(pos).getTotal_comments().equalsIgnoreCase("null"))
        {
            total_comment_txt.setText(allCategoryTopics.get(pos).getTotal_comments().toString().trim());

        }
        else
        {
            total_comment_txt.setText("");
        }


        try {
            image_url=allCategoryTopics.get(pos).getImage_url();
           // Log.i("respo_picfinal", "" + image_url);
            if (image_url != null
                    && !image_url.equalsIgnoreCase("")
                    && !image_url.equalsIgnoreCase("null") )
            {
               // Log.i("respo_pic_if","if");
                Picasso.with(mcontext)
                        .load(image_url)
                        .resize(100, 100)
                        .centerInside()
                        .error(R.drawable.profile_ic)
                        .placeholder(R.drawable.profile_ic)
                        .into(image_view);
            }
            else
            {
                //Log.i("respo_pic_else","else");
                image_view.setBackgroundResource(R.drawable.profile_ic);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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