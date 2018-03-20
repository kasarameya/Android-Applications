package com.tcs.remindmeapplication.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tcs.remindmeapplication.beans.RM_TaskBean;

/**
 * Created by 968660 on 10/24/2015.
 */
public class RM_NotificationReceiver extends BroadcastReceiver {
    NotificationManager mManager;
    int flag=0;
    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle=intent.getExtras();
        RM_TaskBean taskBean= (RM_TaskBean) bundle.getSerializable("alarmBean");
        Log.d("Inside notifcatio", "Hello");
        Intent notificationIntent = new Intent(context, RM_NotificationService.class);
        notificationIntent.putExtras(bundle);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        context.startService(notificationIntent);
        //startNotification(context);
      /*  if(sharedPreferences.getBoolean("start",false)) {
            MyNotification myNotification = new MyNotification(context, taskBean);
            myNotification.startNotification();
            myNotification.stopNotification();
            editor.putBoolean("start",false);
            editor.commit();
            Log.d("Flag",String.valueOf(editor.));
        }
*/


       // mNotificationManager.cancel(11);

    }

   /* private void startNotification(Context context) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!").setAutoCancel(true);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, RM_Home_Activity.class);
        //resultIntent.putExtras(bundle);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(RM_Home_Activity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(11, mBuilder.build());

    }*/
}
