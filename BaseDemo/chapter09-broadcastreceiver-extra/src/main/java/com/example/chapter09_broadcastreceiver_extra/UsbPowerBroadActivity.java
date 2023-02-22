package com.example.chapter09_broadcastreceiver_extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class UsbPowerBroadActivity extends AppCompatActivity {

    private UsbPowerReceiver usbPowerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_power_broad);

        // 实例化usb插拔接收接收者
        usbPowerReceiver = new UsbPowerReceiver();
    }

    // 注册usb插拔广播
    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        // 意图过滤器添加多个action(通俗一点,广播接收者监听多个广播)
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        if(usbPowerReceiver != null)
        {
            registerReceiver(usbPowerReceiver,filter);
        }
    }

    // 销毁usb插拔广播,否者会导致内存泄露
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(usbPowerReceiver != null)
        {
            unregisterReceiver(usbPowerReceiver);
        }
    }

    private class UsbPowerReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            {
                Toast.makeText(context, "usb以插入", Toast.LENGTH_SHORT).show();
            }
            else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
            {
                Toast.makeText(context, "usb以拔除", Toast.LENGTH_SHORT).show();
            }
        }
    }
}