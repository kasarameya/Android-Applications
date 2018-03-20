package com.tcs.remindmeapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tcs.remindmeapplication.beans.RM_FriendBean;
import com.tcs.remindmeapplication.beans.RM_LoginBean;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.utilities.RM_AppConstants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_DbHelper  extends SQLiteOpenHelper {

    public static RM_DbHelper mDataBaseHelper;
    public static RM_DbHelper getInstance(){
        return mDataBaseHelper;
    }

    public static RM_DbHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new RM_DbHelper(context, RM_AppConstants.DatabaseConstants.REMINDER_DATABASE,null,RM_AppConstants.DatabaseConstants.DB_VERSION);
        }
        return mDataBaseHelper;
    }

    private RM_DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RM_AppConstants.DatabaseConstants.CREATE_LOGIN_TABLE);
        db.execSQL(RM_AppConstants.DatabaseConstants.CREATE_TASK_TABLE);
        db.execSQL(RM_AppConstants.DatabaseConstants.CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 1:
                //Alter Table from 0 to current
            case 2:
                //Alter Table from 1 to current
            case 3:
                //Alter Table from 2 to current
            case 4:
                //Alter Table from 3 to current
        }
    }

    // CRUD operation for Login Table
    public void addUser(RM_LoginBean loginBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USERNAME,loginBean.getUsername());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_PASSWORD,loginBean.getPassword());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_IMAGE_URL,loginBean.getProfileImage());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_DOB,loginBean.getDob());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_EMAIL,loginBean.getEmail());
        db.insert(RM_AppConstants.DatabaseConstants.LOGIN_TABLE, null, contentValues);
        db.close();
    }

    public List<RM_LoginBean> selectAllUser(){
        List<RM_LoginBean> mLoginBeanList = new ArrayList<RM_LoginBean>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(RM_AppConstants.DatabaseConstants.SELECT_ALL_LOGIN, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                RM_LoginBean loginBean = new RM_LoginBean();
                loginBean.setUser_id(cursor.getInt(0));
                loginBean.setUsername(cursor.getString(1));
                loginBean.setPassword(cursor.getString(2));
                loginBean.setProfileImage(cursor.getString(3));
                loginBean.setDob(cursor.getString(4));
                loginBean.setEmail(cursor.getString(5));
                mLoginBeanList.add(loginBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mLoginBeanList;
    }

    public void updateUser(RM_LoginBean loginBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USERNAME,loginBean.getUsername());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_PASSWORD,loginBean.getPassword());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_IMAGE_URL,loginBean.getProfileImage());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_DOB,loginBean.getDob());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_EMAIL,loginBean.getEmail());
        db.update(RM_AppConstants.DatabaseConstants.LOGIN_TABLE, contentValues, RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_LOGIN + "=?", new String[]{String.valueOf(loginBean.getUser_id())});
        db.close();
    }

    public void deleteUser(RM_LoginBean loginBean) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RM_AppConstants.DatabaseConstants.LOGIN_TABLE, RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_LOGIN + "=?", new String[]{String.valueOf(loginBean.getUser_id())});
        db.close();
    }

    // CRUD operation for Task Table
    public void addTask(RM_TaskBean taskBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_TITLE,taskBean.getTask_title());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_DES,taskBean.getTask_desc());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_DAY_OF_DATE,taskBean.getDate());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_MONTH,taskBean.getMonth());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_YEAR,taskBean.getYear());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_HOURS,taskBean.getHour());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_MINUTES,taskBean.getMinute());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_LONGITUDE,taskBean.getLongitude());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_LATITUDE,taskBean.getLatitude());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_STATUS,taskBean.getStatus());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NOTIFY,taskBean.getNotify());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_RINGTONE,taskBean.getRingtone());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_VIBRATION,taskBean.getVibrate());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_TASK,taskBean.getUser_id());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_TYPE,taskBean.getTask_type());

        db.insert(RM_AppConstants.DatabaseConstants.TASK_TABLE, null, contentValues);
        db.close();
    }

    public List<RM_TaskBean> selectAllTask(int status,int user_id){
        List<RM_TaskBean> mTaskBeanList = new ArrayList<RM_TaskBean>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(RM_AppConstants.DatabaseConstants.SELECT_ALL_TASK, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                RM_TaskBean taskBean = new RM_TaskBean();
                taskBean.setTask_id(cursor.getInt(0));
                taskBean.setTask_title(cursor.getString(1));
                taskBean.setTask_desc(cursor.getString(2));
                taskBean.setDate(cursor.getInt(3));
                taskBean.setMonth(cursor.getInt(4));
                taskBean.setYear(cursor.getInt(5));
                taskBean.setHour(cursor.getInt(6));
                taskBean.setMinute(cursor.getInt(7));
                taskBean.setLongitude(cursor.getDouble(8));
                taskBean.setLatitude(cursor.getDouble(9));
                taskBean.setStatus(cursor.getInt(10));
                taskBean.setNotify(cursor.getInt(11));
                taskBean.setRingtone(cursor.getString(12));
                taskBean.setVibrate(cursor.getInt(13));
                taskBean.setUser_id(cursor.getInt(14));
                taskBean.setTask_type(cursor.getInt(15));

                Log.d("Task id", String.valueOf(taskBean.getTask_id()));
                if(taskBean.getStatus() == status && taskBean.getUser_id() == user_id)
                    mTaskBeanList.add(taskBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mTaskBeanList;
    }

    public void updateTask(RM_TaskBean taskBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_TITLE,taskBean.getTask_title());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_DES,taskBean.getTask_desc());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_DAY_OF_DATE,taskBean.getDate());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_MONTH,taskBean.getMonth());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_YEAR,taskBean.getYear());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_HOURS,taskBean.getHour());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_MINUTES,taskBean.getMinute());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_LONGITUDE,taskBean.getLongitude());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_LATITUDE,taskBean.getMonth());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_STATUS,taskBean.getStatus());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NOTIFY, taskBean.getNotify());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_RINGTONE, taskBean.getRingtone());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_VIBRATION, taskBean.getVibrate());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_TASK, taskBean.getUser_id());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_TASK_TYPE,taskBean.getTask_type());

        db.update(RM_AppConstants.DatabaseConstants.TASK_TABLE, contentValues, RM_AppConstants.DatabaseConstants.COLUMN_TASK_ID + "=?", new String[]{String.valueOf(taskBean.getTask_id())});
        Log.d("Update Bean Id", String.valueOf(taskBean.getTask_id()));
        db.close();
    }

    public void deleteTask(RM_TaskBean taskBean) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RM_AppConstants.DatabaseConstants.TASK_TABLE, RM_AppConstants.DatabaseConstants.COLUMN_TASK_ID + "=?", new String[]{String.valueOf(taskBean.getTask_id())});
        db.close();
    }
    public int getMaxId()
    {
        SQLiteDatabase db = getWritableDatabase();
        int a=0;
        Cursor cursor=db.rawQuery(RM_AppConstants.DatabaseConstants.SELECT_MAX_TASK, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            a = cursor.getInt(0);

            Log.d("Max column value", String.valueOf(a));
        }
        return a;

    }





    // CRUD operation for Friend list Table
    public void addFriends(RM_FriendBean friendBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NAME,friendBean.getName());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NICKNAME,friendBean.getNickname());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_PHONENUMBER,friendBean.getPhoneNumber());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_EMAIL,friendBean.getEmail());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_ADDRESS,friendBean.getAddress());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_FRIEND_IMAGE, friendBean.getFriend_image());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_FRIEND,friendBean.getUser_id());
        db.insert(RM_AppConstants.DatabaseConstants.FRIEND_LIST_TABLE, null, contentValues);
        db.close();
    }

    public List<RM_FriendBean> selectAllFriends(int user_id){
        List<RM_FriendBean> mFriendBeanList = new ArrayList<RM_FriendBean>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(RM_AppConstants.DatabaseConstants.SELECT_ALL_FRIEND, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                RM_FriendBean friendBean = new RM_FriendBean();
                friendBean.setFriend_id(cursor.getInt(0));
                friendBean.setName(cursor.getString(1));
                friendBean.setNickname(cursor.getString(2));
                friendBean.setPhoneNumber(cursor.getString(3));
                friendBean.setEmail(cursor.getString(4));
                friendBean.setFriend_image(cursor.getString(5));
                friendBean.setUser_id(cursor.getInt(6));
                friendBean.setAddress(cursor.getString(7));
                if(friendBean.getUser_id()==user_id)
                {
                    mFriendBeanList.add(friendBean);
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mFriendBeanList;
    }

    public void updateFriend(RM_FriendBean friendBean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NAME,friendBean.getName());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_NICKNAME,friendBean.getNickname());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_PHONENUMBER,friendBean.getPhoneNumber());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_EMAIL,friendBean.getEmail());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_ADDRESS,friendBean.getAddress());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_FRIEND_IMAGE, friendBean.getFriend_image());
        contentValues.put(RM_AppConstants.DatabaseConstants.COLUMN_USER_ID_FRIEND,friendBean.getUser_id());
        db.update(RM_AppConstants.DatabaseConstants.FRIEND_LIST_TABLE, contentValues, RM_AppConstants.DatabaseConstants.COLUMN_FRIEND_ID + "=?", new String[]{String.valueOf(friendBean.getFriend_id())});
        db.close();
    }

    public void deleteFriends(RM_FriendBean friendBean) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(RM_AppConstants.DatabaseConstants.FRIEND_LIST_TABLE, RM_AppConstants.DatabaseConstants.COLUMN_FRIEND_ID + "=?", new String[]{String.valueOf(friendBean.getFriend_id())});
        db.close();
    }

}
