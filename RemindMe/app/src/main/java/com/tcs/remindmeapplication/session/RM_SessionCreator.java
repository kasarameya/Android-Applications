package com.tcs.remindmeapplication.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.tcs.remindmeapplication.utilities.RM_AppConstants;

import java.util.HashMap;

/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_SessionCreator {
    
        SharedPreferences pref;
        SharedPreferences.Editor editor;     // an editor is used to edit your preferences
        Context context;


        public RM_SessionCreator(Context context) {
            this.context = context;
        /*
         * Setting the mode as Private so that the preferences should only be
         * used in this application and not by any other application
         * also the preferences can be Shared Globally by using -
         *Activity.MODE_WORLD_READABLE - to read Application components data
         *globally and,
         *Activity.MODE_WORLD_WRITEABLE -file can be written globally by any
         *other application.
         */

            pref = context.getSharedPreferences(RM_AppConstants.SessionConstants.PREF_NAME, Context.MODE_PRIVATE);
        /*
         * the same pref mode can be set to private by using 0 as a flag instead
         * of Context.MODE_PRIVATE
         */

            editor = pref.edit();
        }

        // Creating a login session
        public void createLoginSession(String name, String email) {
            editor.putBoolean(RM_AppConstants.SessionConstants.IS_LOGIN, true);
            editor.putString(RM_AppConstants.SessionConstants.KEY_NAME, name);
            editor.putString(RM_AppConstants.SessionConstants.KEY_PASSWORD, email);
            editor.commit();
        }

        // Check login status
        public void checkLogin() {
            if (!this.isLoggedIn()) {

               /* Intent i = new Intent(context, LoginActivity.class);    // user is not logged in redirect to Login Activity
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
            }

        }

        // Getting stored session data of user and returing this data as a HASH MAP
        public HashMap<String, String> getUserDetails() {
            HashMap<String, String> user = new HashMap<String, String>();
            user.put(RM_AppConstants.SessionConstants.KEY_NAME, pref.getString(RM_AppConstants.SessionConstants.KEY_NAME, null)); // user name
            user.put(RM_AppConstants.SessionConstants.KEY_PASSWORD, pref.getString(RM_AppConstants.SessionConstants.KEY_PASSWORD, null));// user password
            return user;
        }

        // Clearing a session data
        public void logoutUser() {
            editor.clear();
            editor.commit();

           /* Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Closing all the Activities
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Add new Flag to start new Activity
            context.startActivity(i);*/
        }

        // Get Login State
        public boolean isLoggedIn() {
            return pref.getBoolean(RM_AppConstants.SessionConstants.IS_LOGIN, false);
        }
    }


