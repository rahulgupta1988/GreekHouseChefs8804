package dao_db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "ALL_LATE_PLATE_CHEF_LIST".
 */
@Entity
public class AllLatePlateChefList {
    private String dayname;
    private String date;
    private String total_student_lunch;
    private String total_student_dinner;
    private String lunch_allergy;
    private String dinner_allergy;
    private String week_interval;

    @Generated
    public AllLatePlateChefList() {
    }

    @Generated
    public AllLatePlateChefList(String dayname, String date, String total_student_lunch, String total_student_dinner, String lunch_allergy, String dinner_allergy, String week_interval) {
        this.dayname = dayname;
        this.date = date;
        this.total_student_lunch = total_student_lunch;
        this.total_student_dinner = total_student_dinner;
        this.lunch_allergy = lunch_allergy;
        this.dinner_allergy = dinner_allergy;
        this.week_interval = week_interval;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_student_lunch() {
        return total_student_lunch;
    }

    public void setTotal_student_lunch(String total_student_lunch) {
        this.total_student_lunch = total_student_lunch;
    }

    public String getTotal_student_dinner() {
        return total_student_dinner;
    }

    public void setTotal_student_dinner(String total_student_dinner) {
        this.total_student_dinner = total_student_dinner;
    }

    public String getLunch_allergy() {
        return lunch_allergy;
    }

    public void setLunch_allergy(String lunch_allergy) {
        this.lunch_allergy = lunch_allergy;
    }

    public String getDinner_allergy() {
        return dinner_allergy;
    }

    public void setDinner_allergy(String dinner_allergy) {
        this.dinner_allergy = dinner_allergy;
    }

    public String getWeek_interval() {
        return week_interval;
    }

    public void setWeek_interval(String week_interval) {
        this.week_interval = week_interval;
    }

}
