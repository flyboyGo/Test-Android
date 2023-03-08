package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActReceiveActivity extends AppCompatActivity {

    private TextView tv_receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_receive);
        tv_receive = findViewById(R.id.tv_receive);

        // 从上一个页面中发送的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");

        // 从上一个页面中发送的意图中直接获取字符串
        String request_string = getIntent().getStringExtra("request_string");

        // 格式化字符串
        String desc = String.format("收到请求消息:\n请求时间: %s\n请求内容: %s\n请求字符串: %s", request_time,request_content,request_string);

        tv_receive.setText(desc);
    }
}