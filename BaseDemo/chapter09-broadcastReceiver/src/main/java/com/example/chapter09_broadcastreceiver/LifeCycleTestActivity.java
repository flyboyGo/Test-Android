package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class LifeCycleTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_test);
        Log.d("test","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test","onDestroy");
    }
}