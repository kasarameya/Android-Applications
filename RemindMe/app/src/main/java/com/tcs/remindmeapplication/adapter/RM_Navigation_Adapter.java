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

import java.util.ArrayList;

/**
 * Created by hp- hp on 20-10-2015.
 */
public class RM_Navigation_Adapter extends BaseAdapter {
    private ArrayList<String> mlabels;
    private ArrayList<Integer> mIcons;
    private Activity mcontext;

    public ArrayList<String> getLabels() {
        return mlabels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.mlabels = labels;
    }

    public ArrayList<Integer> getIcons() {
        return mIcons;
    }

    public void setIcons(ArrayList<Integer> icons) {
        this.mIcons = icons;
    }

    public Activity getMcontext() {
        return mcontext;
    }

    public void setMcontext(Activity mcontext) {
        this.mcontext = mcontext;
    }

    public RM_Navigation_Adapter(ArrayList<String> labels, ArrayList<Integer> icons, Activity mcontext) {
        this.mlabels = labels;
        this.mIcons = icons;

        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mlabels.size();
    }

    @Override
    public Object getItem(int position) {
        return mlabels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view == null) {
            LayoutInflater layoutInflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.rm_nav_frag_item_layout, parent, false);

        }
        else
        {
            view=convertView;
        }
        ImageView mIcon= (ImageView) view.findViewById(R.id.nav_item_icon);
        TextView mText= (TextView) view.findViewById(R.id.nav_item_label);
        mIcon.setImageResource(mIcons.get(position));
        mText.setText(mlabels.get(position));

        return view;
    }
}

