package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.CravingManager;

/**
 * Created by Praveen on 20-Jul-17.
 */

public class CravingActivity extends HeaderActivty {
    Context mContext;
    EditText craving_txt;
    TextView submit_btn;
    MaterialDialog materialDialog=null;
    LinearLayout linear;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        setHeaderTitele("Craving Requests");
        initViews();
    }

    public void initViews()
    {
        linear=(LinearLayout)findViewById(R.id.linear);
        craving_txt=(EditText) findViewById(R.id.craving_txt);
        submit_btn=(TextView) findViewById(R.id.submit_btn);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String craving_str=craving_txt.getText().toString().trim();
                if(craving_str.length()>0)
                {
                    ConnectivityManager connec =
                            (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                    {

                        materialDialog.show();

                     new CravingManager(mContext).initCravingAPI(craving_str, new CallBackManager() {
                        @Override
                        public void onSuccess(String responce)
                        {
                            materialDialog.dismiss();

                            if(responce.equals("100"))
                            {
                                Toast.makeText(mContext,"Your craving is added successfully!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CravingActivity.this,HomeActivityStudent.class);
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

                else{
                   // Toast.makeText(mContext,"Please enter your query!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Please enter your query!", Snackbar.LENGTH_LONG)
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CravingActivity.this,HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }

   /* @Override
    protected void onResume() {

    }*/
}
