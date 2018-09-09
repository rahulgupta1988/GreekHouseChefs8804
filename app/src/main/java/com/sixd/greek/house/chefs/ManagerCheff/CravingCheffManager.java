package com.sixd.greek.house.chefs.ManagerCheff;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.model.AllergyListModel;
import com.sixd.greek.house.chefs.model.CravingListModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.HttpClientSingle;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
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
 * Created by Praveen on 31-Jul-17.
 */

public class CravingCheffManager{
    String responseStr="";
    Context context;
    public static List<CravingListModel> cravingListModels = new ArrayList<>();

    public CravingCheffManager(Context context){
        this.context=context;
    }



    public void initGetCravingListForChefAPI(final CallBackManager callBackManager)
    {
        String sessionToken=new LoginManager(context).getSessionToken();
        Log.i("responstring", "cravingList serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_CRAVING_LIST_API
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.GET_CRAVING_LIST_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("cravingList responce",""+e.toString());
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
                Log.i("cravingList responce","sucess "+responseStr);
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
        cravingListModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {

                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONArray jsonObject1=jsonObject.getJSONArray("responseData");

                            for(int i=0;i<jsonObject1.length();i++)
                            {
                                CravingListModel allergyListModel=new CravingListModel();
                                JSONObject obj1=jsonObject1.getJSONObject(i);
                                allergyListModel.setStudent_wishlist_id(obj1.getString("student_wishlist_id"));
                                allergyListModel.setItem_name(obj1.getString("item_name"));
                                allergyListModel.setWishlist_date(obj1.getString("wishlist_date"));
                                allergyListModel.setStudent_name(obj1.getString("student_name"));
                                cravingListModels.add(allergyListModel);
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


    public void initPostCravingListByChefAPI(String craving_id_str,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addCraving serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.POST_CRAVING_DATA_API+"&student_wishlist_id="+craving_id_str
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.POST_CRAVING_DATA_API)
                .add("student_wishlist_id", craving_id_str)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("add addCraving responce",""+e.toString());
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
                Log.i("add addCraving responce","sucess "+responseStr);

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
        cravingListModels.clear();
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





    /*public String parseDataCravingList(String jsonResponse) {
        String responseString="";
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllCravingListDao().deleteAll();
        AllCravingListDao allCravingListDao=daoSession.getAllCravingListDao();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null))
            {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject)
                {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONArray jsarray1 = jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "jsarray1.length()=" + jsarray1.length());
                            for (int i = 0; i < jsarray1.length(); i++)
                            {
                                Log.i("inside loop", "" + i);
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);
                                String student_wishlist_id = jsonObject1.optString("student_wishlist_id");
                                String item_name = jsonObject1.optString("item_name");
                                String wishlist_date = jsonObject1.optString("wishlist_date");
                                String student_name=jsonObject1.optString("student_name");

                                AllCravingList menuCategory=new AllCravingList(student_wishlist_id,item_name,
                                        wishlist_date,student_name);
                                allCravingListDao.insertOrReplace(menuCategory);

                            }
                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = "Received data is not compatible.";
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            allCravingListDao.getDatabase().close();
        }
        return responseString;
    }
*/

/*    public List<AllCravingList> getAllCravingLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllCravingListDao allCravingListDao=daoSession.getAllCravingListDao();
        try {
            List<AllCravingList> allCravingLists=new ArrayList<>();
            allCravingLists=allCravingListDao.loadAll();
            return allCravingLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allCravingListDao.getDatabase().close();
        }
    }*/



    public void initGetErrorhtmlAPI(String error_content,
                                    final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetErrorhtml serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.POST_HTML_API+"&devicetype="+"Android"
                +"&error_content="+error_content +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param",Constant.API.POST_HTML_API)
                .add("devicetype","Android")
                .add("error_content", error_content)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("add GetErrorhtml respo",""+e.toString());
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

                final String parse_str=parseHTMLData(responseStr);
                Log.i("add GetErrorhtml respo","sucess "+responseStr);

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



    public String parseHTMLData(String jsonResponse) {
        String responseString="";
        String responseCode = "";
        cravingListModels.clear();
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
