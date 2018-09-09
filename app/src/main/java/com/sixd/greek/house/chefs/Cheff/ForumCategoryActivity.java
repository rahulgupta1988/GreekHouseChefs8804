package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.CategoryAdapterSearch;
import com.sixd.greek.house.chefs.ManagerCheff.ForumManager;
import com.sixd.greek.house.chefs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dao_db.AllFormCategory;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class ForumCategoryActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView list_menu_development;
    LinearLayout linear;
    Intent intent;
    List<AllFormCategory> allFormCategories;
    String Forum_category_id="",forum_category_name="";
    EditText search_txt;

    CategoryAdapterSearch adapter_newTest=null;
    List<AllFormCategory> temp_llPackageCategoryLists;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Forum");
        initViews();
    }

    public void initViews()
    {
        allFormCategories = new ForumManager(ForumCategoryActivity.this).getAllFormCategories();
        Log.i("respo_allFormCateg_size", ""+allFormCategories.size());

        temp_llPackageCategoryLists = new ArrayList<AllFormCategory>();

        linear=(LinearLayout)findViewById(R.id.linear);
        search_txt=(EditText)findViewById(R.id.search_txt);
        list_menu_development=(ListView) findViewById(R.id.list_menu_development);
        list_menu_development.setDivider(null);

        for (int x = 0; x < allFormCategories.size(); x++)
        {
            temp_llPackageCategoryLists.add(allFormCategories.get(x));
        }

        adapter_newTest = new CategoryAdapterSearch(ForumCategoryActivity.this, R.layout.forum_category_adapter, temp_llPackageCategoryLists);
        list_menu_development.setAdapter(adapter_newTest);


        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                // When user changed the Text
                String s = String.valueOf(cs);
                if (!s.equalsIgnoreCase(null))
                {
                    ForumCategoryActivity.this.adapter_newTest.getFilter().filter(cs);
                    List<AllFormCategory> asd = new ArrayList<AllFormCategory>();
                    for (int i = 0; i < temp_llPackageCategoryLists.size(); i++)
                    {
                        if (Pattern.compile(Pattern.quote(s), Pattern.CASE_INSENSITIVE).matcher(temp_llPackageCategoryLists.get(i).getCategory_name()).find()) {
                            asd.add(temp_llPackageCategoryLists.get(i));
                        }
                    }
                    adapter_newTest = new CategoryAdapterSearch(ForumCategoryActivity.this, R.layout.forum_category_adapter, asd);
                    list_menu_development.setAdapter(adapter_newTest);
                }
                else
                {
                    adapter_newTest = new CategoryAdapterSearch(ForumCategoryActivity.this, R.layout.forum_category_adapter, temp_llPackageCategoryLists);
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


    /*    list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                      Forum_category_id = allFormCategories.get(position).getForum_category_id();
                      forum_category_name=allFormCategories.get(position).getCategory_name();
                      Log.i("respo_forum_categ_name=", "" + forum_category_name);

                    materialDialog.show();
                    new ForumManager(mContext).initGetForumtopicsAPI(Forum_category_id, new CallBackManager()
                    {
                        @Override
                        public void onSuccess(String responce) {
                            materialDialog.dismiss();
                            if (responce.equals("100"))
                            {
                                List<AllCategoryTopics> allCategoryTopics;
                                allCategoryTopics = new ForumManager(ForumCategoryActivity.this).getallCategoryTopics();
                                Log.i("respo_Categ_Topics_size", "" + allCategoryTopics.size());

                                if (allCategoryTopics.size() > 0)
                                {
                                    Intent forum_intent = new Intent(mContext, ForumTopicsActivity.class);
                                    forum_intent.putExtra("category_name",forum_category_name);
                                    forum_intent.putExtra("Forum_category_id",Forum_category_id);
                                    startActivity(forum_intent);
                                    finish();
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(linear, "Category topics is not available!", Snackbar.LENGTH_LONG)
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
                                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                            {
                                Snackbar snackbar = Snackbar
                                        .make(linear,""+responce, Snackbar.LENGTH_LONG)
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
        Intent intent=new Intent(ForumCategoryActivity.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }

}

