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
import dao_db.TopicComment;


public class ForumCommentAdapter extends BaseAdapter {
    Context mcontext;
    List<TopicComment> allTopicComments;

    public ForumCommentAdapter(Context context,  List<TopicComment> allTopicComments)
    {
        this.mcontext = context;
        this.allTopicComments = allTopicComments;
    }

    @Override
    public int getCount() {
        return allTopicComments.size();
    }

    @Override
    public Object getItem(int i) {
        return allTopicComments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final int pos = i;
        TextView created_by_txt,post_time_ago_txt,comment_description;
        String image_url="";
        ImageView image_view;
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.forum_comment_adapter, null);

        image_view=(ImageView)convertView.findViewById(R.id.image_view);
        created_by_txt=(TextView)convertView.findViewById(R.id.created_by_txt);
        post_time_ago_txt=(TextView)convertView.findViewById(R.id.post_time_ago_txt);
        comment_description=(TextView)convertView.findViewById(R.id.comment_description);


        if (allTopicComments.get(pos).getComment_created_by() != null
                && !allTopicComments.get(pos).getComment_created_by().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComment_created_by().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=allTopicComments.get(pos).getComment_created_by().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            created_by_txt.setText(result.toString().trim());
        }
        else
        {
            created_by_txt.setText("");
        }

        if (allTopicComments.get(pos).getComment_created_on()!= null
                && !allTopicComments.get(pos).getComment_created_on().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComment_created_on().equalsIgnoreCase("null"))
        {
            post_time_ago_txt.setText(allTopicComments.get(pos).getComment_created_on().toString().trim());

        }
        else
        {
            post_time_ago_txt.setText("");
        }

        if (allTopicComments.get(pos).getComments_desc() != null
                && !allTopicComments.get(pos).getComments_desc().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComments_desc().equalsIgnoreCase("null"))
        {
           // topic_name.setText(allCategoryTopics.get(pos).getTopic_name().toString().trim());
            String Created_on=allTopicComments.get(pos).getComments_desc().toString().trim().toLowerCase();
            String result = upperCaseFirst(Created_on);
            comment_description.setText(result.toString().trim());
        }
        else
        {
            comment_description.setText("");
        }





        try {
            image_url=allTopicComments.get(pos).getComment_image_url();
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
               // Log.i("respo_pic_else","else");
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