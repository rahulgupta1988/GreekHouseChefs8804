package com.sixd.greek.house.chefs.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sixd.greek.house.chefs.model.EventModel;
import com.sixd.greek.house.chefs.utils.Constant;
import com.sixd.greek.house.chefs.utils.HttpClientSingle;
import com.sixd.greek.house.chefs.utils.InternetConnectionDetector;
import com.sixd.greek.house.chefs.utils.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao_db.AllEvents;
import dao_db.AllEventsDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.MenuCategoryDao;
import dao_db.MenuItemDishes;
import dao_db.MenuItemDishesDao;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praveen on 26-Jul-17.
 */

public class EventManager extends BaseManager{

    String responseString="";
    String responseStr="";
    Context context;
    public EventManager(Context context){
        this.context=context;
    }

    public void initEventAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "eventList serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.EVENT_API +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.EVENT_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("event responce",""+e.toString());
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
                final String parse_str=parseDataEvents(responseStr);
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


    public String parseDataEvents(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllEventsDao().deleteAll();
        AllEventsDao allEventsDao=daoSession.getAllEventsDao();

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

                                String event_id = jsonObject1.optString("event_id");
                                String is_student_calender = jsonObject1.optString("is_student_calender");
                                String event_title = jsonObject1.optString("event_title");
                                String event_start = jsonObject1.optString("event_start");
                                String event_end = jsonObject1.optString("event_end");
                                String start_time = jsonObject1.optString("start_time");
                                String end_time = jsonObject1.optString("end_time");
                                String description = jsonObject1.optString("description");

                                AllEvents allEvents=new AllEvents(event_id,is_student_calender,event_title,
                                        event_start,event_end,start_time,end_time,description);
                                allEventsDao.insertOrReplace(allEvents);

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
                allEventsDao.getDatabase().close();
            }
            return responseString;
        }


        public List<AllEvents> getAllEvents()
        {
            DaoSession daoSession = getDBSessoin(context);
            AllEventsDao allEventsDao=daoSession.getAllEventsDao();
            try {
                List<AllEvents> allEventses=new ArrayList<>();
                allEventses=allEventsDao.loadAll();
                return allEventses;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            finally {
                allEventsDao.getDatabase().close();
            }
        }



}

