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
import java.util.List;

import dao_db.DaoSession;
import dao_db.UserLoginInfo;
import dao_db.UserLoginInfoDao;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praveen on 14-Jul-17.
 */

public class SignUpManager {

    String responseStr="";
    Context context;

    public SignUpManager(Context context){
        this.context=context;
    }

    public void initSignUpAPI(String user_name,String first_name,String password,
                              String email,String house_code,final CallBackManager callBackManager){

        Log.i("responstring", "SIGNUP_API serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.SIGNUP_API
                + "&user_name="+user_name+"&first_name="+first_name+"&password="+password
                +"&email="+email+"&house_code="+house_code);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.SIGNUP_API)
                .add("user_name",user_name)
                .add("first_name",first_name)
                .add("password",password)
                .add("email",email)
                .add("house_code",house_code)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,"");
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("Login responce",""+e.toString());
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
                Log.i("signup responce","sucess "+responseStr);
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

        DaoSession daoSession=BaseManager.getDBSessoin(context);
        UserLoginInfoDao userLoginInfoDao= daoSession.getUserLoginInfoDao();
        userLoginInfoDao.deleteAll();
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
                            final String mas=jsonObject.getString("responseData");
                            ((Activity)context).runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    try {
                                        /*Toast.makeText(context,""+mas,Toast.LENGTH_SHORT).show();*/
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

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


    public String getSessionToken(){
        DaoSession daoSession=BaseManager.getDBSessoin(context);
        UserLoginInfoDao userLoginInfoDao= daoSession.getUserLoginInfoDao();
        List<UserLoginInfo> userLoginInfoList= userLoginInfoDao.loadAll();
        UserLoginInfo userLoginInfo=null;
        if(userLoginInfoList.size()>0)
            userLoginInfo=userLoginInfoList.get(0);
        if(userLoginInfo!=null)
            return  userLoginInfo.getSession_token();
        return "";
    }
}
