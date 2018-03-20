package com.tcs.remindmeapplication.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.services.RM_AlarmReceiver;
import com.tcs.remindmeapplication.services.RM_NotificationReceiver;

public class RM_TaskDetails_Activity extends AppCompatActivity {
    TextView mTaskTitle;
    TextView mTaskDescription;
    TextView mTaskDate;
    TextView mTaskTime;
    TextView mTaskRingtone;
    Toolbar mToolbar;
    RM_TaskBean taskBean;
    TextView mStatus;
    TextView mVibration;
    TextView mNotification;
    TextView mPlaceLabel,mLatitude,mLongitude,status,vibration,notificaation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        taskBean= (RM_TaskBean) bundle.getSerializable("pendingItem");

        setContentView(R.layout.rm__task_details_date_activity);
       // Toast.makeText(RM_TaskDetails_Activity.this,taskBean.getTask_type()+ "date", Toast.LENGTH_SHORT).show();
       /* if(taskBean.getTask_type()==0){
            setContentView(R.layout.rm__task_details_date_activity);
            Toast.makeText(RM_TaskDetails_Activity.this,taskBean.getTask_type()+ "date", Toast.LENGTH_SHORT).show();
        }
        else if(taskBean.getTask_type()==1){
            setContentView(R.layout.rm_task_detail_location_activity);
            Toast.makeText(RM_TaskDetails_Activity.this,taskBean.getTask_type()+ "date", Toast.LENGTH_SHORT).show();
        }
*/


        mTaskTitle= (TextView) findViewById(R.id.tv_taskDetails_taskTitle);
        mTaskDescription= (TextView) findViewById(R.id.tv_taskDetails_taskDescription);
        mTaskDate= (TextView) findViewById(R.id.tv_taskDetails_date);
        mTaskTime= (TextView) findViewById(R.id.tv_taskDetails_time);
        mTaskRingtone= (TextView) findViewById(R.id.tv_taskDetails_ringtone);
        mToolbar= (Toolbar) findViewById(R.id.toolbar_taskDetails);
        mStatus= (TextView) findViewById(R.id.tv_taskDetails_Status);
        mVibration= (TextView) findViewById(R.id.tv_taskDetails_VIbration);
        mNotification= (TextView) findViewById(R.id.tv_taskDetails_notification);
        mPlaceLabel= (TextView) findViewById(R.id.textView11);
        mLatitude = (TextView) findViewById(R.id.textView9);
        mLongitude = (TextView) findViewById(R.id.textView10);
        status= (TextView) findViewById(R.id.textView12);
        notificaation= (TextView) findViewById(R.id.textView13);
        vibration= (TextView) findViewById(R.id.textView14);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        /*Bundle bundle=getIntent().getExtras();
        taskBean= (RM_TaskBean) bundle.getSerializable("pendingItem");*/

        mTaskTitle.setText(taskBean.getTask_title());
        mTaskDescription.setText(taskBean.getTask_desc());
        mTaskDate.setText(String.valueOf(taskBean.getDate())+"/"+String.valueOf(taskBean.getMonth()+1)+"/"+String.valueOf(taskBean.getYear()));
        mTaskTime.setText(String.valueOf(taskBean.getHour())+":"+String.valueOf(taskBean.getMinute()));
        mTaskRingtone.setText(taskBean.getRingtone());
        if(taskBean.getStatus() == 0)
        {
            mStatus.setText("Pending");
        }
        else {
            mStatus.setText("Completed");
        }

        if(taskBean.getVibrate() == 0)
        {
            mVibration.setText("Disabled");
        }
        else {
            mVibration.setText("Enabled");
        }

        if(taskBean.getNotify() == 1)
        {
            mNotification.setText("Enabled");
        }
        else {
            mNotification.setText("Disabled");
        }
        if(taskBean.getTask_type()== 1){
            mPlaceLabel.setText("Place");
            mLongitude.setText("Longitude");
            mLatitude.setText("Latitude");
            status.setVisibility(View.GONE);
            mTaskDate.setText(taskBean.getLatitude() + "");
            mTaskTime.setText(taskBean.getLongitude()+"");
            vibration.setVisibility(View.GONE);
            notificaation.setVisibility(View.GONE);
            mStatus.setVisibility(View.GONE);
            mVibration.setVisibility(View.GONE);
            mNotification.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__task_details_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id == android.R.id.home)
        {
            finish();
        }

        if(id == R.id.delete_task)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    RM_TaskDetails_Activity.this);

            // set title
            alertDialogBuilder.setTitle("Do you want to delete this task?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to delete!")
                    .setCancelable(false)
                    .setTitle("Delete Task")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            Toast.makeText(RM_TaskDetails_Activity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RM_TaskDetails_Activity.this, RM_Home_Activity.class);


                            Intent delAlarmIntent = new Intent(RM_TaskDetails_Activity.this, RM_AlarmReceiver.class);
                            PendingIntent pendingIntentForAlarmDel = PendingIntent.getBroadcast(RM_TaskDetails_Activity.this, taskBean.getTask_id(), delAlarmIntent, 0);


                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(pendingIntentForAlarmDel);

                            Intent delNotificationIntent = new Intent(RM_TaskDetails_Activity.this, RM_NotificationReceiver.class);
                            PendingIntent pendingIntentForNotiDel = PendingIntent.getBroadcast(getApplicationContext(), taskBean.getTask_id(), delNotificationIntent, 0);


                            AlarmManager notManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            notManager.cancel(pendingIntentForNotiDel);
                            RM_DbHelper.getInstance(RM_TaskDetails_Activity.this).deleteTask(taskBean);
                            startActivity(intent);


                            finish();
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();






        }

        if(id==R.id.markcomplete_task)

        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    RM_TaskDetails_Activity.this);

            // set title
            alertDialogBuilder.setTitle("Do you want to mark this task as complete?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to confirm!")
                    .setCancelable(false)
                    .setTitle("Mark as Complete the task")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            taskBean.setStatus(1);
                            RM_DbHelper.getInstance(RM_TaskDetails_Activity.this).updateTask(taskBean);
                            Toast.makeText(RM_TaskDetails_Activity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RM_TaskDetails_Activity.this, RM_Home_Activity.class);

                            Intent markCompleteForAlarm = new Intent(RM_TaskDetails_Activity.this, RM_AlarmReceiver.class);
                            PendingIntent pendingIntentMcAlarm = PendingIntent.getBroadcast(RM_TaskDetails_Activity.this, taskBean.getTask_id(), markCompleteForAlarm, 0);


                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(pendingIntentMcAlarm);

                            Intent markCompleteForNotification = new Intent(RM_TaskDetails_Activity.this, RM_NotificationReceiver.class);
                            PendingIntent pendingIntentMcNotification = PendingIntent.getBroadcast(getApplicationContext(), taskBean.getTask_id(), markCompleteForNotification, 0);


                            AlarmManager notManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            notManager.cancel(pendingIntentMcNotification);
                            // Toast.makeText(RM_NotificationService.this, "Notification Cancelled", Toast.LENGTH_SHORT).show();

                            startActivity(intent);

                            finish();

                            //finish();
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }

        if(id==R.id.edit_task)

        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    RM_TaskDetails_Activity.this);

            // set title
            alertDialogBuilder.setTitle("Do you want to edit this task?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to edit!")
                    .setCancelable(false)
                    .setTitle("Edit Task")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (taskBean.getTask_type() == 0) {
                                Bundle bundle = new Bundle();
                                taskBean.setStatus(2);
                                bundle.putSerializable("editTask", taskBean);
                                Intent intent = new Intent(RM_TaskDetails_Activity.this, RM_AddTaskByDate_Activity.class);
                                intent.putExtras(bundle);
                                Intent editAlarm = new Intent(RM_TaskDetails_Activity.this, RM_AlarmReceiver.class);
                                PendingIntent pendingIntentEditAlarm = PendingIntent.getBroadcast(RM_TaskDetails_Activity.this, taskBean.getTask_id(), editAlarm, 0);


                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.cancel(pendingIntentEditAlarm);

                                Intent intentEditNotification = new Intent(RM_TaskDetails_Activity.this, RM_NotificationReceiver.class);
                                PendingIntent pendingIntentEditNotification = PendingIntent.getBroadcast(getApplicationContext(), taskBean.getTask_id(), intentEditNotification, 0);


                                AlarmManager notManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                notManager.cancel(pendingIntentEditNotification);
                                startActivity(intent);

                            } else if (taskBean.getTask_type() == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("editTask", taskBean);
                                Intent intent = new Intent(RM_TaskDetails_Activity.this,RM_AddTaskByLocation_Activity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();




        }

        return super.onOptionsItemSelected(item);
    }
                        }
