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
import com.sixd.greek.house.chefs.CheffAdapter.LatePlateDetailsAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.LatePlateDetailsAdapterDinner;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.SubmitBudgetAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.BudgetManager;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllDinnerAllergyList;
import dao_db.AllLunchAllergyList;
import dao_db.AllPastBudgetInfo;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class PastBudgetInfoDetails extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView past_budget_list;
    LinearLayout linear;
    Intent intent;
    List<AllPastBudgetInfo> getAllPastBudgetInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Past Budget Details");
        initViews();
    }

    public void initViews()
    {
        getAllPastBudgetInfo = new BudgetManager(PastBudgetInfoDetails.this).getAllPastBudgetInfo();

        linear=(LinearLayout)findViewById(R.id.linear);
        past_budget_list=(ListView)findViewById(R.id.past_budget_list);
        past_budget_list.setDivider(null);
        past_budget_list.setAdapter(new SubmitBudgetAdapter(PastBudgetInfoDetails.this, getAllPastBudgetInfo));

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PastBudgetInfoDetails.this,SubmitBudgetCheff.class);
        startActivity(intent);
        finish();
    }

}

