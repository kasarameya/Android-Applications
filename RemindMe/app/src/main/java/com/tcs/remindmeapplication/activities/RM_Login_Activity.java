package com.tcs.remindmeapplication.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.remindmeapplication.R;
import com.tcs.remindmeapplication.beans.RM_LoginBean;
import com.tcs.remindmeapplication.database.RM_DbHelper;
import com.tcs.remindmeapplication.utilities.RM_AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RM_Login_Activity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText et_Login_username;
    private EditText et_Login_password;
    private Button btn_Login_login;
    private ImageView mImageView;
    private List<RM_LoginBean> loginBeanList;
    public static int user_id;
    private TextView mSwitchUser;
    public static String user_name;
    File destination;
    public int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_login_activity_layout);
        et_Login_username= (EditText) findViewById(R.id.et_username);
        et_Login_password= (EditText) findViewById(R.id.et_password);
        mToolbar= (Toolbar) findViewById(R.id.login_activity_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mImageView= (ImageView) findViewById(R.id.login_profileimage);
        mSwitchUser= (TextView) findViewById(R.id.tv_switchuser);
        SpannableString content = new SpannableString("Switch User?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mSwitchUser.setText(content);
        if (RM_SetPassword_Activity.imagepath.equals("profileImage")) {
                mImageView.setImageResource(R.drawable.rm_profile_icon);
                mToolbar.setLogo(new BitmapDrawable(getResources(), getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
            } else {
                mImageView.setImageBitmap(getRoundedShape(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath)));
                mToolbar.setLogo(new BitmapDrawable(getResources(), getRoundedShapefortoolbar(BitmapFactory.decodeFile(RM_SetPassword_Activity.imagepath))));
            }

        btn_Login_login= (Button) findViewById(R.id.btn_Login);
        mSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageResource(R.drawable.rm_profile_icon);
                mToolbar.setLogo(new BitmapDrawable(getResources(), getRoundedShapefortoolbar(BitmapFactory.decodeResource(getResources(), R.drawable.rm_profile_icon))));
                et_Login_password.setText(null);
                et_Login_username.setText(null);
                count=1;
            }
        });
        btn_Login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBeanList = RM_DbHelper.getInstance(RM_Login_Activity.this).selectAllUser();
                RM_AppConstants.LoginConstants.FLAG = 0;
                if (et_Login_username.getText().toString().equalsIgnoreCase("")) {
                    et_Login_username.setError("User Id can not be null");
                    RM_AppConstants.LoginConstants.FLAG = 1;
                }
                if (et_Login_password.getText().toString().equalsIgnoreCase("")) {
                    et_Login_password.setError("Password field can not be null");
                    RM_AppConstants.LoginConstants.FLAG = 1;
                }
                if (RM_AppConstants.LoginConstants.FLAG == 0) {

                    for (int i = 0; i < loginBeanList.size(); i++) {
                        if (et_Login_username.getText().toString().equals(loginBeanList.get(i).getUsername())
                                && et_Login_password.getText().toString().equals(loginBeanList.get(i).getPassword())) {
                            user_id = loginBeanList.get(i).getUser_id();
                            user_name=loginBeanList.get(i).getUsername();
                            if(count==1)
                            {
                                RM_AppConstants.SetPasswordConstants.imagepath=loginBeanList.get(i).getProfileImage();
                                changeSharedValueOnSelectImage();
                                changeSharedValueOnLogin();
                                count=0;
                            }
                            else
                            {
                                if (!et_Login_username.getText().toString().equals(RM_SetPassword_Activity.name)) {
                                        RM_AppConstants.LoginConstants.FLAG = 4;
                                        break;
                                    }
                            }
                            loginBeanList.get(i).setProfileImage(RM_AppConstants.SetPasswordConstants.imagepath);
                            RM_DbHelper.mDataBaseHelper.updateUser(loginBeanList.get(i));
                            RM_AppConstants.LoginConstants.FLAG = 0;
                            break;
                        }
                        RM_AppConstants.LoginConstants.FLAG = 2;
                    }
                }
                if (loginBeanList.size() == 0) {
                    RM_AppConstants.LoginConstants.FLAG = 2;
                }
                if(RM_AppConstants.LoginConstants.FLAG ==4)
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RM_Login_Activity.this);
                    builder1.setMessage("Another user has Logged in");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                if (RM_AppConstants.LoginConstants.FLAG == 2) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RM_Login_Activity.this);
                    builder1.setMessage("Invaild username or password");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                if (RM_AppConstants.LoginConstants.FLAG == 0) {
                    Intent i = new Intent(RM_Login_Activity.this, RM_Home_Activity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(RM_Login_Activity.this, "Login successful", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(RM_Login_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (items[item].equals("Choose from Library")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 2);
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
        if (requestCode == 1) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
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
            RM_AppConstants.SetPasswordConstants.imagepath=destination.getAbsolutePath().toString();
            mImageView.setImageBitmap(getRoundedShape(thumbnail));
            changeSharedValueOnSelectImage();
            recreate();
        } else {
            if (requestCode == 2) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                RM_AppConstants.SetPasswordConstants.imagepath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(RM_AppConstants.SetPasswordConstants.imagepath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(RM_AppConstants.SetPasswordConstants.imagepath);
                bm=getRoundedShape(bm);
                mImageView.setImageBitmap(bm);
                changeSharedValueOnSelectImage();
                recreate();
            }
            else
            {
                Toast.makeText(RM_Login_Activity.this, "you have not picked image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void changeSharedValueOnSelectImage()
    {
        RM_SetPassword_Activity.imagepath=RM_AppConstants.SetPasswordConstants.imagepath;
        final SharedPreferences sharedpreferences=getSharedPreferences("Defalut", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("imagepath",RM_AppConstants.SetPasswordConstants.imagepath);
        editor.commit();
    }
    public void changeSharedValueOnLogin()
    {
        final SharedPreferences sharedpreferences=getSharedPreferences("Defalut", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name",et_Login_username.getText().toString());
        editor.commit();
    }
    public static Bitmap getRoundedShapefortoolbar(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
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
    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
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
        getMenuInflater().inflate(R.menu.menu_rm__login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
