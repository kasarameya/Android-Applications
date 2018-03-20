package com.tcs.remindmeapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;


import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.adapter.RM_ContactListAdapter;
import com.tcs.remindmeapplication.beans.RM_FriendBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

import java.util.ArrayList;

public class RM_ContactList_Activity extends AppCompatActivity {
    private ListView mLvContactList;
    private Toolbar mToolbar;
    private EditText mEtSearchView;
    private SearchView mSvSearchView;
    private ArrayList<RM_FriendBean> mFriendBeanList;
    private RM_ContactListAdapter adapter;
    private int displayPosition;
    private int share_flag = 0; // it you want to share this contact than its value will become 1
    private RM_FriendBean mRM_FriendBean;
    private int position_of_that_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_activity_layout);///made changes here
        mLvContactList = (ListView) findViewById(R.id.lv_contact_list_activity);
        mToolbar = (Toolbar) findViewById(R.id.tb_contact_list_activity);
        setSupportActionBar(mToolbar);
        mFriendBeanList = new ArrayList<RM_FriendBean>();
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if(RM_SetPassword_Activity.imagepath.equals("profileImage"))
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(), RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
        }
        else
        {
            mToolbar.setLogo(new BitmapDrawable(getResources(),RM_Login_Activity.getRoundedShapefortoolbar(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath))));
        }
        updateView();
        //getting intent from pending fregment to share the given task with contact
        Intent intent = getIntent();


        share_flag = intent.getIntExtra("FlagForShare", 0);
        position_of_that_task=intent.getIntExtra("PositionOfTask", 0);



        mLvContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                displayPosition = position;
                if (share_flag == 0) {

                    Intent intent = new Intent(RM_ContactList_Activity.this, RM_FriendDetail_Activity.class);
                    if(RM_ContactListAdapter.mFlag) {
                        mRM_FriendBean = RM_DbHelper.getInstance(RM_ContactList_Activity.this).selectAllFriends(RM_Login_Activity.user_id).get(position);

                    }
                    else if(RM_ContactListAdapter.mFlag ==false) {
                        mRM_FriendBean = adapter.getFriendBean(position);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CONTACTINFO", mRM_FriendBean);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 502);
                } else if (share_flag == 1) {
                    shareFriend();

                }
            }
        });

//
        mLvContactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (share_flag == 0) {
                    selectTaskOptions();

                }
                else if(share_flag==1){

                    Toast.makeText(RM_ContactList_Activity.this, "You can't long pressed it", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

//share friend with this method on long click
    private void shareFriend() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        String Description ="Description :-"+RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getTask_desc() ;
        String Date  ="Date of Task :-"+RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getDate()+"/"+
                RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getMonth()+"/"+
                RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getYear();

        String Time ="Time of Task :- "+ RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getHour()+":"
                +RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getMinute();

        String shareBody = Description +System.getProperty("line.separator")+ Date+System.getProperty("line.separator")+Time ;
        String subject_body = RM_DbHelper.getInstance(this).selectAllTask(0,RM_Login_Activity.user_id).get(position_of_that_task).getTask_title();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,subject_body);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        String contact_phone_number=RM_DbHelper.getInstance(this).selectAllFriends(RM_Login_Activity.user_id).get(displayPosition).getPhoneNumber();
        sharingIntent.putExtra("address", contact_phone_number);
        String contact_email=RM_DbHelper.getInstance(this).selectAllFriends(RM_Login_Activity.user_id).get(displayPosition).getEmail();
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact_email});
        startActivity(Intent.createChooser(sharingIntent, "Share via"));


    }

    private void selectTaskOptions() {
        final CharSequence[] items = { "Open TaskBook", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Task");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Open TaskBook")) {
                    Intent intent = new Intent(RM_ContactList_Activity.this,RM_Home_Activity.class);
                    intent.putExtra("FlagForShare",1);
                    intent.putExtra("PositionOfContact",displayPosition);
                    startActivity(intent);
                }else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__contact_list_, menu);

        MenuItem searchItem = menu.findItem(R.id.icon_contact_list_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        changeSearchViewTextColor(searchView);
        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.menu_search) + "</font>"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }
    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        else if (id == R.id.icon_contact_list_add) {
            Intent intent = new Intent(this, RM_AddContact_Activity.class);
            startActivityForResult(intent, 500);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == 501) {
            updateView();

        } else if (requestCode == 502 && resultCode == 503) {
            updateView();

        }
    }

    private void updateView() {
        adapter = new RM_ContactListAdapter(this, RM_DbHelper.getInstance(this).selectAllFriends(RM_Login_Activity.user_id));
        mLvContactList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }





}
