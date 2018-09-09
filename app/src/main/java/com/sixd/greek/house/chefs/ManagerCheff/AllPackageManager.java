
package com.sixd.greek.house.chefs.ManagerCheff;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sixd.greek.house.chefs.manager.BaseManager;
import com.sixd.greek.house.chefs.manager.SignUpManager;

/**
 * Created by ramji on 15/11/16.
 */
public class AllPackageManager extends BaseManager {

    Context context;
    String responseString = "";

    public AllPackageManager(Context context) {
        this.context = context;
    }





   /* public String parsegetLeaveReasonList(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession = getDBSessoin(context);
        daoSession.getLeaveReasonListDao().deleteAll();
        LeaveReasonListDao leaveReasonListDao = daoSession.getLeaveReasonListDao();
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
                            for (int i = 0; i < jsarray1.length(); i++) {
                                Log.i("inside loop", "" + i);
                                JSONObject jsonObject1 = jsarray1.getJSONObject(i);
                                String leave_reason_id = jsonObject1.optString("leave_reason_id");
                                String leave_reason_category_id = jsonObject1.optString("leave_reason_category_id");
                                String leave_reason_name = jsonObject1.optString("leave_reason_name");
                                String is_active = jsonObject1.optString("is_active");
                                String leave_reason_category_name = jsonObject1.optString("leave_reason_category_name");

                                LeaveReasonList leaveReasonList = new LeaveReasonList(leave_reason_id,
                                        leave_reason_category_id,leave_reason_name,is_active,leave_reason_category_name);
                                leaveReasonListDao.insertOrReplace(leaveReasonList);
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
            leaveReasonListDao.getDatabase().close();
        }
        return responseString;
    }











    public List<StoreUnpaidInvoiceList> getUnpaidInvoiceLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        StoreUnpaidInvoiceListDao storeUnpaidInvoiceListDao=daoSession.getStoreUnpaidInvoiceListDao();
        try {
            List<StoreUnpaidInvoiceList> storeUnpaidInvoiceLists=new ArrayList<>();
            storeUnpaidInvoiceLists=storeUnpaidInvoiceListDao.loadAll();
            return storeUnpaidInvoiceLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            storeUnpaidInvoiceListDao.getDatabase().close();
        }
    }*/

}
