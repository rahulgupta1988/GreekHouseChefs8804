package com.sixd.greek.house.chefs.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.HttpClientSingle;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.OkHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao_db.DaoSession;
import dao_db.UserLoginInfo;
import dao_db.UserLoginInfoDao;
import dao_db.UserType;
import dao_db.UserTypeDao;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praveen on 10-Jul-17.
 */

public class LoginManager {

    String responseStr="";
    Context context;

    public LoginManager(Context context){
        this.context=context;
    }

    public void initLoginAPI(String user_name,String password,
                             String apns_token,final CallBackManager callBackManager)
    {

        Log.i("responstring", "Login serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.LOIGIN_API
                + "&user_name="+user_name+"&password="+password+"&devicetype="+"Android"
                +"&deviceid="+"123"+"&apns_token="+apns_token);

        RequestBody formBody = new FormBody.Builder()
                .add("param",Constant.API.LOIGIN_API)
                .add("user_name",user_name)
                .add("password",password)
                .add("devicetype","Android")
                .add("deviceid","123")
                .add("apns_token",apns_token)
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
                Log.i("Login responce","sucess "+responseStr);
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
                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");
                          //  UserLoginInfo userLoginInfo=gson.fromJson(jsonObject1.toString(),UserLoginInfo.class);


                            String student_id=jsonObject1.getString("student_id");
                            String chef_id=jsonObject1.getString("chef_id");
                            String user_id=jsonObject1.getString("user_id");
                            String user_name=jsonObject1.getString("user_name");
                            String first_name=jsonObject1.getString("first_name");
                            String last_name=jsonObject1.getString("last_name");
                            String email=jsonObject1.getString("email");
                            String phone=jsonObject1.getString("phone");
                            String address=jsonObject1.getString("address");
                            String city=jsonObject1.getString("city");
                            String state=jsonObject1.getString("state");
                            String zip=jsonObject1.getString("zip");
                            String image_url=jsonObject1.getString("image_url");
                            String status=jsonObject1.getString("status");
                            String session_token=jsonObject1.getString("session_token");
                            String house_id=jsonObject1.getString("house_id");
                            String house_name=jsonObject1.getString("house_name");
                            String campus_id=jsonObject1.getString("campus_id");
                            String campus_name=jsonObject1.getString("campus_name");
                            String code=jsonObject1.getString("code");
                            String chef_name=jsonObject1.getString("chef_name");
                            String chef_image_url=jsonObject1.getString("chef_image_url");
                            String is_lateplate=jsonObject1.getString("is_lateplate");
                            String is_lateplate_outofhouse=jsonObject1.getString("is_lateplate_outofhouse");

                            UserLoginInfo userLoginInfo=new UserLoginInfo(student_id,chef_id,user_id,user_name,first_name,last_name,
                                    email,phone,address,city,state,zip,image_url,status,session_token,house_id,house_name,campus_id,
                                    campus_name,code,chef_name,chef_image_url,is_lateplate,is_lateplate_outofhouse);
                            userLoginInfoDao.insertOrReplace(userLoginInfo);

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


    public List<UserLoginInfo> getUserInfo()
    {
        DaoSession daoSession=BaseManager.getDBSessoin(context);
        UserLoginInfoDao userDao=daoSession.getUserLoginInfoDao();
        try {
            List<UserLoginInfo> userLoginInfos = new ArrayList<>();
            userLoginInfos = userDao.loadAll();
            return userLoginInfos;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<UserType> getUserTypes()
    {
        DaoSession daoSession=BaseManager.getDBSessoin(context);
        UserTypeDao userTypeDao=daoSession.getUserTypeDao();
        try {
            List<UserType> userTypes=new ArrayList<>();
            userTypes=userTypeDao.loadAll();
            return userTypes;

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            userTypeDao.getDatabase().close();
        }
    }

}
