package com.tcs.remindmeapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_FriendBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

public class RM_FriendDetail_Activity extends AppCompatActivity {
    TextView name, nickname,number,address,email;
    ImageView img,mImg;
    ImageButton mIbCall;
    RM_FriendBean friendBean;
    int position;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_detail_activity_layout);
        Intent intent = getIntent();
       friendBean = (RM_FriendBean) intent.getSerializableExtra("CONTACTINFO");

        initialiseField();
        setDataToField(); // method to set all data to field
    }


//method to initialise all member variables
    private void initialiseField() {
        name = (TextView) findViewById(R.id.tv_friend_detail_activity_name);
        number = (TextView) findViewById(R.id.tv_friend_detail_activity_phone_number);
        img = (ImageView) findViewById(R.id.iv_friend_detail_activity_contact_picture);
        mImg= (ImageView) findViewById(R.id.backgroun_friend_detail_image);
        nickname = (TextView) findViewById(R.id.tv_friend_detail_activity_nick_name);
        address= (TextView) findViewById(R.id.tv_friend_detail_activity_address);
        email= (TextView) findViewById(R.id.tv_friend_detail_activity_email);
        mToolbar = (Toolbar) findViewById(R.id.tb_friend_detail_activity);

        setSupportActionBar(mToolbar);
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

    }

    private void setDataToField() {
        name.setText(friendBean.getName());
        nickname.setText(friendBean.getNickname());
        address.setText(friendBean.getAddress());
        email.setText(friendBean.getEmail());
        number.setText(friendBean.getPhoneNumber());

        if (friendBean.getFriend_image() == null) {
            //Toast.makeText(RM_FriendDetail_Activity.this, "image null", Toast.LENGTH_SHORT).show();
            img.setImageResource(R.drawable.rm_profile_icon);
        } else {
            Bitmap bit = BitmapFactory.decodeFile(friendBean.getFriend_image());
            bit=getRoundedShape(bit);
            img.setImageBitmap(bit);
        }
        mImg.setImageBitmap(BitmapFactory.decodeFile(friendBean.getFriend_image()));
    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__friend_detail_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icon_friend_detail_delete) {
            deleteFriend();
            return true;
        }
        if (id == R.id.icon_friend_detail_edit) {

            editDetail();
            return true;
        }
        if(id==android.R.id.home){
            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void editDetail() {
        Intent intent = new Intent(RM_FriendDetail_Activity.this,RM_AddContact_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT",friendBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void deleteFriend() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Alert!");
        adb.setMessage("Do you really want to delete?");
        adb.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                RM_DbHelper.getInstance(RM_FriendDetail_Activity.this).deleteFriends(friendBean);
                setResult(503);
                finish();
            }
        });
        adb.show();
    }



}
