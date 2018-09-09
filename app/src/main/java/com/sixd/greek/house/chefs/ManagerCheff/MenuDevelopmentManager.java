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
 * Created by Praveen on 09-Aug-17.
 */

public class MenuDevelopmentManager extends BaseManager {

    String responseString="";
    String responseStr="";
    Context context;
   // public static List<MenuDevelopmentModel> menuDevelopmentModels = new ArrayList<>();

    public MenuDevelopmentManager(Context context){
        this.context=context;

    }

    public void initMenuDevelopmentAPI(final CallBackManager callBackManager){

        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "getCategories serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.MENU_DEVELOPMENT_CATEGORY_API
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.MENU_DEVELOPMENT_CATEGORY_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("getCategories responce",""+e.toString());
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
                final String parse_str=parseDataCategory(responseStr);
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


      public String parseDataCategory(String jsonResponse) {
        String responseCode = "";
          DaoSession daoSession = getDBSessoin(context);
          daoSession.getMenuCategoryDao().deleteAll();
          MenuCategoryDao menuCategoryDao=daoSession.getMenuCategoryDao();

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
                                String menu_category_id = jsonObject1.optString("menu_category_id");
                                String image_url = jsonObject1.optString("image_url");
                                String category_name = jsonObject1.optString("category_name");
                                String parent_id=jsonObject1.optString("parent_id");
                                String total_menu_items=jsonObject1.optString("total_menu_items");


                                MenuCategory menuCategory=new MenuCategory(menu_category_id,image_url,
                                        category_name,parent_id,total_menu_items);
                                menuCategoryDao.insertOrReplace(menuCategory);

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
            menuCategoryDao.getDatabase().close();
        }
        return responseString;
    }



    public void initMenuItemsAPI(String category_id,final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetmenuItems serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.MENU_DEVELOPMENT_ITEMS_API + "&category_id=" + category_id
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.MENU_DEVELOPMENT_ITEMS_API)
                .add("category_id",category_id)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("GetmenuItems api respon",""+e.toString());
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
                final String parse_str=parseDataMenuItems(responseStr);
                Log.i("GetmenuItems responce","sucess "+responseStr);
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


    public String parseDataMenuItems(String jsonResponse) {
        int id;
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getMenuItemDishesDao().deleteAll();
        MenuItemDishesDao menuItemDishesDao = daoSession.getMenuItemDishesDao();
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

                            JSONArray jsonArray= jsonObject1.getJSONArray("item_dishes_list");
                            Log.i("item_dishes_list", "jsarray1.length()=" + jsonArray.length());
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject2= jsonArray.getJSONObject(i);
                                String menu_id=jsonObject2.getString("menu_id");
                                String menu_title=jsonObject2.getString("menu_title");
                                String menu_description=jsonObject2.optString("description");

                                MenuItemDishes menuItemDishes=new MenuItemDishes(menu_id,menu_title,menu_description);
                                menuItemDishesDao.insertOrReplace(menuItemDishes);
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
            menuItemDishesDao.getDatabase().close();
        }
        return responseString;
    }


    /*public String parseDataMenuItems(String jsonResponse) {
        int id;
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getMenuItemsDao().deleteAll();
        daoSession.getMenuItemDishesDao().deleteAll();
        MenuItemsDao menuItemsDao = daoSession.getMenuItemsDao();
        MenuItemDishesDao menuItemDishesDao = daoSession.getMenuItemDishesDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null)) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsarray1 = jsonObject.getJSONArray("responseData");
                            Log.i("responseString", "jsarray1.length()=" + jsarray1.length());
                            for (int i = 0; i < jsarray1.length(); i++)
                            {
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);
                                String sub_category_id = jsonObject1.optString("sub_category_id");
                                String sub_category_item_name = jsonObject1.optString("sub_category_item_name");
                                String item_dishes_count = jsonObject1.optString("item_dishes_count");

                                JSONArray jsa1 = jsonObject1.getJSONArray("item_dishes_list");
                                for (int j = 0; j < jsa1.length(); j++)
                                {
                                    JSONObject jsonObject2 = jsa1.getJSONObject(j);
                                    String menu_id = jsonObject2.optString("menu_id");
                                    String menu_title = jsonObject2.optString("menu_title");

                                    MenuItemDishes menuItemDishes=new MenuItemDishes(sub_category_id,menu_id,menu_title);
                                    menuItemDishesDao.insertOrReplace(menuItemDishes);
                                }

                                MenuItems menuItems=new MenuItems(sub_category_id,sub_category_item_name,item_dishes_count);
                                menuItemsDao.insertOrReplace(menuItems);
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
            menuItemsDao.getDatabase().close();
            menuItemDishesDao.getDatabase().close();
        }
        return responseString;
    }
*/

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

}


