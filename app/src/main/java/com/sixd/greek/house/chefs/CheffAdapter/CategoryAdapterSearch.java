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
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;



public class CategoryAdapterSearch extends ArrayAdapter
{
    String offlinecheckin_value="";
    Context context;
    int layoutResourceId;
    ViewHolder holder = null;
    List<AllFormCategory> temp_llPackageCategoryLists;
    String Forum_category_id="",forum_category_name="";
    MaterialDialog materialDialog=null;

    public CategoryAdapterSearch(Context context, int layoutResourceId, List<AllFormCategory> temp_llPackageCategoryLists)
    {
        super(context, layoutResourceId, temp_llPackageCategoryLists);
        // TODO Auto-generated constructor stub
        try {
            this.layoutResourceId = layoutResourceId;
            this.context = context;
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
        TextView forum_category_txt, post_and_topics_txt;
        LinearLayout ll_final;
        ImageView image_view,store_checkin_status;
        LinearLayout linear;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = ((Activity) context)
                        .getLayoutInflater();
                convertView = inflater.inflate(R.layout.forum_category_adapter, parent,
                        false);
                holder.forum_category_txt = (TextView) convertView
                        .findViewById(R.id.forum_category_txt);
                holder.post_and_topics_txt = (TextView) convertView
                        .findViewById(R.id.post_and_topics_txt);
                holder.image_view = (ImageView) convertView.findViewById(R.id.image_view);
                holder.store_checkin_status=(ImageView) convertView.findViewById(R.id.store_checkin_status);

                holder.ll_final = (LinearLayout) convertView.findViewById(R.id.ll_final);
                holder.linear=(LinearLayout)convertView.findViewById(R.id.linear);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            if (temp_llPackageCategoryLists.get(position).getCategory_name() != null
                    && !temp_llPackageCategoryLists.get(position).getCategory_name().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getCategory_name().equalsIgnoreCase("null"))
            {
                String Sub_cat_item_name=temp_llPackageCategoryLists.get(position).getCategory_name().toString().trim().toLowerCase();
                String result = upperCaseFirst(Sub_cat_item_name);
                holder.forum_category_txt.setText(result.toString().trim());
            }
            else
            {
                holder.forum_category_txt.setText("");
            }

            if (temp_llPackageCategoryLists.get(position).getTotal_topics() != null
                    && !temp_llPackageCategoryLists.get(position).getTotal_topics().equalsIgnoreCase("")
                    && !temp_llPackageCategoryLists.get(position).getTotal_topics().equalsIgnoreCase("null"))
            {
            /*post_and_topics_txt.setText(allFormCategories.get(pos).getTotal_comments() +" Posts"+""+" - "+
                    allFormCategories.get(pos).getTotal_topics() +" Topics");*/

                holder.post_and_topics_txt.setText(temp_llPackageCategoryLists.get(position).getTotal_topics() +" Topics");
            }
            else
            {
                holder.post_and_topics_txt.setText("0 Posts");
            }


            String firstLetter = String.valueOf(position+1);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(temp_llPackageCategoryLists.get(position).getCategory_name().toString().trim());
            //int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            holder.image_view.setImageDrawable(drawable);

            holder.ll_final.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    {
                        Forum_category_id = temp_llPackageCategoryLists.get(position).getForum_category_id();
                        forum_category_name=temp_llPackageCategoryLists.get(position).getCategory_name();
                        Log.i("respo_forum_categ_name=", "" + forum_category_name+"respo_Forum_category_id="+Forum_category_id);

                        materialDialog.show();
                        new ForumManager(context).initGetForumtopicsAPI(Forum_category_id, new CallBackManager()
                        {
                            @Override
                            public void onSuccess(String responce) {
                                materialDialog.dismiss();
                                if (responce.equals("100"))
                                {
                                    List<AllCategoryTopics> allCategoryTopics;
                                    allCategoryTopics = new ForumManager(context).getallCategoryTopics();
                                    Log.i("respo_Categ_Topic_size1", "" + allCategoryTopics.size());

                                    //if (allCategoryTopics.size() > 0)
                                    {
                                        Intent forum_intent = new Intent(context, ForumTopicsActivity.class);
                                        forum_intent.putExtra("category_name", forum_category_name);
                                        forum_intent.putExtra("Forum_category_id", Forum_category_id);
                                        forum_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(forum_intent);
                                        ((ForumCategoryActivity) context).finish();
                                    }
                                  /*  else
                                    {
                                        Snackbar snackbar = Snackbar
                                                .make(holder.linear, "Category topics is not available!", Snackbar.LENGTH_LONG)
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
                                    }*/
                                }

                                else if(responce.contains("Topic is not"))
                                {
                                    List<AllCategoryTopics> allCategoryTopics;
                                    allCategoryTopics = new ForumManager(context).getallCategoryTopics();
                                    Log.i("respo_Categ_Topic_size2", "" + allCategoryTopics.size());
                                    {
                                        Intent forum_intent = new Intent(context, ForumTopicsActivity.class);
                                        forum_intent.putExtra("category_name", forum_category_name);
                                        forum_intent.putExtra("Forum_category_id", Forum_category_id);
                                        forum_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(forum_intent);
                                        ((ForumCategoryActivity) context).finish();
                                    }
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
                                        (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
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


        } catch (Exception e) {
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


