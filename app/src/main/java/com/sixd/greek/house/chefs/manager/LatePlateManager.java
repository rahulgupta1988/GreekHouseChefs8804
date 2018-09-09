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
 * Created by Praveen on 27-Jul-17.
 */

public class LatePlateManager {
    String responseStr="";
    Context context;

    public LatePlateManager(Context context){
        this.context=context;
    }

    public void initaddLatePlateAPI(String week_type,String student_type ,String request_type ,String request_date,final CallBackManager callBackManager){
        String sessionToken=new LoginManager(context).getSessionToken();

        Log.i("responstring", "addLatePlateRequest serviceUrl-->" + Constant.BASEURL + "&param=" +
                Constant.API.ADDMSG_LATE_PLATE_API+ "&week_type="+week_type
                + "&student_type="+student_type+"&request_type="+request_type+"&request_date="+request_date
                +"&token="+sessionToken);


       /* RequestBody formBody = new FormBody.Builder()
                .add("param","addLatePlateRequest")
                .add("student_type","in_house")
                .add("request_type","is_onetime")
                .add("request_date","{\"lunch\":[\"2017-08-12\",\"2017-08-13\"],\"dinner\":[\"2017-08-12\"]}")
                .build();*/

        RequestBody formBody = new FormBody.Builder()
                .add("param",Constant.API.ADDMSG_LATE_PLATE_API)
                .add("week_type",week_type)
                .add("student_type",student_type)
                .add("request_type",request_type)
                .add("request_date",request_date)
                .build();
        OkHttpClient okHttpClient= HttpClientSingle.getClient();
        Request request= OkHttpRequest.getRequest(formBody,sessionToken);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("FAILaddLatePla responce",""+e.toString());
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
                Log.i("addLatePlaFINA responce","sucess"+responseStr);

                final String parse_str=parseData(responseStr);
                Log.i("addLatePla responce","sucess"+responseStr);
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





}
