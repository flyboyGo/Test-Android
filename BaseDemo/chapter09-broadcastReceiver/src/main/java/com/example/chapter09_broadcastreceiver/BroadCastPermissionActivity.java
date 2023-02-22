package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;

public class BroadCastPermissionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_permission);

        findViewById(R.id.btn_broad_permission).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        intent.setAction("com.example.chapter09_broadcastreceiver_extra");
        // android广播的使用即跨进程发送广播,android8.0以后必须添加包名，否则接收不到
        intent.setPackage("com.example.chapter09_broadcastreceiver_extra");
        // sendOrderedBroadcast(intent,"com.example.chapter09_broadcastreceiver.ORDER_PERMISSION");
        sendBroadcast(intent,"com.example.chapter09_broadcastreceiver.ORDER_PERMISSION");
    }
}