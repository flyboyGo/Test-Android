package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver.receiver.StandardAReceiver;
import com.example.chapter09_broadcastreceiver.receiver.StandardBReceiver;

public class BroadStandardActivity extends AppCompatActivity implements View.OnClickListener {

    private StandardAReceiver standardAReceiver;
    private StandardBReceiver standardBReceiver;

    public static  final String STANDARD_ACTION = "com.example.chapter09_broadcastreceiver";

    /*
       广播的收发过程分为三个步骤：
         1、发送标准广播(无序广播(如果有多个接收者，可以默认为同时收到,并没有一定的顺序)!!!!!)
         2、定义广播接收器
         3、注册、注销广播接收器
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_standard);

        findViewById(R.id.btn_send_standard_broad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 发送标准广播
        Intent intent = new Intent(STANDARD_ACTION);
        // 携带数据
        Bundle bundle = new Bundle();
        bundle.putString("msg","标准广播携带的数据");
        // 装载数据
        intent.putExtras(bundle);
        // 发送标准广播
        sendBroadcast(intent);
        Toast.makeText(this, "发送标准广播成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 实例化一个广播接收器
        standardAReceiver = new StandardAReceiver();
        // 创建一个意图过滤器，只处理STANDARD_ACTION的广播
        IntentFilter filterA = new IntentFilter(STANDARD_ACTION);
        // 注册广播接收器
        registerReceiver(standardAReceiver,filterA);

        standardBReceiver = new StandardBReceiver();
        IntentFilter filterB = new IntentFilter(STANDARD_ACTION);
        registerReceiver(standardBReceiver,filterB);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器
        unregisterReceiver(standardAReceiver);
        unregisterReceiver(standardBReceiver);
    }
}