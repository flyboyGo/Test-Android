package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver.BroadStandardActivity;

public class StandardAReceiver extends BroadcastReceiver {

    // 一旦接收到标准广播,马上触发接收器的onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(BroadStandardActivity.STANDARD_ACTION))
        {
            // 读取数据
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("msg");
            Toast.makeText(context, "广播接收器A接收到一个标准广播(无序),携带的数据为 = " + msg, Toast.LENGTH_SHORT).show();
        }
    }
}
