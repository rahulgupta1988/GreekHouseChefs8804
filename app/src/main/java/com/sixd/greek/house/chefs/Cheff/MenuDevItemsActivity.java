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
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
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

public class MenuDevItemsActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView list_menu_development;
    LinearLayout linear;
    String sub_category_id="",sub_category_name="";
    Intent intent;
    String category_name="",menu_category_id="";

    List<MenuCategory> getMenuCategories;
    List<MenuCategory> temp_ll_MenuCategory;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Menu Bank Options");
        initViews();
    }

    public void initViews()
    {
        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        menu_category_id= intent.getStringExtra("menu_category_id");
        Log.i("respo_category_name=", "" + category_name + " menu_category_id=" + menu_category_id);
       // setHeaderTitele(category_name_val);


        try
        {
            String result = upperCaseFirst(category_name);
            header_txt.setText(""+result.toString().trim());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        getMenuCategories = new MenuDevelopmentManager(MenuDevItemsActivity.this).getmMenuCategories();
        temp_ll_MenuCategory=new ArrayList<MenuCategory>();
        temp_ll_MenuCategory.clear();
        Log.i("respo_menulist_size", "" + getMenuCategories.size());

        for(int x=0;x<getMenuCategories.size();x++)
        {
            if (getMenuCategories.get(x).getParent_id().equalsIgnoreCase(menu_category_id))
            {
                temp_ll_MenuCategory.add(getMenuCategories.get(x));
            }
        }


        Log.i("respo_menuCatlist_size", ""+getMenuCategories.size());
        Log.i("respo_temp_menuItemsize", ""+temp_ll_MenuCategory.size());

        linear=(LinearLayout)findViewById(R.id.linear);
        //initMenuDevelopmentAPI();

        list_menu_development=(ListView) findViewById(R.id.list_menu_development);
        list_menu_development.setDivider(null);
        list_menu_development.setAdapter(new MenuItemAdapter(MenuDevItemsActivity.this, temp_ll_MenuCategory));

        list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                {
                    String menu_category_id_server_call = temp_ll_MenuCategory.get(position).getMenu_category_id();
                    sub_category_name=temp_ll_MenuCategory.get(position).getCategory_name();

                    materialDialog.show();

                    new MenuDevelopmentManager(mContext).initMenuItemsAPI(menu_category_id_server_call, new CallBackManager() {
                        @Override
                        public void onSuccess(String responce)
                        {
                            materialDialog.dismiss();
                            if (responce.equals("100"))
                            {
                                List<MenuItemDishes> getMenuItemDishesList;
                                getMenuItemDishesList= new MenuDevelopmentManager(MenuDevItemsActivity.this).getMenuItemDishes();
                                if(getMenuItemDishesList.size()>0)
                                {
                                    intent=new Intent(mContext,MenuDevItemsDishActivity.class);
                                    intent.putExtra("sub_category_id_val",sub_category_id);
                                    intent.putExtra("sub_category_name",sub_category_name);
                                    intent.putExtra("category_name",category_name);
                                    intent.putExtra("menu_category_id",menu_category_id);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(linear, "Menu items is not available!", Snackbar.LENGTH_LONG)
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

            }
        });

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
        Intent intent=new Intent(MenuDevItemsActivity.this,MenuDevCategoryActivity.class);
        startActivity(intent);
        finish();
    }

}

