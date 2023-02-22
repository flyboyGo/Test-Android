package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReadStringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_string);

        TextView tv =  findViewById(R.id.tv_resource);

        // 从strings.xml中获取叫weather_str的字符串的值
        String value = getString(R.string.weather_str);
        tv.setText(value);
    }
}