package com.example.chapter09_broadcastreceiver_max.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StaticBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "静态注册的广播,接收到广播", Toast.LENGTH_SHORT).show();
    }
}
