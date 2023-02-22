package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(Intent.ACTION_TIME_TICK))
        {

            Log.d("test","分钟变更事件");
        }
    }
}
