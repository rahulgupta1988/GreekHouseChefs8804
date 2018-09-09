package com.sixd.greek.house.chefs.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Praveen on 10-Jul-17.
 */

public class HttpClientSingle {

    public static OkHttpClient okHttpClient=null;

    public static OkHttpClient getClient(){

        if(okHttpClient!=null){
            return okHttpClient;
        }

        else{
            okHttpClient=new OkHttpClient();
            return okHttpClient;
        }
    }
}
