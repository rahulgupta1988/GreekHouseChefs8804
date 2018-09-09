package com.sixd.greek.house.chefs.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
 * Created by Praveen on 12-Jul-17.
 */

public class ForgotPasswordManager {
    String responseStr="";
    Context context;

    public ForgotPasswordManager(Context context){
        this.context=context;
    }

    public void initforgotPasswordAPI(String email,String type,final CallBackManager callBackManager) {

        Log.i("responstring", "FORGOTPASSWORD_API serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.FORGOTPASSWORD_API + "&email=" + email
                + "&type=" + type);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.FORGOTPASSWORD_API)
                .add("email", email)
                .add("type", type)
                .build();


        OkHttpClient okHttpClient = HttpClientSingle.getClient();
        Request request = OkHttpRequest.getRequest(formBody, "");
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBackManager.onFailure(e.toString());

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                responseStr = response.body().string();
                final String parse_str = parseData(responseStr);
                Log.i("FORGOTPASSWORD responce","sucess "+responseStr);
                ((Activity) context).runOnUiThread(new Runnable() {
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
                            final String mas=jsonObject.getString("responseData");
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                       // Toast.makeText(context,""+mas,Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


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
