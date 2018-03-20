package com.tcs.remindmeapplication.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RM_SetPassword_Activity extends AppCompatActivity {

    ImageView RM_Setpassword_mImageView;
    EditText RM_SetPassword_mUsername;
    EditText RM_SetPassword_mPassword;
    EditText RM_SetPassword_mDOB;
    EditText RM_SetPassword_mEmail;
    Button RM_SetPassword_mRegister;
    public static RM_LoginBean rm_loginBean;
    private List<RM_LoginBean> loginBeanList;
    private TextView tv_mTextview;
    Calendar myCalendar;
    public static String name;
    public static String imagepath;
    File destination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password_activity_layout);
        RM_SetPassword_mUsername= (EditText) findViewById(R.id.et_registration_username);
        RM_SetPassword_mPassword= (EditText) findViewById(R.id.et_registration_password);
        RM_SetPassword_mDOB= (EditText) findViewById(R.id.et_datePicker);
        RM_SetPassword_mRegister= (Button) findViewById(R.id.btn_setPassword);
        tv_mTextview = (TextView)findViewById(R.id.tv_Setpassword_message);
        RM_SetPassword_mEmail= (EditText) findViewById(R.id.et_setpassword_email);
        RM_Setpassword_mImageView= (ImageView) findViewById(R.id.profileimage);
        final SharedPreferences sharedpreferences=getSharedPreferences("Defalut", this.MODE_PRIVATE);
        name=sharedpreferences.getString("name", "Defalut");
        imagepath=sharedpreferences.getString("imagepath","Defalut");
        if(RM_Launch_Activity.status==0) {
            if (!name.equals("Defalut")) {
                RM_Launch_Activity.status=1;
                Intent i = new Intent(RM_SetPassword_Activity.this, RM_Launch_Activity.class);
                startActivity(i);
            }
        }
        RM_SetPassword_mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_mTextview.setVisibility(View.INVISIBLE);
                RM_AppConstants.SetPasswordConstants.FLAG=0;
                if(RM_SetPassword_mUsername.getText().toString().equalsIgnoreCase(""))
                {
                    RM_SetPassword_mUsername.setError("User id can not be null");
                    RM_AppConstants.SetPasswordConstants.FLAG=1;
                }
                if(!RM_SetPassword_mUsername.getText().toString().matches("[a-zA-Z.? ]*"))
                {
                    RM_SetPassword_mUsername.setError("User id can not contain special charcter or number");
                    RM_AppConstants.SetPasswordConstants.FLAG=1;
                }
                if(RM_SetPassword_mPassword.getText().toString().equalsIgnoreCase(""))
                {
                    RM_SetPassword_mPassword.setError("Password can not be null");
                    RM_AppConstants.SetPasswordConstants.FLAG=3;
                }
                if(!(RM_SetPassword_mPassword.getText().toString().length()>=8&&RM_SetPassword_mPassword.getText().toString().length()<=15))
                {
                    if( RM_AppConstants.SetPasswordConstants.FLAG==3) {
                        RM_AppConstants.SetPasswordConstants.FLAG = 1;
                    }
                    else
                    {
                        RM_SetPassword_mPassword.setError("Password length should be 8 to 15 digits");
                        RM_AppConstants.SetPasswordConstants.FLAG = 1;
                    }
                }
                if(RM_SetPassword_mDOB.getText().toString().equalsIgnoreCase(""))
                {
                    RM_SetPassword_mDOB.setError("Date of Birth can not be null");
                    RM_AppConstants.SetPasswordConstants.FLAG=1;
                }
                if(RM_SetPassword_mEmail.getText().toString().equalsIgnoreCase(""))
                {
                    RM_SetPassword_mEmail.setError("Email can not be null");
                    RM_AppConstants.SetPasswordConstants.FLAG=1;
                }
                if(!RM_SetPassword_mEmail.getText().toString().equalsIgnoreCase(""))
                {
                   Boolean b= isValidEmail(RM_SetPassword_mEmail.getText().toString());
                    if(b==false)
                    {
                        RM_SetPassword_mEmail.setError("Invalid Email");
                        RM_AppConstants.SetPasswordConstants.FLAG=1;
                    }

                }
                if(RM_AppConstants.SetPasswordConstants.FLAG==0) {
                    loginBeanList= RM_DbHelper.getInstance(RM_SetPassword_Activity.this).selectAllUser();
                    for (int i = 0; i < loginBeanList.size(); i++) {
                        if (RM_SetPassword_mEmail.getText().toString().equals(loginBeanList.get(i).getEmail())) {
                            RM_AppConstants.SetPasswordConstants.FLAG = 1;
                            tv_mTextview.setVisibility(View.VISIBLE);
                            tv_mTextview.setText("User Email already exist");
                            break;
                        }
                    }
                }
                if(RM_AppConstants.SetPasswordConstants.FLAG==0) {
                    rm_loginBean=new RM_LoginBean(RM_SetPassword_mPassword.getText().toString(),
                            RM_AppConstants.SetPasswordConstants.imagepath,
                            RM_SetPassword_mUsername.getText().toString(),RM_SetPassword_mDOB.getText().toString(),RM_SetPassword_mEmail.getText().toString());
                    RM_DbHelper.getInstance(RM_SetPassword_Activity.this).addUser(rm_loginBean);
                    imagepath=rm_loginBean.getProfileImage();
                    name=RM_SetPassword_mUsername.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("name",RM_SetPassword_mUsername.getText().toString());
                    editor.putString("imagepath",imagepath);
                    editor.commit();
                    Intent i = new Intent(RM_SetPassword_Activity.this, RM_Launch_Activity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(RM_SetPassword_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTime();
            }

        };
        RM_SetPassword_mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RM_SetPassword_mDOB.setError(null);
                DatePickerDialog datePickerDialog =  new DatePickerDialog(RM_SetPassword_Activity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.setTitle("Select Date Of Birth");
                datePickerDialog.show();

            }
        });
        RM_Setpassword_mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {

            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }
    private void updateTime() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        RM_SetPassword_mDOB.setText(sdf.format(myCalendar.getTime()));
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(RM_SetPassword_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1);
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
        if (requestCode == 1&& data!=null) {
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
            RM_Setpassword_mImageView.setImageBitmap(getRoundedShape(thumbnail));
        } else {
            if (requestCode == 2&& data!=null) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,null);
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
                RM_Setpassword_mImageView.setImageBitmap(bm);

            }
            else {
                Toast.makeText(RM_SetPassword_Activity.this, "You have not picked image", Toast.LENGTH_SHORT).show();
            }
        }
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
        getMenuInflater().inflate(R.menu.menu_rm__set_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
