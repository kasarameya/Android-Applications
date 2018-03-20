package com.tcs.remindmeapplication.activities;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.services.RM_AlarmReceiver;

import java.io.IOException;

public class RM_Alarm_Activity extends AppCompatActivity {
    ImageView mAlarmImage;
    TextView mTaskName;
    TextView mTaskDescription;
    Button mStopTask;
    RM_TaskBean alarmBean1;
    MediaPlayer mediaPlayer;
    Vibrator vibrator;
    TextView mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_alarm_activity);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        findViewById(R.id.iv_alarm).startAnimation(shake);
        /*mAlarmImage = (ImageView) findViewById(R.id.iv_alarm);
        RotateAnimation anim = new RotateAnimation(0, 30, 0, 0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1500);
        anim.setFillAfter(true);
        mAlarmImage.startAnimation(anim);
*/
        mTaskName = (TextView) findViewById(R.id.tv_alarm_taskName);
        mTaskDescription = (TextView) findViewById(R.id.tv_alarm_taskDescription);
        mStopTask = (Button) findViewById(R.id.button_alarm_stopTask);



        final Bundle bundle = getIntent().getExtras();
        alarmBean1 = (RM_TaskBean) bundle.getSerializable("alarmBean");
        Log.d("updated for Id", String.valueOf(alarmBean1.getTask_id()));
        if (alarmBean1 != null) {

            mTaskName.setText(alarmBean1.getTask_title());
            mTaskDescription.setText(alarmBean1.getTask_desc());


            Uri uri = Uri.parse(alarmBean1.getRingtone());
            KeyguardManager km = (KeyguardManager) RM_Alarm_Activity.this.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
            kl.disableKeyguard();

            PowerManager pm = (PowerManager) RM_Alarm_Activity.this
                    .getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
            wakeLock.acquire();

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            mediaPlayer.setLooping(true);

            if (alarmBean1.getVibrate() == 1) {
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(10 * 1000);
            }



                mStopTask.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        try {

                        wakeLock.release();
                        if (mediaPlayer != null || mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();



                            mediaPlayer.reset();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (alarmBean1.getVibrate() == 1) {
                            vibrator.cancel();
                        }
                        }catch (Exception e) {
                            Log.d("Null Pointer","exception");
                        }

                        alarmBean1.setStatus(1);
                        Log.i("status", String.valueOf(alarmBean1.getStatus()));
                        RM_DbHelper.getInstance(RM_Alarm_Activity.this).updateTask(alarmBean1);
                        //Toast.makeText(RM_Alarm_Activity.this, String.valueOf(alarmBean1.getStatus())+"hello", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(RM_Alarm_Activity.this, RM_AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(RM_Alarm_Activity.this, alarmBean1.getTask_id(), intent, 0);


                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);

                        Intent intent1 = new Intent("SYNC_UPDATE");
                        intent1.putExtra("FILE_PATH","inside filepath");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);

                        /*Intent intent1 = new Intent(RM_Alarm_Activity.this, RM_SplashScreen_Activity.class);
                        startActivity(intent1);*/

                        finish();
                    }
                });
            }
        }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
