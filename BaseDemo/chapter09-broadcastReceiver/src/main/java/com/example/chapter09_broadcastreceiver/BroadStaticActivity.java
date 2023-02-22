package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chapter09_broadcastreceiver.receiver.ShockReceiver;

public class BroadStaticActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_static);
        findViewById(R.id.btn_send_shock).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Android8.0之后删除了大部分静态注册，防止退出App后仍在接收广播，
        // 为了让应用能够继续接收静态广播，需要给静态注册的广播指定包名。
        String fullName = "com.example.chapter09_broadcastreceiver.receiver.ShockReceiver";
        // 发送静态广播之时，需要通过setComponent方法指定接收器的完整路径
        ComponentName componentName = new ComponentName(this,fullName);

        Intent intent = new Intent(ShockReceiver.SHOCK_ACTION);
        // 设置需要发送的组件(精确的全类名)
         intent.setComponent(componentName);
        // 设置需要发送的包名
        // intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }
}