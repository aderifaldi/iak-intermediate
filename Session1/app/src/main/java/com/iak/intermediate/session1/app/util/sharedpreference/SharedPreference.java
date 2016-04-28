package com.iak.intermediate.session1.app.util.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aderifaldi on 13/01/2016.
 */
public class SharedPreference {

    public static final String PREF_NAME = "IAKIntermediatePreferences";
    private static final String NOTIFICATION_STATUS = "isNotificationOn";

    public static boolean getNotificationStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(NOTIFICATION_STATUS, false);
    }

    public static void setNotificationStatus(Context context, boolean data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICATION_STATUS, data);
        editor.apply();
    }

    /**
     * INTEGER
     */
//    public static void saveAppVersion(Context context, int appVersion){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(PREF_VERSION_APP, appVersion);
//        editor.apply();
//    }
//
//    public static int getAppVersion(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getInt(PREF_VERSION_APP, 0);
//    }

    /**
     * STRING
     */
//    public static void saveUserEmail(Context context, String userEmail){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(PREF_USER_EMAIL, userEmail);
//        editor.apply();
//    }
//
//    public static String getUserEmail(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(PREF_USER_EMAIL, null);
//    }

}
