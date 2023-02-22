package com.example.chapter09_broadcastreceiver_max.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class OrderABroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String stringMsg = bundle.getString("original");

        // 给广播添加额外的消息
        // 方法一:
        Bundle bundle2 = new Bundle();
        bundle2.putString("msgA","A添加的消息方法一");
        setResultExtras(bundle2);
        // 方法二:
        setResultData("A添加的消息方法二");

        Toast.makeText(context, "A = " +stringMsg, Toast.LENGTH_SHORT).show();
    }
}
