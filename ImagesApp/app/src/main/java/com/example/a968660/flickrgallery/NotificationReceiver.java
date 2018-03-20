package com.example.a968660.flickrgallery;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by hp- hp on 03-12-2016.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent i) {
        if (getResultCode() != Activity.RESULT_OK) {
// A foreground activity cancelled the broadcast
            return;
        }
        int requestCode = i.getIntExtra(FlickrService.REQUEST_CODE, 0);
        Notification notification = i.getParcelableExtra(FlickrService.NOTIFICATION);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(c);
        notificationManager.notify(requestCode, notification);
    }
}
