package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JumpSecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_second);
        findViewById(R.id.btn_jump_second).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 创建一个意图对象，准备跳到指定的活动页面
        Intent intent = new Intent(JumpSecondActivity.this, JumpFirstActivity.class);
        // 栈中存在待跳转的活动实例时，则重新创建活动实例，并清除原有实例上方的所有实例
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}