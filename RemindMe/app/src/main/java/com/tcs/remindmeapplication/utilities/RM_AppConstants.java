package com.tcs.remindmeapplication.utilities;

/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_AppConstants {

    public static class DatabaseConstants
    {
        public static final int DB_VERSION = 1;
        public static final String REMINDER_DATABASE = "reMindMe.db";
        public static final String LOGIN_TABLE = "LOGIN_TABLE";
        public static final String FRIEND_LIST_TABLE = "FRIEND_LIST_TABLE";
        public static final String TASK_TABLE = "TASK_TABLE";

        //Login Table
        public static final String COLUMN_USER_ID_LOGIN = "USER_ID_LOGIN";
        public static final String COLUMN_USERNAME = "USERNAME";
        public static final String COLUMN_PASSWORD = "PASSWORD";
        public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
        public static final String COLUMN_DOB = "DOB";
        public static final String COLUMN_USER_EMAIL = "USER_EMAIL";

        //Task Table
        public static final String COLUMN_TASK_ID = "TASK_ID";
        public static final String COLUMN_TASK_TITLE = "TASK_TITLE";
        public static final String COLUMN_TASK_DES = "TASK_DES";
        public static final String COLUMN_DAY_OF_DATE = "DAY_OF_DATE";
        public static final String COLUMN_MONTH = "MONTH";
        public static final String COLUMN_YEAR = "YEAR";
        public static final String COLUMN_HOURS = "HOURS";
        public static final String COLUMN_MINUTES = "MINUTES";
        public static final String COLUMN_LONGITUDE = "LONGITUDE";
        public static final String COLUMN_LATITUDE = "LATITUDE";
        public static final String COLUMN_STATUS = "STATUS";
        public static final String COLUMN_NOTIFY = "NOTIFY";
        public static final String COLUMN_RINGTONE = "RINGTONE";
        public static final String COLUMN_VIBRATION = "VIBRATION";
        public static final String COLUMN_USER_ID_TASK = "USER_ID_TASK";

        //FriendList Table
        public static final String COLUMN_FRIEND_ID = "FRIEND_ID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_NICKNAME = "NICKNAME";
        public static final String COLUMN_PHONENUMBER = "PHONENUMBER";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_ADDRESS = "ADDRESS";
        public static final String COLUMN_FRIEND_IMAGE = "FRIEND_IMAGE";
        public static final String COLUMN_USER_ID_FRIEND = "USER_ID_FRIEND";
        public static final String COLUMN_TASK_TYPE = "TASK_TYPE";


        public static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + LOGIN_TABLE +"(" + COLUMN_USER_ID_LOGIN + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USERNAME +" TEXT NOT NULL," + COLUMN_PASSWORD +" TEXT NOT NULL," + COLUMN_IMAGE_URL +" TEXT,"+COLUMN_DOB+" TEXT NOT NULL,"+ COLUMN_USER_EMAIL +" TEXT NOT NULL"+ ")";
        public static final String SELECT_ALL_LOGIN = "SELECT * FROM "+ LOGIN_TABLE;

       // public static final String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE +"(" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_TASK_TITLE+" TEXT NOT NULL," + COLUMN_TASK_DES +" TEXT," + COLUMN_DAY_OF_DATE +" INTEGER," + COLUMN_MONTH +" INTEGER,"+COLUMN_YEAR+" INTEGER," +COLUMN_HOURS+" INTEGER," + COLUMN_MINUTES +" INTEGER," + COLUMN_LONGITUDE +" REAL," + COLUMN_LATITUDE +" REAL,"+COLUMN_STATUS+" INTEGER NOT NULL,"+COLUMN_NOTIFY+" INTEGER,"+COLUMN_RINGTONE+" TEXT,"+COLUMN_USER_ID_TASK+" INTEGER"+" FOREIGN KEY ("+COLUMN_USER_ID_TASK+") REFERENCES "+LOGIN_TABLE+"("+COLUMN_USER_ID_LOGIN+")"+ ")";
       public static final String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE +"("
               + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + COLUMN_TASK_TITLE+" TEXT NOT NULL,"
               + COLUMN_TASK_DES +" TEXT,"
               + COLUMN_DAY_OF_DATE +" INTEGER,"
               + COLUMN_MONTH +" INTEGER,"
               + COLUMN_YEAR+" INTEGER,"
               + COLUMN_HOURS+" INTEGER,"
               + COLUMN_MINUTES +" INTEGER,"
               + COLUMN_LONGITUDE +" REAL,"
               + COLUMN_LATITUDE +" REAL,"
               + COLUMN_STATUS+" INTEGER NOT NULL,"
               + COLUMN_NOTIFY+" INTEGER,"
               + COLUMN_RINGTONE+" TEXT,"
               + COLUMN_VIBRATION+" INTEGER,"
               + COLUMN_USER_ID_TASK+" INTEGER,"
               + COLUMN_TASK_TYPE+" INTEGER"
               +")";

        public static final String SELECT_ALL_TASK = "SELECT * FROM "+ TASK_TABLE;
        public static final String SELECT_MAX_TASK = "SELECT MAX ( "+COLUMN_TASK_ID+" )FROM  "+ TASK_TABLE ;

       // public static final String CREATE_FRIEND_TABLE = "CREATE TABLE " + FRIEND_LIST_TABLE +"(" + COLUMN_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_NAME+" TEXT NOT NULL," + COLUMN_NICKNAME +" TEXT," + COLUMN_PHONENUMBER+" TEXT NOT NULL," + COLUMN_EMAIL +" TEXT,"+COLUMN_FRIEND_IMAGE+" TEXT," +COLUMN_USER_ID_FRIEND+" INTEGER," + COLUMN_ADDRESS +" TEXT"+" FOREIGN KEY ("+COLUMN_USER_ID_FRIEND+") REFERENCES "+LOGIN_TABLE+"("+COLUMN_USER_ID_LOGIN+")"+ ")";
       public static final String CREATE_FRIEND_TABLE = "CREATE TABLE " + FRIEND_LIST_TABLE +"(" + COLUMN_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               +COLUMN_NAME+" TEXT NOT NULL,"
               + COLUMN_NICKNAME +" TEXT,"
               + COLUMN_PHONENUMBER+" TEXT NOT NULL,"
               + COLUMN_EMAIL +" TEXT,"
               +COLUMN_FRIEND_IMAGE+" TEXT,"
               +COLUMN_USER_ID_FRIEND+" INTEGER,"
               + COLUMN_ADDRESS +" TEXT"+ ")";
        public static final String SELECT_ALL_FRIEND = "SELECT * FROM "+ FRIEND_LIST_TABLE;
    }

    public static class SessionConstants {
        public static final String PREF_NAME = "Pref";     // Shared Preference file name
        public static final String IS_LOGIN = "IsLoggedIn";     // Shared Preferences Key
        public static final String KEY_NAME = "name";
        public static final String KEY_PASSWORD = "password";
    }


    public static class SetPasswordConstants{
        public static  int FLAG=1;
        public static String imagepath="profileImage";
    }
    public static class LoginConstants
    {
        public static int FLAG=1;
    }

    public static class ResetConstants
    {
        public static int FLAG=1;
    }
    public static class DeleteAccountConstants
    {
        public static int FLAG=1;
    }

}
