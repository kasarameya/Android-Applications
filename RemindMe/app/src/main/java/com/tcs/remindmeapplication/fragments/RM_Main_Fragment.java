package com.tcs.remindmeapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.adapter.RM_Main_Adapter;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_Main_Fragment extends Fragment {
    int flag;
    ViewPager mViewPager;
    RM_Main_Adapter mRMMainAdapter;
    TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.rm_main_fragment_layout, null);


        mViewPager= (ViewPager) view.findViewById(R.id.vp);
        mTabLayout= (TabLayout) view.findViewById(R.id.tb_layout);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.teal_A600));
        mRMMainAdapter =new RM_Main_Adapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mRMMainAdapter);

        //       mTabLayout= (TabLayout) findViewById(R.id.tb_layout);

        mTabLayout.setupWithViewPager(mViewPager);
       // mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

       //setupTabListener();



        return view;
    }

    private void setupTabListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i=tab.getPosition();
                mViewPager.setCurrentItem(i,true);

                if(i==0)
                {
                    mTabLayout.setBackgroundColor(getResources().getColor(R.color.teal_400));
                }
                else if(i==1)
                {
                    mTabLayout.setBackgroundColor(getResources().getColor(R.color.teal_500));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void getFlag(int flag) {
        this.flag = flag;
    }

}
