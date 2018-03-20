package com.tcs.remindmeapplication.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.activities.RM_AddTaskByDate_Activity;
import com.tcs.remindmeapplication.activities.RM_AddTaskByLocation_Activity;
import com.tcs.remindmeapplication.activities.RM_Home_Activity;
import com.tcs.remindmeapplication.activities.RM_Login_Activity;
import com.tcs.remindmeapplication.activities.RM_TaskDetails_Activity;
import com.tcs.remindmeapplication.adapter.RM_CompletedList_Adapter;
import com.tcs.remindmeapplication.beans.RM_TaskBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

import java.util.ArrayList;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_Completed_Fragment extends Fragment {
    ListView mCompletedFragment;
    RM_CompletedList_Adapter rm_completedList_adapter;
    // RM_PendingList_Adapter rm_pendingList_adapter;
    ArrayList<RM_TaskBean> completedBeans;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rm_completed_fragment_layout,null);

        mCompletedFragment= (ListView) view.findViewById(R.id.lv_completed_fragment);
        completedBeans= (ArrayList<RM_TaskBean>) RM_DbHelper.getInstance(getActivity()).selectAllTask(1, RM_Login_Activity.user_id);
        // Log.i("sise",String.valueOf(completedBeans.size()));
        rm_completedList_adapter= new RM_CompletedList_Adapter(getActivity(),completedBeans);
        //rm_pendingList_adapter=new RM_PendingList_Adapter(getActivity(),completedBeans);

        mCompletedFragment.setAdapter(rm_completedList_adapter);
        rm_completedList_adapter.notifyDataSetChanged();

        mCompletedFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final CharSequence[] items = new CharSequence[]{"Re-Activate Task", "Delete Task", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items[i].equals("Re-Activate Task")) {
                            if (completedBeans.get(position).getTask_type() == 0) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("editTask", completedBeans.get(position));
                                Intent intent = new Intent(getActivity(), RM_AddTaskByDate_Activity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else if (completedBeans.get(position).getTask_type() == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("editTask", completedBeans.get(position));
                                Intent intent = new Intent(getActivity(), RM_AddTaskByLocation_Activity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } else if (items[i].equals("Delete Task")) {
                           // Toast.makeText(getActivity(), "123456", Toast.LENGTH_SHORT).show();
                            RM_TaskBean taskBean = completedBeans.get(position);
                            RM_DbHelper.getInstance(getActivity()).deleteTask(taskBean);
                            Toast.makeText(getActivity(), "Task Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), RM_Home_Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else if (items[i].equals("Cancel")) {
                           // Toast.makeText(getActivity(), "cancel_123456", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }

                    }
                });
                builder.show();


            }
        });


        return view;
    }
}
