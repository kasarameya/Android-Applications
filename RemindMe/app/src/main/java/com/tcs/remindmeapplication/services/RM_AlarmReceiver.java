package com.tcs.remindmeapplication.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tcs.remindmeapplication.activities.RM_Alarm_Activity;

/**
 * Created by 968660 on 10/21/2015.
 */
public class RM_AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();

        Intent intent1=new Intent(context,RM_Alarm_Activity.class);

        intent1.putExtras(bundle);
        intent1.addFlags(intent1.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

    }
}
