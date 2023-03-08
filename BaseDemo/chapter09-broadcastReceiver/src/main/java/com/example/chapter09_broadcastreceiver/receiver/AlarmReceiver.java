package com.example.chapter09_broadcastreceiver.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ALARM_ACTION = "com.example.chapter09_broadcastreceiver.alarm";

    private Context context;

    public AlarmReceiver(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(ALARM_ACTION))
        {
            Log.d("test","收到闹钟广播");
            sendAlarm();
        }
    }

    // 发送闹钟广播
    public void sendAlarm()
    {
        // 创建一个普通视图
        Intent intent = new Intent(ALARM_ACTION);

        // 创建一个用于广播的延迟意图
        // 针对 S+（版本 10000 及更高版本）要求在创建 PendingIntent 时指定 FLAG_IMMUTABLE 或 FLAG_MUTABLE 之一。
        // 强烈考虑使用 FLAG_IMMUTABLE，仅当某些功能依赖于 PendingIntent 是可变的时才使用 FLAG_MUTABLE
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_IMMUTABLE);

        // 从系统服务中获取闹钟管理器
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //允许在设备空闲时发送广播,Android6.0之后新增的方法
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 1000,pendingIntent);
        }
        else
        {
            // 设置一次性闹钟，延迟若干秒后，携带延迟意图发送闹钟广播（但Android6.0之后，set方法在暗屏时不保证发送广播，
            // 必须调用setAndAllowWhileIdle方法）
            alarmManager.set(AlarmManager.RTC_WAKEUP, 1000,pendingIntent);
        }

        // 设置重复闹钟，每隔一定间隔就发送闹钟广播（但从Android4.4开始，setRepeating方法不保证按时发送广播）
        // alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000, pIntent);
    }
}
