package com.example.chapter09_broadcastreceiver_extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.chapter09_broadcastreceiver_extra.reveiver.BatteryChangeReceiver;

public class BatteryChangeBroadActivity extends AppCompatActivity {

    private BatteryChangeReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_change);

        // 实例化也给广播接收者
        batteryReceiver = new BatteryChangeReceiver();
    }

    // 注册广播接收者，并设置接收广播的类型
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        if(batteryReceiver != null)
        {
            registerReceiver(batteryReceiver,filter);
        }
    }

    // 销毁广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(batteryReceiver != null)
        {
            unregisterReceiver(batteryReceiver);
        }
    }
}