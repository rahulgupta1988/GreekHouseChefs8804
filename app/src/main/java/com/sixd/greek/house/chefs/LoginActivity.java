package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.Cheff.HomeActivityCheff;
import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.SharedPreferenceManager;
import com.sixd.greek.house.chefs.utils.ValidationUtility;

import java.util.List;

import dao_db.DaoSession;
import dao_db.UserLoginInfo;
import dao_db.UserType;
import dao_db.UserTypeDao;


/**
 * Created by Praveen on 10-Jul-17.
 */

public class LoginActivity extends AppCompatActivity{

    Context mContext;
    MaterialDialog materialDialog=null;

    EditText username_edit,pass_edit;
    TextView login_btn;
    TextView signup_btn;
    TextView forgot_txt;
    ImageView remeber_check;

    Intent intent;
    String user_type="";
    List<UserType> userTypes;
    LinearLayout linear;
    String finalrole="";

    String username="", password ="",apns_token="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);  //activity_login
        mContext=this;
        userTypes = new LoginManager(LoginActivity.this).getUserTypes();

        Intent intent = getIntent();
        finalrole = intent.getStringExtra("roleval");
        Log.i("ROLE",""+finalrole);


        try
        {
            Constant.notificationId = new GetGCMID(LoginActivity.this).getGCMId();
            Log.i("respo_apnstoken4",""+Constant.notificationId);
            apns_token=Constant.notificationId;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        initViews();
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

    }

    public void initViews(){
        remeber_check=(ImageView)findViewById(R.id.remeber_check);
        forgot_txt=(TextView)findViewById(R.id.forgot_txt);
        signup_btn=(TextView) findViewById(R.id.signup_btn);
        username_edit=(EditText)findViewById(R.id.username_edit);
        pass_edit=(EditText)findViewById(R.id.pass_edit);
        login_btn=(TextView) findViewById(R.id.login_btn);
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

        if (user_type.equalsIgnoreCase("student"))
        {
            //linear.setBackgroundResource(R.drawable.student_login_bg);
            signup_btn.setVisibility(View.VISIBLE);
        }
        else if (user_type.equalsIgnoreCase("cheff"))
        {
            //linear.setBackgroundResource(R.drawable.cheff_login_bg);
            signup_btn.setVisibility(View.GONE);
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connec =
                        (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                // Check for network connections
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
                {
                    submit();
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

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                intent.putExtra("roleval",finalrole);
                startActivity(intent);
                finish();
            }
        });

        forgot_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("roleval",finalrole);
                startActivity(intent);
                finish();
            }
        });



        if(SharedPreferenceManager.getRemCheck(mContext)){
            remeber_check.setSelected(true);
            String rem_userName=SharedPreferenceManager.getUserName(mContext);
            String rem_password=SharedPreferenceManager.getPassword(mContext);
            username_edit.setText(rem_userName);
            pass_edit.setText(rem_password);
        }
        else{
            remeber_check.setSelected(false);
        }


        remeber_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remeber_check.isSelected()) {
                    remeber_check.setSelected(false);
                    SharedPreferenceManager.putRemCheck(mContext,false);
                }
                else  {
                    remeber_check.setSelected(true);
                    SharedPreferenceManager.putRemCheck(mContext,true);
                }
            }
        });


    }



    public void submit(){
        if(emailpassVerify()){
            ConnectivityManager connec =
                    (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
            // Check for network connections
            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
            {
                initLogin();
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

    public boolean emailpassVerify() {
        username = username_edit.getText().toString().trim();
        password = pass_edit.getText().toString().trim();

        if (!ValidationUtility.validUserNamePassString(username))
        {
            //Toast.makeText(mContext,""+Constant.Messages.USERNAME_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.USERNAME_INVALIDE, Snackbar.LENGTH_LONG)
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



            return false;
        }
        else if(!ValidationUtility.validUserNamePassString(password)) {
            //Toast.makeText(mContext,""+Constant.Messages.PASSWORD_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.PASSWORD_INVALIDE, Snackbar.LENGTH_LONG)
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

            return false;
        }

        return true;

    }

    public void initLogin() {

        final boolean remember_me=remeber_check.isSelected();

        materialDialog.show();
        new LoginManager(mContext).initLoginAPI(username,password,apns_token,new CallBackManager() {
            @Override
            public void onSuccess(String responce) {
                materialDialog.dismiss();
                if(responce.toString().equals("100")){

                    // Remember username and password
                    if(remember_me) {
                        SharedPreferenceManager.putUserName(mContext, username);
                        SharedPreferenceManager.putPassword(mContext, password);
                    }
                    else{
                            SharedPreferenceManager.putUserName(mContext,"");
                            SharedPreferenceManager.putPassword(mContext,"");
                        }


                    String login_user_type="";
                    List<UserLoginInfo> userLoginInfos;
                    userLoginInfos = new LoginManager(LoginActivity.this).getUserInfo();
                    try
                    {
                        login_user_type=userLoginInfos.get(0).getCode();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    if (login_user_type.equalsIgnoreCase("ST"))
                    {
                        if(finalrole.equalsIgnoreCase("ST"))
                        {
                            // Toast.makeText(getApplicationContext(),"ST",Toast.LENGTH_LONG).show();
                            intent=new Intent(mContext,HomeActivityStudent.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(mContext,""+Constant.Messages.LOGIN_SUCCESS,Toast.LENGTH_SHORT).show();
                        }
                        else {

                            DaoSession daoSession= BaseManager.getDBSessoin(LoginActivity.this);
                            daoSession.getUserTypeDao().deleteAll();
                            daoSession.getUserLoginInfoDao().deleteAll();
                            Toast.makeText(mContext,"You seem to be at wrong login. Please select your correct role and try again!!",Toast.LENGTH_SHORT).show();
                            intent=new Intent(mContext,UserSelectionActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else if (login_user_type.equalsIgnoreCase("CF"))
                    {
                        if(finalrole.equalsIgnoreCase("CF"))
                        {
                            // Toast.makeText(getApplicationContext(),"CF",Toast.LENGTH_LONG).show();
                            intent=new Intent(mContext,HomeActivityCheff.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(mContext,""+Constant.Messages.LOGIN_SUCCESS,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DaoSession daoSession = BaseManager.getDBSessoin(LoginActivity.this);
                            daoSession.getUserTypeDao().deleteAll();
                            daoSession.getUserLoginInfoDao().deleteAll();
                            Toast.makeText(mContext,"You seem to be at wrong login. Please select your correct role and try again!!",Toast.LENGTH_SHORT).show();
                            intent=new Intent(mContext,UserSelectionActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
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



    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            Constant.notificationId = new GetGCMID(LoginActivity.this).getGCMId();
            Log.i("respo_apnstoken4",""+Constant.notificationId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        intent=new Intent(LoginActivity.this,UserSelectionActivity.class);
        startActivity(intent);
        finish();
    }

}
