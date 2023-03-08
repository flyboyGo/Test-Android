package com.example.chapter10_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    public int number = 0;
    private Timer timer;

    public void test()
    {
        Log.d("test","调用MyService中的test方法");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String value = intent.getStringExtra("key");
        Log.d("test", "Service onStartCommand : " + value);
        if(TextUtils.equals(value,"stop")) {
            stopSelf();
        }

        // 创建定时器,计数器使用
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                number ++;
                Log.d("test", " number = " + number);
            }
        },0,1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String value = intent.getStringExtra("key");
        Log.d("test", "Service onStart : " + value);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String value = intent.getStringExtra("key");
        Log.d("test", "Service onBind" + value);

        // 创建定时器,计数器使用
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                number ++;
                Log.d("test", " number = " + number);
            }
        },0,1000);

        // 通过自定义的MyBinder传递MyService对象
        return new MyBinder(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","Service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
        Log.d("test", "Service onDestroy");
    }

    // 继承系统提供的Binder类
    public class MyBinder extends Binder
    {
        private MyService service = null;

        public MyBinder(MyService service)
        {
            this.service = service;
        }
        public void getTest()
        {
            Log.d("test","myBinder调用了getTest方法");
            service.test();
        }
        public MyService getMyService()
        {
            return service;
        }
    }
}
