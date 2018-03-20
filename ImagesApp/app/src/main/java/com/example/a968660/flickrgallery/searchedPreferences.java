package com.example.a968660.flickrgallery;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * FlickrGallery
 * <p>
 *
 * <br/>
 * <b>-------Class Description-------------------</b>
 * <br/>
 */

public class searchedPreferences {
    private static final String SHARED_PREF_NAME = "MySharedPref";
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_RESULT_ID = "lastResultId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static void storeInSharedPref(Context context, String s){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_SEARCH_QUERY, s);
        editor.apply();
    }
    public static String getPrefSearchQuery(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREF_SEARCH_QUERY,null);
    }

    public static void storeLastResultId(Context context, String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_LAST_RESULT_ID, s);
        editor.apply();
    }

    public static String getPrefLastResultId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREF_LAST_RESULT_ID, null);
    }

    public static void storeAlarmStare(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_ALARM_ON, b);
        editor.apply();
    }

    public static boolean getAlarmState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREF_IS_ALARM_ON, false);
    }

}
