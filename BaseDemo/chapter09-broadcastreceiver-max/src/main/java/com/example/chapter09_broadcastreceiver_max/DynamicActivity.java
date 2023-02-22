package com.example.chapter09_broadcastreceiver_max;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.chapter09_broadcastreceiver_max.receiver.DynamicBroadcastReceiver;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    private DynamicBroadcastReceiver dynamicBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        findViewById(R.id.btn_send_dynamic_broadcast).setOnClickListener(this);

        dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("dynamic_broadcast");
        registerReceiver(dynamicBroadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("dynamic_broadcast");
        sendBroadcast(intent);
    }
}