package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ActionUriActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_uri);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_msg).setOnClickListener(this);
        findViewById(R.id.btn_my).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        String phoneNo = "12345";
        if(view.getId() == R.id.btn_phone)
        {
            // 设置意图动作为准备拨号
            intent.setAction(Intent.ACTION_DIAL);
            // 声明一个拨号的uri
            Uri uri = Uri.parse("tel:" + phoneNo);
            intent.setData(uri);
        }
        else if(view.getId() == R.id.btn_msg)
        {
            // 设置意图动作为发短信
            intent.setAction(Intent.ACTION_SENDTO);
            // 声明一个发短信的uri
            Uri uri = Uri.parse("smsto:" + phoneNo);
            intent.setData(uri);

        }
        else if(view.getId() == R.id.btn_my)
        {
            // 设置意图动作为自定义的页面
            intent.setAction("android.intent.action.calculator");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        }
        startActivity(intent);
    }
}