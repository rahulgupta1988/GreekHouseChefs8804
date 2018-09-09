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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dao_db.AllPastBudgetInfo;
import dao_db.AllPastBudgetInfoDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.MenuCategoryDao;
import dao_db.MenuItemDishes;
import dao_db.MenuItemDishesDao;
import dao_db.SubmitBudget;
import dao_db.SubmitBudgetDao;
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

public class BudgetManager extends BaseManager {

    String responseString="";
    String responseStr="";
    Context context;
   // public static List<MenuDevelopmentModel> menuDevelopmentModels = new ArrayList<>();

    public BudgetManager(Context context){
        this.context=context;

    }

    public void initgetSubmitBudgetAPI(String Week_number,String Week_year,final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "getSubmitBudget serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_SUBMIT_BUDGET_API+"&Week_number="+Week_number+"&Week_year="+Week_year
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.GET_SUBMIT_BUDGET_API)
                .add("Week_number",Week_number)
                .add("Week_year",Week_year)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("getSubmitBudget responc",""+e.toString());
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
                final String parse_str=parseGetBudgetData(responseStr);
                Log.i("getSubmitBudget respo","sucess "+responseStr);
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


    public String parseGetBudgetData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getSubmitBudgetDao().deleteAll();;
        SubmitBudgetDao submitBudgetDao= daoSession.getSubmitBudgetDao();
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
                            JSONArray jsarray1 = jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "jsarray1.length()=" + jsarray1.length());
                            for (int i = 0; i < jsarray1.length(); i++)
                            {
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);
                                String Week_interval = jsonObject1.optString("Week_interval");
                                String Giftcardamountspend_1 = jsonObject1.optString("Giftcardamountspend_1");
                                String credititems_1 = jsonObject1.optString("credititems_1");
                                String Creditamount_1 = jsonObject1.optString("Creditamount_1");
                                String Giftcardamountspend_2 = jsonObject1.optString("Giftcardamountspend_2");
                                String credititems_2 = jsonObject1.optString("credititems_2");
                                String Creditamount_2 = jsonObject1.optString("Creditamount_2");
                                String Giftcardamountspend_3 = jsonObject1.optString("Giftcardamountspend_3");
                                String credititems_3 = jsonObject1.optString("credititems_3");
                                String Creditamount_3 = jsonObject1.optString("Creditamount_3");
                                String Giftcardamountspend_4 = jsonObject1.optString("Giftcardamountspend_4");
                                String credititems_4 = jsonObject1.optString("credititems_4");
                                String Creditamount_4 = jsonObject1.optString("Creditamount_4");
                                String Giftcardamountspend_5 = jsonObject1.optString("Giftcardamountspend_5");
                                String credititems_5 = jsonObject1.optString("credititems_5");
                                String Creditamount_5 = jsonObject1.optString("Creditamount_5");
                                String Giftcardamountspend_6 = jsonObject1.optString("Giftcardamountspend_6");
                                String credititems_6 = jsonObject1.optString("credititems_6");
                                String Creditamount_6 = jsonObject1.optString("Creditamount_6");
                                String Giftcardamountspend_7 = jsonObject1.optString("Giftcardamountspend_7");
                                String credititems_7 = jsonObject1.optString("credititems_7");
                                String Creditamount_7 = jsonObject1.optString("Creditamount_7");
                                String Giftcardamountspend_8 = jsonObject1.optString("Giftcardamountspend_8");
                                String credititems_8 = jsonObject1.optString("credititems_8");
                                String Creditamount_8 = jsonObject1.optString("Creditamount_8");
                                String submit_budget_id = jsonObject1.optString("submit_budget_id");
                                String Chef_id = jsonObject1.optString("Chef_id");
                                String Week_number = jsonObject1.optString("Week_number");
                                String Year = jsonObject1.optString("Year");
                                String Account_name = jsonObject1.optString("Account_name");
                                String Weekly_budget = jsonObject1.optString("Weekly_budget");
                                String Grocery_store = jsonObject1.optString("Grocery_store");
                                String Costco = jsonObject1.optString("Costco");
                                String Sysco = jsonObject1.optString("Sysco");
                                String Other = jsonObject1.optString("Other");
                                String Total = jsonObject1.optString("Total");
                                String Over_under = jsonObject1.optString("Over_under");
                                String Starting_balance = jsonObject1.optString("Starting_balance");
                                String Ending_balance = jsonObject1.optString("Ending_balance");
                                String Special_event = jsonObject1.optString("Special_event");
                                String Total_invoice = jsonObject1.optString("Total_invoice");
                                String Is_draft = jsonObject1.optString("Is_draft");

                                SubmitBudget submitBudget= new SubmitBudget(Week_interval,Giftcardamountspend_1,credititems_1,Creditamount_1,Giftcardamountspend_2,
                                        credititems_2,Creditamount_2,Giftcardamountspend_3,credititems_3,Creditamount_3,Giftcardamountspend_4,
                                        credititems_4,Creditamount_4,Giftcardamountspend_5,credititems_5,Creditamount_5,Giftcardamountspend_6,
                                        credititems_6,Creditamount_6,Giftcardamountspend_7,credititems_7,Creditamount_7,Giftcardamountspend_8,credititems_8,
                                        Creditamount_8,submit_budget_id,Chef_id,Week_number,Year,Account_name,Weekly_budget,Grocery_store,Costco,Sysco,
                                        Other,Total,Over_under,Starting_balance,Ending_balance,Special_event,Total_invoice,Is_draft);
                                submitBudgetDao.insertOrReplace(submitBudget);
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
            submitBudgetDao.getDatabase().close();
        }
        return responseString;
    }



    String  Account_name="",Weekly_budget="",Grocery_store="",Costco="",Sysco="",Total="",Other="",
            Over_under="",Starting_balance="",Ending_balance="",
            Giftcardamountspend_1="",Giftcardamountspend_2="",Giftcardamountspend_3="",Giftcardamountspend_4="",
            Giftcardamountspend_5="",Giftcardamountspend_6="",Giftcardamountspend_7="",Giftcardamountspend_8="",
            Credititems_1="",Credititems_2="",Credititems_3="",Credititems_4="",Credititems_5="",

            Credititems_6="",Credititems_7="",Credititems_8="",Creditamount_1="",Creditamount_2="",
            Creditamount_3="",Creditamount_4="",Creditamount_5="",Creditamount_6="",Creditamount_7="",
            Creditamount_8="",
                    Special_event="",Total_invoice="",Is_draft="",Submit_budget_id="";




    public void initaddBudgetAPI(String Week_number,String Week_year,String Account_name,String Weekly_budget,
                                 String Grocery_store,String Costco,
           String Sysco,String Total, String Other,String Over_under, String Starting_balance, String Ending_balance,
           String Giftcardamountspend_1,String Giftcardamountspend_2, String Giftcardamountspend_3,String Giftcardamountspend_4,
           String Giftcardamountspend_5, String Giftcardamountspend_6, String Giftcardamountspend_7,String Giftcardamountspend_8,
           String Credititems_1,String Credititems_2,String Credititems_3, String Credititems_4,String Credititems_5,String Credititems_6,
           String Credititems_7,String Credititems_8,
                                 String Creditamount_1, String Creditamount_2,String Creditamount_3,
           String Creditamount_4, String Creditamount_5,String Creditamount_6, String Creditamount_7, String Creditamount_8,
           String Special_event,String Total_invoice, String Is_draft,String Submit_budget_id, final CallBackManager callBackManager)
    {

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "AddEditsubmitBudget serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ADD_SUBMIT_BUDGET_API + "&Week_number=" + Week_number
                + "&Week_year=" + Week_year+ "&Account_name=" + Account_name
                + "&Account_name=" + Account_name+ "&Weekly_budget=" + Weekly_budget
                + "&Grocery_store=" + Grocery_store+ "&Costco=" + Costco+ "&Sysco=" + Sysco
                + "&Total=" + Total+ "&Total=" + Total+ "&Over_under=" + Over_under
                + "&Starting_balance=" + Starting_balance+ "&Ending_balance=" + Ending_balance
                + "&Giftcardamountspend_1=" + Giftcardamountspend_1+ "&Giftcardamountspend_2=" + Giftcardamountspend_2
                + "&Giftcardamountspend_3=" + Giftcardamountspend_3+ "&Giftcardamountspend_4=" + Giftcardamountspend_4
                + "&Giftcardamountspend_5=" + Giftcardamountspend_5+ "&Giftcardamountspend_6=" + Giftcardamountspend_6
                + "&Giftcardamountspend_7=" + Giftcardamountspend_7+ "&Giftcardamountspend_8=" + Giftcardamountspend_8
                + "&Credititems_1=" + Credititems_1 + "&Credititems_2=" + Credititems_2 + "&Credititems_3=" + Credititems_3
                + "&Credititems_4=" + Credititems_4+ "&Credititems_5=" + Credititems_5+ "&Credititems_6=" + Credititems_6
                + "&Credititems_7=" + Credititems_7+ "&Credititems_8=" + Credititems_8
                + "&Creditamount_1=" + Creditamount_1+ "&Creditamount_2=" + Creditamount_2+ "&Creditamount_3=" + Creditamount_3
                + "&Creditamount_4=" + Creditamount_4+ "&Creditamount_5=" + Creditamount_5+ "&Creditamount_6=" + Creditamount_6
                + "&Creditamount_7=" + Creditamount_7+ "&Creditamount_8=" + Creditamount_8

                + "&Special_event=" + Special_event+ "&Total_invoice=" + Total_invoice+ "&Is_draft=" + Is_draft
                + "&Submit_budget_id=" + Submit_budget_id
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADD_SUBMIT_BUDGET_API)
                .add("Week_number", Week_number)
                .add("Week_year", Week_year)
                .add("Account_name", Account_name)
                .add("Weekly_budget", Weekly_budget)
                .add("Grocery_store", Grocery_store)
                .add("Costco",Costco)
                .add("Sysco", Sysco)
                .add("Total", Total)
                .add("Other", Total)
                .add("Over_under", Over_under)
                .add("Starting_balance", Starting_balance)
                .add("Ending_balance",Ending_balance)
                .add("Giftcardamountspend_1", Giftcardamountspend_1)
                .add("Giftcardamountspend_2", Giftcardamountspend_2)
                .add("Giftcardamountspend_3", Giftcardamountspend_3)
                .add("Giftcardamountspend_4",Giftcardamountspend_4)
                .add("Giftcardamountspend_5", Giftcardamountspend_5)
                .add("Giftcardamountspend_6", Giftcardamountspend_6)
                .add("Giftcardamountspend_7", Giftcardamountspend_7)
                .add("Giftcardamountspend_8", Giftcardamountspend_8)
                .add("Credititems_1", Credititems_1)
                .add("Credititems_2",Credititems_2)
                .add("Credititems_3", Credititems_3)
                .add("Credititems_4",Credititems_4)
                .add("Credititems_5", Credititems_5)
                .add("Credititems_6",Credititems_6)
                .add("Credititems_7", Credititems_7)
                .add("Credititems_8",Credititems_8)
                .add("Creditamount_1", Creditamount_1)
                .add("Creditamount_2",Creditamount_2)
                .add("Creditamount_3", Creditamount_3)
                .add("Creditamount_4",Creditamount_4)
                .add("Creditamount_5", Creditamount_5)
                .add("Creditamount_6",Creditamount_6)
                .add("Creditamount_7", Creditamount_7)
                .add("Creditamount_8",Creditamount_8)
                .add("Special_event", Special_event)
                .add("Total_invoice",Total_invoice)
                .add("Is_draft", Is_draft)
                .add("Submit_budget_id",Submit_budget_id)

                .build();




        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("AddsubmitBudget respo",""+e.toString());
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
                final String parse_str=parseaddBudgetData(responseStr);
                Log.i("AddsubmitBudget resp","sucess "+responseStr);
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


    public String parseaddBudgetData(String jsonResponse) {
        String responseString="";
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet())
            {
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




    public void initGetBudgetPastInfoAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetBudgetPastInfo serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.PAST_BUDGET_API
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.PAST_BUDGET_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("GetBudgetPastInfo respo",""+e.toString());
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
                final String parse_str=parseAllPastBudgetInfo(responseStr);
                Log.i("GetBudgetPastInfo respo","sucess "+responseStr);
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



    public String parseAllPastBudgetInfo(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllPastBudgetInfoDao().deleteAll();
        AllPastBudgetInfoDao allPastBudgetInfoDao= daoSession.getAllPastBudgetInfoDao();
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

                            String chef_name=jsonObject1.getString("chef_name");
                            JSONArray jsonArray= jsonObject1.getJSONArray("week_interval");
                            Log.i("item_dishes_list", "jsarray1.length()=" + jsonArray.length());
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject2= jsonArray.getJSONObject(i);
                                String interval=jsonObject2.getString("interval");
                                AllPastBudgetInfo allPastBudgetInfo= new AllPastBudgetInfo(interval,chef_name);
                                allPastBudgetInfoDao.insertOrReplace(allPastBudgetInfo);
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
            allPastBudgetInfoDao.getDatabase().close();
        }
        return responseString;
    }

    public List<AllPastBudgetInfo> getAllPastBudgetInfo()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllPastBudgetInfoDao allPastBudgetInfoDao=daoSession.getAllPastBudgetInfoDao();
        try {
            List<AllPastBudgetInfo> allPastBudgetInfos=new ArrayList<>();
            allPastBudgetInfos=allPastBudgetInfoDao.loadAll();
            return allPastBudgetInfos;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allPastBudgetInfoDao.getDatabase().close();
        }
    }


    public List<MenuCategory> getmMenuCategories()
    {
        DaoSession daoSession = getDBSessoin(context);
        MenuCategoryDao menuCategoryDao=daoSession.getMenuCategoryDao();
        try {
            List<MenuCategory> menuCategories=new ArrayList<>();
            menuCategories=menuCategoryDao.loadAll();
            return menuCategories;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            menuCategoryDao.getDatabase().close();
        }
    }

/*    public List<MenuItems> getItemsList()
    {
        DaoSession daoSession = getDBSessoin(context);
        MenuItemsDao menuItemsDao=daoSession.getMenuItemsDao();
        try {
            List<MenuItems> menuItemses=new ArrayList<>();
            menuItemses=menuItemsDao.loadAll();
            return menuItemses;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            menuItemsDao.getDatabase().close();
        }
    }*/

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

    public List<SubmitBudget> getSubmitBudgetData()
    {
        DaoSession daoSession = getDBSessoin(context);
        SubmitBudgetDao submitBudgetDao= daoSession.getSubmitBudgetDao();
        try {
            List<SubmitBudget> submitBudgets=new ArrayList<>();
            submitBudgets=submitBudgetDao.loadAll();
            return submitBudgets;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            submitBudgetDao.getDatabase().close();
        }
    }

}


