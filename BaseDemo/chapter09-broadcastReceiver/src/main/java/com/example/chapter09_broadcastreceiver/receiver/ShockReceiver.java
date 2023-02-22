package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class ShockReceiver extends BroadcastReceiver {

    public static final String SHOCK_ACTION = "com.example.chapter09_broadcastreceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(SHOCK_ACTION))
        {
            Toast.makeText(context, "震动", Toast.LENGTH_SHORT).show();
            Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // 命令震动器吱吱个若干秒，这里的500表示500毫秒
            vb.vibrate(500);
        }
    }
}