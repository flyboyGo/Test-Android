package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver.BroadOrderActivity;

public class OrderAReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // 一旦接收到有序广播,马上触发接收器的onReceive方法
        if(intent != null && intent.getAction().equals(BroadOrderActivity.ORDER_ACTION))
        {
            Toast.makeText(context, "接收器A到一个有序广播", Toast.LENGTH_SHORT).show();

            Log.d("test",getResultData());
            Log.d("test",getResultExtras(true).getString("msg"));
        }
    }
}
