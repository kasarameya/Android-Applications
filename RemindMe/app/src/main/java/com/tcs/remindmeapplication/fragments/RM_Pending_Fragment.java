package com.tcs.remindmeapplication.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.activities.RM_ContactList_Activity;
import com.tcs.remindmeapplication.activities.RM_FriendDetail_Activity;
import com.tcs.remindmeapplication.activities.RM_Home_Activity;
import com.tcs.remindmeapplication.activities.RM_Login_Activity;
import com.tcs.remindmeapplication.activities.RM_TaskDetails_Activity;
import com.tcs.remindmeapplication.adapter.RM_PendingList_Adapter;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

import java.util.ArrayList;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_Pending_Fragment extends Fragment {
    ListView mPendingFragment;
    RM_PendingList_Adapter RMPending_list_adapter;
    ArrayList<RM_TaskBean> pendingBeans;
    int flag_for_share_by_adress_book;
    int displayPosition;
    int postion_of_launching_contact;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            pendingBeans= (ArrayList<RM_TaskBean>) RM_DbHelper.getInstance(getActivity()).selectAllTask(0, RM_Login_Activity.user_id);
            RMPending_list_adapter.updateList(pendingBeans);
            Toast.makeText(context, "In Receiver", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rm_pending_fragment_layout,null);

        mPendingFragment= (ListView) view.findViewById(R.id.lv_pending_fragment);
        pendingBeans= (ArrayList<RM_TaskBean>) RM_DbHelper.getInstance(getActivity()).selectAllTask(0, RM_Login_Activity.user_id);
        RMPending_list_adapter=new RM_PendingList_Adapter(getActivity(),pendingBeans);

        mPendingFragment.setAdapter(RMPending_list_adapter);
        RMPending_list_adapter.notifyDataSetChanged();


        if(pendingBeans.size()== 0)
        {

        }




        //Code done by himanshu

        Intent intent = getActivity().getIntent();
        flag_for_share_by_adress_book = intent.getIntExtra("FlagForShare",0);
        postion_of_launching_contact = intent.getIntExtra("PositionOfContact",0);

        mPendingFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayPosition = i;
                if (flag_for_share_by_adress_book == 0) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pendingItem", pendingBeans.get(i));
                    Intent intent = new Intent(getActivity(), RM_TaskDetails_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if(flag_for_share_by_adress_book==1){
                    shareTask();

                }
            }
        });

        mPendingFragment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag_for_share_by_adress_book == 0) {
                    selectFriendOption();

                }
                else if(flag_for_share_by_adress_book==1){

                    Toast.makeText(getActivity(), "You can't long pressed it", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });



      /*  listView= (ListView) view.findViewById(R.id.pending_list);
        pending_list_adapter=new Pending_list_adapter(getActivity(),pendingBeans);
        listView.setAdapter(pending_list_adapter);*/


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("SYNC_UPDATE"));
    }

    private void selectFriendOption() {
        final CharSequence[] items = { "Open Contacts", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Send Task to Friend");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Open Contacts")) {
                    Intent intent = new Intent(getActivity(),RM_ContactList_Activity.class);
                    intent.putExtra("FlagForShare",1);
                    intent.putExtra("PositionOfTask",displayPosition);
                    startActivity(intent);
                }else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void shareTask() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String Description ="Description :-"+RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getTask_desc() ;
        String Date  ="Date of Task :-"+RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getDate()+"/"+
                RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getMonth()+"/"+
                RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getYear();

        String Time ="Time of Task :- "+ RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getHour()+":"
                +RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getMinute();

        String shareBody = Description +System.getProperty("line.separator")+ Date+System.getProperty("line.separator")+Time ;

        String subject_body = RM_DbHelper.getInstance(getActivity()).selectAllTask(0,RM_Login_Activity.user_id).get(displayPosition).getTask_title();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject_body);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        // sharingIntent.putExtra(Intent.EXTRA_PHONE_NUMBER,"12345");
        String contact_phone_number=RM_DbHelper.getInstance(getActivity()).selectAllFriends(RM_Login_Activity.user_id).get(postion_of_launching_contact).getPhoneNumber();
        sharingIntent.putExtra("address",contact_phone_number );

        String contact_email=RM_DbHelper.getInstance(getActivity()).selectAllFriends(RM_Login_Activity.user_id).get(postion_of_launching_contact).getEmail();
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact_email});
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
