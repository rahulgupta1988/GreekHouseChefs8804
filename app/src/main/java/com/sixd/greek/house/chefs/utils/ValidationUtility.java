package com.sixd.greek.house.chefs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Praveen on 17-Jul-17.
 */

public class ValidationUtility {

    // EMAIL_PATTERN
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public ValidationUtility(){}

   /* public static boolean validEmailAddress(String emailid){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailid);
        return matcher.matches();
    }
*/

    public static boolean validEmailAddress(String emailid){
       // Pattern regex = Pattern.compile("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(in|edu)|([0-9]{1,3}\\.{3}[0-9]{1,3}))");
        Pattern regex = Pattern.compile("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(edu)|([0-9]{1,3}\\.{3}[0-9]{1,3}))");


        Matcher matcher = regex.matcher(emailid);
        if (matcher.matches()) {
            return true;
        }else {
            return false;
        }

    }

    public static boolean validHouseCodeString(String txt){
        String adjusted = txt.replaceAll("(?m)^[ \t]*\r?\n", "");
        boolean haveonlybanklines=adjusted.length()>0;
        boolean empty=txt != null && txt.length() > 0;

        if(haveonlybanklines && empty)
            return true;
        else return false;
    }


    public static boolean validUserNamePassString(String txt){
        String adjusted = txt.replaceAll("(?m)^[ \t]*\r?\n", "");
        boolean haveonlybanklines=adjusted.length()>0;
        boolean empty=txt != null && txt.length() > 0;

        if(haveonlybanklines && empty) {
            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(txt);
            boolean found = matcher.find();
            if(found)return false;
            else return true;
        }
        else return false;
    }

    public static boolean validString(String txt){
        String adjusted = txt.replaceAll("(?m)^[ \t]*\r?\n", "");
        boolean haveonlybanklines=adjusted.length()>0;
        boolean empty=txt != null && txt.length() > 0;

        if(haveonlybanklines && empty)
            return true;
        else return false;
    }
}
