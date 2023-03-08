package com.example.chapter10_service_extra.service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

public class ExtendJobIntentService extends JobIntentService {

    public static final Integer JOB_ID = 1;
    @Override
    public void onCreate() {
        Log.d("test","ExtendJobIntentService onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("test","ExtendJobIntentService onStartCommand id = " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test","ExtendJobIntentService onStart");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Integer param  = intent.getExtras().getInt("param");
        Log.d("test","onHandleWork param = " + param);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        Log.d("test","ExtendJobIntentService onBind");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","ExtendJobIntentService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","ExtendJobIntentService onDestroy");
    }
}
