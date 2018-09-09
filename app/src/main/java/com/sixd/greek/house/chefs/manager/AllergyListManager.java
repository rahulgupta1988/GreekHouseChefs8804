package com.sixd.greek.house.chefs.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sixd.greek.house.chefs.model.AllergyListModel;
import com.sixd.greek.house.chefs.model.EventModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.HttpClientSingle;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praveen on 09-Aug-17.
 */

public class AllergyListManager{

    String responseStr="";
    Context context;
    public static List<AllergyListModel> allergyListModels = new ArrayList<>();

    public AllergyListManager(Context context){
        this.context=context;

    }

    public void initAlleryAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "allergyList serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ALLERGY_API
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ALLERGY_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("allergy responce",""+e.toString());
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBackManager.onFailure(e.toString());

                    }
                });

            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                responseStr=response.body().string();
                final String parse_str=parseData(responseStr);
                Log.i("allergy responce","sucess "+responseStr);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBackManager.onSuccess(parse_str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }



    public String parseData(String jsonResponse) {
        String responseString="";
        String responseCode = "";
        allergyListModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {

                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONArray jsonObject1=jsonObject.getJSONArray("responseData");
                            for(int i=0;i<jsonObject1.length();i++){
                                AllergyListModel allergyListModel=new AllergyListModel();
                                JSONObject obj1=jsonObject1.getJSONObject(i);
                                allergyListModel.setAllergy_id(obj1.getString("allergy_id"));
                                allergyListModel.setAllergy_name(obj1.getString("allergy_name"));
                                allergyListModel.setStatus(obj1.getString("status"));
                                allergyListModel.setUser_id(obj1.getString("user_id"));
                                allergyListModel.setUser_check(obj1.getString("user_check"));
                                allergyListModels.add(allergyListModel);
                            }

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }
                }
                else {
                    responseString = Constant.Messages.SERVER_RESPONCE;
                }
            }

            else {
                responseString = Constant.Messages.INTERNET_CONNECTIVITY;
            }
        } catch (Exception e) {
            responseString = Constant.Messages.SERVER_RESPONCE;
            e.printStackTrace();
        }


        return responseString;
    }



    public void initAddMSGAlleryAPI(String allergy_name,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addAllergyMessage serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ADDMSG_ALLERGY_API+"&allergy_name="+allergy_name
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADDMSG_ALLERGY_API)
                .add("allergy_name",allergy_name)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("msg allergy responce",""+e.toString());
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBackManager.onFailure(e.toString());

                    }
                });

            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                responseStr=response.body().string();
                final String parse_str=parseAddMSGData(responseStr);
                Log.i("msg allergy responce","sucess "+responseStr);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBackManager.onSuccess(parse_str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    public String parseAddMSGData(String jsonResponse) {
        String responseString="";
        String responseCode = "";
        allergyListModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {

                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonObject1=jsonObject.getJSONArray("responseData");
                            for(int i=0;i<jsonObject1.length();i++){
                                AllergyListModel allergyListModel=new AllergyListModel();
                                JSONObject obj1=jsonObject1.getJSONObject(i);
                                allergyListModel.setAllergy_id(obj1.getString("allergy_id"));
                                allergyListModel.setAllergy_name(obj1.getString("allergy_name"));
                                allergyListModel.setStatus(obj1.getString("status"));
                                allergyListModel.setUser_id(obj1.getString("user_id"));
                                allergyListModel.setUser_check(obj1.getString("user_check"));
                                allergyListModels.add(allergyListModel);
                            }


                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }
                }
                else {
                    responseString = Constant.Messages.SERVER_RESPONCE;
                }
            }

            else {
                responseString = Constant.Messages.INTERNET_CONNECTIVITY;
            }
        } catch (Exception e) {
            responseString = Constant.Messages.SERVER_RESPONCE;
            e.printStackTrace();
        }


        return responseString;
    }



    public void initAlleryAddAPI(String allergy_id_str,String is_blank,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addAllergy serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ALLERGY_ADD_API+"&allergy_id="+allergy_id_str+"&is_blank="+is_blank
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ALLERGY_ADD_API)
                .add("allergy_id",allergy_id_str)
               .add("is_blank",is_blank)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("add allergy responce",""+e.toString());
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBackManager.onFailure(e.toString());

                    }
                });

            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                responseStr=response.body().string();

                final String parse_str=parseAddData(responseStr);
                Log.i("add allergy responce","sucess "+responseStr);

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBackManager.onSuccess(parse_str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }



    public String parseAddData(String jsonResponse) {
        String responseString="";
        String responseCode = "";
        allergyListModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {

                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }
                }
                else {
                    responseString = Constant.Messages.SERVER_RESPONCE;
                }
            }

            else {
                responseString = Constant.Messages.INTERNET_CONNECTIVITY;
            }
        } catch (Exception e) {
            responseString = Constant.Messages.SERVER_RESPONCE;
            e.printStackTrace();
        }


        return responseString;
    }

}


