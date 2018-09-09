package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.sixd.greek.house.chefs.CheffAdapter.MenuPagerAdapterCheff;
import com.sixd.greek.house.chefs.CheffAdapter.MenuWeekIntervalAdapter;
import com.sixd.greek.house.chefs.EventActivity;
import com.sixd.greek.house.chefs.HeaderActivty;
import com.sixd.greek.house.chefs.HomeActivityStudent;
import com.sixd.greek.house.chefs.ManagerCheff.BudgetManager;
import com.sixd.greek.house.chefs.ManagerCheff.LatePlateManagerCheff;
import com.sixd.greek.house.chefs.ManagerCheff.SubmitRecipeManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.adapter.MenuPagerAdapter;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.manager.MenuCalendarManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import java.util.List;

import dao_db.AllPastBudgetInfo;
import dao_db.AllWeekIntervalList;
import dao_db.SubmitBudget;
import dao_db.UserLoginInfo;


/**
 * Created by Praveen on 19-Jul-17.
 */

public class SubmitBudgetCheff extends HeaderActivtyCheff
{
    LinearLayout budget_layout;
    String submit_budget_id_server_val="";
    TextView past_budget_txt;
    RelativeLayout date_sel_lay1;

    Context mContext;
    MaterialDialog materialDialog=null;
    //TextView submit_recipe_rl;
    LinearLayout linear;
    ScrollView regScroll;
    String account_name_val,weekly_budget_val,grocery_store_val,sams_val,syco_val,other_val,total_val,overunder_val,
            starting_balance_val,amoumt_spent_val1,amoumt_spent_val2,amoumt_spent_val3,amoumt_spent_val4,amoumt_spent_val5,amoumt_spent_val6,amoumt_spent_val7,amoumt_spent_val8,
    ending_balance_val,
            item_val1,amount_val1,item_val2,amount_val2,item_val3,amount_val3,item_val4,item_val5,item_val6,item_val7,item_val8,
    amount_val4,amount_val5,amount_val6,amount_val7,amount_val8,special_event_val,total_invoice_val;

    EditText account_name_edt,weekly_budget_edt,grocery_store_edt,sams_edt,syco_edt,other_edt,total_edt,overunder_edt,
            starting_balance_edt,amoumt_spent_edt1,amoumt_spent_edt2,amoumt_spent_edt3,amoumt_spent_edt4,
            amoumt_spent_edt5,amoumt_spent_edt6,amoumt_spent_edt7,amoumt_spent_edt8, ending_balance_edt,
            item_edt1,amount_edt1,item_edt2,amount_edt2,item_edt3,amount_edt3,item_edt4,item_edt5,item_edt6,item_edt7,item_edt8,
           amount_edt4,amount_edt5,amount_edt6,amount_edt7,amount_edt8,special_event_edt,total_invoice_edt;


      TextView submit_budget_txt,submit_budget_txt1;

    String Week_number="",Week_year="",interval="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Submit Budget");


        initViews();


        {
            Snackbar snackbar = Snackbar
                    .make(linear, "Please select the week for submitting budget!", Snackbar.LENGTH_LONG)
                    .setAction("", new View.OnClickListener() {
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

    public void initViews()
    {
        budget_layout=(LinearLayout) findViewById(R.id.budget_layout);
        budget_layout.setVisibility(View.GONE);

        linear = (LinearLayout) findViewById(R.id.linear);
        ScrollView view = (ScrollView) findViewById(R.id.regScroll);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });


        date_sel_lay1=(RelativeLayout)findViewById(R.id.date_sel_lay);
        date_sel_lay1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               /* submit budget*/
                initgetWeekIntervalAPIForSubmitBudget();
            }
        });


        past_budget_txt=(TextView)findViewById(R.id.past_budget_txt);
        past_budget_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGetBudgetPastInfoAPI();
            }
        });

        account_name_edt = (EditText) findViewById(R.id.account_name_edt);
        weekly_budget_edt = (EditText) findViewById(R.id.weekly_budget_edt);
        grocery_store_edt = (EditText) findViewById(R.id.grocery_store_edt);
        sams_edt = (EditText) findViewById(R.id.sams_edt);
        syco_edt = (EditText) findViewById(R.id.syco_edt);
        other_edt = (EditText) findViewById(R.id.other_edt);
        total_edt = (EditText) findViewById(R.id.total_edt);
        overunder_edt = (EditText) findViewById(R.id.overunder_edt);
        starting_balance_edt = (EditText) findViewById(R.id.starting_balance_edt);
        amoumt_spent_edt1 = (EditText) findViewById(R.id.amoumt_spent_edt1);
        amoumt_spent_edt2 = (EditText) findViewById(R.id.amoumt_spent_edt2);
        amoumt_spent_edt3 = (EditText) findViewById(R.id.amoumt_spent_edt3);
        amoumt_spent_edt4 = (EditText) findViewById(R.id.amoumt_spent_edt4);

        amoumt_spent_edt5 = (EditText) findViewById(R.id.amoumt_spent_edt5);
        amoumt_spent_edt6 = (EditText) findViewById(R.id.amoumt_spent_edt6);
        amoumt_spent_edt7 = (EditText) findViewById(R.id.amoumt_spent_edt7);
        amoumt_spent_edt8 = (EditText) findViewById(R.id.amoumt_spent_edt8);



        ending_balance_edt = (EditText) findViewById(R.id.ending_balance_edt);
        item_edt1 = (EditText) findViewById(R.id.item_edt1);
        amount_edt1 = (EditText) findViewById(R.id.amount_edt1);
        item_edt2 = (EditText) findViewById(R.id.item_edt2);
        amount_edt2 = (EditText) findViewById(R.id.amount_edt2);
        item_edt3 = (EditText) findViewById(R.id.item_edt3);
        amount_edt3 = (EditText) findViewById(R.id.amount_edt3);
        item_edt4 = (EditText) findViewById(R.id.item_edt4);

        item_edt5 = (EditText) findViewById(R.id.item_edt5);
        item_edt6 = (EditText) findViewById(R.id.item_edt6);
        item_edt7 = (EditText) findViewById(R.id.item_edt7);
        item_edt8 = (EditText) findViewById(R.id.item_edt8);

        amount_edt4 = (EditText) findViewById(R.id.amount_edt4);
        amount_edt5 = (EditText) findViewById(R.id.amount_edt5);
        amount_edt6 = (EditText) findViewById(R.id.amount_edt6);
        amount_edt7 = (EditText) findViewById(R.id.amount_edt7);
        amount_edt8 = (EditText) findViewById(R.id.amount_edt8);

        special_event_edt = (EditText) findViewById(R.id.special_event_edt);
        total_invoice_edt = (EditText) findViewById(R.id.total_invoice_edt);
        submit_budget_txt=(TextView)findViewById(R.id.submit_budget_txt);
        submit_budget_txt1=(TextView)findViewById(R.id.submit_budget_txt1);

        amoumt_spent_edt1.setEnabled(false);
        amoumt_spent_edt2.setEnabled(false);
        amoumt_spent_edt3.setEnabled(false);
        amoumt_spent_edt4.setEnabled(false);
        amoumt_spent_edt5.setEnabled(false);
        amoumt_spent_edt6.setEnabled(false);
        amoumt_spent_edt7.setEnabled(false);
        amoumt_spent_edt8.setEnabled(false);



        starting_balance_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        submit_budget_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String val="0";
                call(val);
            }

        });

        submit_budget_txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String val="1";
                call(val);
            }

        });

    }



    public void call(String draftval)
    {
        {

            account_name_val=account_name_edt.getText().toString().trim();
            weekly_budget_val=weekly_budget_edt.getText().toString().trim();
            grocery_store_val=grocery_store_edt.getText().toString().trim();
            sams_val=sams_edt.getText().toString().trim();
            syco_val=syco_edt.getText().toString().trim();
            other_val=other_edt.getText().toString().trim();
            total_val=total_edt.getText().toString().trim();
            overunder_val=overunder_edt.getText().toString().trim();
            starting_balance_val=starting_balance_edt.getText().toString().trim();
            amoumt_spent_val1=amoumt_spent_edt1.getText().toString().trim();
            amoumt_spent_val2=amoumt_spent_edt2.getText().toString().trim();
            amoumt_spent_val3=amoumt_spent_edt3.getText().toString().trim();
            amoumt_spent_val4=amoumt_spent_edt4.getText().toString().trim();

            amoumt_spent_val5=amoumt_spent_edt5.getText().toString().trim();
            amoumt_spent_val6=amoumt_spent_edt6.getText().toString().trim();
            amoumt_spent_val7=amoumt_spent_edt7.getText().toString().trim();
            amoumt_spent_val8=amoumt_spent_edt8.getText().toString().trim();

            ending_balance_val=ending_balance_edt.getText().toString().trim();
            item_val1=item_edt1.getText().toString().trim();
            amount_val1=amount_edt1.getText().toString().trim();
            item_val2=item_edt2.getText().toString().trim();
            amount_val2=amount_edt2.getText().toString().trim();
            item_val3=item_edt3.getText().toString().trim();
            amount_val3=amount_edt3.getText().toString().trim();
            item_val4=item_edt4.getText().toString().trim();

            item_val5=item_edt5.getText().toString().trim();
            item_val6=item_edt6.getText().toString().trim();
            item_val7=item_edt7.getText().toString().trim();
            item_val8=item_edt8.getText().toString().trim();

            amount_val4=amount_edt4.getText().toString().trim();

            amount_val5=amount_edt5.getText().toString().trim();
            amount_val6=amount_edt6.getText().toString().trim();
            amount_val7=amount_edt7.getText().toString().trim();
            amount_val8=amount_edt8.getText().toString().trim();



            special_event_val=special_event_edt.getText().toString().trim();
            total_invoice_val=total_invoice_edt.getText().toString().trim();

            Log.i("account_name_val",""+account_name_val);
            Log.i("weekly_budget_val",""+weekly_budget_val);
            Log.i("grocery_store_val",""+grocery_store_val);
            Log.i("sams_val",""+sams_val);
            Log.i("syco_val",""+syco_val);
            Log.i("other_val",""+other_val);
            Log.i("total_val",""+total_val);
            Log.i("overunder_val",""+overunder_val);
            Log.i("starting_balance_val",""+starting_balance_val);
            Log.i("amoumt_spent_val1",""+amoumt_spent_val1);
            Log.i("amoumt_spent_val2",""+amoumt_spent_val2);
            Log.i("amoumt_spent_val3",""+amoumt_spent_val3);
            Log.i("amoumt_spent_val4",""+amoumt_spent_val4);
            Log.i("amoumt_spent_val5",""+amoumt_spent_val5);
            Log.i("amoumt_spent_val6",""+amoumt_spent_val6);
            Log.i("amoumt_spent_val7",""+amoumt_spent_val7);
            Log.i("amoumt_spent_val8",""+amoumt_spent_val8);

            Log.i("ending_balance_val",""+ending_balance_val);
            Log.i("item_val1",""+item_val1);
            Log.i("amount_val1",""+amount_val1);
            Log.i("item_val2",""+item_val2);
            Log.i("amount_val2",""+amount_val2);
            Log.i("item_val3",""+item_val3);
            Log.i("amount_val3",""+amount_val3);
            Log.i("item_val4",""+item_val4);
            Log.i("item_val5",""+item_val5);
            Log.i("item_val6",""+item_val6);
            Log.i("item_val7",""+item_val7);
            Log.i("item_val8",""+item_val8);

            Log.i("amount_val4",""+amount_val4);
            Log.i("amount_val5",""+amount_val5);
            Log.i("amount_val6",""+amount_val6);
            Log.i("amount_val7",""+amount_val7);
            Log.i("amount_val8",""+amount_val8);
            Log.i("special_event_val",""+special_event_val);
            Log.i("total_invoice_val",""+total_invoice_val);


            if(interval.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Select week interval",Toast.LENGTH_LONG).show();
            }
            else if(account_name_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter account name",Toast.LENGTH_LONG).show();
            }
            else if(weekly_budget_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter weekly budget",Toast.LENGTH_LONG).show();
            }
            else if(grocery_store_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter grocery store",Toast.LENGTH_LONG).show();
            }
            else if(sams_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter sams/costco",Toast.LENGTH_LONG).show();
            }
            else if(syco_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter sysco",Toast.LENGTH_LONG).show();
            }

            else if(total_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter total",Toast.LENGTH_LONG).show();
            }
            else if(overunder_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter over/under",Toast.LENGTH_LONG).show();
            }
            else if(starting_balance_val.length()<=0)
            {
                Toast.makeText(SubmitBudgetCheff.this,"Enter starting balance",Toast.LENGTH_LONG).show();

            }
            else
            {
                initaddBudgetAPI(draftval);
            }
        }
    }


String check_toast_msg="";

   public void initaddBudgetAPI(String draftvalfinal)
    {

        String  Account_name="",Weekly_budget="",Grocery_store="",Costco="",Sysco="",Total="",Other="",
                Over_under="",Starting_balance="",Ending_balance="",
                Giftcardamountspend_1="",Giftcardamountspend_2="",Giftcardamountspend_3="",Giftcardamountspend_4="",
                Giftcardamountspend_5="",Giftcardamountspend_6="",Giftcardamountspend_7="",Giftcardamountspend_8="",
                Credititems_1="",Credititems_2="",Credititems_3="",Credititems_4="",Credititems_5="",
                Credititems_6="",Credititems_7="",Credititems_8=""
                ,Creditamount_1="",Creditamount_2="",
                Creditamount_3="",Creditamount_4="",Creditamount_5="",Creditamount_6="",Creditamount_7="",
                Creditamount_8="",Special_event="",Total_invoice="",Is_draft="",Submit_budget_id="";

        Account_name=account_name_val;
        Weekly_budget=weekly_budget_val;
        Grocery_store=grocery_store_val;
        Costco=sams_val;
        Sysco=syco_val;
        Total=total_val;
        Other=other_val;
        Over_under=overunder_val;
        Starting_balance=starting_balance_val;
        Ending_balance=ending_balance_val;
        Giftcardamountspend_1=amoumt_spent_val1;
        Giftcardamountspend_2=amoumt_spent_val2;
        Giftcardamountspend_3=amoumt_spent_val3;
        Giftcardamountspend_4=amoumt_spent_val4;
        Giftcardamountspend_5=amoumt_spent_val5;
        Giftcardamountspend_6=amoumt_spent_val6;
        Giftcardamountspend_7=amoumt_spent_val7;
        Giftcardamountspend_8=amoumt_spent_val8;
        Credititems_1=item_val1;
        Credititems_2=item_val2;
        Credititems_3=item_val3;
        Credititems_4=item_val4;
        Credititems_5=item_val5;
        Credititems_6=item_val6;
        Credititems_7=item_val7;
        Credititems_8=item_val8;

        Creditamount_1=amount_val1;
        Creditamount_2=amount_val2;
        Creditamount_3=amount_val3;
        Creditamount_4=amount_val4;
        Creditamount_5=amount_val5;
        Creditamount_6=amount_val6;
        Creditamount_7=amount_val7;
        Creditamount_8=amount_val8;

        Special_event=special_event_val;
        Total_invoice=total_invoice_val;
        Is_draft=draftvalfinal;
        check_toast_msg=draftvalfinal;
        Submit_budget_id=submit_budget_id_server_val;

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {

            materialDialog.show();
            new BudgetManager(mContext).initaddBudgetAPI(Week_number,Week_year,Account_name,Weekly_budget,Grocery_store,Costco,Sysco,Total,Other,
                    Over_under,Starting_balance,Ending_balance,Giftcardamountspend_1,Giftcardamountspend_2,Giftcardamountspend_3,
                    Giftcardamountspend_4,Giftcardamountspend_5,Giftcardamountspend_6,Giftcardamountspend_7,Giftcardamountspend_8,
                    Credititems_1,Credititems_2,Credititems_3,Credititems_4,Credititems_5,Credititems_6,Credititems_7,Credititems_8,
                    Creditamount_1,Creditamount_2,Creditamount_3,Creditamount_4,Creditamount_5,Creditamount_6,Creditamount_7,
                    Creditamount_8,Special_event,Total_invoice,Is_draft,Submit_budget_id,new CallBackManager()
                    {

                        @Override
                        public void onSuccess(String responce)
                        {
                            materialDialog.dismiss();
                            if(responce.equals("100"))
                            {

                                if (check_toast_msg.equalsIgnoreCase("0"))
                                {
                                    Toast.makeText(mContext, "Budget added successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(mContext, "Budget submitted successfully!", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent = new Intent(SubmitBudgetCheff.this, HomeActivityCheff.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
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



    public void enableSubmitIfReady() {
        starting_balance_val=starting_balance_edt.getText().toString().trim();
        if(starting_balance_val.length()>0)
        {
            amoumt_spent_edt1.setEnabled(true);
            amoumt_spent_edt2.setEnabled(true);
            amoumt_spent_edt3.setEnabled(true);
            amoumt_spent_edt4.setEnabled(true);
            amoumt_spent_edt5.setEnabled(true);
            amoumt_spent_edt6.setEnabled(true);
            amoumt_spent_edt7.setEnabled(true);
            amoumt_spent_edt8.setEnabled(true);
        }
        else {
            amoumt_spent_edt1.setEnabled(false);
            amoumt_spent_edt2.setEnabled(false);
            amoumt_spent_edt3.setEnabled(false);
            amoumt_spent_edt4.setEnabled(false);
            amoumt_spent_edt5.setEnabled(false);
            amoumt_spent_edt6.setEnabled(false);
            amoumt_spent_edt7.setEnabled(false);
            amoumt_spent_edt8.setEnabled(false);
        }

    }



    public void initgetSubmitBudgetAPI()
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
            new BudgetManager(mContext).initgetSubmitBudgetAPI(Week_number,Week_year,new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {

                        budget_layout.setVisibility(View.VISIBLE);

                        List<SubmitBudget> submitBudgets;
                        submitBudgets = new BudgetManager(SubmitBudgetCheff.this).getSubmitBudgetData();
                        submit_budget_id_server_val = submitBudgets.get(0).getSubmit_budget_id();

                        account_name_edt.setText(submitBudgets.get(0).getAccount_name());
                        weekly_budget_edt.setText(submitBudgets.get(0).getWeekly_budget());
                        grocery_store_edt.setText(submitBudgets.get(0).getGrocery_store());
                        sams_edt.setText(submitBudgets.get(0).getCostco());
                        syco_edt.setText(submitBudgets.get(0).getSysco());
                        other_edt.setText(submitBudgets.get(0).getOther());
                        total_edt.setText(submitBudgets.get(0).getTotal());
                        overunder_edt.setText(submitBudgets.get(0).getOver_under());
                        starting_balance_edt.setText(submitBudgets.get(0).getStarting_balance());
                        ending_balance_edt.setText(submitBudgets.get(0).getEnding_balance());
                        amoumt_spent_edt1.setText(submitBudgets.get(0).getGiftcardamountspend_1());
                        amoumt_spent_edt2.setText(submitBudgets.get(0).getGiftcardamountspend_2());
                        amoumt_spent_edt3.setText(submitBudgets.get(0).getGiftcardamountspend_3());
                        amoumt_spent_edt4.setText(submitBudgets.get(0).getGiftcardamountspend_4());
                        amoumt_spent_edt5.setText(submitBudgets.get(0).getGiftcardamountspend_5());
                        amoumt_spent_edt6.setText(submitBudgets.get(0).getGiftcardamountspend_6());
                        amoumt_spent_edt7.setText(submitBudgets.get(0).getGiftcardamountspend_7());
                        amoumt_spent_edt8.setText(submitBudgets.get(0).getGiftcardamountspend_8());
                        item_edt1.setText(submitBudgets.get(0).getCredititems_1());
                        item_edt2.setText(submitBudgets.get(0).getCredititems_2());
                        item_edt3.setText(submitBudgets.get(0).getCredititems_3());
                        item_edt4.setText(submitBudgets.get(0).getCredititems_4());
                        item_edt5.setText(submitBudgets.get(0).getCredititems_5());
                        item_edt6.setText(submitBudgets.get(0).getCredititems_6());
                        item_edt7.setText(submitBudgets.get(0).getCredititems_7());
                        item_edt8.setText(submitBudgets.get(0).getCredititems_8());
                        amount_edt1.setText(submitBudgets.get(0).getCreditamount_1());
                        amount_edt2.setText(submitBudgets.get(0).getCreditamount_2());
                        amount_edt3.setText(submitBudgets.get(0).getCreditamount_3());
                        amount_edt4.setText(submitBudgets.get(0).getCreditamount_4());
                        amount_edt5.setText(submitBudgets.get(0).getCreditamount_5());
                        amount_edt6.setText(submitBudgets.get(0).getCreditamount_6());
                        amount_edt7.setText(submitBudgets.get(0).getCreditamount_7());
                        amount_edt8.setText(submitBudgets.get(0).getCreditamount_8());
                        special_event_edt.setText(submitBudgets.get(0).getSpecial_event());
                        total_invoice_edt.setText(submitBudgets.get(0).getTotal_invoice());

                    } else {

                        budget_layout.setVisibility(View.GONE);
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
                    budget_layout.setVisibility(View.GONE);
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
            snackbar.show();        }
    }



    public void initgetWeekIntervalAPIForSubmitBudget()
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
            new MenuCalendarManager(mContext).initgetWeekIntervalAPI(new CallBackManager() {

                         @Override
                         public void onSuccess(String responce) {

                             materialDialog.dismiss();
                             if (responce.equals("100"))
                             {

                                 final List<AllWeekIntervalList> allWeekIntervalLists;
                                 allWeekIntervalLists = new MenuCalendarManager(SubmitBudgetCheff.this).getAllWeekIntervalLists();
                                 Log.i("respo_weekinterval_size", "" + allWeekIntervalLists.size());

                                 if (allWeekIntervalLists.size() > 0)
                                 {
                                     PopUp = new PopupWindow(mContext);
                                     LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                     View layout = layoutInflater.inflate(R.layout.dialog_menu_date_cheff, null);
                                     TextView cancel_check = (TextView) layout.findViewById(R.id.cancel_check);
                                     ListView week_interval_list = (ListView) layout.findViewById(R.id.week_interval_list);


                                     if (android.os.Build.VERSION.SDK_INT >= 24) {
                                         Rect rc = new Rect();
                                         submit_rl.getWindowVisibleDisplayFrame(rc);
                                         int[] xy = new int[2];
                                         submit_rl.getLocationInWindow(xy);
                                         rc.offset(xy[0], xy[1]);
                                         PopUp.setContentView(layout);
                                         DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
                                         int height = displaymetrics.heightPixels;
                                         int width = displaymetrics.widthPixels;
                                         PopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                         PopUp.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                                         PopUp.setFocusable(true);
                                         PopUp.setOutsideTouchable(false);
                                         // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.


                                         int OFFSET_X = 0;
                                         int OFFSET_Y = -50;
                                         PopUp.setBackgroundDrawable(new BitmapDrawable());
                                         WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                                         int screenHeight = wm.getDefaultDisplay().getHeight();
                                         int newheight = screenHeight / 2;
                                         PopUp.setHeight(newheight - OFFSET_Y);
                                         PopUp.showAtLocation(layout, Gravity.BOTTOM, 0, OFFSET_Y);


                                     } else {

                                         Rect rc = new Rect();
                                         submit_rl.getWindowVisibleDisplayFrame(rc);
                                         int[] xy = new int[2];
                                         submit_rl.getLocationInWindow(xy);
                                         rc.offset(xy[0], xy[1]);
                                         PopUp.setContentView(layout);
                                         DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
                                         int height = displaymetrics.heightPixels;
                                         int width = displaymetrics.widthPixels;
                                         PopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                                         PopUp.setHeight((LinearLayout.LayoutParams.WRAP_CONTENT));
                                         PopUp.setFocusable(true);
                                         PopUp.setOutsideTouchable(false);
                                         // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
                                         int OFFSET_X = 0;
                                         int OFFSET_Y = 0;
                                         PopUp.setBackgroundDrawable(new BitmapDrawable());
                                         // Displaying the popup at the specified location, + offsets.
                                         PopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

                                         //TextView done_check=(TextView)layout.findViewById(R.id.done_check);
                                     }

                                     cancel_check.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             PopUp.dismiss();
                                         }
                                     });

                                     week_interval_list.setAdapter(new MenuWeekIntervalAdapter(SubmitBudgetCheff.this, allWeekIntervalLists));

                                     week_interval_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                             Week_number = allWeekIntervalLists.get(position).getWeek().toString().trim();
                                             Week_year = allWeekIntervalLists.get(position).getYear().toString().trim();
                                             interval = allWeekIntervalLists.get(position).getInterval().toString().trim();

                                            /* Toast.makeText(mContext, "Week_number=" + Week_number
                                                     + "Week_year=" + Week_year, Toast.LENGTH_LONG).show();*/

                                             initgetSubmitBudgetAPI();

                                             daterange.setText(interval);
                                             PopUp.dismiss();
                                         }
                                     });


                                 } else {
                                     Snackbar snackbar = Snackbar
                                             .make(linear, "Week Interval not available!", Snackbar.LENGTH_LONG)
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
                         public void onFailure(String responce) {
                             materialDialog.dismiss();
                         }
                     }

            );
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





    public void initGetBudgetPastInfoAPI()
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
            new BudgetManager(mContext).initGetBudgetPastInfoAPI(new CallBackManager() {

                     @Override
                     public void onSuccess(String responce) {

                         materialDialog.dismiss();
                         if (responce.equals("100"))
                         {
                             List<AllPastBudgetInfo> getAllPastBudgetInfo;
                             getAllPastBudgetInfo = new BudgetManager(SubmitBudgetCheff.this).getAllPastBudgetInfo();

                             if (getAllPastBudgetInfo.size() > 0)
                             {
                                 Intent event_intent=new Intent(mContext,PastBudgetInfoDetails.class);
                                 startActivity(event_intent);
                                 finish();
                             } else {
                                 Snackbar snackbar = Snackbar
                                         .make(linear, "No budget is submitted yet!", Snackbar.LENGTH_LONG)
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
                 }

            );
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




    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SubmitBudgetCheff.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }

}
