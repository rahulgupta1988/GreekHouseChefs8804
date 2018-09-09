package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.SharedPreferenceManager;
import com.sixd.greek.house.chefs.utils.ValidationUtility;

import dao_db.DaoSession;
import dao_db.UserType;
import dao_db.UserTypeDao;


/**
 * Created by Praveen on 10-Jul-17.
 */

public class UserSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    MaterialDialog materialDialog=null;
    TextView student_btn,cheff_btn;
    Intent intent;
    String role="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection_page);
        mContext=this;
        initViews();
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();

        try
        {
            Constant.notificationId = new GetGCMID(UserSelectionActivity.this).getGCMId();
            Log.i("respo_apnstoken3", "" + Constant.notificationId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void initViews()
    {
        student_btn=(TextView)findViewById(R.id.student_btn);
        cheff_btn=(TextView)findViewById(R.id.cheff_btn);
        student_btn.setOnClickListener(this);
        cheff_btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.student_btn:
                {
                    String strDate="student";
                    DaoSession daoSession= BaseManager.getDBSessoin(UserSelectionActivity.this);
                    daoSession.getUserTypeDao().deleteAll();
                    UserTypeDao userTypeDao=daoSession.getUserTypeDao();
                    UserType userType=new UserType(strDate);
                    userTypeDao.insertOrReplace(userType);

                    role="ST";
                    intent = new Intent(UserSelectionActivity.this, LoginActivity.class);
                    intent.putExtra("roleval",role);

                    startActivity(intent);
                    finish();
                }
               break;

            case R.id.cheff_btn:
                 {
                    String strDate = "cheff";
                    DaoSession daoSession = BaseManager.getDBSessoin(UserSelectionActivity.this);
                    daoSession.getUserTypeDao().deleteAll();
                    UserTypeDao userTypeDao = daoSession.getUserTypeDao();
                    UserType userType = new UserType(strDate);
                    userTypeDao.insertOrReplace(userType);

                     role="CF";
                     intent = new Intent(UserSelectionActivity.this, LoginActivity.class);
                     intent.putExtra("roleval",role);
                    startActivity(intent);
                    finish();
                  }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
