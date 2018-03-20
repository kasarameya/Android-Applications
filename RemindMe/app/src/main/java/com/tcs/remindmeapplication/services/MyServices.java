package com.tcs.remindmeapplication.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.activities.RM_Home_Activity;
import com.tcs.remindmeapplication.activities.RM_Login_Activity;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

import java.util.List;


/**
 * Created by hp- hp on 28-10-2015.
 */
public class MyServices extends Service{
    private GpsTracker gps;
    SharedPreferences sharedPreferences;
    Double latitude,longitude,distance;
    String lat,lng;
    String mPlace;
    SharedPreferences.Editor editor;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        gps = new GpsTracker(MyServices.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            //sharedPreferences = getApplicationContext().getSharedPreferences("Location", 0);
            //String chkLat, chkLng;
            //chkLat = sharedPreferences.getString("Latitude", null);
            /*chkLng = sharedPreferences.getString("Longitude", null);
            Toast.makeText(MyServices.this,chkLng+ "shared", Toast.LENGTH_SHORT).show();*/
            /*latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            lat = latitude + "";
            lng = longitude + "";
            Double latD= Double.parseDouble(chkLat);
            Double lngD= Double.parseDouble(chkLng);
            distance = distFrom(latitude,longitude,latD,lngD);
            if(distance<=5000) {
                showNotification();
            }
*/



            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            lat = latitude + "";
            lng = longitude + "";

            List<RM_TaskBean> pendingTask = RM_DbHelper.getInstance().selectAllTask(0, RM_Login_Activity.user_id);
            for(int i=0;i<pendingTask.size();i++) {
                if(pendingTask.get(i).getTask_type()==0) {
                    pendingTask.remove(i);
                }
            }
           // Log.d("size", pendingTask.size() + "");
          // Toast.makeText(MyServices.this,pendingTask.get(0).getLongitude()+" hello", Toast.LENGTH_SHORT).show();
            for(int i=0;i<pendingTask.size();i++) {

                Double latD= pendingTask.get(i).getLatitude();
                Double lngD= pendingTask.get(i).getLongitude();

                distance = distFrom(latitude,longitude,latD,lngD);
                if(distance<=5000) {
                    pendingTask.get(i).setStatus(1);
                    mPlace = pendingTask.get(i).getRingtone();

                    RM_DbHelper.getInstance(this).updateTask(pendingTask.get(i));
                    showNotification();
                }
            }


        } else {
            Toast.makeText(MyServices.this, "GPS or Network is not enabled", Toast.LENGTH_SHORT).show();
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
        }


        stopSelf(); // to stop the service
        return  START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MyServices.this, RM_Home_Activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(MyServices.this, 0, intent, 0);


        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0

        long[] pattern = { 0, 200, 500, 200, 500 };
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("Location Reached")
                .setContentText(mPlace)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent).setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(pattern)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }


    public static Double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double dist = (Double) (earthRadius * c);

        return dist;
    }

}
