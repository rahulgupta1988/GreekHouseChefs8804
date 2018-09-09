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
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemDishAdapter;
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

public class MenuDevItemsDishActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    TabLayout menu_tabs;
    ViewPager menuPager;
    TextView user_name;
    ImageView profile_icon;
    ListView list_menu_development;
    MenuDevelopmentModel menuDevelopmentModel;
    LinearLayout linear;
    String category_id="",Sub_category_id="";
    List<MenuCategory> getMenuCategories;
    List<MenuItemDishes> getMenuItemDishesList;
    List<MenuItemDishes> temp_ll_MenuItemDishes;
    Intent intent;
    String category_name="",sub_category_id_val="",sub_category_name_val="";
    String menu_category_id="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Menu Dish");
        initViews();
    }

    public void initViews()
    {
        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        sub_category_id_val = intent.getStringExtra("sub_category_id_val");
        sub_category_name_val = intent.getStringExtra("sub_category_name");
        menu_category_id= intent.getStringExtra("menu_category_id");
        Log.i("respo_sub_cat_id_val=", "" + sub_category_id_val + " & " + "sub_category_name_val=" + "" + sub_category_name_val);
        //setHeaderTitele(sub_category_name_val);

        try
        {
            String result = upperCaseFirst(sub_category_name_val);
            header_txt.setText("" + result.toString().trim());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        getMenuCategories = new MenuDevelopmentManager(MenuDevItemsDishActivity.this).getmMenuCategories();
        getMenuItemDishesList= new MenuDevelopmentManager(MenuDevItemsDishActivity.this).getMenuItemDishes();
        Log.i("respo_menuCatlist_size", ""+getMenuCategories.size());
        Log.i("respo_menuItemDish_size", ""+getMenuItemDishesList.size());

        temp_ll_MenuItemDishes=new ArrayList<MenuItemDishes>();

        linear=(LinearLayout)findViewById(R.id.linear);

        list_menu_development=(ListView) findViewById(R.id.list_menu_development);
        list_menu_development.setDivider(null);
        list_menu_development.setAdapter(new MenuItemDishAdapter(MenuDevItemsDishActivity.this, getMenuItemDishesList));


        list_menu_development.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String menu_id = getMenuItemDishesList.get(position).getMenu_id().toString().trim();

                intent=new Intent(mContext,MenuDishDescriptionActivity.class);
                intent.putExtra("sub_category_id_val",sub_category_id_val);
                intent.putExtra("sub_category_name",sub_category_name_val);
                intent.putExtra("category_name",category_name);
                intent.putExtra("menu_category_id",menu_category_id);
                intent.putExtra("menu_id",menu_id);
                startActivity(intent);
                finish();
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
        Intent intent=new Intent(MenuDevItemsDishActivity.this,MenuDevItemsActivity.class);
        intent.putExtra("category_name",category_name);
        intent.putExtra("menu_category_id",menu_category_id);
        startActivity(intent);
        finish();
    }

}

