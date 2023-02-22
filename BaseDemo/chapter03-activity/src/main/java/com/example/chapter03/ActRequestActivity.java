package com.example.chapter03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chapter03.util.DateUtil;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_request_info;
    private TextView tv_response_info;
    ActivityResultLauncher<Intent> register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_request);

        tv_request_info = findViewById(R.id.tv_request);
        tv_response_info = findViewById(R.id.tv_response);

        findViewById(R.id.btn_request).setOnClickListener(this);

        // 新方法,两个页面之间传递信息
        // 注册器
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result != null)
                {
                    Intent data = result.getData();
                    if(data != null && result.getResultCode() == Activity.RESULT_OK)
                    {
                        Bundle bundle = data.getExtras();
                        String response_time = bundle.getString("response_time");
                        String response_content = bundle.getString("response_content");
                        // 格式化字符串
                        String desc = String.format("收到响应消息:\n响应时间: %s\n响应内容: %s", response_time,response_content);
                        // 将响应的数据显示到页面当中
                        tv_response_info.setText(desc);
                    }
                }
            }
        });
    }

    /*
       向上一个Activity 返回数据

       过时了!!!!!
       处理下一个页面的应答数据，详细步骤说明如下：
       上一个页面打包好请求数据，调用startActivityForResult方法执行跳转动作
       下一个页面接收并解析请求数据，进行相应处理
       下一个页面在返回上一个页面时，打包应答数据并调用setResult方法返回数据包裹
       上一个页面重写方法onActivityResult，解析获得下一个页面的返回数据

     */

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ActResponseActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("request_time", DateUtil.getNowTime());
        bundle.putString("request_content",tv_request_info.getText().toString());

        // 意图添加Bundle
        intent.putExtras(bundle);

        // 本页面打包好请求数据，调用startActivityForResult方法执行跳转动作(过时了!!!!!!!!!!)
        // startActivityForResult(intent,1);

        // 最新方法
        register.launch(intent);

    }

    // 重写方法onActivityResult，解析获得下一个页面的返回数据(过时了!!!!!!!!!!!)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        String response_time = bundle.getString("response_time");
        String response_content = bundle.getString("response_content");
        // 格式化字符串
        String desc = String.format("收到响应消息:\n响应时间: %s\n响应内容: %s", response_time,response_content);
        // 将响应的数据显示到页面当中
        tv_response_info.setText(desc);
    }
}