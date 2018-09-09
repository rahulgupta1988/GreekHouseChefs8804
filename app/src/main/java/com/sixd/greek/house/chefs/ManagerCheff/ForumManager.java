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
import com.sixd.greek.house.chefs.model.MenuModel;
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

import dao_db.AllCategoryTopics;
import dao_db.AllCategoryTopicsDao;
import dao_db.AllFormCategory;
import dao_db.AllFormCategoryDao;
import dao_db.DaoSession;
import dao_db.MenuCategory;
import dao_db.MenuCategoryDao;
import dao_db.MenuItemDishes;
import dao_db.MenuItemDishesDao;
import dao_db.TopicComment;
import dao_db.TopicCommentDao;
import dao_db.TopicDetail;
import dao_db.TopicDetailDao;
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

public class ForumManager extends BaseManager {

    String responseString="";
    String responseStr="";
    Context context;
   // public static List<MenuDevelopmentModel> menuDevelopmentModels = new ArrayList<>();

    public ForumManager(Context context){
        this.context=context;

    }

    public void initgetForumCategoryAPI(final CallBackManager callBackManager)
    {
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "getForumCategory serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_FORUM_CATEGPRY_API
                +"&token="+sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.GET_FORUM_CATEGPRY_API)
                .build();

        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("getForumCategory respo",""+e.toString());
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
                final String parse_str=parseForumCategory(responseStr);
                Log.i("getForumCategory respo","sucess "+responseStr);
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


      public String parseForumCategory(String jsonResponse)
      {
          String responseCode = "";
          DaoSession daoSession = getDBSessoin(context);
          daoSession.getAllFormCategoryDao().deleteAll();
          AllFormCategoryDao allFormCategoryDao=daoSession.getAllFormCategoryDao();

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
                                String Forum_category_id = jsonObject1.optString("Forum_category_id");
                                String Category_name = jsonObject1.optString("Category_name");
                                String Created_on = jsonObject1.optString("Created_on");
                                String Total_topics=jsonObject1.optString("Total_topics");
                                String Total_comments=jsonObject1.optString("Total_comments");

                                AllFormCategory allFormCategory=new AllFormCategory(Forum_category_id,Category_name,
                                        Created_on,Total_topics,Total_comments);
                                allFormCategoryDao.insertOrReplace(allFormCategory);

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
             allFormCategoryDao.getDatabase().close();
        }
        return responseString;
    }



    public void initGetForumtopicsAPI(String Category_id,final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "GetForumtopics serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_FORUM_TOPICS_API + "&Category_id=" + Category_id
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.GET_FORUM_TOPICS_API)
                .add("Category_id", Category_id)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("GetForumtopics apirespo",""+e.toString());
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
                final String parse_str=parseForumtopicsData(responseStr);
                Log.i("GetForumtopics responce","sucess "+responseStr);
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

    public String parseForumtopicsData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getAllCategoryTopicsDao().deleteAll();
        AllCategoryTopicsDao allCategoryTopicsDao=daoSession.getAllCategoryTopicsDao();


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
                                String Forum_topics_id = jsonObject1.optString("Forum_topics_id");
                                String Forum_category_id = jsonObject1.optString("Forum_category_id");
                                String Topic_name = jsonObject1.optString("Topic_name");
                                String Created_on=jsonObject1.optString("Created_on");
                                String Created_by=jsonObject1.optString("Created_by");
                                String Total_comments=jsonObject1.optString("Total_comments");
                                String image_url=jsonObject1.optString("image_url");

                                AllCategoryTopics allCategoryTopics=new AllCategoryTopics(Forum_topics_id,Forum_category_id,
                                        Topic_name,Created_on,Created_by,Total_comments,image_url);
                                allCategoryTopicsDao.insertOrReplace(allCategoryTopics);

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
            allCategoryTopicsDao.getDatabase().close();
        }
        return responseString;
    }






    public void initForumTopicDetailsAPI(String Forum_topics_id,final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "ForumTopicDetails serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.GET_FORUM_DETAILS_API + "&Forum_topics_id=" + Forum_topics_id
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.GET_FORUM_DETAILS_API)
                .add("Forum_topics_id",Forum_topics_id)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("ForumTopicDetails_respo",""+e.toString());
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
                final String parse_str=parseForumTopicDetailsData(responseStr);
                Log.i("ForumTopicDetails_respo","sucess "+responseStr);
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

    public String parseForumTopicDetailsData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getTopicDetailDao().deleteAll();
        daoSession.getTopicCommentDao().deleteAll();
        TopicDetailDao topicDetailDao=daoSession.getTopicDetailDao();
        TopicCommentDao topicCommentDao=daoSession.getTopicCommentDao();

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

                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");

                            String Topic_name=jsonObject1.getString("Topic_name");
                            String Description=jsonObject1.getString("Description");
                            String Topic_created_by=jsonObject1.getString("Topic_created_by");
                            String Topic_image_url=jsonObject1.getString("Topic_image_url");
                            String Topic_created_on=jsonObject1.getString("Topic_created_on");
                            String Total_comments=jsonObject1.getString("Total_comments");


                            JSONArray jsarray2=jsonObject1.getJSONArray("Comments");
                            for(int i=0;i<jsarray2.length();i++)
                            {

                                JSONObject jsonObject2 = jsarray2.getJSONObject(i);

                                String Comments_desc = jsonObject2.getString("Comments_desc");
                                String Comment_created_by = jsonObject2.getString("Comment_created_by");
                                String Comment_created_on = jsonObject2.getString("Comment_created_on");
                                String comment_image_url = jsonObject2.getString("comment_image_url");

                                TopicComment allCategoryTopics=new TopicComment(Comments_desc,
                                        Comment_created_by,Comment_created_on,comment_image_url);
                                topicCommentDao.insertOrReplace(allCategoryTopics);
                            }

                            TopicDetail topicDetail=new TopicDetail(Topic_name,Description,Topic_created_by,
                                    Topic_image_url,Topic_created_on,Total_comments);
                            topicDetailDao.insertOrReplace(topicDetail);


                        }
                        else
                        {
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
            topicDetailDao.getDatabase().close();
            topicCommentDao.getDatabase().close();
        }
        return responseString;
    }




    public void initAddForumCommentsAPI(String Forum_topics_id,String Comment,final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "ForumComments serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ADD_FORUM_COMMENTS_API+ "&Forum_topics_id=" + Forum_topics_id + "&Comment=" + Comment
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADD_FORUM_COMMENTS_API)
                .add("Forum_topics_id", Forum_topics_id)
                .add("Comment", Comment)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("ForumComments apirespo",""+e.toString());
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
                final String parse_str=parseAddForumCommentsData(responseStr);
                Log.i("ForumComments responce","sucess "+responseStr);
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


    public String parseAddForumCommentsData(String jsonResponse)
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
                        if (responseCode.equalsIgnoreCase("100"))
                        {
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



   /* ==============*/

    public void initAddForumTopicAPI(String Forum_category_id,String Forum_topic_name,String Forum_description,
                                     final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "AddForumTopic serviceUrl-->" + Constant.BASEURL + "&param=" +
              Constant.API.ADD_FORUM_TOPIC_API+ "&Forum_category_id=" + Forum_category_id
                +"&Forum_topic_name="+Forum_topic_name+"&Forum_description="+Forum_description
                + "&token=" + sessionToken);

        RequestBody formBody = new FormBody.Builder()
                .add("param", Constant.API.ADD_FORUM_TOPIC_API)
                .add("Forum_category_id", Forum_category_id)
                .add("Forum_topic_name", Forum_topic_name)
                .add("Forum_description", Forum_description)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody, sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("AddForumTopic apirespo",""+e.toString());
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
                final String parse_str=parseAddForumTopicData(responseStr);
                Log.i("AddForumTopic responce","sucess "+responseStr);
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


    public String parseAddForumTopicData(String jsonResponse)
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
                        if (responseCode.equalsIgnoreCase("100"))
                        {
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




    public List<AllFormCategory> getAllFormCategories()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllFormCategoryDao allFormCategoryDao=daoSession.getAllFormCategoryDao();
        try {
            List<AllFormCategory> allFormCategories=new ArrayList<>();
            allFormCategories=allFormCategoryDao.loadAll();
            return allFormCategories;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allFormCategoryDao.getDatabase().close();
        }
    }


    public List<AllCategoryTopics> getallCategoryTopics()
    {
        DaoSession daoSession = getDBSessoin(context);
        AllCategoryTopicsDao allCategoryTopicsDao=daoSession.getAllCategoryTopicsDao();
        try {
            List<AllCategoryTopics> allCategoryTopicses=new ArrayList<>();
            allCategoryTopicses=allCategoryTopicsDao.loadAll();
            return allCategoryTopicses;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            allCategoryTopicsDao.getDatabase().close();
        }
    }



    public List<TopicDetail> getTopicDetails()
    {
        DaoSession daoSession = getDBSessoin(context);
        TopicDetailDao topicDetailDao=daoSession.getTopicDetailDao();
        try {
            List<TopicDetail> topicDetails=new ArrayList<>();
            topicDetails=topicDetailDao.loadAll();
            return topicDetails;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            topicDetailDao.getDatabase().close();
        }
    }


    public List<TopicComment> getTopicComments()
    {
        DaoSession daoSession = getDBSessoin(context);
        TopicCommentDao topicCommentDao=daoSession.getTopicCommentDao();
        try {
            List<TopicComment> topicComments=new ArrayList<>();
            topicComments=topicCommentDao.loadAll();
            return topicComments;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            topicCommentDao.getDatabase().close();
        }
    }


}


