package com.example.viewgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowLayout flowLayout = this.findViewById(R.id.flow_layout);
        List<String> data = new ArrayList<>();
        data.add("手机");
        data.add("ipad");
        data.add("鼠标");
        data.add("键盘");
        data.add("watch");
        data.add("MacPro");
        data.add("air podair podair podair podair podair podair podair podpodair podpodair pod");
        data.add("显示器");
        data.add("男鞋");
        data.add("ipad");
        data.add("鼠标");
        data.add("键盘");
        data.add("watch");
        data.add("MacPro");
        data.add("ipad");
        data.add("鼠标");
        data.add("键盘");
        data.add("watch");
        data.add("MacPro");
        data.add("男鞋");
        data.add("ipad");
        data.add("鼠标");
        data.add("键盘");
        data.add("watch");
        data.add("MacPro");
        data.add("ipad");
        data.add("鼠标");
        flowLayout.setTextList(data);
        flowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, String text) {
                Log.d("test","setOnItemClickListener == > " + text);
            }
        });

        flowLayout.setMaxLines(4);
    }
}