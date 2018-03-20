package com.tcs.remindmeapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.services.MyServices;
import com.tcs.remindmeapplication.services.RM_AutoCompleteObject;
import com.tcs.remindmeapplication.services.RM_GonParserBeanObject;
import com.tcs.remindmeapplication.services.RM_HttpManager;
import com.tcs.remindmeapplication.services.RM_JsonHelper;

import java.util.ArrayList;

public class RM_AddTaskByLocation_Activity extends AppCompatActivity {
    private AutoCompleteTextView mPlaceName;

    private ArrayAdapter mAdapter;
    private ArrayList<String> mStrings=new ArrayList<>();
    private String mUriForJsonAutoComplete,mUriForJsonGeoCoder;
    RM_AutoCompleteObject object3;
    RM_GonParserBeanObject objects;
    private Button mBtnSave;
    private EditText mEtTitle,mEtDescription;
    private RM_TaskBean rm_taskBean = null;
    private String latitude, longitude,placeName;
    private String taskTitle,taskDescription;
    private RM_TaskBean mTaskBean2;
    private Boolean isEditable = false;
    private Toolbar mToolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm__add_task_by_location_activity);

        initialiseVariables();



        /*mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlaceName.getText().toString() == "") {
                    Toast.makeText(RM_AddTaskByLocation_Activity.this, "Select Destination", Toast.LENGTH_SHORT).show();

                } else {
                    goForGoogleData(mPlaceName.getText().toString());

                }


            }
        });*/
        mPlaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                getAutoComplete(mPlaceName.getText().toString());

            }
        });



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)

        {
            mTaskBean2= (RM_TaskBean) bundle.getSerializable("editTask");
            isEditable=true;
            if(mTaskBean2 !=null)
            {
                mEtTitle.setText(mTaskBean2.getTask_title());
                mEtDescription.setText(mTaskBean2.getTask_desc());


            }
        }




    }

    private void initialiseVariables() {
        mPlaceName=(AutoCompleteTextView)findViewById(R.id.actv_addTaskByLocation_place);
        mAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mStrings);
        mPlaceName.setAdapter(mAdapter);
        mPlaceName.setThreshold(1);
        mEtTitle = (EditText) findViewById(R.id.et_addTaskByLocation_title);
        mEtDescription = (EditText) findViewById(R.id.et_addTaskByLocation_description);
        mToolbar= (Toolbar) findViewById(R.id.add_location_activity_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public String[] getAutoComplete(String placeget)
    {

        mStrings=new ArrayList<>();
        mUriForJsonAutoComplete="https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+placeget+"&types=geocode&key=AIzaSyA3vVA7B6WBmQYNobtZNz3dwxwLGKUahsc";
        RM_HttpManager httpManager=new RM_HttpManager(new RM_HttpManager.ServiceResponds() {
            @Override
            public void onServiseResponce(String responce)
            {

                object3= (RM_AutoCompleteObject) RM_JsonHelper.Deserialise(responce, RM_AutoCompleteObject.class);
                for(int i=0;i<object3.getPredictions().size();i++)
                {
                    mStrings.add(object3.getPredictions().get(i).getDescription());
                }

                update();

            }
        });
        httpManager.execute(mUriForJsonAutoComplete);



        return null;
    }

    private void update() {
        mAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mStrings);
        mPlaceName.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__add_task_by_location_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (mPlaceName.getText().toString() == "") {
                Toast.makeText(RM_AddTaskByLocation_Activity.this, "Select Destination", Toast.LENGTH_SHORT).show();

            } else {
                goForGoogleData(mPlaceName.getText().toString());

            }
            return true;
        }
         if(id == android.R.id.home)
         {
             finish();
             return true;
         }
        return super.onOptionsItemSelected(item);
    }
    private void goForGoogleData(String place) {
        place=place.replaceAll(" ","%20");

        mUriForJsonGeoCoder = "https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=AIzaSyA3vVA7B6WBmQYNobtZNz3dwxwLGKUahsc";

        RM_HttpManager httpManager = new RM_HttpManager(new RM_HttpManager.ServiceResponds() {
            @Override
            public void onServiseResponce(String responce) {

                objects = (RM_GonParserBeanObject) RM_JsonHelper.Deserialise(responce, RM_GonParserBeanObject.class);
                latitude = objects.getResults().get(0).getGeometry().getLocations().getLat();
                longitude = objects.getResults().get(0).getGeometry().getLocations().getLng();

                placeName = mPlaceName.getText().toString();
                Toast.makeText(RM_AddTaskByLocation_Activity.this,placeName, Toast.LENGTH_SHORT).show();

                saveDetails();

            }
        });
        httpManager.execute(mUriForJsonGeoCoder);
    }

    private void saveDetails() {

        if(isEditable==false) {

            taskDescription = mEtDescription.getText().toString();
            taskTitle = mEtTitle.getText().toString();
            rm_taskBean = new RM_TaskBean();
            rm_taskBean.setTask_desc(taskDescription);
            rm_taskBean.setTask_title(taskTitle);
            //rm_taskBean.setHour(Integer.parseInt(latitude));
            rm_taskBean.setLatitude(Double.parseDouble(latitude));
            rm_taskBean.setLongitude(Double.parseDouble(longitude));
            rm_taskBean.setRingtone(placeName);
            rm_taskBean.setTask_type(1);
            rm_taskBean.setUser_id(RM_Login_Activity.user_id);
            RM_DbHelper.getInstance(RM_AddTaskByLocation_Activity.this).addTask(rm_taskBean);
            Toast.makeText(RM_AddTaskByLocation_Activity.this, "Task Added", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(RM_AddTaskByLocation_Activity.this, RM_Home_Activity.class);
            startActivity(intent1);
        }
        else if(isEditable == true){
            taskDescription = mEtDescription.getText().toString();
            taskTitle = mEtTitle.getText().toString();
            rm_taskBean = mTaskBean2;
            rm_taskBean.setTask_desc(taskDescription);
            rm_taskBean.setTask_title(taskTitle);
            rm_taskBean.setLatitude(Double.parseDouble(latitude));
            rm_taskBean.setLongitude(Double.parseDouble(longitude));
            rm_taskBean.setRingtone(placeName);
            rm_taskBean.setTask_type(1);
            rm_taskBean.setStatus(0);
            rm_taskBean.setUser_id(RM_Login_Activity.user_id);
            RM_DbHelper.getInstance(RM_AddTaskByLocation_Activity.this).addTask(rm_taskBean);
            Toast.makeText(RM_AddTaskByLocation_Activity.this, "Task Updated", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(RM_AddTaskByLocation_Activity.this, RM_Home_Activity.class);
            startActivity(intent1);

        }

    }
}
