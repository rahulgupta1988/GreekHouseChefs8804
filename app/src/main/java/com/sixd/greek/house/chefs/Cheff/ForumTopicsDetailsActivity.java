package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.CheffAdapter.CommentAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.ForumCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.ForumCommentAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.ForumTopicsAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.Menu_BreakfastListAdapter;
import com.sixd.greek.house.chefs.manager.AllergyListManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;
import dao_db.TopicComment;
import dao_db.TopicDetail;
import dao_db.UserLoginInfo;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class ForumTopicsDetailsActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    RecyclerView list_menu_development;
    RelativeLayout realativelinear;
    Intent intent;
    String category_name="",Forum_topics_id="",Forum_category_id="";
    List<TopicDetail> allTopicDetails;
    List<TopicComment> allTopicComments;

    TextView created_by_txt,post_time_ago_txt,topic_name,topic_description,total_comment_txt;
    String image_url="";
    ImageView image_view,image_view_cheff,add_comment_img;
    EditText comment_ed;
    TextView category_return_txt_details,add_topic_txt_details;

    String topicpage="topicpagedetails";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=this;
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Forum ");
        initViews();
    }

    public void initViews()
    {

        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        Forum_topics_id=intent.getStringExtra("Forum_topics_id");
        Forum_category_id=intent.getStringExtra("Forum_category_id");
        Log.i("respo_category_name=", "" + category_name+" and "+"Forum_topics_id="+Forum_topics_id);

       /* intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        Log.i("respo_category_name=", "" + category_name);
        // setHeaderTitele(category_name_val);

        try
        {
            String result = upperCaseFirst(category_name);
            header_txt.setText(""+result.toString().trim());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/



        allTopicDetails = new ForumManager(ForumTopicsDetailsActivity.this).getTopicDetails();
        allTopicComments = new ForumManager(ForumTopicsDetailsActivity.this).getTopicComments();
        Log.i("respo_allTopicDetails=", "" + allTopicDetails.size()
                + " and " + "respo_allCommentsDetails=" + allTopicComments.size());


        category_return_txt_details=(TextView)findViewById(R.id.category_return_txt_details);
        category_return_txt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumTopicsDetailsActivity.this, ForumCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add_topic_txt_details=(TextView)findViewById(R.id.add_topic_txt_details);
        add_topic_txt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumTopicsDetailsActivity.this, ForumAddTopicActivity.class);
                intent.putExtra("category_name", category_name);
                intent.putExtra("Forum_category_id", Forum_category_id);
                intent.putExtra("Forum_topics_id", Forum_topics_id);
                intent.putExtra("topicpage", topicpage);
                startActivity(intent);
                finish();
            }
        });


        // yyrty

        realativelinear=(RelativeLayout)findViewById(R.id.realativelinear);


       /* NestedScrollView view = (NestedScrollView) findViewById(R.id.regScroll);
        view.setScrollbarFadingEnabled(false);
        view.setVerticalScrollBarEnabled(true);
        view.setVerticalFadingEdgeEnabled(false);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });*/


        image_view_cheff=(ImageView)findViewById(R.id.image_view_cheff);
        try
        {
            List<UserLoginInfo> userDatas = null;
            userDatas = new LoginManager(getApplicationContext()).getUserInfo();
            if(userDatas!=null)
            {
                if(userDatas.get(0)!=null){

                    String userprofileimage = userDatas.get(0).getImage_url();
                    Glide.with(this)
                            .load(userprofileimage)
                            .placeholder(R.drawable.profile_ic)
                            .error(R.drawable.profile_ic)
                            .crossFade()
                            .fitCenter()
                            .into(image_view_cheff);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        image_view=(ImageView)findViewById(R.id.image_view);
        created_by_txt=(TextView)findViewById(R.id.created_by_txt);
        post_time_ago_txt=(TextView)findViewById(R.id.post_time_ago_txt);
        topic_name=(TextView)findViewById(R.id.topic_name);
        topic_description=(TextView)findViewById(R.id.topic_description);
        total_comment_txt=(TextView)findViewById(R.id.total_comment_txt);

        if (allTopicDetails.get(0).getTopic_created_by() != null
                && !allTopicDetails.get(0).getTopic_created_by().equalsIgnoreCase("")
                && !allTopicDetails.get(0).getTopic_created_by().equalsIgnoreCase("null"))
        {
            String Sub_cat_item_name=allTopicDetails.get(0).getTopic_created_by().toString().trim().toLowerCase();
            String result = upperCaseFirst(Sub_cat_item_name);
            created_by_txt.setText(result.toString().trim());
        }
        else
        {
            created_by_txt.setText("");
        }

        if (allTopicDetails.get(0).getTopic_created_on()!= null
                && !allTopicDetails.get(0).getTopic_created_on().equalsIgnoreCase("")
                && !allTopicDetails.get(0).getTopic_created_on().equalsIgnoreCase("null"))
        {
            post_time_ago_txt.setText(allTopicDetails.get(0).getTopic_created_on().toString().trim());

        }
        else
        {
            post_time_ago_txt.setText("");
        }

        if (allTopicDetails.get(0).getTopic_name() != null
                && !allTopicDetails.get(0).getTopic_name().equalsIgnoreCase("")
                && !allTopicDetails.get(0).getTopic_name().equalsIgnoreCase("null"))
        {
            String Created_on=allTopicDetails.get(0).getTopic_name().toString().trim().toLowerCase();
            String result = upperCaseFirst(Created_on);
            topic_name.setText(result.toString().trim());
        }
        else
        {
            topic_name.setText("");
        }

        if (allTopicDetails.get(0).getDescription() != null
                && !allTopicDetails.get(0).getDescription().equalsIgnoreCase("")
                && !allTopicDetails.get(0).getDescription().equalsIgnoreCase("null"))
        {
            String Created_on=allTopicDetails.get(0).getDescription().toString().trim().toLowerCase();
            String result = upperCaseFirst(Created_on);
            topic_description.setText(result.toString().trim());
        }
        else
        {
            topic_description.setText("");
        }

        if (allTopicDetails.get(0).getTotal_comments() != null
                && !allTopicDetails.get(0).getTotal_comments().equalsIgnoreCase("")
                && !allTopicDetails.get(0).getTotal_comments().equalsIgnoreCase("null"))
        {
            total_comment_txt.setText(allTopicDetails.get(0).getTotal_comments().toString().trim());

        }
        else
        {
            total_comment_txt.setText("");
        }


        try {
            image_url=allTopicDetails.get(0).getTopic_image_url();
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


        /*  <Display Comment Description>*/

         /*list_menu_development=(RecyclerView) findViewById(R.id.list_menu_development);
        // list_menu_development.setDivider(null);
         list_menu_development.setAdapter(new ForumCommentAdapter(ForumTopicsDetailsActivity.this, allTopicComments));
       //  ForumTopicsDetailsActivity.ListUtils.setDynamicHeight(list_menu_development);
        // Helper.getListViewSize(list_menu_development);*/


        list_menu_development=(RecyclerView)findViewById(R.id.list_menu_development);
        list_menu_development.setFocusable(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        list_menu_development.setLayoutManager(layoutManager);
        SeparatorDecoration decoration = new SeparatorDecoration(ForumTopicsDetailsActivity.this,getResources().getColor(R.color.div_color), 1.0f);
        list_menu_development.addItemDecoration(decoration);
        list_menu_development.setItemAnimator(new DefaultItemAnimator());
        list_menu_development.setNestedScrollingEnabled(false);
        list_menu_development.setAdapter(new CommentAdapter(mContext));

        

        comment_ed=(EditText)findViewById(R.id.comment_ed);
        add_comment_img=(ImageView)findViewById(R.id.add_comment_img);

        add_comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getCurrentFocus()!=null)
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                String comment_msg=comment_ed.getText().toString().trim();

                if(comment_msg.length()>0)
                {
                    initAddForumCommentsAPI(comment_msg);
                }
                else
                {
                    // Toast.makeText(mContext,"Enter allergy!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(realativelinear, "Write comment!", Snackbar.LENGTH_LONG)
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


    public void initAddForumCommentsAPI(String Comment)
    {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new ForumManager(mContext).initAddForumCommentsAPI(Forum_topics_id,Comment,new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    if(responce.equals("100"))
                    {
                      /*  Need to update all service like topic and details*/
                        initForumTopicsAPI();
                    }
                    else
                    {
                        materialDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(realativelinear,""+responce, Snackbar.LENGTH_LONG)
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
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(realativelinear, "No internet connection!", Snackbar.LENGTH_LONG)
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


    public void initForumTopicsAPI()
    {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            new ForumManager(mContext).initGetForumtopicsAPI(Forum_category_id, new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    if (responce.equals("100"))
                    {
                        initForumTopicDetailsAPI();
                    }
                    else
                    {
                        materialDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(realativelinear, "" + responce, Snackbar.LENGTH_LONG)
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
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });
        }
        else {
            materialDialog.dismiss();
            Snackbar snackbar = Snackbar
                    .make(realativelinear, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();        }
    }



    public void initForumTopicDetailsAPI()
    {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            new ForumManager(mContext).initForumTopicDetailsAPI(Forum_topics_id, new CallBackManager() {

                 @Override
                 public void onSuccess(String responce)
                 {
                     materialDialog.dismiss();
                     if (responce.equals("100"))
                     {
                         Toast.makeText(getApplicationContext(),"Comment added successfully!",
                                 Toast.LENGTH_LONG).show();
                         comment_ed.setText("");


                     /*=================Set data again for details page==================*/

                         List<TopicDetail> allTopicDetails;
                         List<TopicComment> allTopicComments;
                         allTopicDetails = new ForumManager(ForumTopicsDetailsActivity.this).getTopicDetails();
                         allTopicComments = new ForumManager(ForumTopicsDetailsActivity.this).getTopicComments();
                         Log.i("respo_allTopicDetails=", "" + allTopicDetails.size()
                                 + " and " + "respo_allCommentsDetails=" + allTopicComments.size());

                         if (allTopicDetails.get(0).getTotal_comments() != null
                                 && !allTopicDetails.get(0).getTotal_comments().equalsIgnoreCase("")
                                 && !allTopicDetails.get(0).getTotal_comments().equalsIgnoreCase("null"))
                         {
                             total_comment_txt.setText(allTopicDetails.get(0).getTotal_comments().toString().trim());

                         }
                         else
                         {
                             total_comment_txt.setText("");
                         }

                       /*  list_menu_development.setAdapter(new ForumCommentAdapter(ForumTopicsDetailsActivity.this, allTopicComments));
                        // ForumTopicsDetailsActivity.ListUtils.setDynamicHeight(list_menu_development);
                         //Helper.getListViewSize(list_menu_development);*/




                         list_menu_development=(RecyclerView)findViewById(R.id.list_menu_development);
                         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                         list_menu_development.setLayoutManager(layoutManager);
                         SeparatorDecoration decoration = new SeparatorDecoration(ForumTopicsDetailsActivity.this,getResources().getColor(R.color.div_color), 1.0f);
                         list_menu_development.addItemDecoration(decoration);
                         list_menu_development.setItemAnimator(new DefaultItemAnimator());
                         list_menu_development.setNestedScrollingEnabled(false);
                         list_menu_development.setAdapter(new CommentAdapter(mContext));
                     }
                     else
                     {
                         Snackbar snackbar = Snackbar
                                 .make(realativelinear, "" + responce, Snackbar.LENGTH_LONG)
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
                 public void onFailure(String responce) {
                     materialDialog.dismiss();
                 }
             });
        }
        else {
            materialDialog.dismiss();
            Snackbar snackbar = Snackbar
                    .make(realativelinear, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();        }
    }







    public static class ListUtils
    {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
// when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
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





    public static class Helper
    {
        public static void getListViewSize(ListView myListView) {
            ListAdapter myListAdapter = myListView.getAdapter();
            if (myListAdapter == null) {
                //do nothing return null
                return;
            }
            //set listAdapter in loop for getting final size
            int totalHeight = 0;
            for (int size = 0; size < myListAdapter.getCount(); size++) {
                View listItem = myListAdapter.getView(size, null, myListView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            //setting listview item in adapter
            ViewGroup.LayoutParams params = myListView.getLayoutParams();
            params.height = 150+totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
            myListView.setLayoutParams(params);
            // print height of adapter on log
            Log.i("height of listItem:", String.valueOf(totalHeight));
        }
    }










    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForumTopicsDetailsActivity.this,ForumTopicsActivity.class);
        intent.putExtra("category_name",category_name);
        intent.putExtra("Forum_category_id",Forum_category_id);
        startActivity(intent);
        finish();
    }






}

