package com.example.chapter10_service_extra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.chapter10_service_extra.service.BasicService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // startForegroundService()
    //mHandler.removeCallbacks(mRunnable);

    public void startService(View view) {
        Intent intent = new Intent(MainActivity.this, BasicService.class);
        startService(intent);
    }

    //保存所启动的Service的IBinder对象,同时定义一个ServiceConnection对象
    BasicService.MyBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("test", "onServiceConnected");
            binder = (BasicService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("test", "onServiceDisconnected");
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent(MainActivity.this, BasicService.class);
        // startService(intent);
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    public void bindServiceSetParams(View view) {
        if(binder == null){
            Log.d("test","bindServiceSetParams,binder is null");
            return;
        }
        binder.setBinderInfo(12);
    }

    public void bindServiceGetParams(View view) {
        if(binder == null){
            Log.d("test","bindServiceGetParams,binder is null");
            return;
        }
        Log.d("test","binderInfo = " + binder.getBinderInfo());
    }

    public void unbindService(View view) {
        unbindService(connection);
        binder = null;
    }

    public void stopService(View view) {
        stopService(new Intent(MainActivity.this,BasicService.class));
    }
}