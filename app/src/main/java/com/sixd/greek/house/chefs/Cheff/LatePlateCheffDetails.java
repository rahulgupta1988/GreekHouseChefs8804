package com.sixd.greek.house.chefs.Cheff;

import android.app.Dialog;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.CheffAdapter.AddMenuAdapterBreakFast;
import com.sixd.greek.house.chefs.CheffAdapter.LatePlateDetailsAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.LatePlateDetailsAdapterDinner;
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.MenuItemAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.StudentListAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;
import com.sixd.greek.house.chefs.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllDinnerAllergyList;
import dao_db.AllLunchAllergyList;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class LatePlateCheffDetails extends HeaderActivtyCheff {
    Context mContext;
    MaterialDialog materialDialog=null;
    ListView allergy_lunch_list,allergy_dinner_list;
    LinearLayout linear;
    Intent intent;
    List<AllLunchAllergyList> getAllLunchAllergyLists;
    List<AllDinnerAllergyList> getAllDinnerAllergyLists;
    LinearLayout  lunch_ll,dinner_ll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Allergy Details");
        initViews();
    }

    public void initViews()
    {
        linear=(LinearLayout)findViewById(R.id.linear);
        allergy_lunch_list=(ListView) findViewById(R.id.allergy_lunch_list);
        allergy_dinner_list=(ListView) findViewById(R.id.allergy_dinner_list);

        lunch_ll=(LinearLayout)findViewById(R.id.lunch_ll);
        dinner_ll=(LinearLayout)findViewById(R.id.dinner_ll);

        getAllLunchAllergyLists = new LatePlateManagerCheff(LatePlateCheffDetails.this).getAllLunchAllergyLists();
        Log.i("respo_LunchAllergy_size", "" + getAllLunchAllergyLists.size());
        getAllDinnerAllergyLists = new LatePlateManagerCheff(LatePlateCheffDetails.this).getAllDinnerAllergyLists();
        Log.i("respo_DinerAllergy_size", "" + getAllDinnerAllergyLists.size());

        if (getAllLunchAllergyLists.size()>0)
        {
            lunch_ll.setVisibility(View.VISIBLE);
            allergy_lunch_list.setDivider(null);
            allergy_lunch_list.setAdapter(new LatePlateDetailsAdapter(LatePlateCheffDetails.this, getAllLunchAllergyLists));
        }
        else
        {
            lunch_ll.setVisibility(View.GONE);
        }

        if (getAllDinnerAllergyLists.size()>0)
        {
            dinner_ll.setVisibility(View.VISIBLE);
            allergy_dinner_list.setDivider(null);
            allergy_dinner_list.setAdapter(new LatePlateDetailsAdapterDinner(LatePlateCheffDetails.this, getAllDinnerAllergyLists));
        }
        else
        {
            dinner_ll.setVisibility(View.GONE);
        }




        allergy_lunch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String student_name_lunch_list = getAllLunchAllergyLists.get(position).getStudent_name();
                String Allergy_name = getAllLunchAllergyLists.get(position).getAllergy_name();
                Log.i("respo_list_lunch", "" + student_name_lunch_list);

                Constant.ListElements_NUMBERS.clear();
                String s = student_name_lunch_list;
                String[] array = s.split("\\|");
                for (String word : array) {
                    System.out.println(word);
                    Constant.ListElements_NUMBERS.add(word);

                }
                Log.i("respo_ed_size", "" + Constant.ListElements_NUMBERS.size());
                if (Constant.ListElements_NUMBERS.size() != 0)
                {
                    ShowStudentListDialog(Allergy_name);
                }
            }
        });

        allergy_dinner_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String student_name_dinner_list = getAllDinnerAllergyLists.get(position).getStudent_name();
                String Allergy_name = getAllDinnerAllergyLists.get(position).getAllergy_name();
                Log.i("respo_list_dinner", "" + student_name_dinner_list);

                Constant.ListElements_NUMBERS.clear();
                String s = student_name_dinner_list;
                String[] array = s.split("\\|");
                for (String word : array) {
                    System.out.println(word);
                    Constant.ListElements_NUMBERS.add(word);

                }
                Log.i("respo_ed_size", "" + Constant.ListElements_NUMBERS.size());
                if (Constant.ListElements_NUMBERS.size() != 0) {
                    ShowStudentListDialog(Allergy_name);
                }
            }
        });
    }




    public void ShowStudentListDialog(String Allergy_name)
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
       /* dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);*/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.student_list);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        int width = ViewGroup.LayoutParams.FILL_PARENT;
        int height = ViewGroup.LayoutParams.FILL_PARENT;
        dialog.getWindow().setLayout(width, height);
        TextView btn_cancel_ll=(TextView)window.findViewById(R.id.btn_cancel_ll);
       // TextView btn_ok_ll=(TextView)window.findViewById(R.id.btn_ok_ll);

        TextView dialogTex=(TextView)window.findViewById(R.id.dialogTex);
        dialogTex.setText(""+Allergy_name);
        ListView student_list=(ListView)window.findViewById(R.id.student_list);
        student_list.setAdapter(new StudentListAdapter(mContext, Constant.ListElements_NUMBERS));


        //Window window = this.getWindow();
    /*    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);


       /* btn_ok_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });*/


        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }



    @Override
    public void onBackPressed() {
        Constant.ListElements_NUMBERS.clear();
        Intent intent=new Intent(LatePlateCheffDetails.this,LatePlateChefActivity.class);
        startActivity(intent);
        finish();
    }

}

