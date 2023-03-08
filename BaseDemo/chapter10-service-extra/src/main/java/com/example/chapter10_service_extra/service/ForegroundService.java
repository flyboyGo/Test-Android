package com.example.chapter10_service_extra.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.chapter10_service_extra.ExtendActivity;
import com.example.chapter10_service_extra.R;

import java.util.ArrayList;
import java.util.List;

public class ForegroundService extends Service {

    int notificationId = 1;
    int count = 0;
    private List<Thread> threadList = new ArrayList<>();

    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private String CHANNEL_ID = "my_channel_01";
    private PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","ForegroundService onCreate");

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 适配Android 8.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 创建通知的频道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(this,String.valueOf(CHANNEL_ID));

        // 创建延时意图,点击通知跳转到页面当中，注意避免创建多个activity实例，AndroidManifest.xml中配置相关信息，Intent.FLAG_ACTIVITY_SINGLE_TOP
        Intent intent = new Intent(this, ExtendActivity.class);
        pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        // 可以添加自定义的View
        builder.setContentTitle("前台服务")
                .setContentText("前台服务正在运行")
                .setWhen(System.currentTimeMillis())  // 通知创建的时间
                .setSmallIcon(R.drawable.ic_launcher_background) // 通知显示的小图标,只能用alpha图层的图片
                .setContentIntent(pendingIntent);// 点击通知，返回activity,注意避免创建多个activity实例
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){ // 默认是主线程的Looper，因为需要更新通知,属于刷新UI
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Notification notification = builder.setContentText("前台服务正在运行 count = " + count).build();
                manager.notify(1,notification);// 更新通知,指定通知的id
            }else if(msg.what == 2){
               Notification notification = builder.setContentText("前台服务正在运行 count = " + count).build();
                manager.notify(2,notification);// 更新通知,指定通知的id
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = builder.build();
        // 提升服务,注意：需要在清单文件中声明权限
        startForeground(notificationId, notification); // 服务可以被启动多次,通知也可以被创建多个,

        Log.d("test","ForegroundService onStartCommand,flags = " + flags + ",startId = " + startId);
        Thread thread = new Thread(){
            int selfNotificationId = notificationId;
            @Override
            public void run() {
                super.run();
                while (true){
                    Log.d("test",getName() + " count = " + count);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    setCount();
                    mHandler.sendEmptyMessage(selfNotificationId);
                }
            }
        };
        thread.start();
        threadList.add(thread);
        notificationId++;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test","ForegroundService onStart");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("test","ForegroundService onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","ForegroundService onUnbind");
        return super.onUnbind(intent);
    }

    private synchronized void setCount(){
        count++;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("test","ForegroundService onTaskRemoved");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","ForegroundService onDestroy");
        for (Thread thread : threadList) {
            if(thread != null){
                // 中止线程
                thread.interrupt();
            }
        }
        threadList.clear();
    }
}
