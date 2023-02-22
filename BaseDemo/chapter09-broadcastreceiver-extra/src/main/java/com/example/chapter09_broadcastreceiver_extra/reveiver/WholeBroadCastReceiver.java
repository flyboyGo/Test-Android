package com.example.chapter09_broadcastreceiver_extra.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WholeBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "接收到其他APP发送的全局广播", Toast.LENGTH_SHORT).show();
    }
}
