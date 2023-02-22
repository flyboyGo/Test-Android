package com.example.chapter09_broadcastreceiver_extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

import com.example.chapter09_broadcastreceiver_extra.reveiver.BroadCastPermissionReceiver;

public class BroadCastPermissonActivity extends AppCompatActivity{

    private BroadCastPermissionReceiver boradCastPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_permission);

        boradCastPermission = new BroadCastPermissionReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.chapter09_broadcastreceiver_extra");
        registerReceiver(boradCastPermission,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(boradCastPermission);
    }
}