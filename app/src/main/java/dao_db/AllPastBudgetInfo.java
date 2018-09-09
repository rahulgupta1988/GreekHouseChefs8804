package dao_db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "ALL_PAST_BUDGET_INFO".
 */
@Entity
public class AllPastBudgetInfo {
    private String interval;
    private String chef_name;

    @Generated
    public AllPastBudgetInfo() {
    }

    @Generated
    public AllPastBudgetInfo(String interval, String chef_name) {
        this.interval = interval;
        this.chef_name = chef_name;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getChef_name() {
        return chef_name;
    }

    public void setChef_name(String chef_name) {
        this.chef_name = chef_name;
    }

}