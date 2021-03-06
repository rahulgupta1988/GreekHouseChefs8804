package dao_db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "STUDEN_NAME_LUNCH".
 */
@Entity
public class StudenNameLunch {
    private String dayname;
    private String studentname;

    @Generated
    public StudenNameLunch() {
    }

    @Generated
    public StudenNameLunch(String dayname, String studentname) {
        this.dayname = dayname;
        this.studentname = studentname;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

}
