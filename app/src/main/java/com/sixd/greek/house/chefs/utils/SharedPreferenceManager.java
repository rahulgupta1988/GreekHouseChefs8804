package com.sixd.greek.house.chefs.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class SharedPreferenceManager {

    private static final String SECURITY_SP = "ghc6d-app-sp";

    public static void putUserName(final Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SharePKey.USERNAME,userName);
        editor.commit();
    }


    public static String getUserName(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String username=sp.getString(Constant.SharePKey.USERNAME, null);
        return username;
    }

    public static void putPassword(final Context context, String pass) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SharePKey.PASSWORD,pass);
        editor.commit();
    }


    public static String getPassword(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        String username=sp.getString(Constant.SharePKey.PASSWORD, null);
        return username;
    }


    public static void putRemCheck(final Context context, boolean rem) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constant.SharePKey.REMEMBER,rem);
        editor.commit();
    }


    public static boolean getRemCheck(final Context context) {
        SharedPreferences sp = context.getSharedPreferences(SECURITY_SP, Context.MODE_PRIVATE);
        boolean rem_check=sp.getBoolean(Constant.SharePKey.REMEMBER, false);
        return rem_check;
    }

}
