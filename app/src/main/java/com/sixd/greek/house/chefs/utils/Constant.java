package com.sixd.greek.house.chefs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramji on 14-Aug-17.
 */

 public class Constant {

  //  public static final String BASEURL="http://greekhousechefs.6degreesit.com/Services";// Old Live server

  //  public static final String BASEURL="http://192.168.0.190/chef_mobile_app/Services";// harshad

  //  public static final String BASEURL="http://192.168.0.155/chef_mobile_app/Services";  //amit sr

    public static final String BASEURL="http://192.168.0.188/chef_mobile_app/Services";  //manish

   //public static final String BASEURL="http://ghctest.6degreesit.com/services";// Office Live server

   //public static final String BASEURL="http://app.greekhousechefs.com/services";// Client Live server


    public static String notificationId="";
    public static String GCMSenderId = "944210482964";  //467865743742  //670120994725

    public class HeaderParams{
        public static final String ACCEPT_TYPE="application/json";
        public static final String CONTENT_TYPE="application/x-www-form-urlencoded";
    }

    public class API {
        public static final String LOIGIN_API ="login";
        public static final String LOGOUT_API ="logout";
        public static final String SIGNUP_API ="studentSignUp";
        public static final String FORGOTPASSWORD_API ="forgetUsernameAndPassword";
        public static final String MENU_CALENDER_API ="menuCalendarList";
        public static final String EVENT_API ="eventList";
        public static final String CONTACTUS_API ="contactUs";
        public static final String CRAVING_API ="craving";
        public static final String  RATE_API ="getAllRating";
        public static final String  ALLERGY_API ="allergyList";
        public static final String  ADDMSG_ALLERGY_API ="addAllergyMessage";
        public static final String  ALLERGY_ADD_API ="addAllergy";
        public static final String  ADDMSG_LATE_PLATE_API ="addLatePlateRequest";

        public static final String  MENU_DEVELOPMENT_CATEGORY_API ="getCategories";
        public static final String  MENU_DEVELOPMENT_ITEMS_API ="GetmenuItems";
        public static final String  ADD_RECIPE_API ="addRecipe";
        public static final String  WEEK_INTERVAL_API ="getWeekInterval";
       /* public static final String  LATE_PLATE_CHEF_API ="GetlatePlate";
        public static final String  LATE_PLATE_CHEF_DETAILS_API ="GetallergyForLatePlate";*/
        public static final String  MASTER_MENU_ITEMS_API ="GetMasterAllMenuItems";

        public static final String  ADD_MENU_CALENDER_API ="addEditMenuCalender";
        public static final String  CHANGE_PASSWORD_API ="changePassword";

        public static final String  LATE_OUT_INFO_API ="LateplateDetailsForInhouseAndOutHouse";


        public static final String  GET_SUBMIT_BUDGET_API ="getSubmitBudget";
        public static final String  ADD_SUBMIT_BUDGET_API ="AddEditsubmitBudget";
        public static final String  PAST_BUDGET_API ="GetBudgetPastInfo";

        public static final String  GET_FORUM_CATEGPRY_API ="getForumCategory";
        public static final String  GET_FORUM_TOPICS_API ="GetForumtopics";
        public static final String  GET_FORUM_DETAILS_API ="ForumTopicDetails";
        public static final String  ADD_FORUM_COMMENTS_API ="AddForumComments";
        public static final String  ADD_FORUM_TOPIC_API ="AddForumTopic";
        public static final String  GET_CRAVING_LIST_API ="GetCravingListForChef";
        public static final String  POST_CRAVING_DATA_API ="PostCravingListByChef";
        public static final String  POST_HTML_API ="GetErrorhtml";
    }

    public class Messages {
        public static final String SERVER_RESPONCE="Received data is not compatible.";
        public static final String INTERNET_CONNECTIVITY="Please check your internet connection!";
        public static final String SIGNUP_SUCCESS="Registration successfully!";
        public static final String LOGIN_SUCCESS="Successfully LoggedIn!";
        public static final String USERNAME_INVALIDE="Invalid username!";
        public static final String USERNAME_INVALIDE_CHRACTERS="Username must be at least 4 characters!";
        public static final String USERNAME_INVALIDE_SPACE="Space not allowed in username!";
        public static final String PASSWORD_INVALIDE="Invalid password!";
        public static final String NAME_INVALIDE="Invalid name!";
        public static final String EMAIL_INVALIDE="Invalid email!";
        public static final String CAMPUSE_CODE_INVALIDE="Invalid house code!";
        public static final String FORGOTPASSWORD_SUCCESS_DIALOG_MSG="Username and Password sent on your email!";
    }

  public class SocialURL{
      public static final String INSTAGRAM_URL="https://www.instagram.com/greekhousechefs/";
      public static final String FACEBOOK_URL="https://www.facebook.com/GreekHouseChefs/";
      public static final String TWITTER_URL="https://twitter.com/GreekHouseChefs";
  }


    public class SharePKey{
        public static final String USERNAME="username";
        public static final String PASSWORD="password";
        public static final String REMEMBER="remeber";
    }


    /*===========================BreakFast Constatnt=======================*/

    public static List<String> ListElementsMenutitle = new ArrayList<String>();
    public static List<String> ListElementsMenutitle2 = new ArrayList<String>();
    public static List<String> ListElementsMenutitle3 = new ArrayList<String>();
    public static List<String> ListElementsMenutitle4 = new ArrayList<String>();
    public static List<String> ListElementsMenutitle5 = new ArrayList<String>();
    public static List<String> ListElementsMenutitle6 = new ArrayList<String>();
    public static List<String> ListElementsMenutitle7 = new ArrayList<String>();
    public static List<String> ListElementsFinal_temp = new ArrayList<String>();

    /*===========================Lunch Constatnt=======================*/

    public static List<String> ListElementsMenutitle_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle2_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle3_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle4_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle5_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle6_lunch = new ArrayList<String>();
    public static List<String> ListElementsMenutitle7_lunch = new ArrayList<String>();
    public static List<String> ListElementsFinal_temp_lunch = new ArrayList<String>();

     /*===========================Dinner Constatnt=======================*/

    public static List<String> ListElementsMenutitle_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle2_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle3_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle4_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle5_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle6_dinner = new ArrayList<String>();
    public static List<String> ListElementsMenutitle7_dinner = new ArrayList<String>();
    public static List<String> ListElementsFinal_temp_dinner = new ArrayList<String>();



    public static List<String> ListElements_NUMBERS = new ArrayList<String>();

    public static List<String> ListElements_NAME = new ArrayList<String>();

}
