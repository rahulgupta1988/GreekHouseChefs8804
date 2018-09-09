package com.sixd.greek.house.chefs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.manager.AllergyListManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.model.AllergyListModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Praveen on 01-Aug-17.
 */

public class AllergyActivity extends HeaderActivty {

    ScrollView alg_list1;
    int sizeofcheck;
    String is_blank="";

    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout alg_list;
    TextView submit_btn;
    TextView add_allergy_icon;
    EditText allergy_txt;
    List<String> selected_allergyID=new ArrayList<>();
    LinearLayout linear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();
        setHeaderTitele("Allergy");
        selected_allergyID.clear();
        initViews();
    }


    public void initViews() {


        alg_list1=(ScrollView)findViewById(R.id.alg_list1);
        alg_list1.setScrollbarFadingEnabled(false);
        alg_list1.setVerticalScrollBarEnabled(true);
        alg_list1.setVerticalFadingEdgeEnabled(false);

        linear=(LinearLayout)findViewById(R.id.linear);

        add_allergy_icon=(TextView)findViewById(R.id.add_allergy_icon);
        allergy_txt=(EditText)findViewById(R.id.allergy_txt);
        submit_btn=(TextView)findViewById(R.id.submit_btn);
        alg_list=(LinearLayout) findViewById(R.id.alg_list);

        add_allergy_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ids 324325", "" + selected_allergyID.toString());
                String alg_msg = allergy_txt.getText().toString().trim();

                if (alg_msg.length() > 0) {
                    initMSGAlleryAPI(alg_msg);
                } else {
                    // Toast.makeText(mContext,"Enter allergy!",Toast.LENGTH_SHORT).show();

                    Snackbar snackbar = Snackbar
                            .make(linear, "Enter allergy!", Snackbar.LENGTH_LONG)
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


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject idOnj = new JSONObject();
                    JSONArray idarr = new JSONArray();
                    for (int i = 0; i < selected_allergyID.size(); i++) {
                        idarr.put(selected_allergyID.get(i));
                    }
                    idOnj.put("ids", idarr);
                    Log.i("json obj 43543", "" + idOnj.toString());

                    sizeofcheck = selected_allergyID.size();
                    Log.i("selectedsize", "" + sizeofcheck);

                    if (sizeofcheck == 0) {
                        is_blank = "1";
                    } else {
                        is_blank = "0";
                    }

                    initAddAlleryAPI(idOnj.toString(), is_blank);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        initAlleryAPI();
    }


    public void initAlleryAPI(){
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new AllergyListManager(mContext).initAlleryAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if(responce.equals("100"))
                    {
                        allergy_txt.setText("");
                        setAlgList();
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(linear, ""+responce, Snackbar.LENGTH_LONG)
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

                        if(responce.contains("Received data"))
                        {
                            String sessionToken=new LoginManager(mContext).getSessionToken();
                            String error_content=""+Constant.BASEURL + "&param=" +
                                    Constant.API.ALLERGY_API
                                    +"&token="+sessionToken.toString();
                            initGetErrorhtmlAPI(error_content);
                        }

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
            snackbar.show();        }
    }

    public void initMSGAlleryAPI(String alg_msg)
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

            new AllergyListManager(mContext).initAddMSGAlleryAPI(alg_msg, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {

                       /* Snackbar snackbar = Snackbar
                                .make(linear,"Your allergy requirement has been added. Please select and submit to inform Chef about the same!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });

                        snackbar.setActionTextColor(Color.RED);

                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        textView.setMaxLines(3);
                        snackbar.show();*/

                        Toast.makeText(getApplicationContext(), "Your allergy requirement has been added. Please select and submit to inform Chef about the same!",
                                Toast.LENGTH_LONG).show();


                        allergy_txt.setText("");
                        setAlgList();
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


    public void initAddAlleryAPI(String allergi_id_str, final String is_blank){
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new AllergyListManager(mContext).initAlleryAddAPI(allergi_id_str, is_blank, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {
                        //Toast.makeText(mContext,responce, Toast.LENGTH_SHORT).show();//Allergy is selected successfully
                        if (is_blank.equalsIgnoreCase("0")) {
                            Toast.makeText(mContext, "Thanks for letting us know! We’ll do our best to accommodate your allergy!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Great to know that you are not allergic to anything!", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(mContext, HomeActivityStudent.class);
                        startActivity(intent);
                        finish();
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


    public void setAlgList(){
       /* RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        alg_list.setLayoutManager(layoutManager);
        alg_list.setItemAnimator(new DefaultItemAnimator());
        alg_list.setAdapter(new AlleryListAdapter(mContext, new AlleryListAdapter.AllergyCall() {
            @Override
            public void allergyID(String id) {
                selected_allergyID.add(id);
            }

            @Override
            public void removeAllergy(String id) {
                selected_allergyID.remove(id);
            }
        }));*/
        selected_allergyID.clear();
        alg_list.removeAllViews();
        int listSize=AllergyListManager.allergyListModels.size();
        Log.i("list size",""+listSize);

        for(int i=0;i<listSize;i++){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.allergylist_item,null);
            final ImageView ch_ic=(ImageView)itemView.findViewById(R.id.ch_ic);
            final TextView allergy_txt=(TextView)itemView.findViewById(R.id.allergy_txt);

            AllergyListModel allergyListModel=AllergyListManager.allergyListModels.get(i);
            String allergy_id=allergyListModel.getAllergy_id();
            String allergy_name=allergyListModel.getAllergy_name();
            String user_check=allergyListModel.getUser_check();

            ch_ic.setTag(allergy_id);
            allergy_txt.setText(""+allergy_name);
            if(user_check.equals("YES")) {
                ch_ic.setSelected(true);
                selected_allergyID.add(ch_ic.getTag().toString());
            }
            else ch_ic.setSelected(false);

            ch_ic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ch_ic.isSelected()){
                        ch_ic.setSelected(false);
                        selected_allergyID.remove(ch_ic.getTag().toString());
                    }

                    else{
                        ch_ic.setSelected(true);
                        selected_allergyID.add(ch_ic.getTag().toString());
                    }
                }
            });


            alg_list.addView(itemView);
        }

    }


    public void initGetErrorhtmlAPI(String content)
    {
        String error_content="";
        error_content=content;

        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new CravingCheffManager(mContext).initGetErrorhtmlAPI(error_content, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100")) {

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



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AllergyActivity.this, HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }

}