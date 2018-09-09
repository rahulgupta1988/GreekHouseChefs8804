package com.sixd.greek.house.chefs.manager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import dao_db.DaoMaster;
import dao_db.DaoSession;

public class BaseManager {

    public static final String LOG_TAG = "BaseManager";

    public static DaoSession getDBSessoin(Context context) {
        long startTime = System.currentTimeMillis();
        if (context != null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "chefhouse6d.sqlite", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            if (daoSession != null) {
                return daoSession;
            } else {
                Log.i(LOG_TAG, "getDBSessoin:  - Application context is null");
                return null;
            }
        }
        Log.i(LOG_TAG, "getDBSessoin: Execution time - " + (System.currentTimeMillis() - startTime));
        return null;
    }

    public void closeDatabase(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "chefhouse6d.sqlite", null);
        helper.close();
    }

}




/**
 * Created by Praveen on 11-Jul-17.
 */

/*
public class BaseManager {
    public static final String LOG_TAG = "BaseManager";
    public static DaoMaster.DevOpenHelper helper=null;

    public static DaoSession getDBSessoin(Context context) {
        try {
            if(context!=null){
                DaoMaster.DevOpenHelper helper=getHelper(context);
                //Database sqLiteDatabase=helper.getEncryptedWritableDb("chefsixd");
                DaoMaster daoMaster=new DaoMaster(sqLiteDatabase);
                DaoSession daoSession=daoMaster.newSession();
                if (daoSession != null) {
                    return daoSession;
                } else {
                    Log.i(LOG_TAG, "getDBSessoin:  - daoSession is null");
                    return null;
                }
            }
            else {
                Log.i(LOG_TAG, "getDBSessoin:  - Application context is null");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeDatabase(Context context) {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,"chefhouse6d.sqlite",null);
        helper.close();
    }

    public static DaoMaster.DevOpenHelper getHelper(Context context){

        if(helper==null){
            Log.i("Created Helper","created");
            helper=new DaoMaster.DevOpenHelper(context,"chefhouse6d.sqlite",null);
            return helper;
        }

        else{
            Log.i("not Created Helper","return old");
            return helper;
        }
    }
}
*/
