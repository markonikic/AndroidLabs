package com.cst2335.niki0007;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DirectAction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.text.BreakIterator;

public class ProfileActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageButton;
    private DirectAction data;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(ACTIVITY_NAME, "In Function: onCreate()");

        ImageButton mImageButton = (ImageButton) findViewById(R.id.imageButton2);
        mImageButton.setOnClickListener(c ->{
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
       //     }
        });

        Intent fromMain = getIntent();
            String email = fromMain.getStringExtra("EMAIL");
       EditText emailEditText = findViewById(R.id.yourEmail);
        emailEditText.setText(email);

        Button chatBtn = (Button) findViewById(R.id.button4);
        chatBtn.setOnClickListener(c->{
            Intent gotoChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(gotoChat);
        });

        //button5
        Button weatherBtn = (Button) findViewById(R.id.button5);
        weatherBtn.setOnClickListener(c->{
            Intent gotoWeather = new Intent(ProfileActivity.this, WeatherHome.class);
            startActivity(gotoWeather);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton mImageButton = (ImageButton) findViewById(R.id.imageButton2);
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In Function: onActivityResult");
    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME, "In Function: onStart()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.e(ACTIVITY_NAME, "In Function: onResume()");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.e(ACTIVITY_NAME, "In Function: onPause()");
    }
    @Override
    protected  void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME, "In Function: onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In Function: onDestroy");
    }

}