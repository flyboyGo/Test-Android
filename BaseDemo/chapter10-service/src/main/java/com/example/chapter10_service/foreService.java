package com.example.chapter10_service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class foreService extends Service {

    private int number = 0;
    private Timer timer = null;
    Notification notification = null;
    NotificationManager notificationManager = null;
    String CHANNEL_ID = "foreService";
    private final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

    @Override
    public void onCreate() {
        super.onCreate();

        // 版本兼容
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "前台服务", NotificationManager.IMPORTANCE_NONE);
            // 锁屏可见
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // 创建前台服务通知的频道(通道)
            notificationManager.createNotificationChannel(channel);
        }

        // 创建延时意图
        Intent intent = new Intent(this, MainActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 无效,AndroidManifest.xml中配置相关信息
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, 0);

        // 创建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification = builder.setSmallIcon(R.drawable.cart)
                .setContentTitle("标题")
                .setContentText("详细内容,计数 number = " + number)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent) // 点击通知，返回activity,注意避免创建多个activity实例
                .build();

        // notificationManager.notify(1,builder.build());

        // 提升服务,注意：需要在清单文件中声明权限
        startForeground(1,notification);

        Log.d("test","前台服务开启");
        Toast.makeText(this, "前台服务开启", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String value = intent.getStringExtra("key");
        if(TextUtils.equals(value,"low")) {
            Log.d("test", "onStartCommand : " + value);
            // 将前台服务降级未普通的后台服务
            stopForeground(true);
        }
        else if(TextUtils.equals(value,"stop_self"))
        {
            Log.d("test", "onStartCommand : " + value);
            stopSelf();
        }

        // 创建定时器,计数器使用
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                number ++;
                Log.d("test","number = " + number);
                Notification notification = builder.setSmallIcon(R.drawable.cart)
                        .setContentTitle("标题")
                        .setContentText("详细内容,计数 number = " + number)
                        .setWhen(System.currentTimeMillis())
                        .build();
                notificationManager.notify(1,notification);
            }
        },0,1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","前台服务销毁");
        Toast.makeText(this, "前台服务销毁", Toast.LENGTH_SHORT).show();
        // 取消定时器
        if(timer != null){
            timer.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
