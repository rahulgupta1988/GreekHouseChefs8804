package com.sixd.greek.house.chefs.model;

/**
 * Created by Praveen on 11-Aug-17.
 */

public class MenuModel {

    String date;
    String MenuTitle;
    String menucalender_cs;

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public String getDaynameStart() {
        return daynameStart;
    }

    public void setDaynameStart(String daynameStart) {
        this.daynameStart = daynameStart;
    }

    String dayname;
    String daynameStart;

    public String getMenucalender_cs() {
        return menucalender_cs;
    }

    public void setMenucalender_cs(String menucalender_cs) {
        this.menucalender_cs = menucalender_cs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMenuTitle() {
        return MenuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        MenuTitle = menuTitle;
    }
}
