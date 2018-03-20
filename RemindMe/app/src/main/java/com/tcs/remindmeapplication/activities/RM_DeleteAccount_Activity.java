package com.tcs.remindmeapplication.activities;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_LoginBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.utilities.RM_AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RM_DeleteAccount_Activity extends AppCompatActivity {
    private EditText et_DeleteAccount_mUserid;
    private EditText et_DeleteAccount_mPassword;
    private Button btn_DeleteAccount_mdeleteaccount;
    private List<RM_LoginBean> loginBeanList;
    private TextView tv_mTextview;
    private RM_LoginBean rm_loginBean;
    Calendar myCalendar;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rm__delete_account_);
        et_DeleteAccount_mUserid= (EditText) findViewById(R.id.et_DeleteAccount_userid);
        et_DeleteAccount_mPassword= (EditText) findViewById(R.id.et_DeleteAccount_password);
        mToolbar= (Toolbar) findViewById(R.id.delete_activity_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginBeanList= RM_DbHelper.getInstance(RM_DeleteAccount_Activity.this).selectAllUser();
        tv_mTextview= (TextView) findViewById(R.id.tv_DeleteAccount_message);
        if(RM_SetPassword_Activity.imagepath.equals("profileImage"))
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(), RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
        }
        else
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(),RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath))));
        }
        btn_DeleteAccount_mdeleteaccount= (Button) findViewById(R.id.btn_DeleteAccount_deleteAccount);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTime();
            }

        };

        btn_DeleteAccount_mdeleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RM_AppConstants.DeleteAccountConstants.FLAG=0;
                tv_mTextview.setVisibility(View.INVISIBLE);
                if(et_DeleteAccount_mUserid.getText().toString().equalsIgnoreCase(""))
                {
                    et_DeleteAccount_mUserid.setError("Userid Field can not null");
                    RM_AppConstants.DeleteAccountConstants.FLAG=1;
                }
                if(et_DeleteAccount_mPassword.getText().toString().equalsIgnoreCase(""))
                {
                    et_DeleteAccount_mPassword.setError("Password Field can not null");
                    RM_AppConstants.DeleteAccountConstants.FLAG=1;
                }
//                if(et_DeleteAccount_mDOB.getText().toString().equalsIgnoreCase(""))
//                {
//                    et_DeleteAccount_mDOB.setError("Date of birth Field can not null");
//                    RM_AppConstants.DeleteAccountConstants.FLAG=1;
//                }
                if(RM_AppConstants.DeleteAccountConstants.FLAG==0) {
                    for (int i = 0; i < loginBeanList.size(); i++) {
                        rm_loginBean = loginBeanList.get(i);
                        if (et_DeleteAccount_mUserid.getText().toString().equals(loginBeanList.get(i).getUsername())
                                && et_DeleteAccount_mPassword.getText().toString().equals(loginBeanList.get(i).getPassword())
                                ) {
                            RM_AppConstants.DeleteAccountConstants.FLAG = 0;
                            break;
                        }
                        RM_AppConstants.DeleteAccountConstants.FLAG = 2;
                    }
                }
                if(RM_AppConstants.DeleteAccountConstants.FLAG==2)
                {
                    tv_mTextview.setVisibility(View.VISIBLE);
                    tv_mTextview.setText("Userid Or password invaild");
                }
                if(RM_AppConstants.DeleteAccountConstants.FLAG==0)
                {
                    RM_DbHelper.getInstance(RM_DeleteAccount_Activity.this).deleteUser(rm_loginBean);
                    Intent i=new Intent(RM_DeleteAccount_Activity.this,RM_Login_Activity.class);
                    startActivity(i);
                    finish();
                    RM_SetPassword_Activity.imagepath="profileImage";
                    Toast.makeText(RM_DeleteAccount_Activity.this, "Account delete successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTime() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
      //  et_DeleteAccount_mDOB.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__delete_account_, menu);
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
