package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter02.util.DateUtil;

public class ButtonEnableActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn, btn2, btn3;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_enable);

        btn = findViewById(R.id.btn_enable);
        btn2 = findViewById(R.id.btn_disable);
        btn3 = findViewById(R.id.btn_test);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_enable:
                btn3.setEnabled(true);
                btn3.setTextColor(Color.BLACK);
                break;
            case R.id.btn_disable:
                btn3.setEnabled(false);
                btn3.setTextColor(Color.GRAY);
                break;
            case R.id.btn_test:
                String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
                tv.setText(desc);
                break;
        }
    }
}