package com.tcs.remindmeapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_TaskBean;

import java.util.ArrayList;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_CompletedList_Adapter extends BaseAdapter {
    private Activity mcontext;
    private ArrayList<RM_TaskBean> mTaskList;

    public RM_CompletedList_Adapter(Activity mcontext, ArrayList<RM_TaskBean> mTaskList) {

        this.mcontext = mcontext;
        this.mTaskList = mTaskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mTaskList.get(position).getTask_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view == null) {
            LayoutInflater layoutInflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.rm_completed_list_item_layout, parent, false);

        }
        else
        {
            view=convertView;
        }

        TextView mTaskname= (TextView) view.findViewById(R.id.tv_completed_task_name);
        TextView mTaskdate= (TextView) view.findViewById(R.id.tv_completed_task_date);
        TextView mTaskTime= (TextView) view.findViewById(R.id.tv_completed_task_time);
        ImageView mTaskLogo= (ImageView) view.findViewById(R.id.iv_completed_task_logo);

        mTaskname.setText(mTaskList.get(position).getTask_title());
        if(mTaskList.get(position).getTask_type()==0) {
            mTaskdate.setText(mTaskList.get(position).getDate() + "/" + (mTaskList.get(position).getMonth() + 1) + "/" + mTaskList.get(position).getYear());
            mTaskTime.setText(mTaskList.get(position).getHour() + ":" + mTaskList.get(position).getMinute());

        }
        else if(mTaskList.get(position).getTask_type()==1){
            mTaskdate.setText("");
            mTaskTime.setText( mTaskList.get(position).getRingtone()+"");

        }

        if(mTaskList.get(position).getLatitude() == 0)
        {
            mTaskLogo.setImageResource(R.drawable.rm_taskdate_logo);
        }
        else if(mTaskList.get(position).getMonth()==0)
        {
            mTaskLogo.setImageResource(R.drawable.location_icon_nav);
        }

        return view;
    }
}
