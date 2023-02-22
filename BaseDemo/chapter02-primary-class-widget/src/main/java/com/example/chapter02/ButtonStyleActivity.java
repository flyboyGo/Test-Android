package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter02.util.DateUtil;

public class ButtonStyleActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_style);

        tv = findViewById(R.id.tv);
    }

    public void doClick(View view) {
        System.out.println("按钮被点击了!");
        // tv.setText(DateUtil.getNowTime());

        String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
        tv.setText(desc);
    }


}