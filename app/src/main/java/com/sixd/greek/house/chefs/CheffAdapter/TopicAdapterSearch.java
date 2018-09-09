package com.sixd.greek.house.chefs.CheffAdapter;


import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sixd.greek.house.chefs.Cheff.ForumCategoryActivity;
import com.sixd.greek.house.chefs.Cheff.ForumTopicsActivity;
import com.sixd.greek.house.chefs.Cheff.ForumTopicsDetailsActivity;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.squareup.picasso.Picasso;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;



public class TopicAdapterSearch extends ArrayAdapter
{
    String offlinecheckin_value="";
    Context context;
    int layoutResourceId;
    ViewHolder holder = null;
    List<AllCategoryTopics> temp_llPackageCategoryLists;
    String category_name="",Forum_category_id="";
    String Forum_topics_id="";
    MaterialDialog materialDialog=null;

    public TopicAdapterSearch(Context context, int layoutResourceId, List<AllCategoryTopics> temp_llPackageCategoryLists,
                              String category_name,String Forum_category_id)
    {
        super(context, layoutResourceId, temp_llPackageCategoryLists);
        // TODO Auto-generated constructor stub
        try {
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.category_name = category_name;
            this.Forum_category_id = Forum_category_id;
            this.temp_llPackageCategoryLists = temp_llPackageCategoryLists;
            materialDialog=new MaterialDialog.Builder(context)
                    .content("Please Wait...")
                    .progress(true, 0)
                    .cancelable(false).build();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ViewHolder {
       TextView created_by_txt,post_time_ago_txt,topic_name,total_comment_txt;
        String image_url="";
        ImageView image_view;
        LinearLayout ll_final;
        LinearLayout linear;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null)
            {
                holder = new ViewHolder();
                LayoutInflater inflater = ((Activity) context)
                        .getLayoutInflater();
                convertView = inflater.inflate(R.layout.forum_topics_adapter, parent,
                        false);


                holder.image_view=(ImageView)convertView.findViewById(R.id.image_view);
                holder.created_by_txt=(TextView)convertView.findViewById(R.id.created_by_txt);
                holder.post_time_ago_txt=(TextView)convertView.findViewById(R.id.post_time_ago_txt);
                holder.topic_name=(TextView)convertView.findViewById(R.id.topic_name);
                holder.total_comment_txt=(TextView)convertView.findViewById(R.id.total_comment_txt);
                holder.ll_final = (LinearLayout) convertView.findViewById(R.id.ll_final);
                holder.linear=(LinearLayout)convertView.findViewById(R.id.linear);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }


            if (temp_llPackageCategoryLists.get(position).getCreated_by() != null
                    && !temp_llPackageCategoryLists.get(position).getCreated_by().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getCreated_by().equalsIgnoreCase("null"))
            {
                String Sub_cat_item_name=temp_llPackageCategoryLists.get(position).getCreated_by().toString().trim().toLowerCase();
                String result = upperCaseFirst(Sub_cat_item_name);
                holder.created_by_txt.setText(result.toString().trim());
            }
            else
            {
                holder.created_by_txt.setText("");
            }

            if (temp_llPackageCategoryLists.get(position).getCreated_on()!= null
                    && !temp_llPackageCategoryLists.get(position).getCreated_on().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getCreated_on().equalsIgnoreCase("null"))
            {
                holder.post_time_ago_txt.setText(temp_llPackageCategoryLists.get(position).getCreated_on().toString().trim());
            }
            else
            {
                holder.post_time_ago_txt.setText("");
            }

            if (temp_llPackageCategoryLists.get(position).getTopic_name() != null
                    && !temp_llPackageCategoryLists.get(position).getTopic_name().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getTopic_name().equalsIgnoreCase("null"))
            {
                // topic_name.setText(temp_llPackageCategoryLists.get(position).getTopic_name().toString().trim());
                String Created_on=temp_llPackageCategoryLists.get(position).getTopic_name().toString().trim().toLowerCase();
                String result = upperCaseFirst(Created_on);
                holder.topic_name.setText(result.toString().trim());
            }
            else
            {
                holder.topic_name.setText("");
            }


            if (temp_llPackageCategoryLists.get(position).getTotal_comments() != null
                    && !temp_llPackageCategoryLists.get(position).getTotal_comments().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getTotal_comments().equalsIgnoreCase("null"))
            {
                holder.total_comment_txt.setText(temp_llPackageCategoryLists.get(position).getTotal_comments().toString().trim());

            }
            else
            {
                holder.total_comment_txt.setText("");
            }


            try {
                holder.image_url=temp_llPackageCategoryLists.get(position).getImage_url();
                // Log.i("respo_picfinal", "" + image_url);
                if (holder.image_url != null
                        && !holder.image_url.equalsIgnoreCase("")
                        && !holder.image_url.equalsIgnoreCase("null") )
                {
                    // Log.i("respo_pic_if","if");
                    Picasso.with(context)
                            .load(holder.image_url)
                            .resize(100, 100)
                            .centerInside()
                            .error(R.drawable.profile_ic)
                            .placeholder(R.drawable.profile_ic)
                            .into(holder.image_view);
                }
                else
                {
                    //Log.i("respo_pic_else","else");
                    holder.image_view.setBackgroundResource(R.drawable.profile_ic);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            holder.ll_final.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    {
                        Forum_topics_id = temp_llPackageCategoryLists.get(position).getForum_topics_id();

                        materialDialog.show();

                        new ForumManager(context).initForumTopicDetailsAPI(Forum_topics_id, new CallBackManager()
                        {
                            @Override
                            public void onSuccess(String responce) {
                                materialDialog.dismiss();
                                if (responce.equals("100"))
                                {
                                    Intent forum_intent = new Intent(context, ForumTopicsDetailsActivity.class);
                                    forum_intent.putExtra("category_name",category_name);
                                    forum_intent.putExtra("Forum_topics_id", Forum_topics_id);
                                    forum_intent.putExtra("Forum_category_id", Forum_category_id);

                                    forum_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(forum_intent);
                                    ((ForumTopicsActivity) context).finish();
                                }
                                else
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(holder.linear, "" + responce, Snackbar.LENGTH_LONG)
                                            .setAction("RETRY", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                }
                                            });

                                    snackbar.setActionTextColor(Color.RED);

                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.YELLOW);
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(String responce)
                            {
                                materialDialog.dismiss();

                                ConnectivityManager connec =
                                        (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                                // Check for network connections
                                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(holder.linear,""+responce, Snackbar.LENGTH_LONG)
                                            .setAction("RETRY", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                }
                                            });

                                    snackbar.setActionTextColor(Color.RED);

                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.YELLOW);
                                    snackbar.show();
                                }
                                else {
                                    Snackbar snackbar = Snackbar
                                            .make(holder.linear, "No internet connection!", Snackbar.LENGTH_LONG)
                                            .setAction("RETRY", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                }
                                            });

                                    snackbar.setActionTextColor(Color.RED);

                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.YELLOW);
                                    snackbar.show();
                                }

                            }
                        });

                    }
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Show location settings when the user acknowledges the alert dialog
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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


