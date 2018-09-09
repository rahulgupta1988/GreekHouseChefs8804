package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.ForumCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.ForumTopicsAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class ForumAddTopicActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView list_menu_development;
    LinearLayout linear;
    Intent intent;
    String category_name="",Forum_category_id="",topicpage="";
    TextView category_return_txt;
    String Forum_topics_id="";

    EditText topic_name_ed_txt,topic_description_ed_txt;
    TextView submit_topic;

    String topic_name_val="",topic_description_val="";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Add Topic");
        initViews();
    }

    public void initViews()
    {

        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        Forum_category_id= intent.getStringExtra("Forum_category_id");
        topicpage= intent.getStringExtra("topicpage");
        Forum_topics_id= intent.getStringExtra("Forum_topics_id");

        Log.i("respo_category_name=", "" + category_name + " and " + "Forum_category_id=" + Forum_category_id
                + " and " + "Forum_topics_id=" + Forum_topics_id + " and " + "topicpage=" + topicpage);
        // setHeaderTitele(category_name_val);

        linear=(LinearLayout)findViewById(R.id.linear);


        topic_name_ed_txt=(EditText)findViewById(R.id.topic_name_ed_txt);
        topic_description_ed_txt=(EditText)findViewById(R.id.topic_description_ed_txt);
        submit_topic=(TextView)findViewById(R.id.submit_topic);

        submit_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                topic_name_val = topic_name_ed_txt.getText().toString().trim();
                topic_description_val = topic_description_ed_txt.getText().toString().trim();

                if (topic_name_val.length() > 0) {
                    if (topic_description_val.length() > 0) {
                        initAddForumTopicAPI(topic_name_val, topic_description_val);
                    } else {
                        // Toast.makeText(mContext,"Enter allergy!",Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar
                                .make(linear, "Enter topic description!", Snackbar.LENGTH_LONG)
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


                } else {
                    // Toast.makeText(mContext,"Enter allergy!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Enter topic name!", Snackbar.LENGTH_LONG)
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




    public void initAddForumTopicAPI(String Forum_topic_name,String Forum_description)
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

            new ForumManager(mContext).initAddForumTopicAPI(Forum_category_id, Forum_topic_name, Forum_description,
                    new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    if (responce.equals("100"))
                    {
                      /*  Need to update all service like topic and details*/
                        initgetForumCategoryAPI();
                    }
                    else
                    {
                        materialDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(linear, "" + responce, Snackbar.LENGTH_LONG)
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
                    .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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



    public void initgetForumCategoryAPI()
    {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            new ForumManager(mContext).initgetForumCategoryAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    if (responce.equals("100"))
                    {
                        initForumTopicsAPI();
                    }
                    else
                    {
                        materialDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(linear, "" + responce, Snackbar.LENGTH_LONG)
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
                            (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    // Check for network connections
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                        Snackbar snackbar = Snackbar
                                .make(linear, "" + responce, Snackbar.LENGTH_LONG)
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
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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
        else {
            materialDialog.dismiss();

            Snackbar snackbar = Snackbar
                    .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        Toast.makeText(getApplicationContext(),"Topic added successfully!",
                                Toast.LENGTH_LONG).show();

                        topic_name_ed_txt.setText("");
                        topic_description_ed_txt.setText("");


                        Intent intent=new Intent(ForumAddTopicActivity.this,ForumTopicsActivity.class);
                        intent.putExtra("category_name",category_name);
                        intent.putExtra("Forum_category_id",Forum_category_id);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar
                                .make(linear, "" + responce, Snackbar.LENGTH_LONG)
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
                    .make(linear, "No internet connection!", Snackbar.LENGTH_LONG)
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





    @Override
    public void onBackPressed()
    {

        if (topicpage.equalsIgnoreCase("topicpage"))
        {
            Intent intent=new Intent(ForumAddTopicActivity.this,ForumTopicsActivity.class);
            intent.putExtra("category_name",category_name);
            intent.putExtra("Forum_category_id",Forum_category_id);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent=new Intent(ForumAddTopicActivity.this,ForumTopicsDetailsActivity.class);
            intent.putExtra("category_name",category_name);
            intent.putExtra("Forum_category_id",Forum_category_id);
            intent.putExtra("Forum_topics_id",Forum_topics_id);
            startActivity(intent);
            finish();
        }

    }

}

