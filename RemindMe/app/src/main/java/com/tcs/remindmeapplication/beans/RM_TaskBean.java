package com.tcs.remindmeapplication.beans;

import java.io.Serializable;

/**
 * Created by 977975 on 10/20/2015.
 */
public class RM_TaskBean implements Serializable {



    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    String task_title;
    String task_desc;
    int date,month,year;
    int hour,minute,task_id,user_id;
    int task_type = 0 ;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    int notify,status,vibrate;
    double latitude, longitude;
    String ringtone;


    public RM_TaskBean() {
        this.task_title = "";
    }

    public RM_TaskBean(int date, int hour, int latitude, int longitude, int minute, int month, int notify, String password, String ringtone, String task_desc,String task_title, int year, int status,int task_type) {
        this.date = date;
        this.hour = hour;
        this.latitude = latitude;
        this.longitude = longitude;
        this.minute = minute;
        this.month = month;
        this.notify = notify;
        this.ringtone = ringtone;
        this.task_desc = task_desc;
        this.task_title = task_title;
        this.year = year;
        this.status = status;
        this.task_type =task_type;

    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
    //zdgsjklg
}
