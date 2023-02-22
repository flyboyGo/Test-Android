package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ActSendActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_send);
        tv_send = findViewById(R.id.tv_send);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ActReceiveActivity.class);

        // 创建一个新的包裹Bundle对象
        Bundle bundle= new Bundle();
        bundle.putString("request_time", DateUtil.getNowTime());
        bundle.putString("request_content", tv_send.getText().toString());

        // intent添加bundle对象
        intent.putExtras(bundle);

        // intent直接添加字符串
        intent.putExtra("request_string","just do it");

        // 跳转页面
        startActivity(intent);
    }
}