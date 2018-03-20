package com.tcs.remindmeapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.fragments.RM_Main_Fragment;
import com.tcs.remindmeapplication.fragments.RM_Navigation_Fragment;

public class RM_Home_Activity extends AppCompatActivity {

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    RelativeLayout viewGroup;
    Point p;
    Button location,date;
    PopupWindow popup;
    private static int share_flag_from_Contact_list_activity =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_home_activity_layout);

        mToolbar= (Toolbar) findViewById(R.id.includeTb);
        setSupportActionBar(mToolbar);
        ImageButton btn_show = (ImageButton) findViewById(R.id.show_popup);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Open popup window
                if (p != null) {
                    showPopup(RM_Home_Activity.this, p);

                }

            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    try {


        if (RM_SetPassword_Activity.imagepath.equals("profileImage")) {
            mToolbar.setLogo(new BitmapDrawable(getResources(), RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
        } else {
            mToolbar.setLogo(new BitmapDrawable(getResources(), RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath))));
        }
    }catch (Exception e)
    {
        Toast.makeText(RM_Home_Activity.this, "Null Pointer exception", Toast.LENGTH_SHORT).show();
    }

        mDrawerLayout= (DrawerLayout) findViewById(R.id.DrawerLId);
        mActionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.openddrawer,R.string.opendrawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        Intent intent = getIntent();
        share_flag_from_Contact_list_activity = intent.getIntExtra("FlagForShare",0);

        showMainFrag();
        showNavFrag();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        ImageButton button = (ImageButton) findViewById(R.id.show_popup);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    // The method that displays the popup.
    private void showPopup(final Activity context, Point p) {
        int popupWidth = 320;
        int popupHeight = 400;

        // Inflate the popup_layout.xml
        viewGroup = (RelativeLayout) context.findViewById(R.id.popup);
        //viewGroup.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popupwindow, viewGroup);

        // Creating the PopupWindow
        popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 100;
        int OFFSET_Y = 100;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new ColorDrawable());
        // popup.setAnimationStyle(R.style.Animation);
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.START, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        location = (Button) layout.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
                Intent intent=new Intent(RM_Home_Activity.this,RM_AddTaskByLocation_Activity.class);
                startActivity(intent);
            }

        });
        date = (Button) layout.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
                Intent intent = new Intent(RM_Home_Activity.this, RM_AddTaskByDate_Activity.class);
                startActivity(intent);
            }
        });

    }
    private void showNavFrag() {
        RM_Navigation_Fragment RMNavigationfragment =new RM_Navigation_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag_container, RMNavigationfragment).commit();
    }

    private void showMainFrag() {

        RM_Main_Fragment RMMainFragment =new RM_Main_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frag_container, RMMainFragment).commit();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
       mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void closeDrawer()
    {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onBackPressed() {

        if(share_flag_from_Contact_list_activity==0){
            Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       }else{
            super.onBackPressed();
        }



    super.onBackPressed();
    }
}
