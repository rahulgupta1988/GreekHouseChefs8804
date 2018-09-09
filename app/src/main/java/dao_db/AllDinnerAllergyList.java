package dao_db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "ALL_DINNER_ALLERGY_LIST".
 */
@Entity
public class AllDinnerAllergyList {
    private String allergy_name;
    private String student_name;

    @Generated
    public AllDinnerAllergyList() {
    }

    @Generated
    public AllDinnerAllergyList(String allergy_name, String student_name) {
        this.allergy_name = allergy_name;
        this.student_name = student_name;
    }

    public String getAllergy_name() {
        return allergy_name;
    }

    public void setAllergy_name(String allergy_name) {
        this.allergy_name = allergy_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

}
