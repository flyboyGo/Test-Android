package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver.BroadOrderActivity;
import com.example.chapter09_broadcastreceiver.R;

public class OrderBReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // 一旦接收到有序广播,马上触发接收器的onReceive方法
        if(intent != null && intent.getAction().equals(BroadOrderActivity.ORDER_ACTION))
        {
            Toast.makeText(context, "接收器B到一个有序广播", Toast.LENGTH_SHORT).show();

            // 中断广播,此时后面的接收器无法收到该广播
            // abortBroadcast();

            // 新的方式获取数据,数据并没有装载在intent中
            // 获取广播携带的数据

            // 第六个参数
            String resultData = getResultData();
            Log.d("test",resultData);
            setResultData("已修改数据");


            // 第七个参数
            Bundle bundle = getResultExtras(true);
            String msg = bundle.getString("msg");
            Log.d("test",msg);

            bundle.putString("msg","有序广播携带的数据,已修改");
            setResultExtras(bundle);
        }
    }
}
