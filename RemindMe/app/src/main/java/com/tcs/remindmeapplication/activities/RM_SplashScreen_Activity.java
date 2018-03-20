package com.tcs.remindmeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.session.RM_SessionCreator;

public class RM_SplashScreen_Activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    RM_SessionCreator sessionCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity_layout);
        sessionCreator=new RM_SessionCreator(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash_screen with a timer. This will be useful when you
             * want to show case your app logoo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity\

                Intent i = new Intent(RM_SplashScreen_Activity.this, RM_SetPassword_Activity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
