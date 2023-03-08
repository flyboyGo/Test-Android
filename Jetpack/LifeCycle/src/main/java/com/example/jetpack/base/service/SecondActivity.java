package com.example.jetpack.base.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jetpack.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void startGps(View view) {
        startService(new Intent(this,MyLocationService.class));
    }

    public void stopGps(View view) {
        stopService(new Intent(this,MyLocationService.class));
    }
}