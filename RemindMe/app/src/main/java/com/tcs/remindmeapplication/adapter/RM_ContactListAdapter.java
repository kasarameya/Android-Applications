package com.tcs.remindmeapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_FriendBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 963995 on 10/20/2015.
 */
public class RM_ContactListAdapter extends BaseAdapter implements Filterable{

    private Activity mContext;
    private List<RM_FriendBean> mFriendBeanList;
    CustomFilter filter;
    public static Boolean mFlag = true;
    ArrayList<RM_FriendBean> filters;
    private ArrayList<RM_FriendBean> mFilterList;

    public RM_ContactListAdapter(Activity mContext, List<RM_FriendBean> mFriendBeanList) {
        this.mContext = mContext;
        this.mFriendBeanList = mFriendBeanList;
        this.mFilterList = (ArrayList<RM_FriendBean>) mFriendBeanList;

    }

    @Override
    public int getCount() {
        return mFriendBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriendBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;//
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        TextView tvName;
        final TextView tvNumber;
        ImageView ivFriendPicture;
        ImageView ivCallIcon;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.contact_list_adapter_single_item_view_layout,null);
        }else
        {
            view=convertView;
        }

        tvName = (TextView)view.findViewById(R.id.tv_contact_list_adapter_name);
        tvNumber = (TextView) view.findViewById(R.id.tv_contact_list_adapter_number);
        ivFriendPicture = (ImageView) view.findViewById(R.id.iv_contact_list_adapter_contact_picture);
        ivCallIcon = (ImageView) view.findViewById(R.id.iv_contact_list_adapter_call_icon);
        RM_FriendBean friendBean=mFriendBeanList.get(position);

//setting data to field fields
        if(friendBean!=null){
            tvName.setText(friendBean.getName());
            tvNumber.setText(friendBean.getPhoneNumber());
            if(friendBean.getFriend_image()==null) {
                ivFriendPicture.setImageResource(R.drawable.rm_profile_icon);}
            else
            {
                Bitmap bit = BitmapFactory.decodeFile(friendBean.getFriend_image());
                bit=getRoundedShape(bit);
                ivFriendPicture.setImageBitmap(bit);
            }
        }

        ivCallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tvNumber.getText().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        });
        return view;
    }


// method to round the profile pic
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 200;
        int targetHeight = 200;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
    public RM_FriendBean getFriendBean(int position) {
        return mFriendBeanList.get(position);
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraints) {
            FilterResults results = new FilterResults();
            if(constraints != null && constraints.length()>0) {
                constraints = constraints.toString().toUpperCase();
                filters = new ArrayList<RM_FriendBean>();
                for(int i = 0;i<mFilterList.size();i++) {
                    if(mFilterList.get(i).getName().toUpperCase().contains(constraints)) {
                        filters.add(mFilterList.get(i));
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }
            else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFriendBeanList = (List<RM_FriendBean>) results.values;
            mFlag = false;
            notifyDataSetChanged();
        }
    }
}
