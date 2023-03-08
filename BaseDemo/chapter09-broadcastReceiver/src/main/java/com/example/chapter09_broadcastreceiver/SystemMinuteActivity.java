package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.chapter09_broadcastreceiver.receiver.TimeReceiver;

public class SystemMinuteActivity extends AppCompatActivity {

    private TimeReceiver timeReceiver;

    /*
       接收分钟到达广播
         Intent.ACTION_TIME_TICK

       接收网络变更广播
         android.net.conn.CONNECTIVITY_CHANGE
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_minute);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 创建一个分钟变更的广播接收者
        timeReceiver = new TimeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(timeReceiver);
    }
}