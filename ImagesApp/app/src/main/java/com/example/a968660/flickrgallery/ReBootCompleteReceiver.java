package com.example.a968660.flickrgallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by hp- hp on 03-12-2016.
 */

public class ReBootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "Reboot Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        boolean alarmState = searchedPreferences.getAlarmState(context);
        FlickrService.setServicedAlarm(context, alarmState);

    }
}
