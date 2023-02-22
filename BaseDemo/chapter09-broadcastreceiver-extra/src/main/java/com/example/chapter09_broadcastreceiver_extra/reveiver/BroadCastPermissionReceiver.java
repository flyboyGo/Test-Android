package com.example.chapter09_broadcastreceiver_extra.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadCastPermissionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null)
        {
            Log.d("test","action == " + intent.getAction());
            Toast.makeText(context, "权限广播接收到了", Toast.LENGTH_LONG).show();
        }
    }
}
