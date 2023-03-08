package com.example.jetpack.base.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jetpack.R;

public class CommonWidgetActivity extends AppCompatActivity{

    private MyChronometer chronometer;
    private MyTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // 获取相关控件
        chronometer = findViewById(R.id.chronometer);
        textView = findViewById(R.id.textView);

        // 给当前的activity的生命周期方法添加观察者
        getLifecycle().addObserver(chronometer);
        getLifecycle().addObserver(textView);

    }
}