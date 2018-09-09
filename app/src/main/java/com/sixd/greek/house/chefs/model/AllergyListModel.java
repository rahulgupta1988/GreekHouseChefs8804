package com.sixd.greek.house.chefs.model;

/**
 * Created by Praveen on 09-Aug-17.
 */

public class AllergyListModel {

    String allergy_id;

    public String getAllergy_id() {
        return allergy_id;
    }

    public void setAllergy_id(String allergy_id) {
        this.allergy_id = allergy_id;
    }

    public String getAllergy_name() {
        return allergy_name;
    }

    public void setAllergy_name(String allergy_name) {
        this.allergy_name = allergy_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_check() {
        return user_check;
    }

    public void setUser_check(String user_check) {
        this.user_check = user_check;
    }

    String allergy_name;
    String status;
    String user_id;
    String user_check;
}
