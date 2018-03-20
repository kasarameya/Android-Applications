package com.tcs.remindmeapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_FriendBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RM_AddContact_Activity extends AppCompatActivity {
    private Button mBtnReset;
    private EditText mEtName, mEtPhoneNumber, mEtEmail;
    private EditText mEtAddress, mEtNickName;
    Toolbar mToolbar;
    private ImageView mIvContactPicture;
    private int  SELECT_FILE = 1;
    private int REQUEST_CAMERA =0;
    private RM_FriendBean rm_friendBean=null;
    private String selectedImagePath;
    int flag_for_edit=0; // shows it is just old  contact
    int flag_for_error=0; //show any wrong input
    int user_id = 0;
    private  File destination;
    private Uri selectedImageUri;
    private Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity_layout);
        initialise();
        mIvContactPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

//save details after edit
        Bundle bundleEdit = getIntent().getExtras();
        if(bundleEdit != null)
        {
            rm_friendBean = (RM_FriendBean)bundleEdit.getSerializable("OBJECT");
            user_id = rm_friendBean.getUser_id();
            if(rm_friendBean!=null)
            {   mEtName.setText(rm_friendBean.getName());
                mEtPhoneNumber.setText(rm_friendBean.getPhoneNumber());
                mEtEmail.setText(rm_friendBean.getEmail());
                mEtAddress.setText(rm_friendBean.getAddress());
                mEtNickName.setText(rm_friendBean.getNickname());
                selectedImagePath=rm_friendBean.getFriend_image();

                if(rm_friendBean.getFriend_image()==null)
                    mIvContactPicture.setImageResource(R.drawable.rm_profile_icon);
                else
                    mIvContactPicture.setImageBitmap(getRoundedShape(BitmapFactory.decodeFile(selectedImagePath)));
                mToolbar.setTitle("Edit Contact");
                flag_for_edit =1;

            }
        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rm__add_contact_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_save)
        {   saveDetails();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if(id ==R.id.reset){
            resetAllFields();

        }

        return super.onOptionsItemSelected(item);
    }

//method to save details of contact
    public void saveDetails()
    {
        String name = mEtName.getText().toString();
        String number = mEtPhoneNumber.getText().toString();
        String email = mEtEmail.getText().toString();
        String address= mEtAddress.getText().toString();
        String nickName=mEtNickName.getText().toString();

       // Toast.makeText(RM_AddContact_Activity.this, selectedImagePath+"jjdjkahjk", Toast.LENGTH_SHORT).show();

        if(name.equalsIgnoreCase("")) {
            mEtName.setError("Name can not be null");
            flag_for_error = 1;
        }else if(number.equalsIgnoreCase("")){
                mEtPhoneNumber.setError("Number can not be null");
                flag_for_error=1;
            }

        //

// flag to check if any wrong input
        if (flag_for_error==0)
        {
            if(rm_friendBean != null) {
                rm_friendBean.setName(name);
                rm_friendBean.setPhoneNumber(number);
                rm_friendBean.setEmail(email);
                rm_friendBean.setFriend_image(selectedImagePath);
                rm_friendBean.setNickname(nickName);
                rm_friendBean.setAddress(address);
            }
            else
            {
                rm_friendBean = new RM_FriendBean(address, nickName,name,selectedImagePath, email,number,RM_Login_Activity.user_id);

            }
            if(flag_for_edit==0) {
                RM_DbHelper.getInstance(this).addFriends(rm_friendBean);
                setResult(501);
                finish();

            }
            else if(flag_for_edit==1)
            {
                RM_DbHelper.getInstance(this).updateFriend(rm_friendBean);
                Intent intent = new Intent(this,RM_ContactList_Activity.class);
                startActivity(intent);
            }
        }
    }

    private void resetAllFields() {
        mEtName.setText("");
        mEtNickName.setText("");
        mEtEmail.setText("");
        mEtAddress.setText("");
        mEtPhoneNumber.setText("");
        mIvContactPicture.setImageResource(R.drawable.rm_profile_icon);
    }
//method to initialise all variables
    private void initialise() {
        mEtName = (EditText) findViewById(R.id.et_addContact_activity_name);
        mEtPhoneNumber = (EditText)findViewById(R.id.et_addContact_activity_phone_number);
        mEtEmail = (EditText)findViewById(R.id.et_addContact_activity_email);
        mIvContactPicture = (ImageView)findViewById(R.id.iv_addContact_activity_contact_picture);
        mEtAddress = (EditText) findViewById(R.id.et_addContact_activity_address);
        mEtNickName = (EditText) findViewById(R.id.et_addContact_activity_nickname);
        mToolbar= (Toolbar) findViewById(R.id.add_contact_activity_tool_bar);
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
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallary", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Gallary")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) // if user want to take photo from camera
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                cameraPickImage();


            } else if (requestCode == SELECT_FILE) //if user want to choose photo
            {
               selectedImageUri = data.getData();
                selectFileFromGallary();

            }
        }

    }

    private void selectFileFromGallary() {

        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        bm=getRoundedShape(bm);
        mIvContactPicture.setImageBitmap(bm);
    }

    private void cameraPickImage() {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedImagePath = destination.getAbsolutePath().toString();
        mIvContactPicture.setImageBitmap(getRoundedShape(thumbnail));
    }

    //method to give round shape to user pic
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
}
