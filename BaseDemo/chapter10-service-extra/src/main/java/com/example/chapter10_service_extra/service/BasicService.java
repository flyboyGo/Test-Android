package com.example.chapter10_service_extra.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BasicService extends Service {

    private boolean flag = true;
    // 线程集合
    private List<Thread> threadList = new ArrayList<>();
    // 定义onBind方法返回的对象
    private MyBinder binder = new MyBinder();
    private int binderInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","BasicService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test","BasicService onStartCommand flags = " + flags + ",startId = " + startId);
        Thread thread = new Thread(){
          int count = 0;
            @Override
            public void run() {
                super.run();
                while (flag){
                    Log.d("test", getName() + "count=" + count);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    count++;
                }
            }
        };
        thread.start();
        threadList.add(thread);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test","BasicService onStart startId = " + startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("test","BasicService onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","BasicService onUnbind");
        for (Thread thread : threadList) {
            if(thread != null){
                // 中止线程
                thread.interrupt();
            }
        }
        threadList.clear();
        flag=false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","BasicService onDestroy");
        for (Thread thread : threadList) {
            if(thread != null){
                // 中止线程
                thread.interrupt();
            }
        }
        threadList.clear();
        flag=false;
    }

    public class MyBinder extends Binder{

        public int getBinderInfo(){
            return binderInfo;
        }

        public void setBinderInfo(int info){
            binderInfo = info;
        }
    }
}
