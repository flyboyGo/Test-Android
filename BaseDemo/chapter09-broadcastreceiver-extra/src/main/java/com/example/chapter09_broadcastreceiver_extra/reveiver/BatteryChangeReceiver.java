package com.example.chapter09_broadcastreceiver_extra.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryChangeReceiver extends BroadcastReceiver {

    // 创建一个一个电池电量广播接收者，继承自BroadcastReceiver,并实现其中的onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))
        {
            // BatteryManager中保存电池信息的描述,获取当前电量、最大的电量
            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int maxLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            Toast.makeText(context, "电量发生改变,现阶段的电量为:" + currentLevel
                      + " ,当前电量的百分比为:" + ((float)(currentLevel * 1.0f/ maxLevel))*100 + "%", Toast.LENGTH_SHORT).show();
        }
    }
}
