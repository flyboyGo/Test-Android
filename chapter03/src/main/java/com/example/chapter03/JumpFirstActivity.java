package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JumpFirstActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_first);
        findViewById(R.id.btn_jump_first).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 创建一个意图对象，准备跳到指定的活动页面
        Intent intent = new Intent(JumpFirstActivity.this, JumpSecondActivity.class);
        // 栈中存在待跳转的活动实例时，则重新创建活动实例，并清除原有实例上方的所有实例
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*
       启动标志的取值说明如下：
       Intent.FLAG_ACTIVITY_NEW_TASK：开辟一个新的任务栈
       Intent.FLAG_ACTIVITY_SINGLE_TOP：当栈顶为待跳转的活动实例之时，则重用栈顶的实例
       Intent.FLAG_ACTIVITY_CLEAR_TOP：当栈中存在待跳转的活动实例时，则重新创建一个新实例，并清除原实例上方的所有实例
       Intent.FLAG_ACTIVITY_NO_HISTORY：栈中不保存新启动的活动实例
       Intent.FLAG_ACTIVITY_CLEAR_TASK：跳转到新页面时，栈中的原有实例都被清空
     */
}