package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.SignUpManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.ValidationUtility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Praveen on 17-Jul-17.
 */

public class RegistrationActivity extends AppCompatActivity {


    Context mContext;
    MaterialDialog materialDialog=null;
    TextView signin_txt;
    EditText name_edit,campus_edit,email_edit,username_edit,pass_edit;
    TextView signup_btn;
    LinearLayout linear;
    String finalrole="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext=this;

        Intent intent = getIntent();
        finalrole = intent.getStringExtra("roleval");
        Log.i("ROLE", "" + finalrole);

        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0).build();
        initviews();
    }

    public void initviews(){

        linear=(LinearLayout)findViewById(R.id.linear);


        signin_txt=(TextView)findViewById(R.id.signin_txt);
        signin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                intent.putExtra("roleval",finalrole);
                startActivity(intent);
                finish();
            }
        });

        name_edit=(EditText)findViewById(R.id.name_edit);
        campus_edit=(EditText)findViewById(R.id.campus_edit);
        email_edit=(EditText)findViewById(R.id.email_edit);
        username_edit=(EditText)findViewById(R.id.username_edit);
        pass_edit=(EditText)findViewById(R.id.pass_edit);
        signup_btn=(TextView) findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
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
                   if(verifyData())
                   {
                       initSignUp();
                   }
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

    String name_edit_txt,campus_edit_txt,email_edit_txt,username_edit_txt,pass_edit_txt;

    public boolean verifyData(){
        name_edit_txt=name_edit.getText().toString().trim();
        campus_edit_txt=campus_edit.getText().toString().trim();
        email_edit_txt=email_edit.getText().toString().trim();
        username_edit_txt=username_edit.getText().toString().trim();
        pass_edit_txt=pass_edit.getText().toString().trim();

        Log.i("respo_lenghth=",""+username_edit.getText().toString().length());

        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(username_edit_txt);
        boolean found = matcher.find();
        /*if(found)return false;*/

        if(!ValidationUtility.validString(name_edit_txt)) {
            //Toast.makeText(mContext,""+ Constant.Messages.NAME_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.NAME_INVALIDE, Snackbar.LENGTH_LONG)
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
        else if(!ValidationUtility.validHouseCodeString(campus_edit_txt)) {
            //Toast.makeText(mContext,""+ Constant.Messages.CAMPUSE_CODE_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.CAMPUSE_CODE_INVALIDE, Snackbar.LENGTH_LONG)
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
        else if(!ValidationUtility.validEmailAddress(email_edit_txt)) {
           // Toast.makeText(mContext,""+ Constant.Messages.EMAIL_INVALIDE,Toast.LENGTH_SHORT).show();


                Snackbar snackbar = Snackbar
                        .make(linear, "" + Constant.Messages.EMAIL_INVALIDE, Snackbar.LENGTH_LONG)
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

      /*  else if(!email_edit_txt.contains(".edu")) {
            // Toast.makeText(mContext,""+ Constant.Messages.EMAIL_INVALIDE,Toast.LENGTH_SHORT).show();


            Snackbar snackbar = Snackbar
                    .make(linear, "" + Constant.Messages.EMAIL_INVALIDE, Snackbar.LENGTH_LONG)
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

        }*/

        else if(username_edit_txt.toString().trim().isEmpty())
        {

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

        else if(username_edit.getText().toString().trim().length()<4)
        {
            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.USERNAME_INVALIDE_CHRACTERS, Snackbar.LENGTH_LONG)
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
        else if(found)
        {
            Snackbar snackbar = Snackbar
                    .make(linear,""+Constant.Messages.USERNAME_INVALIDE_SPACE, Snackbar.LENGTH_LONG)
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
        else if(!ValidationUtility.validUserNamePassString(pass_edit_txt)) {
           // Toast.makeText(mContext,""+Constant.Messages.PASSWORD_INVALIDE,Toast.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar
                    .make(linear, "" + Constant.Messages.PASSWORD_INVALIDE, Snackbar.LENGTH_LONG)
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

    public void initSignUp()
    {
        materialDialog.show();
        new SignUpManager(mContext).initSignUpAPI(username_edit_txt,name_edit_txt,pass_edit_txt,email_edit_txt,campus_edit_txt, new CallBackManager() {
            @Override
            public void onSuccess(String responce)
            {
                materialDialog.dismiss();
                if(responce.toString().equals("100"))
                {
                    Toast.makeText(mContext,""+Constant.Messages.SIGNUP_SUCCESS,Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(mContext,LoginActivity.class);
                    intent.putExtra("roleval",finalrole);
                    startActivity(intent);
                    finish();
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




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
        intent.putExtra("roleval",finalrole);
        startActivity(intent);
        finish();
    }
}
