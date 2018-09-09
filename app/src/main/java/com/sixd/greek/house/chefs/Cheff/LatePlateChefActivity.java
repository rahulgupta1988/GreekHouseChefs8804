package com.sixd.greek.house.chefs.Cheff;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
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
import com.sixd.greek.house.chefs.CheffAdapter.MenuCategoryAdapter;
import com.sixd.greek.house.chefs.CheffAdapter.StudentListAdapter;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import dao_db.AllDinnerAllergyList;
import dao_db.AllLatePlateChefList;
import dao_db.AllLunchAllergyList;
import dao_db.MenuCategory;
import dao_db.MenuItemDishes;
import dao_db.StudenNameDinner;
import dao_db.StudenNameLunch;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class LatePlateChefActivity extends HeaderActivtyCheff {

    TextView date_range;

    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout linear;
    Intent intent;
    TextView mon_txt_lunch,mon_txt_dinner,mon_txt_allergy,mon_txt_allergy1,tue_txt_lunch,tue_txt_dinner,tue_txt_allergy,tue_txt_allergy1,
            wed_txt_lunch,wed_txt_dinner,wed_txt_allergy,wed_txt_allergy1,thu_txt_lunch,thu_txt_dinner,thu_txt_allergy,thu_txt_allergy1,
            fri_txt_lunch,fri_txt_dinner,fri_txt_allergy,fri_txt_allergy1,sat_txt_lunch,sat_txt_dinner,sat_txt_allergy,sat_txt_allergy1,
            sun_txt_lunch,sun_txt_dinner,sun_txt_allergy,sun_txt_allergy1;


    String date_range_val,mon_txt_lunch_val,mon_txt_dinner_val,tue_txt_lunch_val,tue_txt_dinner_val,
            wed_txt_lunch_val,wed_txt_dinner_val,thu_txt_lunch_val,thu_txt_dinner_val,
            fri_txt_lunch_val,fri_txt_dinner_val,sat_txt_lunch_val,sat_txt_dinner_val,
            sun_txt_lunch_val,sun_txt_dinner_val;

    ImageView monday_img,tuesday_img,wednesday_img,thursday_img,friday_img,saturday_img,sunday_img;

    List<AllLatePlateChefList> allLatePlateChefLists;
    List<StudenNameLunch> getStudenNameLunch;
    List<StudenNameDinner> getStudenNameDinners;

    String Date="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Late Plate Numbers");
        initViews();
    }

    public void initViews()
    {

        allLatePlateChefLists = new LatePlateManagerCheff(LatePlateChefActivity.this).getAllLatePlateChefLists();
        Log.i("respo_LatePlatChef_size", "" + allLatePlateChefLists.size());
        getStudenNameLunch=new LatePlateManagerCheff(LatePlateChefActivity.this).getStudenNameLunch();
        getStudenNameDinners=new LatePlateManagerCheff(LatePlateChefActivity.this).getStudenNameDinners();
        Log.i("respo_getStuden_Lunc_Si", "" + getStudenNameLunch.size());
        Log.i("respo_getStuden_Dinn_Si", "" + getStudenNameDinners.size());


        linear=(LinearLayout)findViewById(R.id.linear);
        monday_img=(ImageView)findViewById(R.id.monday_img);
        tuesday_img=(ImageView)findViewById(R.id.tuesday_img);
        wednesday_img=(ImageView)findViewById(R.id.wednesday_img);
        thursday_img=(ImageView)findViewById(R.id.thursday_img);
        friday_img=(ImageView)findViewById(R.id.friday_img);
        saturday_img=(ImageView)findViewById(R.id.saturday_img);
        sunday_img=(ImageView)findViewById(R.id.sunday_img);


        if (allLatePlateChefLists.get(0).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(0).getDinner_allergy().equalsIgnoreCase("0"))
        {
            monday_img.setVisibility(View.GONE);
        }
        else
        {
            monday_img.setVisibility(View.VISIBLE);
        }

        if (allLatePlateChefLists.get(1).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(1).getDinner_allergy().equalsIgnoreCase("0"))
        {
            tuesday_img.setVisibility(View.GONE);
        }
        else
        {
            tuesday_img.setVisibility(View.VISIBLE);
        }
        if (allLatePlateChefLists.get(2).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(2).getDinner_allergy().equalsIgnoreCase("0"))
        {
            wednesday_img.setVisibility(View.GONE);
        }
        else
        {
            wednesday_img.setVisibility(View.VISIBLE);
        }
        if (allLatePlateChefLists.get(3).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(3).getDinner_allergy().equalsIgnoreCase("0"))
        {
            thursday_img.setVisibility(View.GONE);
        }
        else
        {
            thursday_img.setVisibility(View.VISIBLE);
        }
        if (allLatePlateChefLists.get(4).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(4).getDinner_allergy().equalsIgnoreCase("0"))
        {
            friday_img.setVisibility(View.GONE);
        }
        else
        {
            friday_img.setVisibility(View.VISIBLE);
        }
        if (allLatePlateChefLists.get(5).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(5).getDinner_allergy().equalsIgnoreCase("0"))
        {
            saturday_img.setVisibility(View.GONE);
        }
        else
        {
            saturday_img.setVisibility(View.VISIBLE);
        }
        if (allLatePlateChefLists.get(6).getLunch_allergy().equalsIgnoreCase("0")
                && allLatePlateChefLists.get(6).getDinner_allergy().equalsIgnoreCase("0"))
        {
            sunday_img.setVisibility(View.GONE);
        }
        else
        {
            sunday_img.setVisibility(View.VISIBLE);
        }


        monday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(0).getDate();
                initGetlatePlateDetailsApi();
            }
        });
        tuesday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(1).getDate();
                initGetlatePlateDetailsApi();
            }
        });
        wednesday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(2).getDate();
                initGetlatePlateDetailsApi();
            }
        });

        thursday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(3).getDate();
                initGetlatePlateDetailsApi();
            }
        });
        friday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(4).getDate();
                initGetlatePlateDetailsApi();
            }
        });
        saturday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(5).getDate();
                initGetlatePlateDetailsApi();
            }
        });
        sunday_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date=allLatePlateChefLists.get(6).getDate();
                initGetlatePlateDetailsApi();
            }
        });


        date_range=(TextView)findViewById(R.id.date_range);

        mon_txt_lunch=(TextView)findViewById(R.id.mon_txt_lunch);
        mon_txt_dinner=(TextView)findViewById(R.id.mon_txt_dinner);
        mon_txt_allergy=(TextView)findViewById(R.id.mon_txt_allergy);
        mon_txt_allergy1=(TextView)findViewById(R.id.mon_txt_allergy1);
        tue_txt_lunch=(TextView)findViewById(R.id.tue_txt_lunch);
        tue_txt_dinner=(TextView)findViewById(R.id.tue_txt_dinner);
        tue_txt_allergy=(TextView)findViewById(R.id.tue_txt_allergy);
        tue_txt_allergy1=(TextView)findViewById(R.id.tue_txt_allergy1);
        wed_txt_lunch=(TextView)findViewById(R.id.wed_txt_lunch);
        wed_txt_dinner=(TextView)findViewById(R.id.wed_txt_dinner);
        wed_txt_allergy=(TextView)findViewById(R.id.wed_txt_allergy);
        wed_txt_allergy1=(TextView)findViewById(R.id.wed_txt_allergy1);
        thu_txt_lunch=(TextView)findViewById(R.id.thu_txt_lunch);
        thu_txt_dinner=(TextView)findViewById(R.id.thu_txt_dinner);
        thu_txt_allergy=(TextView)findViewById(R.id.thu_txt_allergy);
        thu_txt_allergy1=(TextView)findViewById(R.id.thu_txt_allergy1);

        fri_txt_lunch=(TextView)findViewById(R.id.fri_txt_lunch);
        fri_txt_dinner=(TextView)findViewById(R.id.fri_txt_dinner);
        fri_txt_allergy=(TextView)findViewById(R.id.fri_txt_allergy);
        fri_txt_allergy1=(TextView)findViewById(R.id.fri_txt_allergy1);
        sat_txt_lunch=(TextView)findViewById(R.id.sat_txt_lunch);
        sat_txt_dinner=(TextView)findViewById(R.id.sat_txt_dinner);
        sat_txt_allergy=(TextView)findViewById(R.id.sat_txt_allergy);
        sat_txt_allergy1=(TextView)findViewById(R.id.sat_txt_allergy1);
        sun_txt_lunch=(TextView)findViewById(R.id.sun_txt_lunch);
        sun_txt_dinner=(TextView)findViewById(R.id.sun_txt_dinner);
        sun_txt_allergy=(TextView)findViewById(R.id.sun_txt_allergy);
        sun_txt_allergy1=(TextView)findViewById(R.id.sun_txt_allergy1);


        date_range_val=allLatePlateChefLists.get(0).getWeek_interval();

        mon_txt_lunch_val=allLatePlateChefLists.get(0).getTotal_student_lunch().toString().trim();
        mon_txt_dinner_val=allLatePlateChefLists.get(0).getTotal_student_dinner().toString().trim();
        tue_txt_lunch_val=allLatePlateChefLists.get(1).getTotal_student_lunch().toString().trim();
        tue_txt_dinner_val=allLatePlateChefLists.get(1).getTotal_student_dinner().toString().trim();
        wed_txt_lunch_val=allLatePlateChefLists.get(2).getTotal_student_lunch().toString().trim();
        wed_txt_dinner_val=allLatePlateChefLists.get(2).getTotal_student_dinner().toString().trim();
        thu_txt_lunch_val=allLatePlateChefLists.get(3).getTotal_student_lunch().toString().trim();
        thu_txt_dinner_val=allLatePlateChefLists.get(3).getTotal_student_dinner().toString().trim();
        fri_txt_lunch_val=allLatePlateChefLists.get(4).getTotal_student_lunch().toString().trim();
        fri_txt_dinner_val=allLatePlateChefLists.get(4).getTotal_student_dinner().toString().trim();
        sat_txt_lunch_val=allLatePlateChefLists.get(5).getTotal_student_lunch().toString().trim();
        sat_txt_dinner_val=allLatePlateChefLists.get(5).getTotal_student_dinner().toString().trim();
        sun_txt_lunch_val=allLatePlateChefLists.get(6).getTotal_student_lunch().toString().trim();
        sun_txt_dinner_val=allLatePlateChefLists.get(6).getTotal_student_dinner().toString().trim();


        if (allLatePlateChefLists.get(0).getLunch_allergy() != null
                && !allLatePlateChefLists.get(0).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(0).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(0).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable1 = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable1.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable1.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable1.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mon_txt_allergy.setText(spannable1);
        }
        else {
            mon_txt_allergy.setText("-");
        }
        if (allLatePlateChefLists.get(0).getDinner_allergy() != null
                && !allLatePlateChefLists.get(0).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(0).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(0).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable2 = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable2.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable2.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable2.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mon_txt_allergy1.setText(spannable2);
        }
        else {
            mon_txt_allergy1.setText("-");
        }


        if (allLatePlateChefLists.get(1).getLunch_allergy() != null
                && !allLatePlateChefLists.get(1).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(1).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(1).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tue_txt_allergy.setText(spannable);
        }
        else
        {
            tue_txt_allergy.setText("-");
        }

         if (allLatePlateChefLists.get(1).getDinner_allergy() != null
                && !allLatePlateChefLists.get(1).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(1).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(1).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tue_txt_allergy1.setText(spannable);
        }
        else {
             tue_txt_allergy1.setText("-");
         }


        if (allLatePlateChefLists.get(2).getLunch_allergy() != null
                && !allLatePlateChefLists.get(2).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(2).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(2).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            wed_txt_allergy.setText(spannable);
        }
        else
        {
            wed_txt_allergy.setText("-");
        }
         if (allLatePlateChefLists.get(2).getDinner_allergy() != null
                && !allLatePlateChefLists.get(2).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(2).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(2).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            wed_txt_allergy1.setText(spannable);
        }
         else
         {
             wed_txt_allergy1.setText("-");
         }



        if (allLatePlateChefLists.get(3).getLunch_allergy() != null
                && !allLatePlateChefLists.get(3).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(3).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(3).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            thu_txt_allergy.setText(spannable);
        }
        else
        {
            thu_txt_allergy.setText("-");
        }
         if (allLatePlateChefLists.get(3).getDinner_allergy() != null
                && !allLatePlateChefLists.get(3).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(3).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(3).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            thu_txt_allergy1.setText(spannable);
        }
         else
         {
             thu_txt_allergy1.setText("-");
         }


        if (allLatePlateChefLists.get(4).getLunch_allergy() != null
                && !allLatePlateChefLists.get(4).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(4).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(4).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            fri_txt_allergy.setText(spannable);
        }
        else
        {
            fri_txt_allergy.setText("-");
        }
         if (allLatePlateChefLists.get(4).getDinner_allergy() != null
                && !allLatePlateChefLists.get(4).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(4).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(4).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            fri_txt_allergy1.setText(spannable);
        }
         else
         {
             fri_txt_allergy1.setText("-");
         }


        if (allLatePlateChefLists.get(5).getLunch_allergy() != null
                && !allLatePlateChefLists.get(5).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(5).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(5).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sat_txt_allergy.setText(spannable);
        }
        else
        {
            sat_txt_allergy.setText("-");
        }
         if (allLatePlateChefLists.get(5).getDinner_allergy() != null
                && !allLatePlateChefLists.get(5).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(5).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(5).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sat_txt_allergy1.setText(spannable);
        }
         else
         {
             sat_txt_allergy1.setText("-");
         }



        if (allLatePlateChefLists.get(6).getLunch_allergy() != null
                && !allLatePlateChefLists.get(6).getLunch_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(6).getLunch_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(6).getLunch_allergy().toLowerCase();
            String secondWord = "L|";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sun_txt_allergy.setText(spannable);
        }
        else
        {
            sun_txt_allergy.setText("-");
        }
        if (allLatePlateChefLists.get(6).getDinner_allergy() != null
                && !allLatePlateChefLists.get(6).getDinner_allergy().equalsIgnoreCase("")
                && !allLatePlateChefLists.get(6).getDinner_allergy().equalsIgnoreCase("null"))
        {
            String firstWord = allLatePlateChefLists.get(6).getDinner_allergy().toLowerCase();
            String secondWord = "D";
            // Create a new spannable with the two strings
            Spannable spannable = new SpannableString(firstWord+secondWord);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/opensans_semibold.ttf");
            // Set the custom typeface to span over a section of the spannable object
            spannable.setSpan( new CustomTypefaceSpan("", font2), 0, firstWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("", font), firstWord.length(), firstWord.length() + secondWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // Set the text of a textView with the spannable object
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#80000000")), firstWord.length(),
                    firstWord.length() + secondWord.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sun_txt_allergy1.setText(spannable);
        }
        else
        {
            sun_txt_allergy1.setText("-");
        }


        date_range.setText(date_range_val);

        mon_txt_lunch.setText(mon_txt_lunch_val);
        mon_txt_dinner.setText(mon_txt_dinner_val);
        tue_txt_lunch.setText(tue_txt_lunch_val);
        tue_txt_dinner.setText(tue_txt_dinner_val);
        wed_txt_lunch.setText(wed_txt_lunch_val);
        wed_txt_dinner.setText(wed_txt_dinner_val);
        thu_txt_lunch.setText(thu_txt_lunch_val);
        thu_txt_dinner.setText(thu_txt_dinner_val);
        fri_txt_lunch.setText(fri_txt_lunch_val);
        fri_txt_dinner.setText(fri_txt_dinner_val);
        sat_txt_lunch.setText(sat_txt_lunch_val);
        sat_txt_dinner.setText(sat_txt_dinner_val);
        sun_txt_lunch.setText(sun_txt_lunch_val);
        sun_txt_dinner.setText(sun_txt_dinner_val);



        if (allLatePlateChefLists.get(0).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(0).getDinner_allergy().equalsIgnoreCase("0"))
        {
            mon_txt_allergy.setText("-");
            mon_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(1).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(1).getDinner_allergy().equalsIgnoreCase("0"))
        {
            tue_txt_allergy.setText("-");
            tue_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(2).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(2).getDinner_allergy().equalsIgnoreCase("0"))
        {
            wed_txt_allergy.setText("-");
            wed_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(3).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(3).getDinner_allergy().equalsIgnoreCase("0"))
        {
            thu_txt_allergy.setText("-");
            thu_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(4).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(4).getDinner_allergy().equalsIgnoreCase("0"))
        {
            fri_txt_allergy.setText("-");
            fri_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(5).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(5).getDinner_allergy().equalsIgnoreCase("0"))
        {
            sat_txt_allergy.setText("-");
            sat_txt_allergy1.setText("");
        }
        if (allLatePlateChefLists.get(6).getLunch_allergy().equalsIgnoreCase("0")&&
                allLatePlateChefLists.get(6).getDinner_allergy().equalsIgnoreCase("0"))
        {
            sun_txt_allergy.setText("-");
            sun_txt_allergy1.setText("");
        }





        mon_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!mon_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Monday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        mon_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!mon_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Monday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        tue_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tue_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Tuesday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        tue_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tue_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Tuesday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        wed_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wed_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Wednesday"))
                        {
                           Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        wed_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wed_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Wednesday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        thu_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thu_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Thursday"))
                        {
                          Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        thu_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thu_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Thursday"))
                        {
                          Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        fri_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fri_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Friday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        fri_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fri_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Friday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        sat_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sat_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Saturday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        sat_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sat_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Saturday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });
        sun_txt_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sun_txt_lunch_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameLunch.size();x++)
                    {
                        if (getStudenNameLunch.get(x).getDayname().equalsIgnoreCase("Sunday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameLunch.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Lunch",Constant.ListElements_NAME);
                }
            }
        });
        sun_txt_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sun_txt_dinner_val.equalsIgnoreCase("0"))
                {
                    Constant.ListElements_NAME.clear();
                    for(int x=0;x<getStudenNameDinners.size();x++)
                    {
                        if (getStudenNameDinners.get(x).getDayname().equalsIgnoreCase("Sunday"))
                        {
                            Constant.ListElements_NAME.add(getStudenNameDinners.get(x).getStudentname());
                        }
                    }

                    ShowStudentListDialog("Dinner",Constant.ListElements_NAME);
                }
            }
        });

    }



    public void ShowStudentListDialog(String Allergy_name,List<String> ListElementsStudent1)
    {
        //final Dialog dialog = new Dialog(mContext,R.style.DialogTheme);
        final Dialog dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
       /* dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);*/
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.student_name);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        int width = ViewGroup.LayoutParams.FILL_PARENT;
        int height = ViewGroup.LayoutParams.FILL_PARENT;
        dialog.getWindow().setLayout(width, height);
        TextView btn_cancel_ll=(TextView)window.findViewById(R.id.btn_cancel_ll);
        // TextView btn_ok_ll=(TextView)window.findViewById(R.id.btn_ok_ll);

        TextView dialogTex=(TextView)window.findViewById(R.id.dialogTex);
        dialogTex.setText(""+Allergy_name);
        ListView student_list=(ListView)window.findViewById(R.id.student_list);
        student_list.setAdapter(new StudentListAdapter(mContext, ListElementsStudent1));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        btn_cancel_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }






    public class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LatePlateChefActivity.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }


    public void initGetlatePlateDetailsApi()
    {
        String param="GetallergyForLatePlate";
        
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new LatePlateManagerCheff(mContext).initGetlatePlateAPIDetails(param,Date, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {

                        List<AllLunchAllergyList> getAllLunchAllergyLists;
                        getAllLunchAllergyLists = new LatePlateManagerCheff(LatePlateChefActivity.this).getAllLunchAllergyLists();
                        Log.i("respo_LunchAllergy_size", "" + getAllLunchAllergyLists.size());

                        List<AllDinnerAllergyList> getAllDinnerAllergyLists;
                        getAllDinnerAllergyLists = new LatePlateManagerCheff(LatePlateChefActivity.this).getAllDinnerAllergyLists();
                        Log.i("respo_DinerAllergy_size", "" + getAllDinnerAllergyLists.size());


                        if (getAllLunchAllergyLists.size()==0&&getAllDinnerAllergyLists.size()==0)
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Late plate allergy is not available!", Snackbar.LENGTH_LONG)
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
                            Intent rate_intent = new Intent(mContext, LatePlateCheffDetails.class);
                            startActivity(rate_intent);
                            finish();
                        }

                    } else {
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


}