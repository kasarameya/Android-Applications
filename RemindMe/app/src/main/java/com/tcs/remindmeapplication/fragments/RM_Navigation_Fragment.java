package com.tcs.remindmeapplication.fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.activities.RM_AddTaskByDate_Activity;
import com.tcs.remindmeapplication.activities.RM_AddTaskByLocation_Activity;
import com.tcs.remindmeapplication.activities.RM_ContactList_Activity;
import com.tcs.remindmeapplication.activities.RM_Home_Activity;
import com.tcs.remindmeapplication.activities.RM_Login_Activity;
import com.tcs.remindmeapplication.activities.RM_SetPassword_Activity;
import com.tcs.remindmeapplication.adapter.RM_Navigation_Adapter;

import java.util.ArrayList;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_Navigation_Fragment extends Fragment {
    private ListView listView;
    //ArrayAdapter arrayAdapter;
    //private String[] data={"Add Friend","Add Reminder by date","Add Reminder by Location","Logout"};
    private ArrayList<String> mLabels=new ArrayList<String >();
    private ArrayList<Integer> mIcons=new ArrayList<Integer>();
    private RM_Navigation_Adapter RMNavigation_adapter;
    private ImageView mUserLogo;
    private TextView mTvUserName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.rm_nav_fragment_layout, null);
        View view1=inflater.inflate(R.layout.rm_nav_frag_header_layout,null);

        mLabels.add(0,"Contacts");
        mLabels.add(1,"Add Task by Date/Time");
        mLabels.add(2,"Task by Location");
        mLabels.add(3,"Logout");

        mIcons.add(0,R.drawable.rm_icon_contact_list);
        mIcons.add(1,R.drawable.rm_icon_add_alarm);
        mIcons.add(2,R.drawable.rm_icon_add_location);
        mIcons.add(3,R.drawable.rm_icon_logout);

        RMNavigation_adapter =new RM_Navigation_Adapter(mLabels,mIcons,getActivity());

        mUserLogo= (ImageView) view1.findViewById(R.id.iv_navdrawer_header);
        mTvUserName= (TextView) view1.findViewById(R.id.tv_navdrawer_header);
        try {
            if (RM_SetPassword_Activity.imagepath.equals("profileImage")) {
                mUserLogo.setImageBitmap(RM_Login_Activity.getRoundedShape(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon)));

            } else {
                mUserLogo.setImageBitmap(RM_Login_Activity.getRoundedShape(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath)));
            }
        }catch (Exception e)
        {
            Log.d("NP","exception");
        }
        mTvUserName.setText("Welcome, "+RM_Login_Activity.user_name);
        listView = (ListView) view.findViewById(R.id.nav_frag_listView);



       // arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,data);

        listView.setAdapter(RMNavigation_adapter);
        listView.addHeaderView(view1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ((RM_Home_Activity)getActivity()).closeDrawer();
                    Intent intent1 = new Intent(getActivity(), RM_ContactList_Activity.class);
                    startActivity(intent1);

                }
                if (position == 2) {
                    ((RM_Home_Activity) getActivity()).closeDrawer();
                    Intent intent=new Intent(getActivity(),RM_AddTaskByDate_Activity.class);
                    startActivity(intent);
                }

                if (position == 3) {

                    ((RM_Home_Activity) getActivity()).closeDrawer();
                    Intent intent=new Intent(getActivity(),RM_AddTaskByLocation_Activity.class);
                    startActivity(intent);

                }
                if (position == 4) {
                    Intent i=new Intent(getActivity(), RM_SetPassword_Activity.class);
                    startActivity(i);
                    getActivity().finish();
                    System.exit(0);
                }
            }
        });
        return view;
    }

}
