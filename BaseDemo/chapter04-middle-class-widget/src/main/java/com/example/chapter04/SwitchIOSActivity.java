package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SwitchIOSActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_iosactivity);
        CheckBox sw_status = findViewById(R.id.ck_status);
        tv_result = findViewById(R.id.tv_result);
        sw_status.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        String desc = String.format("防ios开关的状态是%s", isChecked ? "开" : "关");
        tv_result.setText(desc);
    }
}