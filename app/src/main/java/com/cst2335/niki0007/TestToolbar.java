package com.cst2335.niki0007;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        //WeatherBtn
        @SuppressLint("WrongViewCast")
        TextView weatherBtn = (TextView) findViewById(R.id.weatherBtn);
        weatherBtn.setOnClickListener(c->{
            Intent gotoWeather = new Intent(TestToolbar.this, WeatherHome.class);
            startActivity(gotoWeather);
        });
        //LoginBtn
        @SuppressLint("WrongViewCast")
        TextView loginBtn = (TextView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(c->{
            Intent gotoLogin = new Intent(TestToolbar.this, ProfileActivity.class);
            startActivity(gotoLogin);
        });
        //ChatBtn
        @SuppressLint("WrongViewCast")
        TextView chatBtn = (TextView) findViewById(R.id.chatBtn);
        chatBtn.setOnClickListener(c->{
            Intent gotoChat = new Intent(TestToolbar.this, ChatRoomActivity.class);
            startActivity(gotoChat);
        });

        //WeatherBtn
        @SuppressLint("WrongViewCast")
        ImageView weatherIMG = (ImageView) findViewById(R.id.weather);
        weatherIMG.setOnClickListener(c->{
            Intent gotoWeatherIMG = new Intent(TestToolbar.this, WeatherHome.class);
            startActivity(gotoWeatherIMG);
        });

        //ChatBtn
        @SuppressLint("WrongViewCast")
        ImageView chatIMG = (ImageView) findViewById(R.id.chat);
        chatIMG.setOnClickListener(c->{
            Intent gotoChatIMG = new Intent(TestToolbar.this, ChatRoomActivity.class);
            startActivity(gotoChatIMG);
        });

        //LoginBtn
        @SuppressLint("WrongViewCast")
        ImageView loginIMG = (ImageView) findViewById(R.id.login);
        loginIMG.setOnClickListener(c->{
            Intent gotoLoginIMG = new Intent(TestToolbar.this, ProfileActivity.class);
            startActivity(gotoLoginIMG);
        });

    }
}