package com.sixd.greek.house.chefs.ManagerCheff;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.HttpClientSingle;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.OkHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praveen on 31-Jul-17.
 */

public class SubmitRecipeManager {
    String responseStr="";
    Context context;

    public SubmitRecipeManager(Context context){
        this.context=context;
    }



    public void initaddRecipeAPI(String Dish_name,String Dish_time,String Ingrediats,String Method,
                                 final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addRecipe serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ADD_RECIPE_API+"&Dish_name="+Dish_name+"&Dish_time="+Dish_time+"&Ingrediats="+Ingrediats
                +"&Method="+Method+"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADD_RECIPE_API)
                .add("Dish_name", Dish_name)
                .add("Dish_time", Dish_time)
                .add("Ingrediats", Ingrediats)
                .add("Method", Method)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("addRecipe api responce",""+e.toString());
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
                Log.i("craving responce","sucess "+responseStr);
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
        } catch (JSONException e) {
            responseString = Constant.Messages.SERVER_RESPONCE;
            e.printStackTrace();
        }

        return responseString;
    }


    public void initchangePasswordAPI(String old_password,String new_password,String confirm_password,
                                 final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "changePassword serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.CHANGE_PASSWORD_API+"&old_password="+old_password+"&new_password="+new_password
                +"&confirm_password="+confirm_password +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.CHANGE_PASSWORD_API)
                .add("old_password", old_password)
                .add("new_password", new_password)
                .add("confirm_password", confirm_password)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("changePassword api resp",""+e.toString());
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
                final String parse_str=parsePasswordData(responseStr);
                Log.i("changePassword responce","sucess "+responseStr);
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



    public String parsePasswordData(String jsonResponse) {
        String responseString="";
        String responseCode = "";

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
        } catch (JSONException e) {
            responseString = Constant.Messages.SERVER_RESPONCE;
            e.printStackTrace();
        }

        return responseString;
    }

}
