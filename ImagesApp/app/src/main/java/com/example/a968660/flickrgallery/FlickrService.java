package com.example.a968660.flickrgallery;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

/**
 * Created by hp- hp on 03-12-2016.
 */

public class FlickrService extends IntentService {
    public static final String LOCAL_BROADCAST = "NEW_RESULTS_BROADCAST";
    public static final String PERM_PRIVATE =
            "com.bignerdranch.android.photogallery.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";
    private static final String TAG = "FlickrService";
    private static final long POLL_INTERVAL = 1000 * 60; // 60 seconds


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FlickrService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FlickrService.class);
    }

    public static void setServicedAlarm(Context context, boolean isOn) {
        Intent intent = FlickrService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
        searchedPreferences.storeAlarmStare(context, isOn);

    }

    public static boolean hasAlarmStarted(Context context) {
        Intent intent = FlickrService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        String searchText = searchedPreferences.getPrefSearchQuery(this);
        String lastId = searchedPreferences.getPrefLastResultId(this);
        List<GalleryBean> beanList;

        if (searchText == null) {
            beanList = new DataDownloader().fetchPhotos();
        } else
            beanList = new DataDownloader().searchPhotos(searchText);

        if (beanList.size() == 0)
            return;
        String receivedId = beanList.get(0).getId();
        if (receivedId.equalsIgnoreCase(lastId))
            Log.d(TAG, "Old results only");
        else {
            Log.d(TAG, "New results received");
            //Log.d(TAG, "onHandleIntent: "+intent);
            Intent i = new Intent(this, PhotoGalleryActivity.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(getResources().getString(R.string.new_pictures_title))
                    .setContentText(getResources().getString(R.string.new_pictures_text))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            sendBroadcast(new Intent(LOCAL_BROADCAST), PERM_PRIVATE);
            sendNotification(0, notification);
        }
        searchedPreferences.storeLastResultId(this, receivedId);

    }

    private void sendNotification(int i, Notification notification) {
        Intent intent = new Intent(LOCAL_BROADCAST);
        intent.putExtra(REQUEST_CODE, i);
        intent.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(intent, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
