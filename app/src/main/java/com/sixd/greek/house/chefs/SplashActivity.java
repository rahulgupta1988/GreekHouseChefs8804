package com.sixd.greek.house.chefs;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sixd.greek.house.chefs.Cheff.HomeActivityCheff;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;

import java.util.List;

import dao_db.UserLoginInfo;

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try
        {
            Constant.notificationId = new GetGCMID(SplashActivity.this).getGCMId();
            Log.i("respo_apnstoken1",""+Constant.notificationId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            String appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tv_version.setText("Version: " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        showSplashWaiting();
    }

    public void showSplashWaiting(){

        Thread timer = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    List<UserLoginInfo> userDatas = null;
                    userDatas = new LoginManager(getApplicationContext()).getUserInfo();
                    if (userDatas != null) {
                        if (userDatas.size()>0) {

                            String userID = userDatas.get(0).getUser_id();
                            String login_user_type=userDatas.get(0).getCode();
                            if (userID != null)
                            {


                              /*  Intent intent = new Intent(SplashActivity.this, HomeActivityStudent.class);
                                startActivity(intent);
                                finish();*/

                                if (login_user_type.equalsIgnoreCase("ST"))
                                {
                                    intent=new Intent(SplashActivity.this,HomeActivityStudent.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (login_user_type.equalsIgnoreCase("CF"))
                                {
                                    intent=new Intent(SplashActivity.this,HomeActivityCheff.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            else {
                              /*  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();*/

                                Intent intent = new Intent(SplashActivity.this, UserSelectionActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                           /* Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();*/

                            Intent intent = new Intent(SplashActivity.this, UserSelectionActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                       /* Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/

                        Intent intent = new Intent(SplashActivity.this, UserSelectionActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            }
        };

        timer.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            Constant.notificationId = new GetGCMID(SplashActivity.this).getGCMId();
            Log.i("respo_apnstoken2",""+Constant.notificationId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
