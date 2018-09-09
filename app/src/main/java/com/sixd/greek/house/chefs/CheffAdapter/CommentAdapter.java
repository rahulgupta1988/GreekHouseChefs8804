package com.sixd.greek.house.chefs.CheffAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.model.MenuModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao_db.TopicComment;

/**
 * Created by Praveen on 19-Jul-17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MenuHolder> {

    String image_url="";
    Context mContext;
    //List<MenuModel> menuList=null;
    List<TopicComment> allTopicComments;
    public CommentAdapter(Context mContext)
    {
        this.mContext=mContext;
        allTopicComments = new ForumManager(mContext).getTopicComments();
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_comment_adapter, parent, false);
        return new MenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int pos) {

        if (allTopicComments.get(pos).getComment_created_by() != null
                && !allTopicComments.get(pos).getComment_created_by().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComment_created_by().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=allTopicComments.get(pos).getComment_created_by().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            holder.created_by_txt.setText(result.toString().trim());
        }
        else
        {
            holder.created_by_txt.setText("");
        }

        if (allTopicComments.get(pos).getComment_created_on()!= null
                && !allTopicComments.get(pos).getComment_created_on().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComment_created_on().equalsIgnoreCase("null"))
        {
            holder.post_time_ago_txt.setText(allTopicComments.get(pos).getComment_created_on().toString().trim());

        }
        else
        {
            holder.post_time_ago_txt.setText("");
        }

        if (allTopicComments.get(pos).getComments_desc() != null
                && !allTopicComments.get(pos).getComments_desc().equalsIgnoreCase("")
                && !allTopicComments.get(pos).getComments_desc().equalsIgnoreCase("null"))
        {
            // topic_name.setText(allCategoryTopics.get(pos).getTopic_name().toString().trim());
            String Created_on=allTopicComments.get(pos).getComments_desc().toString().trim().toLowerCase();
            String result = upperCaseFirst(Created_on);
            holder.comment_description.setText(result.toString().trim());
        }
        else
        {
            holder.comment_description.setText("");
        }





        try {
            image_url=allTopicComments.get(pos).getComment_image_url();
            // Log.i("respo_picfinal", "" + image_url);
            if (image_url != null
                    && !image_url.equalsIgnoreCase("")
                    && !image_url.equalsIgnoreCase("null") )
            {
                // Log.i("respo_pic_if","if");
                Picasso.with(mContext)
                        .load(image_url)
                        .resize(100, 100)
                        .centerInside()
                        .error(R.drawable.profile_ic)
                        .placeholder(R.drawable.profile_ic)
                        .into(holder.image_view);
            }
            else
            {
                // Log.i("respo_pic_else","else");
                holder.image_view.setBackgroundResource(R.drawable.profile_ic);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return allTopicComments.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        TextView day_txt,month_txt,item_text;
        ImageView cs_circle;

        TextView created_by_txt,post_time_ago_txt,comment_description;
        String image_url="";
        ImageView image_view;

        public MenuHolder(View convertView) {
            super(convertView);
            image_view=(ImageView)convertView.findViewById(R.id.image_view);
            created_by_txt=(TextView)convertView.findViewById(R.id.created_by_txt);
            post_time_ago_txt=(TextView)convertView.findViewById(R.id.post_time_ago_txt);
            comment_description=(TextView)convertView.findViewById(R.id.comment_description);

        }

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



