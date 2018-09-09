package com.sixd.greek.house.chefs.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.sixd.greek.house.chefs.model.MenuModel;
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
import dao_db.AllWeekIntervalList;
import dao_db.AllWeekIntervalListDao;
import dao_db.CalenderData;
import dao_db.CalenderDataDao;
import dao_db.DaoSession;
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
 * Created by Praveen on 17-Jul-17.
 */

public class MenuCalendarManager extends BaseManager{

    String responseString="";
    String responseStr="";
    Context context;
    public static String menu_doc_URL="";
    public static String menu_calendar_id="";
    public static String menu_is_draft="";

    public static List<MenuModel> breaffastList=new ArrayList<>();
    public static List<MenuModel> lunchList=new ArrayList<>();
    public static List<MenuModel> dinnerList=new ArrayList<>();

    public MenuCalendarManager(Context context){
        this.context=context;
    }


    public void initMenucalenderAPI(String Week_number,String year,final CallBackManager callBackManager)
    {
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "menuCalendarList serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.MENU_CALENDER_API+"&Week_number="+Week_number+"&year="+year +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.MENU_CALENDER_API)
                .add("Week_number",Week_number)
                .add("year",year)
                .build();



        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("menu calender responce",""+e.toString());
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
                Log.i("menu calender 3245","sucess "+responseStr);
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



    public String parseData(String jsonResponse)
    {
        String responseString="";
        String responseCode = "";
          breaffastList.clear();
          lunchList.clear();
          dinnerList.clear();

          menu_doc_URL="";
          menu_calendar_id="";
          menu_is_draft="";


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
                            menu_doc_URL=jsonObject.getString("download_url");
                            menu_calendar_id=jsonObject.getString("menu_calendar_id");
                            menu_is_draft=jsonObject.getString("is_draft");

                            JSONArray jsonObject1=jsonObject.getJSONArray("responseData");

                            for(int i=0;i<jsonObject1.length();i++){
                                MenuModel breakmenuModel=new MenuModel();
                                MenuModel lunchmenuModel=new MenuModel();
                                MenuModel dinnermenuModel=new MenuModel();

                                JSONObject jsonObject2=jsonObject1.getJSONObject(i);
                                String date=jsonObject2.getString("date");

                                String menucalender_braekfast_cs=jsonObject2.getString("menucalender_braekfast_cs");
                                String menucalender_lunch_cs=jsonObject2.getString("menucalender_lunch_cs");
                                String menucalender_dinner_cs=jsonObject2.getString("menucalender_dinner_cs");

                                /*JSONArray breakarrary=jsonObject2.getJSONArray("brackfast");
                                JSONArray luncharrary=jsonObject2.getJSONArray("lunch");
                                JSONArray dinnerarrary=jsonObject2.getJSONArray("dinner");

                                String breakTitle="",lunchtitle="",dinnerTitle="";

                                for(int j=0;j<breakarrary.length();j++){
                                    breakTitle=breakTitle+breakarrary.getString(j);
                                }

                                for(int k=0;k<luncharrary.length();k++){
                                    lunchtitle=lunchtitle+luncharrary.getString(k);
                                }

                                for(int l=0;l<dinnerarrary.length();l++){
                                    dinnerTitle=dinnerTitle+dinnerarrary.getString(l);
                                }*/


                                String breakTitle=jsonObject2.getString("brackfast");
                                String lunchtitle=jsonObject2.getString("lunch");
                                String dinnerTitle=jsonObject2.getString("dinner");


                                String dayname=jsonObject2.getString("dayname");
                                String daynameStart=jsonObject2.getString("daynameStart");


                                breakmenuModel.setDate(date);
                                breakmenuModel.setMenuTitle(breakTitle);
                                breakmenuModel.setMenucalender_cs(menucalender_braekfast_cs);
                                breakmenuModel.setDayname(dayname);
                                breakmenuModel.setDaynameStart(daynameStart);



                                lunchmenuModel.setDate(date);
                                lunchmenuModel.setMenuTitle(lunchtitle);
                                lunchmenuModel.setMenucalender_cs(menucalender_lunch_cs);
                                lunchmenuModel.setDayname(dayname);
                                lunchmenuModel.setDaynameStart(daynameStart);


                                dinnermenuModel.setDate(date);
                                dinnermenuModel.setMenuTitle(dinnerTitle);
                                dinnermenuModel.setMenucalender_cs(menucalender_dinner_cs);
                                dinnermenuModel.setDayname(dayname);
                                dinnermenuModel.setDaynameStart(daynameStart);

                                breaffastList.add(breakmenuModel);
                                lunchList.add(lunchmenuModel);
                                dinnerList.add(dinnermenuModel);
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




    public void initgetWeekIntervalAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "getWeekInterval serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.WEEK_INTERVAL_API
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.WEEK_INTERVAL_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("getWeekInterval responc",""+e.toString());
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
                final String parse_str=parseDataWeekInterval(responseStr);
                Log.i("getCategories responce","sucess "+responseStr);
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


    public String parseDataWeekInterval(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllWeekIntervalListDao().deleteAll();
        AllWeekIntervalListDao allWeekIntervalListDao=daoSession.getAllWeekIntervalListDao();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null))
            {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject)
                {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsarray1 = jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "jsarray1.length()=" + jsarray1.length());
                            for (int i = 0; i < jsarray1.length(); i++)
                            {
                                Log.i("inside loop", "" + i);
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);
                                String week = jsonObject1.optString("week");
                                String interval = jsonObject1.optString("interval");
                                String year = jsonObject1.optString("year");
                                String current_week = jsonObject1.optString("current_week");

                                AllWeekIntervalList allWeekIntervalList=new AllWeekIntervalList(week,interval,year,current_week);
                                allWeekIntervalListDao.insertOrReplace(allWeekIntervalList);
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
            allWeekIntervalListDao.getDatabase().close();
        }
        return responseString;
    }



   /* ===========================================================================*/

    /*public void initGetMasterAllMenuItemsAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "MasterAllMenuItems serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.MASTER_MENU_ITEMS_API
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.MASTER_MENU_ITEMS_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("MasterAllMenuItems resp",""+e.toString());
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
                final String parse_str=parseDataMasterAllMenuItems(responseStr);
                Log.i("MasterAllMenuItems resp","sucess "+responseStr);
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


  public String parseDataMasterAllMenuItems(String jsonResponse)
   {
    String responseCode = "";
    DaoSession daoSession = getDBSessoin(context);
    daoSession.getGetMasterAllMenuItemsDao().deleteAll();
    GetMasterAllMenuItemsDao getMasterAllMenuItemsDao=daoSession.getGetMasterAllMenuItemsDao();

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
                            String menu_id = jsonObject1.optString("menu_id");
                            String menu_title = jsonObject1.optString("menu_title");

                            GetMasterAllMenuItems getMasterAllMenuItems=new GetMasterAllMenuItems(menu_id,menu_title);
                            getMasterAllMenuItemsDao.insertOrReplace(getMasterAllMenuItems);
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
        getMasterAllMenuItemsDao.getDatabase().close();
    }
   return responseString;
  }*/



    public void initaddEditMenuCalenderAPI(String meal_type,String week_number,String week_year,String mon_meal,String mon_cs,
             String tue_meal,String tue_cs,String wed_meal,String wed_cs,String thu_meal,String thu_cs,
             String fri_meal,String fri_cs,String sat_meal,String sat_cs,String sun_meal,String sun_cs,String menu_calendar_id,
                                           String Is_draft,
                      final CallBackManager callBackManager)
    {
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addEditMenuCalender serviceUrl-->" + Constant.BASEURL + "&param="+
                Constant.API.ADD_MENU_CALENDER_API +"&meal_type="+meal_type+"&week_number="+week_number
                + "&week_year="+week_year+"&mon_meal=" + mon_meal+ "&mon_cs=" + mon_cs
                + "&tue_meal="+tue_meal+"&tue_cs="+tue_cs+"&wed_meal="+wed_meal+"&wed_cs="+wed_cs
                + "&thu_meal="+thu_meal+"&thu_cs="+thu_cs+"&fri_meal="+fri_meal+"&fri_cs="+fri_cs
                + "&sat_meal="+sat_meal+"&sat_cs="+sat_cs+"&sun_meal="+sun_meal+"&sun_cs="+sun_cs
                + "&menu_calendar_id="+menu_calendar_id+ "&Is_draft="+Is_draft
                + "&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADD_MENU_CALENDER_API)
                .add("meal_type", meal_type)
                .add("week_number",week_number)
                .add("week_year",week_year)

                .add("mon_meal", mon_meal)
                .add("mon_cs", mon_cs)
                .add("tue_meal",tue_meal)
                .add("tue_cs",tue_cs)
                .add("wed_meal",wed_meal)
                .add("wed_cs", wed_cs)
                .add("thu_meal",thu_meal)

                .add("thu_cs", thu_cs)
                .add("fri_meal", fri_meal)
                .add("fri_cs", fri_cs)

                .add("sat_meal", sat_meal)
                .add("sat_cs", sat_cs)
                .add("sun_meal",sun_meal)
                .add("sun_cs", sun_cs)
                .add("menu_calendar_id",menu_calendar_id)
                .add("Is_draft", Is_draft)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("addEditMenuCalender res",""+e.toString());
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
                final String parse_str=parseaddEditMenuCalData(responseStr);
                Log.i("addEditMenuCalender","sucess "+responseStr);
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



    public String parseaddEditMenuCalData(String jsonResponse)
    {
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








    public List<MenuItemDishes> getMenuItemDishes()
    {
        DaoSession daoSession = getDBSessoin(context);
        MenuItemDishesDao menuItemDishesDao=daoSession.getMenuItemDishesDao();
        try {
            List<MenuItemDishes> menuItemDishes=new ArrayList<>();
            menuItemDishes=menuItemDishesDao.loadAll();
            return menuItemDishes;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            menuItemDishesDao.getDatabase().close();
        }
    }

    public List<AllWeekIntervalList> getAllWeekIntervalLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllWeekIntervalListDao allWeekIntervalListDao=daoSession.getAllWeekIntervalListDao();
        try
        {
            List<AllWeekIntervalList> allWeekIntervalLists=new ArrayList<>();
            allWeekIntervalLists=allWeekIntervalListDao.loadAll();
            return allWeekIntervalLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allWeekIntervalListDao.getDatabase().close();
        }
    }

  /*  public List<GetMasterAllMenuItems> getMasterAllMenuItemses()
    {
        DaoSession daoSession = getDBSessoin(context);
        GetMasterAllMenuItemsDao getMasterAllMenuItemsDao=daoSession.getGetMasterAllMenuItemsDao();
        try {
            List<GetMasterAllMenuItems> getMasterAllMenuItemses=new ArrayList<>();
            getMasterAllMenuItemses=getMasterAllMenuItemsDao.loadAll();
            return getMasterAllMenuItemses;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            getMasterAllMenuItemsDao.getDatabase().close();
        }
    }*/

    public List<CalenderData> geCalenderDatas()
    {
        DaoSession daoSession = getDBSessoin(context);
        CalenderDataDao calenderDataDao=daoSession.getCalenderDataDao();
        try {
            List<CalenderData> calenderDatas=new ArrayList<>();
            calenderDatas=calenderDataDao.loadAll();
            return calenderDatas;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            calenderDataDao.getDatabase().close();
        }
    }

}
