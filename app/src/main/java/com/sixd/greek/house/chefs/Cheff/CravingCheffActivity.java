package com.sixd.greek.house.chefs.Cheff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sixd.greek.house.chefs.HeaderActivty;
import com.sixd.greek.house.chefs.HomeActivityStudent;
import com.sixd.greek.house.chefs.ManagerCheff.CravingCheffManager;
import com.sixd.greek.house.chefs.ManagerCheff.MenuDevelopmentManager;
import com.sixd.greek.house.chefs.R;
import com.sixd.greek.house.chefs.manager.AllergyListManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.CravingManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.model.AllergyListModel;
import com.sixd.greek.house.chefs.model.CravingListModel;
import com.sixd.greek.house.chefs.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen on 20-Jul-17.
 */


public class CravingCheffActivity extends HeaderActivtyCheff {

    ScrollView alg_list1;
    int sizeofcheck;
    String is_blank="";

    Context mContext;
    MaterialDialog materialDialog=null;
    LinearLayout alg_list;
    TextView submit_btn;
    List<String> selected_allergyID=new ArrayList<>();
    LinearLayout linear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        materialDialog=new MaterialDialog.Builder(this)
                .content("Please Wait...")
                .progress(true, 0)
                .cancelable(false).build();
        setHeaderTitele("Craving Details");
        selected_allergyID.clear();
        initViews();
    }


    public void initViews() {

        alg_list1=(ScrollView)findViewById(R.id.alg_list1);
        alg_list1.setScrollbarFadingEnabled(false);
        alg_list1.setVerticalScrollBarEnabled(true);
        alg_list1.setVerticalFadingEdgeEnabled(false);

        linear=(LinearLayout)findViewById(R.id.linear);
        submit_btn=(TextView)findViewById(R.id.submit_btn);
        alg_list=(LinearLayout) findViewById(R.id.alg_list);

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

                   /* PostCravingListByChef(idOnj.toString(), is_blank);*/


                    Log.i("respo_idarr=", "" + idarr.length());
                    Log.i("respo_ids_final=", "" + idOnj.toString());

                    int listSize1 = CravingCheffManager.cravingListModels.size();
                    Log.i("list size", "" + listSize1);

                    if (listSize1 != 0) {
                        if (idarr.length() != 0) {
                            PostCravingListByChef(idOnj.toString());
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Please select craving!", Snackbar.LENGTH_LONG)
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
                                .make(linear, "Craving list is empty!", Snackbar.LENGTH_LONG)
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


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        initGetCravingListForChefAPI();
    }


    public void initGetCravingListForChefAPI(){
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new CravingCheffManager(mContext).initGetCravingListForChefAPI(new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {

                        int listSize=CravingCheffManager.cravingListModels.size();
                        Log.i("list size", "" + listSize);
                        if (listSize!=0)
                        {
                            setAlgList();
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(linear, "Craving list is empty!", Snackbar.LENGTH_LONG)
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


                        if(responce.contains("Received data")) {
                            initGetErrorhtmlAPI();
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




    public void initGetErrorhtmlAPI(){
        String error_content="";
        String sessionToken=new LoginManager(mContext).getSessionToken();
        Log.i("responstring", "cravingList serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_CRAVING_LIST_API
                +"&token="+sessionToken);

        error_content=""+Constant.BASEURL + "&param=" +
                Constant.API.GET_CRAVING_LIST_API
                +"&token="+sessionToken.toString();

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





    public void PostCravingListByChef(String craving_id_str){
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            materialDialog.show();

            new CravingCheffManager(mContext).initPostCravingListByChefAPI(craving_id_str, new CallBackManager() {

                @Override
                public void onSuccess(String responce) {
                    materialDialog.dismiss();
                    if (responce.equals("100"))
                    {
                        Toast.makeText(mContext, "Craving updated successfully!", Toast.LENGTH_SHORT).show();//Allergy is selected successfully

                        Intent intent = new Intent(mContext, HomeActivityCheff.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
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


    public void setAlgList()
    {
        selected_allergyID.clear();
        alg_list.removeAllViews();
        int listSize=CravingCheffManager.cravingListModels.size();
        Log.i("list size",""+listSize);

        for(int i=0;i<listSize;i++)
        {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.craving_list_item,null);
            final ImageView ch_ic=(ImageView)itemView.findViewById(R.id.ch_ic);
            final TextView allergy_txt=(TextView)itemView.findViewById(R.id.allergy_txt);

            CravingListModel allergyListModel=CravingCheffManager.cravingListModels.get(i);
            String allergy_id=allergyListModel.getStudent_wishlist_id();
            String allergy_name=allergyListModel.getItem_name();
            /*String user_check=allergyListModel.getUser_check();*/

            ch_ic.setTag(allergy_id);
            allergy_txt.setText(""+allergy_name);
          /*  if(user_check.equals("YES"))
            {
                ch_ic.setSelected(true);
                selected_allergyID.add(ch_ic.getTag().toString());
            }
            else ch_ic.setSelected(false);*/

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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CravingCheffActivity.this,HomeActivityCheff.class);
        startActivity(intent);
        finish();
    }

}
