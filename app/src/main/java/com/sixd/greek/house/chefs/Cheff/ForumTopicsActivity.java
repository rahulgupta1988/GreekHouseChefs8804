package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.TopicAdapterSearch;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dao_db.AllCategoryTopics;
import dao_db.AllFormCategory;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class ForumTopicsActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView list_menu_development;
    RelativeLayout realative_linear;
    Intent intent;
    String category_name="",Forum_category_id="";
    List<AllCategoryTopics> allCategoryTopics;
    TextView category_return_txt_topics,add_topic_txt_topics;
    String Forum_topics_id="";
    String topicpage="topicpage";
    EditText search_txt;
    TopicAdapterSearch adapter_newTest=null;
    List<AllCategoryTopics> temp_llPackageCategoryLists;
    RelativeLayout rl_search;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Forum Topics");
        initViews();
    }

    public void initViews()
    {
        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        Forum_category_id= intent.getStringExtra("Forum_category_id");
        Log.i("respo_category_name=", "" + category_name+" and "+"Forum_category_id="+Forum_category_id);
        // setHeaderTitele(category_name_val);

        try
        {
            String result = upperCaseFirst(category_name);
            header_txt.setText("" + result.toString().trim());
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) header_txt.getLayoutParams();
            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(0,0,80,0);
            header_txt.setLayoutParams(lp);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        allCategoryTopics = new ForumManager(ForumTopicsActivity.this).getallCategoryTopics();
        Log.i("respo_Categ_Topics_size", ""+allCategoryTopics.size());

        temp_llPackageCategoryLists = new ArrayList<AllCategoryTopics>();


        category_return_txt_topics=(TextView)findViewById(R.id.category_return_txt_topics);
        category_return_txt_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumTopicsActivity.this, ForumCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });


        add_topic_txt_topics=(TextView)findViewById(R.id.add_topic_txt_topics);
        add_topic_txt_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(ForumTopicsActivity.this,ForumAddTopicActivity.class);
                intent.putExtra("category_name",category_name);
                intent.putExtra("Forum_category_id",Forum_category_id);
                intent.putExtra("topicpage",topicpage);
                startActivity(intent);
                finish();
            }
        });


        rl_search=(RelativeLayout)findViewById(R.id.rl_search);
        rl_search.setVisibility(View.GONE);
        if (allCategoryTopics.size() > 0)
        {
            rl_search.setVisibility(View.VISIBLE);
        }
        else
        {
            rl_search.setVisibility(View.GONE);
        }

        realative_linear=(RelativeLayout)findViewById(R.id.realative_linear);
        search_txt=(EditText)findViewById(R.id.search_txt);
        list_menu_development=(ListView) findViewById(R.id.list_menu_development);
        list_menu_development.setDivider(null);

        /*list_menu_development.setAdapter(new ForumTopicsAdapter(ForumTopicsActivity.this, allCategoryTopics));*/

        for (int x = 0; x < allCategoryTopics.size(); x++)
        {
            temp_llPackageCategoryLists.add(allCategoryTopics.get(x));
        }

        if (allCategoryTopics.size() > 0)
        {
            adapter_newTest = new TopicAdapterSearch(ForumTopicsActivity.this, R.layout.forum_topics_adapter,
                    temp_llPackageCategoryLists,category_name,Forum_category_id);
            list_menu_development.setAdapter(adapter_newTest);
        }

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String s = String.valueOf(cs);
                if (!s.equalsIgnoreCase(null)) {
                    ForumTopicsActivity.this.adapter_newTest.getFilter().filter(cs);
                    List<AllCategoryTopics> asd = new ArrayList<AllCategoryTopics>();
                    for (int i = 0; i < temp_llPackageCategoryLists.size(); i++) {
                        if (Pattern.compile(Pattern.quote(s), Pattern.CASE_INSENSITIVE).matcher(temp_llPackageCategoryLists.get(i).getTopic_name()).find()) {
                            asd.add(temp_llPackageCategoryLists.get(i));
                        }
                    }
                    adapter_newTest = new TopicAdapterSearch(ForumTopicsActivity.this, R.layout.forum_category_adapter, asd,
                            category_name,Forum_category_id);
                    list_menu_development.setAdapter(adapter_newTest);
                } else {
                    adapter_newTest = new TopicAdapterSearch(ForumTopicsActivity.this, R.layout.forum_category_adapter,
                            temp_llPackageCategoryLists,category_name,Forum_category_id);
                    list_menu_development.setAdapter(adapter_newTest);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });




       /* list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                  Forum_topics_id = allCategoryTopics.get(position).getForum_topics_id();

                    materialDialog.show();

                    new ForumManager(mContext).initForumTopicDetailsAPI(Forum_topics_id, new CallBackManager()
                    {
                        @Override
                        public void onSuccess(String responce) {
                            materialDialog.dismiss();
                            if (responce.equals("100"))
                            {
                                Intent forum_intent = new Intent(mContext, ForumTopicsDetailsActivity.class);
                                forum_intent.putExtra("category_name",category_name);
                                forum_intent.putExtra("Forum_topics_id",Forum_topics_id);
                                forum_intent.putExtra("Forum_category_id",Forum_category_id);
                                startActivity(forum_intent);
                                finish();
                            }
                            else
                            {
                                Snackbar snackbar = Snackbar
                                        .make(realative_linear, "" + responce, Snackbar.LENGTH_LONG)
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
                                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                            {
                                Snackbar snackbar = Snackbar
                                        .make(realative_linear,""+responce, Snackbar.LENGTH_LONG)
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
                                        .make(realative_linear, "No internet connection!", Snackbar.LENGTH_LONG)
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
        });*/




    }


    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForumTopicsActivity.this,ForumCategoryActivity.class);
        startActivity(intent);
        finish();
    }

}

