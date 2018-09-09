package com.chef.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by Praveen on 11-Jul-17.
 */

public class ChefGenerator {

    public static void main(String[] args) {

        Schema schema=new Schema(41,"dao_db");
        createServiceAppSchema(schema);
        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createServiceAppSchema(Schema schema) {
        Entity userlogininfo=schema.addEntity("UserLoginInfo");
        userlogininfo.addStringProperty("student_id");
        userlogininfo.addStringProperty("chef_id");
        userlogininfo.addStringProperty("user_id").primaryKey();
        userlogininfo.addStringProperty("user_name");
        userlogininfo.addStringProperty("first_name");
        userlogininfo.addStringProperty("last_name");
        userlogininfo.addStringProperty("email");
        userlogininfo.addStringProperty("phone");
        userlogininfo.addStringProperty("address");
        userlogininfo.addStringProperty("city");
        userlogininfo.addStringProperty("state");
        userlogininfo.addStringProperty("zip");
        userlogininfo.addStringProperty("image_url");
        userlogininfo.addStringProperty("status");
        userlogininfo.addStringProperty("session_token");
        userlogininfo.addStringProperty("house_id");
        userlogininfo.addStringProperty("house_name");
        userlogininfo.addStringProperty("campus_id");
        userlogininfo.addStringProperty("campus_name");
        userlogininfo.addStringProperty("code");
        userlogininfo.addStringProperty("chef_name");
        userlogininfo.addStringProperty("chef_image_url");
        userlogininfo.addStringProperty("is_lateplate");
        userlogininfo.addStringProperty("is_lateplate_outofhouse");


        Entity userType = schema.addEntity("UserType");
        userType.addStringProperty("userType").primaryKey();

        Entity menuCategory = schema.addEntity("MenuCategory");
        menuCategory.addStringProperty("menu_category_id");
        menuCategory.addStringProperty("image_url");
        menuCategory.addStringProperty("category_name");
        menuCategory.addStringProperty("parent_id");
        menuCategory.addStringProperty("total_menu_items");


        Entity menuItemDishes = schema.addEntity("MenuItemDishes");
        menuItemDishes.addStringProperty("menu_id");
        menuItemDishes.addStringProperty("menu_title");
        menuItemDishes.addStringProperty("description");

        Entity allEvents = schema.addEntity("AllEvents");
        allEvents.addStringProperty("event_id");
        allEvents.addStringProperty("is_student_calender");
        allEvents.addStringProperty("event_title");
        allEvents.addStringProperty("event_start");
        allEvents.addStringProperty("event_end");
        allEvents.addStringProperty("start_time");
        allEvents.addStringProperty("end_time");
        allEvents.addStringProperty("description");

        Entity allWeekIntervalList = schema.addEntity("AllWeekIntervalList");
        allWeekIntervalList.addStringProperty("week");
        allWeekIntervalList.addStringProperty("interval");
        allWeekIntervalList.addStringProperty("year");
        allWeekIntervalList.addStringProperty("current_week");

        Entity allLatePlateChefList = schema.addEntity("AllLatePlateChefList");
        allLatePlateChefList.addStringProperty("dayname");
        allLatePlateChefList.addStringProperty("date");
        allLatePlateChefList.addStringProperty("total_student_lunch");
        allLatePlateChefList.addStringProperty("total_student_dinner");
        allLatePlateChefList.addStringProperty("lunch_allergy");
        allLatePlateChefList.addStringProperty("dinner_allergy");
        allLatePlateChefList.addStringProperty("week_interval");

        Entity studenNameLunch = schema.addEntity("StudenNameLunch");
        studenNameLunch.addStringProperty("dayname");
        studenNameLunch.addStringProperty("studentname");

        Entity studenNameDinner = schema.addEntity("StudenNameDinner");
        studenNameDinner.addStringProperty("dayname");
        studenNameDinner.addStringProperty("studentname");


        Entity allLunchAllergyList = schema.addEntity("AllLunchAllergyList");
        allLunchAllergyList.addStringProperty("allergy_name");
        allLunchAllergyList.addStringProperty("student_name");

        Entity allDinnerAllergyList = schema.addEntity("AllDinnerAllergyList");
        allDinnerAllergyList.addStringProperty("allergy_name");
        allDinnerAllergyList.addStringProperty("student_name");

       /* Entity getMasterAllMenuItems = schema.addEntity("GetMasterAllMenuItems");
        getMasterAllMenuItems.addStringProperty("menu_id").primaryKey();
        getMasterAllMenuItems.addStringProperty("menu_title");*/

        Entity calenderData = schema.addEntity("CalenderData");
        calenderData.addStringProperty("week_number");
        calenderData.addStringProperty("week_year");

        Entity currentWeekLunch = schema.addEntity("CurrentWeekLunch");
        currentWeekLunch.addStringProperty("request_date");
        currentWeekLunch.addStringProperty("is_checked");
        currentWeekLunch.addStringProperty("Dayname");


        Entity currentWeekDinner = schema.addEntity("CurrentWeekDinner");
        currentWeekDinner.addStringProperty("request_date");
        currentWeekDinner.addStringProperty("is_checked");
        currentWeekDinner.addStringProperty("Dayname");


        Entity nextWeekLunch = schema.addEntity("NextWeekLunch");
        nextWeekLunch.addStringProperty("request_date");
        nextWeekLunch.addStringProperty("is_checked");
        nextWeekLunch.addStringProperty("Dayname");


        Entity nextWeekDinner = schema.addEntity("NextWeekDinner");
        nextWeekDinner.addStringProperty("request_date");
        nextWeekDinner.addStringProperty("is_checked");
        nextWeekDinner.addStringProperty("Dayname");


        Entity submitBudget = schema.addEntity("SubmitBudget");
        submitBudget.addStringProperty("Week_interval");
        submitBudget.addStringProperty("Giftcardamountspend_1");
        submitBudget.addStringProperty("credititems_1");
        submitBudget.addStringProperty("Creditamount_1");
        submitBudget.addStringProperty("Giftcardamountspend_2");
        submitBudget.addStringProperty("credititems_2");
        submitBudget.addStringProperty("Creditamount_2");
        submitBudget.addStringProperty("Giftcardamountspend_3");
        submitBudget.addStringProperty("credititems_3");
        submitBudget.addStringProperty("Creditamount_3");
        submitBudget.addStringProperty("Giftcardamountspend_4");
        submitBudget.addStringProperty("credititems_4");
        submitBudget.addStringProperty("Creditamount_4");
        submitBudget.addStringProperty("Giftcardamountspend_5");
        submitBudget.addStringProperty("credititems_5");
        submitBudget.addStringProperty("Creditamount_5");
        submitBudget.addStringProperty("Giftcardamountspend_6");
        submitBudget.addStringProperty("credititems_6");
        submitBudget.addStringProperty("Creditamount_6");
        submitBudget.addStringProperty("Giftcardamountspend_7");
        submitBudget.addStringProperty("credititems_7");
        submitBudget.addStringProperty("Creditamount_7");
        submitBudget.addStringProperty("Giftcardamountspend_8");
        submitBudget.addStringProperty("credititems_8");
        submitBudget.addStringProperty("Creditamount_8");
        submitBudget.addStringProperty("submit_budget_id");
        submitBudget.addStringProperty("Chef_id");
        submitBudget.addStringProperty("Week_number");
        submitBudget.addStringProperty("Year");
        submitBudget.addStringProperty("Account_name");
        submitBudget.addStringProperty("Weekly_budget");
        submitBudget.addStringProperty("Grocery_store");
        submitBudget.addStringProperty("Costco");
        submitBudget.addStringProperty("Sysco");
        submitBudget.addStringProperty("Other");
        submitBudget.addStringProperty("Total");
        submitBudget.addStringProperty("Over_under");
        submitBudget.addStringProperty("Starting_balance");
        submitBudget.addStringProperty("Ending_balance");
        submitBudget.addStringProperty("Special_event");
        submitBudget.addStringProperty("Total_invoice");
        submitBudget.addStringProperty("Is_draft");


        Entity allPastBudgetInfo = schema.addEntity("AllPastBudgetInfo");
        allPastBudgetInfo.addStringProperty("interval");
        allPastBudgetInfo.addStringProperty("chef_name");

 /* ================================Chef Forum===============================================*/

        Entity allFormCategory = schema.addEntity("AllFormCategory");
        allFormCategory.addStringProperty("Forum_category_id").primaryKey();
        allFormCategory.addStringProperty("Category_name");
        allFormCategory.addStringProperty("Created_on");
        allFormCategory.addStringProperty("Total_topics");
        allFormCategory.addStringProperty("Total_comments");


        Entity allCategoryTopics = schema.addEntity("AllCategoryTopics");
        allCategoryTopics.addStringProperty("Forum_topics_id");
        allCategoryTopics.addStringProperty("Forum_category_id");
        allCategoryTopics.addStringProperty("Topic_name");
        allCategoryTopics.addStringProperty("Created_on");
        allCategoryTopics.addStringProperty("Created_by");
        allCategoryTopics.addStringProperty("Total_comments");
        allCategoryTopics.addStringProperty("image_url");


        Entity topicDetail = schema.addEntity("TopicDetail");
        topicDetail.addStringProperty("Topic_name");
        topicDetail.addStringProperty("Description");
        topicDetail.addStringProperty("Topic_created_by");
        topicDetail.addStringProperty("Topic_image_url");
        topicDetail.addStringProperty("Topic_created_on");
        topicDetail.addStringProperty("Total_comments");

        Entity topicComment = schema.addEntity("TopicComment");
        topicComment.addStringProperty("Comments_desc");
        topicComment.addStringProperty("Comment_created_by");
        topicComment.addStringProperty("Comment_created_on");
        topicComment.addStringProperty("comment_image_url");


      /*  Entity allCravingList = schema.addEntity("AllCravingList");
        allCravingList.addStringProperty("student_wishlist_id");
        allCravingList.addStringProperty("item_name");
        allCravingList.addStringProperty("wishlist_date");
        allCravingList.addStringProperty("student_name");*/

    }
}
