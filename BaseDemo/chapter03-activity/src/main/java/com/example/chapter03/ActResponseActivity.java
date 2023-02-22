package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_request_info;
    private TextView tv_response_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_response);

        tv_request_info = findViewById(R.id.tv_request);
        tv_response_info = findViewById(R.id.tv_response);

        // 本页面接收上一个页面发送的数据,并解析请求数据，进行相应处理
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        // 格式化字符串，将请求的信息显示到页面当中
        String desc = String.format("收到请求消息:\n请求时间: %s\n请求内容: %s", request_time,request_content);
        tv_request_info.setText(desc);

        // 按钮添加事件监听
        findViewById(R.id.btn_response).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // 本页面在返回上一个页面时，打包应答数据并调用setResult方法返回数据包裹
        Intent intent = new Intent();
        Bundle bundle2 = new Bundle();
        bundle2.putString("response_time", DateUtil.getNowTime());
        bundle2.putString("response_content", tv_response_info.getText().toString());
        // 意图添加数据
        intent.putExtras(bundle2);

        // 设置携带意图返回上一个页面，Activity.RESULT_Ok 表示处理成功
        setResult(Activity.RESULT_OK,intent);

        // 结束当前页面
        finish();
    }
}