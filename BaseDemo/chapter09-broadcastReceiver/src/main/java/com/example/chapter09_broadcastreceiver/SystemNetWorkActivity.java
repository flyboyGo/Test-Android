package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

import com.example.chapter09_broadcastreceiver.receiver.NetWorkReceiver;

public class SystemNetWorkActivity extends AppCompatActivity {

    private NetWorkReceiver netWorkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_net_work);
    }

    @Override
    protected void onStart() {
        super.onStart();
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netWorkReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(netWorkReceiver);
    }
}