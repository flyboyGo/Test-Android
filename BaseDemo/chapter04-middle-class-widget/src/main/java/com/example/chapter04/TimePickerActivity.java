package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private  TimePicker timePicker;
    TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        timePicker = findViewById(R.id.dp_time);
        timePicker.setIs24HourView(true);
        tv_time = findViewById(R.id.tv_time);

        findViewById(R.id.btn_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                String desc = String.format("您选择的日期是%d时%d分",timePicker.getHour(),timePicker.getMinute());
                tv_time.setText(desc);
                break;
            case R.id.btn_time:
                // 获取一个日历的实例，里面包含了当前的年月日、时分秒
                Calendar calendar = Calendar.getInstance();
                calendar.get(Calendar.YEAR);
                calendar.get(Calendar.MONTH);
                calendar.get(Calendar.DAY_OF_MONTH);
                // 构建一个时间对话框，该对话框已经集成了时间选择器
                TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
                dialog.show();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String desc = String.format("您选择的日期是%d时%d分",i,i1);
        tv_time.setText(desc);
    }
}