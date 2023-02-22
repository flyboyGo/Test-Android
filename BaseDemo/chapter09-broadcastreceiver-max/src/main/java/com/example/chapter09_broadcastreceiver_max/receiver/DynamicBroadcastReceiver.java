package com.example.chapter09_broadcastreceiver_max.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DynamicBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "动态注册的广播接收器接收到广播", Toast.LENGTH_SHORT).show();
    }
}
