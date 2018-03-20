package com.tcs.remindmeapplication.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.activities.RM_Home_Activity;
import com.tcs.remindmeapplication.activities.RM_SplashScreen_Activity;
import com.tcs.remindmeapplication.beans.RM_TaskBean;

/**
 * Created by hp- hp on 25-10-2015.
 */
@SuppressWarnings("deprecation")
public class RM_NotificationService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        try {


            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                RM_TaskBean taskBean = (RM_TaskBean) bundle.getSerializable("alarmBean");
                if(taskBean!=null) {
                    // Getting Notification Service
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("reMindMe")
                                    .setContentText(taskBean.getTask_title()).setAutoCancel(true);
// Creates an explicit intent for an Activity in your app
                    /*Intent resultIntent = new Intent(getApplicationContext(), RM_SplashScreen_Activity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
// Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(RM_Home_Activity.class);
// Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );*/
                    //mBuilder.setContentIntent(resultPendingIntent);
                    Notification note = mBuilder.build();
                    note.defaults |= Notification.DEFAULT_ALL;

                    NotificationManager mNotificationManager =
                            (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                    mNotificationManager.notify(11, note);



                    Intent intent1 = new Intent(getApplicationContext(), RM_NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), taskBean.getTask_id(), intent1, 0);


                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    // Toast.makeText(RM_NotificationService.this, "Notification Cancelled", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(RM_NotificationService.this, "No bean received", Toast.LENGTH_SHORT).show();
                }

            } else {
                //Toast.makeText(RM_NotificationService.this, "Null pointer exception", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.d("Notification","Wassup notitifcation");
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();
    }

}
