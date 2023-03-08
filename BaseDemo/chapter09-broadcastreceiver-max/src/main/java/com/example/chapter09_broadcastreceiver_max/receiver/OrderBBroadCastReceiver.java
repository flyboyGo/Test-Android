package com.example.chapter09_broadcastreceiver_max.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class OrderBBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String stringMsg = bundle.getString("original");
        Toast.makeText(context, "B = "+stringMsg, Toast.LENGTH_SHORT).show();

        // 获取上层广播接收者添加添加的消息
        // 方法一：
        Bundle extras = getResultExtras(true);
        String msgA = extras.getString("msgA");
        Toast.makeText(context, "B = "+msgA, Toast.LENGTH_SHORT).show();

        // 方法二：
        String data = getResultData();
        Toast.makeText(context, "B = "+data, Toast.LENGTH_SHORT).show();
    }
}
