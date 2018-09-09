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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;

import java.util.ArrayList;
import java.util.List;

import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class MenuDevCategoryActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    TabLayout menu_tabs;
    ViewPager menuPager;
    TextView user_name;
    ImageView profile_icon;
    ListView list_menu_development;
    MenuDevelopmentModel menuDevelopmentModel;
    LinearLayout linear;
    String parent_id="",category_name="",menu_category_id="";
    Intent intent;

    List<MenuCategory> getMenuCategories;
    List<MenuCategory> temp_ll_MenuCategory;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Menu Development");
        initViews();
    }

    public void initViews()
    {
        list_menu_development=(ListView) findViewById(R.id.list_menu_development);
        list_menu_development.setDivider(null);
        linear=(LinearLayout)findViewById(R.id.linear);

        getMenuCategories = new MenuDevelopmentManager(MenuDevCategoryActivity.this).getmMenuCategories();
        temp_ll_MenuCategory=new ArrayList<MenuCategory>();
        temp_ll_MenuCategory.clear();
        Log.i("respo_menulist_size", ""+getMenuCategories.size());

        for(int x=0;x<getMenuCategories.size();x++)
        {
            if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase("null"))
            {
                temp_ll_MenuCategory.add(getMenuCategories.get(x));
            }
        }

            list_menu_development.setAdapter(new MenuCategoryAdapter(MenuDevCategoryActivity.this, temp_ll_MenuCategory));

            list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    if (temp_ll_MenuCategory.get(position).getMenu_category_id() != null
                            && !temp_ll_MenuCategory.get(position).getMenu_category_id().equalsIgnoreCase("")
                            && !temp_ll_MenuCategory.get(position).getMenu_category_id().equalsIgnoreCase("null"))
                    {
                        parent_id = temp_ll_MenuCategory.get(position).getParent_id();
                        category_name=temp_ll_MenuCategory.get(position).getCategory_name();
                        menu_category_id=temp_ll_MenuCategory.get(position).getMenu_category_id();

                        String check="1";

                        for(int x=0;x<getMenuCategories.size();x++)
                        {
                            if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase(menu_category_id))
                            {
                                check="2";
                            }
                        }

                        if (check.equalsIgnoreCase("1"))
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Sub Category is not available!", Snackbar.LENGTH_LONG)
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
                        else
                        {
                            intent=new Intent(mContext,MenuDevItemsActivity.class);
                            intent.putExtra("menu_category_id",menu_category_id);
                            intent.putExtra("category_name",category_name);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
            });

    }


    public void initMenuDevelopmentAPI()
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

            new MenuDevelopmentManager(mContext).initMenuDevelopmentAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce)
                {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        final List<MenuCategory> getMenuCategories;
                        final List<MenuCategory> temp_ll_MenuCategory;
                        getMenuCategories = new MenuDevelopmentManager(MenuDevCategoryActivity.this).getmMenuCategories();
                        temp_ll_MenuCategory=new ArrayList<MenuCategory>();
                        temp_ll_MenuCategory.clear();
                        Log.i("respo_menulist_size", ""+getMenuCategories.size());

                        for(int x=0;x<getMenuCategories.size();x++)
                        {
                            if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase("null"))
                            {
                                temp_ll_MenuCategory.add(getMenuCategories.get(x));
                            }
                        }

                        if(temp_ll_MenuCategory.size()>0)
                        {

                            list_menu_development.setAdapter(new MenuCategoryAdapter(MenuDevCategoryActivity.this, temp_ll_MenuCategory));

                            list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                        ConnectivityManager connec1 =
                                                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                                        // Check for network connections
                                        if (connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                                connec1.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                                connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                                connec1.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                                        {
                                            if (temp_ll_MenuCategory.get(position).getMenu_category_id() != null
                                                    && !temp_ll_MenuCategory.get(position).getMenu_category_id().equalsIgnoreCase("")
                                                    && !temp_ll_MenuCategory.get(position).getMenu_category_id().equalsIgnoreCase("null"))
                                            {
                                                parent_id = temp_ll_MenuCategory.get(position).getParent_id();
                                                category_name=temp_ll_MenuCategory.get(position).getCategory_name();
                                                menu_category_id=temp_ll_MenuCategory.get(position).getMenu_category_id();

                                                String check="1";

                                                for(int x=0;x<getMenuCategories.size();x++)
                                                {
                                                    if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase(menu_category_id))
                                                    {
                                                        check="2";
                                                    }
                                                }

                                                if (check.equalsIgnoreCase("1"))
                                                {
                                                    Snackbar snackbar = Snackbar
                                                            .make(linear, "Sub Category is not available!", Snackbar.LENGTH_LONG)
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
                                                else
                                                {
                                                    intent=new Intent(mContext,MenuDevItemsActivity.class);
                                                    intent.putExtra("menu_category_id",menu_category_id);
                                                    intent.putExtra("category_name",category_name);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
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
                        else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Menu Category is not available!", Snackbar.LENGTH_LONG)
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
                    else {
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
                }

                @Override
                public void onFailure(String responce) {
                    materialDialog.dismiss();
                }
            });
        }
        else
        {
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


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MenuDevCategoryActivity.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }

}

