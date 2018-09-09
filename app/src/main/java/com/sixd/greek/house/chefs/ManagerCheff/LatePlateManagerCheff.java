package com.sixd.greek.house.chefs.ManagerCheff;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.CallBackManager;
import com.sixd.greek.house.chefs.manager.LoginManager;
import com.sixd.greek.house.chefs.model.AllergyListModel;
import com.sixd.greek.house.chefs.model.EventModel;
import com.sixd.greek.house.chefs.model.MenuDevelopmentModel;
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

import dao_db.AllDinnerAllergyList;
import dao_db.AllDinnerAllergyListDao;
import dao_db.AllLatePlateChefList;
import dao_db.AllLatePlateChefListDao;
import dao_db.AllLunchAllergyList;
import dao_db.AllLunchAllergyListDao;
import dao_db.CurrentWeekDinner;
import dao_db.CurrentWeekDinnerDao;
import dao_db.CurrentWeekLunch;
import dao_db.CurrentWeekLunchDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.MenuCategoryDao;
import dao_db.MenuItemDishes;
import dao_db.MenuItemDishesDao;
import dao_db.NextWeekDinner;
import dao_db.NextWeekDinnerDao;
import dao_db.NextWeekLunch;
import dao_db.NextWeekLunchDao;
import dao_db.StudenNameDinner;
import dao_db.StudenNameDinnerDao;
import dao_db.StudenNameLunch;
import dao_db.StudenNameLunchDao;
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

public class LatePlateManagerCheff extends BaseManager {

    String responseString="";
    String responseStr="";
    Context context;
   // public static List<MenuDevelopmentModel> menuDevelopmentModels = new ArrayList<>();

    public LatePlateManagerCheff(Context context){
        this.context=context;

    }

    public void initGetlatePlateAPI(String param,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetlatePlate serviceUrl-->" + Constant.BASEURL + "&param=" +
                param
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", param)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("GetlatePlate responce",""+e.toString());
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
                final String parse_str=parseDataLatePlate(responseStr);
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


      public String parseDataLatePlate(String jsonResponse) {
        String responseCode = "",week_interval="";
          DaoSession daoSession = getDBSessoin(context);
          daoSession.getAllLatePlateChefListDao().deleteAll();
          daoSession.getStudenNameLunchDao().deleteAll();
          daoSession.getStudenNameDinnerDao().deleteAll();
          AllLatePlateChefListDao allLatePlateChefListDao=daoSession.getAllLatePlateChefListDao();
          StudenNameLunchDao studenNameLunchDao=daoSession.getStudenNameLunchDao();
          StudenNameDinnerDao studenNameDinnerDao=daoSession.getStudenNameDinnerDao();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null))
            {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject)
                {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            week_interval=jsonObject.getString("week_interval");
                            JSONArray jsarray1 = jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "jsarray1.length()=" + jsarray1.length());
                            for (int i = 0; i < jsarray1.length(); i++)
                            {
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);

                                JSONArray jsonArray_student_name_dinner=jsonObject1.getJSONArray("student_name_dinner");
                                Log.i("respo_stu_name_dinn",""+jsonArray_student_name_dinner.length());
                                JSONArray jsonArray_student_name_lunch=jsonObject1.getJSONArray("student_name_lunch");
                                Log.i("respo_stu_name_lunch",""+jsonArray_student_name_lunch.length());

                                String dayname = jsonObject1.optString("dayname");
                                String date = jsonObject1.optString("date");
                                String total_student_lunch = jsonObject1.optString("total_student_lunch");
                                String total_student_dinner=jsonObject1.optString("total_student_dinner");
                                String lunch_allergy=jsonObject1.optString("lunch_allergy");
                                String dinner_allergy=jsonObject1.optString("dinner_allergy");

                                AllLatePlateChefList allLatePlateChefList=new AllLatePlateChefList(dayname,date,
                                        total_student_lunch,total_student_dinner,lunch_allergy,dinner_allergy,week_interval);
                                allLatePlateChefListDao.insertOrReplace(allLatePlateChefList);

                                for (int j=0;j<jsonArray_student_name_lunch.length();j++)
                                {
                                    String studentname=""+jsonArray_student_name_lunch.get(j);

                                    StudenNameLunch studenNameLunch= new StudenNameLunch(dayname,studentname);
                                    studenNameLunchDao.insertOrReplace(studenNameLunch);
                                }

                                for (int j=0;j<jsonArray_student_name_dinner.length();j++)
                                {
                                    String studentname=""+jsonArray_student_name_dinner.get(j);

                                    StudenNameDinner studenNameDinner= new StudenNameDinner(dayname,studentname);
                                    studenNameDinnerDao.insertOrReplace(studenNameDinner);
                                }

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
            allLatePlateChefListDao.getDatabase().close();
        }
        return responseString;
    }




    public void initGetlatePlateAPIDetails(String param,String Date,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetlatePlateDetails serviceUrl-->" + Constant.BASEURL + "&param=" +
                param+"&Date="+Date
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", param)
                .add("Date", Date)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("GetlatePlateDetails res",""+e.toString());
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
                final String parse_str=parseDataLatePlateDetails(responseStr);
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


   /* public String parseDataLatePlateDetails(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllLunchAllergyListDao().deleteAll();
        daoSession.getAllDinnerAllergyListDao().deleteAll();
        AllLunchAllergyListDao allLunchAllergyListDao=daoSession.getAllLunchAllergyListDao();
        AllDinnerAllergyListDao allDinnerAllergyListDao=daoSession.getAllDinnerAllergyListDao();

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

                            JSONObject jsonObject1 = new JSONObject("responseData");
                            JSONArray jsonArray= jsonObject1.getJSONArray("lunch");
                            for(int j=0;j<jsonArray.length();j++)
                            {
                                JSONObject jsonObject2= jsonArray.getJSONObject(j);
                                String allergy_name= jsonObject2.getString("allergy_name");
                                AllLunchAllergyList allLunchAllergyList=new AllLunchAllergyList(allergy_name);
                                allLunchAllergyListDao.insertOrReplace(allLunchAllergyList);
                            }
                          *//*  JSONArray jsonArray1= jsonObject2.getJSONArray("dinner");
                            for(int k=0;k<jsonArray1.length();k++)
                            {
                                JSONObject jsonObject3= jsonArray1.getJSONObject(k);
                                String allergy_name= jsonObject3.getString("allergy_name");
                                AllDinnerAllergyList allDinnerAllergyList=new AllDinnerAllergyList(allergy_name);
                                allDinnerAllergyListDao.insertOrReplace(allDinnerAllergyList);
                            }*//*

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
            allLunchAllergyListDao.getDatabase().close();
            allDinnerAllergyListDao.getDatabase().close();
        }
        return responseString;
    }*/


    public String parseDataLatePlateDetails(String jsonResponse) {
        int id;
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllLunchAllergyListDao().deleteAll();
        daoSession.getAllDinnerAllergyListDao().deleteAll();
        AllLunchAllergyListDao allLunchAllergyListDao=daoSession.getAllLunchAllergyListDao();
        AllDinnerAllergyListDao allDinnerAllergyListDao=daoSession.getAllDinnerAllergyListDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null)) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONObject jsonObject1= jsonObject.getJSONObject("responseData");

                            JSONArray jsonArray= jsonObject1.getJSONArray("lunch");
                            Log.i("item_dishes_list", "jsarray1.length()=" + jsonArray.length());
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject2= jsonArray.getJSONObject(i);
                                String allergy_name= jsonObject2.getString("allergy_name");
                                String student_name= jsonObject2.getString("student_name");
                                AllLunchAllergyList allLunchAllergyList=new AllLunchAllergyList(allergy_name,
                                        student_name);
                                allLunchAllergyListDao.insertOrReplace(allLunchAllergyList);
                            }

                            JSONArray jsonArray1= jsonObject1.getJSONArray("dinner");
                            Log.i("item_dishes_list", "jsarray1.length()=" + jsonArray1.length());
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                JSONObject jsonObject3= jsonArray1.getJSONObject(i);
                                String allergy_name= jsonObject3.getString("allergy_name");
                                String student_name= jsonObject3.getString("student_name");
                                AllDinnerAllergyList allDinnerAllergyList=new AllDinnerAllergyList(allergy_name,
                                        student_name);
                                allDinnerAllergyListDao.insertOrReplace(allDinnerAllergyList);
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
            allLunchAllergyListDao.getDatabase().close();
            allDinnerAllergyListDao.getDatabase().close();
        }
        return responseString;
    }




    public void initLateplateDetailsForInhouseAndOutHouseAPI(String Is_outhouse_lateplate,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "LateplateDetailsForInhouseAndOutHouse serviceUrl-->" + Constant.BASEURL
                + "parma=" + "" + Constant.API.LATE_OUT_INFO_API
                + "&Is_outhouse_lateplate=" +
                Is_outhouse_lateplate
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.LATE_OUT_INFO_API)
                .add("Is_outhouse_lateplate", Is_outhouse_lateplate)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("Late_and_OutInfo respo",""+e.toString());
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
                final String parse_str=parseDataInfo(responseStr);
                Log.i("Late_and_OutInfo respo","sucess "+responseStr);
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




    public String parseDataInfo(String jsonResponse)
    {
        String responseString="";
        String responseCode = "";
        DaoSession daoSession=BaseManager.getDBSessoin(context);
        CurrentWeekLunchDao currentWeekLunchDao= daoSession.getCurrentWeekLunchDao();
        currentWeekLunchDao.deleteAll();
        CurrentWeekDinnerDao currentWeekDinnerDao= daoSession.getCurrentWeekDinnerDao();
        currentWeekDinnerDao.deleteAll();
        NextWeekLunchDao nextWeekLunchDao= daoSession.getNextWeekLunchDao();
        nextWeekLunchDao.deleteAll();
        NextWeekDinnerDao nextWeekDinnerDao= daoSession.getNextWeekDinnerDao();
        nextWeekDinnerDao.deleteAll();

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

                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");

                            JSONObject current_week= jsonObject1.getJSONObject("current_week");
                            JSONObject next_week= jsonObject1.getJSONObject("next_week");

                            JSONArray lunch= current_week.getJSONArray("lunch");
                            for(int i=0;i<lunch.length();i++){
                                JSONObject jsonObject3= lunch.getJSONObject(i);
                                String request_date= jsonObject3.getString("request_date");
                                String is_checked= jsonObject3.getString("is_checked");
                                String Dayname= jsonObject3.getString("Dayname");
                                CurrentWeekLunch currentWeekLunch= new CurrentWeekLunch(request_date,is_checked,Dayname);
                                currentWeekLunchDao.insertOrReplace(currentWeekLunch);

                            }

                            JSONArray dinner= current_week.getJSONArray("dinner");
                            for(int i=0;i<dinner.length();i++){
                                JSONObject jsonObject3= dinner.getJSONObject(i);
                                String request_date= jsonObject3.getString("request_date");
                                String is_checked= jsonObject3.getString("is_checked");
                                String Dayname= jsonObject3.getString("Dayname");
                                CurrentWeekDinner currentWeekDinner= new CurrentWeekDinner(request_date,is_checked,Dayname);
                                currentWeekDinnerDao.insertOrReplace(currentWeekDinner);

                            }

                            JSONArray lunchnext_week= next_week.getJSONArray("lunch");

                            for(int i=0;i<lunchnext_week.length();i++){
                                JSONObject jsonObject3= lunchnext_week.getJSONObject(i);
                                String request_date= jsonObject3.getString("request_date");
                                String is_checked= jsonObject3.getString("is_checked");
                                String Dayname= jsonObject3.getString("Dayname");
                                NextWeekLunch nextWeekLunch= new NextWeekLunch(request_date,is_checked,Dayname);
                                nextWeekLunchDao.insertOrReplace(nextWeekLunch);

                            }

                            JSONArray dinnernext_week= next_week.getJSONArray("dinner");

                            for(int i=0;i<dinnernext_week.length();i++){
                                JSONObject jsonObject3= dinnernext_week.getJSONObject(i);
                                String request_date= jsonObject3.getString("request_date");
                                String is_checked= jsonObject3.getString("is_checked");
                                String Dayname= jsonObject3.getString("Dayname");
                                NextWeekDinner nextWeekDinner= new NextWeekDinner(request_date,is_checked,Dayname);
                                nextWeekDinnerDao.insertOrReplace(nextWeekDinner);
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
        finally {
            currentWeekLunchDao.getDatabase().close();
            currentWeekDinnerDao.getDatabase().close();
            nextWeekLunchDao.getDatabase().close();
            nextWeekDinnerDao.getDatabase().close();
        }

        return responseString;
    }





    public List<AllLatePlateChefList> getAllLatePlateChefLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllLatePlateChefListDao allLatePlateChefListDao=daoSession.getAllLatePlateChefListDao();
        try {
            List<AllLatePlateChefList> allLatePlateChefLists=new ArrayList<>();
            allLatePlateChefLists=allLatePlateChefListDao.loadAll();
            return allLatePlateChefLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allLatePlateChefListDao.getDatabase().close();
        }
    }

    public List<AllLunchAllergyList> getAllLunchAllergyLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllLunchAllergyListDao allLunchAllergyListDao=daoSession.getAllLunchAllergyListDao();
        try {
            List<AllLunchAllergyList> allLunchAllergyLists=new ArrayList<>();
            allLunchAllergyLists=allLunchAllergyListDao.loadAll();
            return allLunchAllergyLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allLunchAllergyListDao.getDatabase().close();
        }
    }

    public List<AllDinnerAllergyList> getAllDinnerAllergyLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllDinnerAllergyListDao allDinnerAllergyListDao=daoSession.getAllDinnerAllergyListDao();
        try {
            List<AllDinnerAllergyList> allDinnerAllergyLists=new ArrayList<>();
            allDinnerAllergyLists=allDinnerAllergyListDao.loadAll();
            return allDinnerAllergyLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allDinnerAllergyListDao.getDatabase().close();
        }
    }


    public List<CurrentWeekLunch> getCurrentWeekLunchList()
    {
        DaoSession daoSession = getDBSessoin(context);
        CurrentWeekLunchDao currentWeekLunchDao=daoSession.getCurrentWeekLunchDao();
        try {
            List<CurrentWeekLunch> currentWeekLunches=new ArrayList<>();
            currentWeekLunches=currentWeekLunchDao.loadAll();
            return currentWeekLunches;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            currentWeekLunchDao.getDatabase().close();
        }
    }

   public List<CurrentWeekDinner> getCurrentWeekDinnersList()
   {
       DaoSession daoSession = getDBSessoin(context);
       CurrentWeekDinnerDao currentWeekDinnerDao=daoSession.getCurrentWeekDinnerDao();
       try {
           List<CurrentWeekDinner> currentWeekDinners=new ArrayList<>();
           currentWeekDinners=currentWeekDinnerDao.loadAll();
           return currentWeekDinners;
       }
       catch (Exception e){
           e.printStackTrace();
           return null;
       }
       finally {
           currentWeekDinnerDao.getDatabase().close();
       }
   }


    public List<NextWeekLunch> getNextWeekLunchList()
    {
        DaoSession daoSession = getDBSessoin(context);
        NextWeekLunchDao nextWeekLunchDao=daoSession.getNextWeekLunchDao();
        try {
            List<NextWeekLunch> nextWeekLunches=new ArrayList<>();
            nextWeekLunches=nextWeekLunchDao.loadAll();
            return nextWeekLunches;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            nextWeekLunchDao.getDatabase().close();
        }
    }

    public List<NextWeekDinner> getNextWeekDinnerList()
    {
        DaoSession daoSession = getDBSessoin(context);
        NextWeekDinnerDao nextWeekDinnerDao=daoSession.getNextWeekDinnerDao();
        try {
            List<NextWeekDinner> nextWeekDinners=new ArrayList<>();
            nextWeekDinners=nextWeekDinnerDao.loadAll();
            return nextWeekDinners;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            nextWeekDinnerDao.getDatabase().close();
        }
    }


    public List<StudenNameLunch> getStudenNameLunch()
    {
        DaoSession daoSession = getDBSessoin(context);
        StudenNameLunchDao studenNameLunchDao=daoSession.getStudenNameLunchDao();
        try {
            List<StudenNameLunch> studenNameLunches=new ArrayList<>();
            studenNameLunches=studenNameLunchDao.loadAll();
            return studenNameLunches;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            studenNameLunchDao.getDatabase().close();
        }
    }


    public List<StudenNameDinner> getStudenNameDinners()
    {
        DaoSession daoSession = getDBSessoin(context);
        StudenNameDinnerDao studenNameDinnerDao=daoSession.getStudenNameDinnerDao();
        try {
            List<StudenNameDinner> studenNameDinners=new ArrayList<>();
            studenNameDinners=studenNameDinnerDao.loadAll();
            return studenNameDinners;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            studenNameDinnerDao.getDatabase().close();
        }
    }

}


