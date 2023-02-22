package com.example.chapter09_broadcastreceiver_max;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver_max.receiver.LocalBroadCastReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyBroadCastReceiver broadCastReceiver;
    private LocalBroadCastReceiver localBroadCastReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_turn_to).setOnClickListener(this);
        findViewById(R.id.btn_send_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_send_static_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_send_order_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_send_local_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_send_whole_broadcast).setOnClickListener(this);

        broadCastReceiver = new MyBroadCastReceiver();
        localBroadCastReceiver = new LocalBroadCastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("MainActivity send broadcast");
        filter.addAction("SecondActivity send broadcast");
        registerReceiver(broadCastReceiver,filter);

        // 注册本地广播接收器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("local_broad_cast");
        localBroadcastManager.registerReceiver(localBroadCastReceiver,filter2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadCastReceiver);

        // 销毁本地广播接收器
        localBroadcastManager.unregisterReceiver(localBroadCastReceiver);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_turn_to:
                Intent intent = new Intent(this,SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_broadcast:
                Intent intent2 = new Intent();
                intent2.putExtra("msg","主页面发送的广播消息");
                intent2.setAction("MainActivity send broadcast");
                sendBroadcast(intent2);
                break;
            case R.id.btn_send_static_broadcast:
                Intent intent3 = new Intent();
                intent3.setAction("static_broadcast_receiver");
                // Android 8.0及以上禁止了后台执行，因此无法收到静态注册的隐式广播。
                // 设置可以处理此广播的广播接收器的包名
                intent3.setPackage(getPackageName());
                // 添加可后台执行的flag: intent.addFlags(Ox01000000)
                // intent3.setFlags(0X01000000);
                sendBroadcast(intent3);
                break;
            case R.id.btn_send_order_broadcast:
                Intent intent4 = new Intent();
                intent4.setAction("order_broadcast_receiver");
                intent4.setPackage(getPackageName());
                Bundle bundle = new Bundle();
                bundle.putString("original","发送的源头广播");
                intent4.putExtras(bundle);
                sendOrderedBroadcast(intent4,null);
                break;
            case R.id.btn_send_local_broadcast:
                Intent intent5 = new Intent();
                intent5.setAction("local_broad_cast");
                localBroadcastManager.sendBroadcast(intent5);
                break;
            case R.id.btn_send_whole_broadcast:
                Intent intent6 = new Intent();
                intent6.setAction("whole_broad_cast");
                sendBroadcast(intent6);
                break;
        }
    }

    private class MyBroadCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}