package com.tcs.remindmeapplication.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.services.RM_AlarmReceiver;
import com.tcs.remindmeapplication.services.RM_NotificationReceiver;

import java.util.Calendar;

public class RM_AddTaskByDate_Activity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mEtTaskName;
    private EditText mEtTaskDescription;
    private TextView mEtTaskDate;
    private TextView mEtTaskTime;
    private TextView mEtToneName;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mtimePickerDialog;
    private CheckBox mEtVibrate;
    private String s_path,s_path2;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mHour;
    private int min;
    private String mAudioPath;
    private TextView mTaskRingtone;
    private CheckBox mEtNotify;
    private RM_TaskBean mTaskBean = new RM_TaskBean();
    private int mNotifyMe=0;
    private Boolean isEditable=false,flagSound=false;
    private RM_TaskBean mTaskBean2 = new RM_TaskBean();
    private RM_TaskBean mTaskBean3 = new RM_TaskBean();
    private int errorFlag;
    private int vibrateFlag=0;
    private int dateFlag=0;
    private int timeFlag=0;
    private int addFlag=0;
    private int maxEdit;

    private Calendar initialCalendar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_addtaskbydate_layout);

        initialCalendar = Calendar.getInstance();
        mCurrentYear = initialCalendar.get(Calendar.YEAR);
        mCurrentMonth = initialCalendar.get(Calendar.MONTH);
        mCurrentDay = initialCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = initialCalendar.get(Calendar.HOUR_OF_DAY);
        min = initialCalendar.get(Calendar.MINUTE);
        mToolbar= (Toolbar) findViewById(R.id.toolbar_addTaskByDate);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEtTaskName= (EditText) findViewById(R.id.et_addTaskbyDate_task_name);
        mEtTaskDescription= (EditText) findViewById(R.id.et_addTaskbyDate_task_description);
        mEtTaskDate= (TextView) findViewById(R.id.tv_addTaskbyDate_date);
        mEtTaskTime= (TextView) findViewById(R.id.tv_addTaskbyDate_time);
        mTaskRingtone= (TextView) findViewById(R.id.tv_addTaskbyDate_ringtone);
        mEtNotify= (CheckBox) findViewById(R.id.cb_addTaskbyDate_notifyme);
        mEtVibrate= (CheckBox) findViewById(R.id.cb_addTaskbyDate_vibration);

        mEtTaskDate.setText(mCurrentDay + " /" + (mCurrentMonth + 1) + " / " + mCurrentYear);

        mTaskBean=new RM_TaskBean();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)

        {
            mTaskBean2= (RM_TaskBean) bundle.getSerializable("editTask");
            isEditable=true;
            if(mTaskBean2 !=null)
            {
                mEtTaskName.setText(mTaskBean2.getTask_title());
                mEtTaskDescription.setText(mTaskBean2.getTask_desc());
                mEtTaskDate.setText(String.valueOf(mTaskBean2.getDate())+"/"+String.valueOf(mTaskBean2.getMonth()+1)+"/"+String.valueOf(mTaskBean2.getYear()));
                mEtTaskTime.setText(String.valueOf(mTaskBean2.getHour())+":"+String.valueOf(mTaskBean2.getMinute()));
                mTaskRingtone.setText(mTaskBean2.getRingtone());
                if(mTaskBean2.getNotify() == 1)
                {
                    mEtNotify.setChecked(true);
                    mNotifyMe=1;
                }
                if(mTaskBean2.getVibrate() == 1)
                {
                    mEtVibrate.setChecked(true);
                    vibrateFlag=1;
                }
            }
        }


        mEtTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener dateSetListener;
                dateSetListener=new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // if(dayOfMonth == mCurrentDay+1) {

                        mCurrentYear = year;
                        mCurrentMonth = monthOfYear;
                        mCurrentDay = dayOfMonth;
                        mEtTaskDate.setText(mCurrentDay + " /" + (mCurrentMonth + 1) + " / " + mCurrentYear);
                        if(isEditable)
                        {
                            dateFlag=1;
                        }
                        //}
                        //else {
                        // Toast.makeText(RM_AddTaskByDate_Activity.this, "Only 1 day ahead selection allowed", Toast.LENGTH_SHORT).show();
                        //}
                    }

                };
                mDatePickerDialog=new DatePickerDialog(RM_AddTaskByDate_Activity.this,dateSetListener,mCurrentYear,mCurrentMonth,mCurrentDay);
                mDatePickerDialog.setTitle("Select Reminder Date");
                mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePickerDialog.show();


            }
        });


        mEtTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog.OnTimeSetListener onTimeSetListener;
                onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int mHourOfDay, int minute) {

                        mHour = mHourOfDay;
                        min = minute;
                        if(mHour >= initialCalendar.get(Calendar.HOUR_OF_DAY) )

                        {
                            mEtTaskTime.setText(mHour + " : " + min);
                            if(isEditable)
                            {
                                timeFlag=1;
                            }

                        }
                        else {
                            Toast.makeText(RM_AddTaskByDate_Activity.this, "Invalid Time", Toast.LENGTH_SHORT).show();

                        }


                    }
                };
                mtimePickerDialog=new TimePickerDialog(RM_AddTaskByDate_Activity.this,onTimeSetListener,mHour,min,false);
                mtimePickerDialog.setTitle("Select Reminder Time");

                mEtTaskTime.setError(null);
                mtimePickerDialog.show();
            }
        });

        mTaskRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("audio*//*");
                mTaskRingtone.setError(null);
                startActivityForResult(intent, 1);*/

                final Uri currentTone= RingtoneManager.getActualDefaultRingtoneUri(RM_AddTaskByDate_Activity.this, RingtoneManager.TYPE_ALARM);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);

                startActivityForResult(intent, 1);


            }


        });
        mEtNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mNotifyMe = 1;
                    Toast.makeText(RM_AddTaskByDate_Activity.this, "You will notified 1 hour prior to your selected time", Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    mNotifyMe = 0;
                    Toast.makeText(RM_AddTaskByDate_Activity.this, "Notification cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEtVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    vibrateFlag=1;
                    Toast.makeText(RM_AddTaskByDate_Activity.this, "Vibration Enabled", Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked){
                    vibrateFlag=0;
                    Toast.makeText(RM_AddTaskByDate_Activity.this, "Vibration Disabled", Toast.LENGTH_SHORT).show();
                }

            }
        });

//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm_add_task_by_date_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if(id == R.id.save_task) {
            errorFlag=0;
            if (mEtTaskName.getText().toString().equalsIgnoreCase("")) {
                mEtTaskName.setError("Enter the task name");
                errorFlag = 1;
            }

            if (mEtTaskTime.getText().toString().equalsIgnoreCase("Time")) {
                mEtTaskTime.setError("Enter the reminder time");
                errorFlag = 1;
                Toast.makeText(RM_AddTaskByDate_Activity.this, "Enter the reminder time", Toast.LENGTH_SHORT).show();
            }
            if (mTaskRingtone.getText().toString().equalsIgnoreCase("Ringtone")) {
                mTaskRingtone.setError("Please select a ringtone");
                errorFlag = 1;
                Toast.makeText(RM_AddTaskByDate_Activity.this, "Please select a ringtone", Toast.LENGTH_SHORT).show();
            }
            if (errorFlag == 0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        RM_AddTaskByDate_Activity.this);

                // set title
                alertDialogBuilder.setTitle("Do you want to save this task?");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to confirm!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                if (isEditable == false) {
                                    mTaskBean.setTask_title(mEtTaskName.getText().toString());
                                    mTaskBean.setTask_desc(mEtTaskDescription.getText().toString());
                                    mTaskBean.setDate(mCurrentDay);
                                    mTaskBean.setMonth(mCurrentMonth);
                                    mTaskBean.setYear(mCurrentYear);
                                    mTaskBean.setMinute(min);
                                    mTaskBean.setHour(mHour);
                                    mTaskBean.setRingtone(s_path);
                                    mTaskBean.setNotify(mNotifyMe);
                                    mTaskBean.setStatus(0);
                                    mTaskBean.setVibrate(vibrateFlag);
                                    mTaskBean.setUser_id(RM_Login_Activity.user_id);


                                    int max=RM_DbHelper.getInstance(RM_AddTaskByDate_Activity.this).getMaxId();
                                    int newMax=max+1;
                                    RM_DbHelper.getInstance(RM_AddTaskByDate_Activity.this).addTask(mTaskBean);
                                    Toast.makeText(RM_AddTaskByDate_Activity.this, "Task Added", Toast.LENGTH_SHORT).show();

                                    // Log.d("Maxium Id",String.valueOf(max));

                                    if(mTaskBean.getNotify() == 1)
                                    {
                                        Calendar notificationCalendar = Calendar.getInstance();
                                        notificationCalendar.setTimeInMillis(System.currentTimeMillis());
                                        notificationCalendar.set(Calendar.MONTH, mTaskBean.getMonth());
                                        notificationCalendar.set(Calendar.YEAR, mTaskBean.getYear());
                                        notificationCalendar.set(Calendar.DAY_OF_MONTH, mTaskBean.getDate());



                                        notificationCalendar.set(Calendar.HOUR_OF_DAY,  mTaskBean.getHour());
                                        notificationCalendar.set(Calendar.MINUTE, mTaskBean.getMinute());
                                        notificationCalendar.set(Calendar.SECOND, 0);
                                        notificationCalendar.set(Calendar.MILLISECOND,0);

                                        //max++;

                                        AlarmManager notificationAlarmManager = (AlarmManager) RM_AddTaskByDate_Activity.this.getSystemService(Context.ALARM_SERVICE);




                                        Intent notifcationIntent = new Intent(RM_AddTaskByDate_Activity.this, RM_NotificationReceiver.class);
                                        Bundle notificationBundle = new Bundle();
                                        notificationBundle.putSerializable("alarmBean", mTaskBean);
                                        notifcationIntent.putExtras(notificationBundle);

                                        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(RM_AddTaskByDate_Activity.this, newMax, notifcationIntent, 0);
                                        notificationAlarmManager.set(AlarmManager.RTC_WAKEUP, notificationCalendar.getTimeInMillis() - 3600000, notificationPendingIntent);

                                    }


                                    Intent intent1 = new Intent(RM_AddTaskByDate_Activity.this, RM_Home_Activity.class);


                                    startActivity(intent1);
                                    finish();
                                } else {


                                    mTaskBean3.setTask_title(mEtTaskName.getText().toString());
                                    mTaskBean3.setTask_desc(mEtTaskDescription.getText().toString());
                                    if(dateFlag == 1) {
                                        mTaskBean3.setDate(mCurrentDay);
                                        mTaskBean3.setMonth(mCurrentMonth);
                                        mTaskBean3.setYear(mCurrentYear);
                                    }
                                    else if(dateFlag == 0)
                                    {
                                        mTaskBean3.setDate(mTaskBean2.getDate());
                                        mTaskBean3.setMonth(mTaskBean2.getMonth());
                                        mTaskBean3.setYear(mTaskBean2.getYear());
                                    }

                                    if(timeFlag == 1) {
                                        mTaskBean3.setMinute(min);
                                        mTaskBean3.setHour(mHour);
                                    }
                                    else if(timeFlag == 0)
                                    {
                                        mTaskBean3.setMinute(mTaskBean2.getMinute());
                                        mTaskBean3.setHour(mTaskBean2.getHour());
                                    }
                                    if (flagSound) {
                                        mTaskBean3.setRingtone(s_path2);
                                    } else {
                                        mTaskBean3.setRingtone(mTaskBean2.getRingtone());
                                    }
                                    mTaskBean3.setNotify(mNotifyMe);
                                    mTaskBean3.setStatus(0);
                                    mTaskBean3.setVibrate(vibrateFlag);

                                    if(mTaskBean3.getNotify() == 1)
                                    {
                                        maxEdit=RM_DbHelper.getInstance(RM_AddTaskByDate_Activity.this).getMaxId();
                                        int newMaxEdit=maxEdit+1;
                                        Calendar calendarNotificationEdited = Calendar.getInstance();
                                        calendarNotificationEdited.setTimeInMillis(System.currentTimeMillis());
                                        calendarNotificationEdited.set(Calendar.MONTH, mTaskBean3.getMonth());
                                        calendarNotificationEdited.set(Calendar.YEAR, mTaskBean3.getYear());
                                        calendarNotificationEdited.set(Calendar.DAY_OF_MONTH, mTaskBean3.getDate());



                                        calendarNotificationEdited.set(Calendar.HOUR_OF_DAY,  mTaskBean3.getHour());
                                        calendarNotificationEdited.set(Calendar.MINUTE, mTaskBean3.getMinute());
                                        calendarNotificationEdited.set(Calendar.SECOND, 0);
                                        calendarNotificationEdited.set(Calendar.MILLISECOND,0);

                                        //max++;

                                        AlarmManager notificationAlarmManagerForEdited = (AlarmManager) RM_AddTaskByDate_Activity.this.getSystemService(Context.ALARM_SERVICE);




                                        Intent notifcationIntentForEdited = new Intent(RM_AddTaskByDate_Activity.this, RM_NotificationReceiver.class);
                                        Bundle notificationBundleForEdited = new Bundle();
                                        notificationBundleForEdited.putSerializable("alarmBean", mTaskBean3);
                                        notifcationIntentForEdited.putExtras(notificationBundleForEdited);

                                        PendingIntent notificationPendingIntentForEdited = PendingIntent.getBroadcast(RM_AddTaskByDate_Activity.this,newMaxEdit , notifcationIntentForEdited, 0);
                                        notificationAlarmManagerForEdited.set(AlarmManager.RTC_WAKEUP, calendarNotificationEdited.getTimeInMillis() - 3600000, notificationPendingIntentForEdited);



                                    }



                                    //int newMaxEdit=maxEdit+1;
                                    mTaskBean3.setUser_id(RM_Login_Activity.user_id);

                                    RM_DbHelper.getInstance(RM_AddTaskByDate_Activity.this).deleteTask(mTaskBean2);
                                    RM_DbHelper.getInstance(RM_AddTaskByDate_Activity.this).addTask(mTaskBean3);

                                    Toast.makeText(RM_AddTaskByDate_Activity.this, "Task Updated", Toast.LENGTH_SHORT).show();







                                    Intent intent1 = new Intent(RM_AddTaskByDate_Activity.this, RM_Home_Activity.class);



                                    startActivity(intent1);
                                    finish();

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
        }


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && resultCode == RESULT_OK) {
            // Get the Image from data

           /* Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Audio.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           if(isEditable) {
                s_path2 = cursor.getString(columnIndex);
                flagSound=true;
               // Toast.makeText(RM_AddTaskByDate_Activity.this, s_path2, Toast.LENGTH_SHORT).show();
               mTaskRingtone.setText(s_path2);
            }
            else {
                s_path=cursor.getString(columnIndex);
               mTaskRingtone.setText(s_path);
               mTaskRingtone.setError(null);*/
            // Toast.makeText(RM_AddTaskByDate_Activity.this, s_path, Toast.LENGTH_SHORT).show();
   /*     }
            cursor.close();




        }*/ /*else if (s_path == null) {
                Toast.makeText(this, "You haven't picked Audio", Toast.LENGTH_LONG).show();
            }
        else if (imgDecodableString == null) {
                Toast.makeText(AddPatient.this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }*/
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);


            if (uri != null) {

                if(isEditable) {
                    s_path2 = uri.toString();
                    flagSound=true;
                    // Toast.makeText(RM_AddTaskByDate_Activity.this, s_path2, Toast.LENGTH_SHORT).show();
                    mTaskRingtone.setText(s_path2);
                }


                else {
                    s_path = uri.toString();
                    mTaskRingtone.setText(s_path);
                    mTaskRingtone.setError(null);
                }
            }



        }
    }
}
