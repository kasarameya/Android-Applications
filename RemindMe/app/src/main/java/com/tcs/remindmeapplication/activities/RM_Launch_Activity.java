package com.tcs.remindmeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tcs.remindmeapplication.R;

public class RM_Launch_Activity extends AppCompatActivity {
    private Button mButton_registration;
    private Button mButton_login;
    private Button mButton_resetPassword;
    private Button mButton_deleteAccount;
    public static int status=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_launch_activity);
        mButton_registration = (Button)findViewById(R.id.rm_registration_buton);
        mButton_login = (Button)findViewById(R.id.rm_login_button);
        mButton_resetPassword = (Button)findViewById(R.id.rm_reset_password_button);
        mButton_deleteAccount = (Button)findViewById(R.id.rm_delete_account_button);
        mButton_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=1;
                Intent i=new Intent(RM_Launch_Activity.this,RM_SetPassword_Activity.class);
                startActivity(i);
            }
        });
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RM_Launch_Activity.this,RM_Login_Activity.class);
                startActivity(i);

            }
        });
        mButton_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RM_Launch_Activity.this,RM_Reset_Activity.class);
                startActivity(i);

            }
        });
        mButton_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RM_Launch_Activity.this, RM_DeleteAccount_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm_launch_activity, menu);
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
