package com.tcs.remindmeapplication.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_LoginBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.utilities.RM_AppConstants;

import java.util.Calendar;
import java.util.List;

public class RM_Reset_Activity extends AppCompatActivity {


    private EditText et_Reset_mEmail;
    private EditText et_Reset_mNewpassword;
    private EditText et_Reset_mConfirmpassword;
    private Button btn_Reset_mResetpassword;
    private List<RM_LoginBean> loginBeanList;
    private TextView tv_mTextview;
    private RM_LoginBean rm_loginBean;
    private Toolbar mToolbar;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm__reset_);
        et_Reset_mEmail= (EditText) findViewById(R.id.et_Reset_email);
        et_Reset_mNewpassword= (EditText) findViewById(R.id.et_Reset_newpassword);
        mToolbar= (Toolbar) findViewById(R.id.reset_activity_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_Reset_mConfirmpassword= (EditText) findViewById(R.id.et_Reset_confirmpassword);
        tv_mTextview= (TextView) findViewById(R.id.tv_Reset_message);
        btn_Reset_mResetpassword= (Button) findViewById(R.id.btn_Reset_resetpassword);
        if(RM_SetPassword_Activity.imagepath.equals("profileImage"))
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(), RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
        }
        else
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(),RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath))));
        }
        btn_Reset_mResetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RM_AppConstants.ResetConstants.FLAG = 0;
                tv_mTextview.setVisibility(View.INVISIBLE);
                if (!et_Reset_mNewpassword.getText().toString().equals(et_Reset_mConfirmpassword.getText().toString())) {
                    et_Reset_mConfirmpassword.setError("confirm password has not matched");
                    RM_AppConstants.ResetConstants.FLAG = 1;
                }
                if (et_Reset_mEmail.getText().toString().equalsIgnoreCase("")) {
                    et_Reset_mEmail.setError("Email can not be null");
                    RM_AppConstants.ResetConstants.FLAG = 1;
                }
                if (et_Reset_mNewpassword.getText().toString().equalsIgnoreCase("")) {
                    et_Reset_mNewpassword.setError("Password field can not be empty");
                    RM_AppConstants.ResetConstants.FLAG = 3;
                }
                if (!(et_Reset_mNewpassword.getText().toString().length() >= 8 && et_Reset_mNewpassword.getText().toString().length() <= 15)) {
                    if (RM_AppConstants.ResetConstants.FLAG == 3) {
                        RM_AppConstants.ResetConstants.FLAG = 1;
                    } else {
                        et_Reset_mNewpassword.setError("Password length should be 8 to 15 digits");
                        RM_AppConstants.ResetConstants.FLAG = 1;
                    }
                }
                if (RM_AppConstants.ResetConstants.FLAG == 0) {
                    loginBeanList = RM_DbHelper.getInstance(RM_Reset_Activity.this).selectAllUser();
                    for (int i = 0; i < loginBeanList.size(); i++) {
                        if (et_Reset_mEmail.getText().toString().equals(loginBeanList.get(i).getEmail())) {
                            rm_loginBean = loginBeanList.get(i);
                            RM_AppConstants.ResetConstants.FLAG = 0;
                            break;
                        }
                        RM_AppConstants.ResetConstants.FLAG = 2;
                    }
                }
                if (RM_AppConstants.ResetConstants.FLAG == 2) {
                    tv_mTextview.setVisibility(View.VISIBLE);
                    tv_mTextview.setText("Email is invaild");
                }
                if (RM_AppConstants.ResetConstants.FLAG == 0) {
                    rm_loginBean.setPassword(et_Reset_mNewpassword.getText().toString());
                    RM_DbHelper.getInstance(RM_Reset_Activity.this).updateUser(rm_loginBean);
                    Intent i = new Intent(RM_Reset_Activity.this, RM_Login_Activity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(RM_Reset_Activity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__reset_, menu);
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
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
