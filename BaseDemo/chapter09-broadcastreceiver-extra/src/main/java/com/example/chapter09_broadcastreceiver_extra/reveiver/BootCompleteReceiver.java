package com.example.chapter09_broadcastreceiver_extra.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null)
        {
            Toast.makeText(context, "开机成功", Toast.LENGTH_SHORT).show();
            Log.d("test","开机以成功");
        }
    }
}
