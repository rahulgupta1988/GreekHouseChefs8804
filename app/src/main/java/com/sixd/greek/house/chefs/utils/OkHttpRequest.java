package com.sixd.greek.house.chefs.utils;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Praveen on 10-Jul-17.
 */

public class OkHttpRequest {

    public static Request getRequest(RequestBody requestBody,String sessionToken){
        Request request=new Request.Builder()
                .header("Token",sessionToken)
                .header("Accept",Constant.HeaderParams.ACCEPT_TYPE)
                .header("Content-Type",Constant.HeaderParams.CONTENT_TYPE)
                .header("Cache-Control", "no-cache")
                .url(Constant.BASEURL)
                .post(requestBody)
                .build();

        return request;

    }
}
