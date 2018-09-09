package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.ForgotPasswordManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.ValidationUtility;

import java.util.List;

import dao_db.UserType;

/**
 * Created by Praveen on 21-Jul-17.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    Context mContext;
    ImageView line_menu_icon;
    EditText email_txt;
    TextView submit_btn;
    MaterialDialog materialDialog=null;
    String user_type="";
    List<UserType> userTypes;
    LinearLayout linear;
    String finalrole="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        mContext=this;
        userTypes = new LoginManager(ForgotPasswordActivity.this).getUserTypes();

        Intent intent = getIntent();
        finalrole = intent.getStringExtra("roleval");
        Log.i("ROLE",""+finalrole);

        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        initViews();
    }

    public void initViews(){

        linear=(LinearLayout)findViewById(R.id.linear);

        try
        {
            user_type=userTypes.get(0).getUserType();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i("respo_previoustime", user_type);

        /*if (user_type.equalsIgnoreCase("student"))
        {
            linear.setBackgroundResource(R.drawable.student_login_bg);
        }
        else if (user_type.equalsIgnoreCase("cheff"))
        {
            linear.setBackgroundResource(R.drawable.cheff_login_bg);
        }*/


        line_menu_icon=(ImageView)findViewById(R.id.line_menu_icon);
        email_txt=(EditText)findViewById(R.id.email_txt);
        submit_btn=(TextView) findViewById(R.id.submit_btn);

        line_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                intent.putExtra("roleval",finalrole);
                startActivity(intent);
                finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRequest();
            }
        });
    }

    String email="";
    public void initRequest(){

        email=email_txt.getText().toString();
        if(!ValidationUtility.validEmailAddress(email))
        {
           // Toast.makeText(mContext,""+ Constant.Messages.EMAIL_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear,""+ Constant.Messages.EMAIL_INVALIDE, Snackbar.LENGTH_LONG)
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
        else{
            initForgotPassword();
        }
    }

    MaterialDialog forgot_suc_dia=null;
    public void initForgotPassword() {

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();
            new ForgotPasswordManager(mContext).initforgotPasswordAPI(email, "username", new CallBackManager() {
                @Override
                public void onSuccess(String responce) {
                    //Toast.makeText(mContext,""+responce.toString(),Toast.LENGTH_SHORT).show();
                    materialDialog.dismiss();
                    if (responce.toString().equals("100"))
                    {
                        forgot_suc_dia = new MaterialDialog.Builder(mContext)
                                .title(Constant.Messages.FORGOTPASSWORD_SUCCESS_DIALOG_MSG)
                                .cancelable(false)
                                .positiveText("OK")
                                .contentColor(Color.BLACK)
                                .buttonRippleColor(Color.BLUE)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        forgot_suc_dia.dismiss();
                                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                        intent.putExtra("roleval",finalrole);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).build();

                        forgot_suc_dia.show();
                    }
                    else
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
        intent.putExtra("roleval",finalrole);
        startActivity(intent);
        finish();
    }
}
