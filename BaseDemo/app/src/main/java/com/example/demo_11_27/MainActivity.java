package com.example.demo_11_27;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置此Activity使用那个布局文件(xml)
        setContentView(R.layout.activity_main);
        //通过findViewById函数，并指定控件的id，获取到控件对象
        TextView tv = this.findViewById(R.id.tv);
        //设置控件显示的文本内容
        tv.setText("welcome to android");

        //设置按钮的监听事件
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}