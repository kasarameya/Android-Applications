package com.tcs.remindmeapplication.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.services.MyServices;
import com.tcs.remindmeapplication.services.RM_AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_PendingList_Adapter extends BaseAdapter {
    private Activity mcontext;
    private ArrayList<RM_TaskBean> mTaskList;

    public RM_PendingList_Adapter(Activity mcontext, ArrayList<RM_TaskBean> mTaskList) {
        this.mcontext = mcontext;
        this.mTaskList = mTaskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTaskList.get(position).getTask_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view == null) {
            LayoutInflater layoutInflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.rm_pending_list_item_layout, parent, false);

        }
        else
        {
            view=convertView;
        }

        TextView mTaskName= (TextView) view.findViewById(R.id.pending_task_name);
        TextView mTaskDate= (TextView) view.findViewById(R.id.pending_task_date);
        TextView mTaskTime= (TextView) view.findViewById(R.id.tv_pending_task_time);
        ImageView mTaskLogo= (ImageView) view.findViewById(R.id.iv_pending_task_logo);

        mTaskName.setText(mTaskList.get(position).getTask_title());

        if(mTaskList.get(position).getTask_type()==0) {
            mTaskDate.setText(mTaskList.get(position).getDate() + "/" + (mTaskList.get(position).getMonth() + 1) + "/" + mTaskList.get(position).getYear());
            mTaskTime.setText(mTaskList.get(position).getHour() + ":" + mTaskList.get(position).getMinute());
            activateTaskByTime(position);
        }
        else if(mTaskList.get(position).getTask_type()==1){
            mTaskDate.setText("");
            mTaskTime.setText( mTaskList.get(position).getRingtone()+"");
            activateTaskByLocation();
        }

        if(mTaskList.get(position).getLatitude() == 0)
        {
            mTaskLogo.setImageResource(R.drawable.rm_taskdate_logo);
        }
        else if(mTaskList.get(position).getMonth()==0)
        {
            mTaskLogo.setImageResource(R.drawable.location_icon_nav);
        }


       /* AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(mcontext, RM_AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("alarmBean", mTaskList.get(position));
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, mTaskList.get(position).getTask_id(), intent, 0);
        //calendar.set( mTaskList.get(position).getYear(),  mTaskList.get(position).getMonth(),  mTaskList.get(position).getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MONTH,mTaskList.get(position).getMonth());
        calendar.set(Calendar.YEAR,mTaskList.get(position).getYear());
        calendar.set(Calendar.DAY_OF_MONTH,mTaskList.get(position).getDate());



        calendar.set(Calendar.HOUR_OF_DAY,  mTaskList.get(position).getHour());
        calendar.set(Calendar.MINUTE,  mTaskList.get(position).getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        //Toast.makeText(context,patientBeans.get(position).getHour() + " " + patientBeans.get(position).getMinutes() , Toast.LENGTH_SHORT).show();

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

*/

        return view;
    }

    public void updateList(ArrayList<RM_TaskBean> taskList){
        this.mTaskList = taskList;
        notifyDataSetChanged();
    }
    public void activateTaskByTime(int position) {
        AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(mcontext, RM_AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("alarmBean", mTaskList.get(position));
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, mTaskList.get(position).getTask_id(), intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MONTH,mTaskList.get(position).getMonth());
        calendar.set(Calendar.YEAR,mTaskList.get(position).getYear());
        calendar.set(Calendar.DAY_OF_MONTH,mTaskList.get(position).getDate());



        calendar.set(Calendar.HOUR_OF_DAY,  mTaskList.get(position).getHour());
        calendar.set(Calendar.MINUTE,  mTaskList.get(position).getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }
    public void activateTaskByLocation() {
        Intent intent = new Intent(mcontext,MyServices.class);
        mcontext.startService(intent);
    }
}
