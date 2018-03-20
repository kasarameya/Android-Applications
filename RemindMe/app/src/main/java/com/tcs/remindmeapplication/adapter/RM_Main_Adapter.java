package com.tcs.remindmeapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tcs.remindmeapplication.fragments.RM_Completed_Fragment;
import com.tcs.remindmeapplication.fragments.RM_Pending_Fragment;

/**
 * Created by hp- hp on 08-10-2015.
 */
public class RM_Main_Adapter extends FragmentStatePagerAdapter {
    public RM_Main_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;

        switch (i)
        {
            case 0:fragment=new RM_Pending_Fragment();
                break;
            case 1:fragment=new RM_Completed_Fragment();
                break;

        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String s="";
        switch (position){
            case 0:
                s="Pending";
                break;
            case 1:
                s="Completed";
                break;

        }
        return s;
    }
}
