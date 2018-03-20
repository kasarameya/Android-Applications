package com.example.a968660.flickrgallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhotoGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fl_main_frag_container);
        if (fragment == null){
            fragment = new PhotoGalleryFragment();
            fragmentManager.beginTransaction().add(R.id.fl_main_frag_container,fragment).commit();
        }
    }
}
