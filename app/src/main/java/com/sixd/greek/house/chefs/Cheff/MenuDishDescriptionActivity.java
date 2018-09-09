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

public class MenuDishDescriptionActivity extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout linear;
    Intent intent;
    String category_name="",sub_category_id_val="",sub_category_name_val="";
    String menu_category_id="";

    String menu_id="";
    List<MenuItemDishes> getMenuItemDishesList;

    TextView dish_name_txt,description_txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Dish Description");
        initViews();
    }

    public void initViews()
    {

        dish_name_txt=(TextView)findViewById(R.id.dish_name_txt);
        description_txt=(TextView)findViewById(R.id.description_txt);

        getMenuItemDishesList= new MenuDevelopmentManager(MenuDishDescriptionActivity.this).getMenuItemDishes();

        intent = getIntent();
        category_name = intent.getStringExtra("category_name");
        sub_category_id_val = intent.getStringExtra("sub_category_id_val");
        sub_category_name_val = intent.getStringExtra("sub_category_name");
        menu_category_id= intent.getStringExtra("menu_category_id");
        menu_id= intent.getStringExtra("menu_id");
        Log.i("respo_sub_cat_id_val=", "" + sub_category_id_val + " & " + "sub_category_name_val="
                + "" + sub_category_name_val+" & "+"menu_id="+menu_id);
        //setHeaderTitele(sub_category_name_val);



        for(int x=0;x<getMenuItemDishesList.size();x++)
        {
            if (getMenuItemDishesList.get(x).getMenu_id().toString().trim().equalsIgnoreCase(menu_id))
            {
                Log.i("respo_Master=", "" + getMenuItemDishesList.get(x).getMenu_id().toString().trim()+" & "+"menu_id="+menu_id);

                String result = upperCaseFirst(getMenuItemDishesList.get(x).getMenu_title());
                dish_name_txt.setText(result.toString().trim());
                description_txt.setText(getMenuItemDishesList.get(x).getDescription());
            }
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


    @Override
    public void onBackPressed()
    {
        intent=new Intent(mContext,MenuDevItemsDishActivity.class);
        intent.putExtra("sub_category_id_val",sub_category_id_val);
        intent.putExtra("sub_category_name",sub_category_name_val);
        intent.putExtra("category_name",category_name);
        intent.putExtra("menu_category_id",menu_category_id);
        intent.putExtra("menu_id",menu_id);
        startActivity(intent);
        finish();
    }

}

