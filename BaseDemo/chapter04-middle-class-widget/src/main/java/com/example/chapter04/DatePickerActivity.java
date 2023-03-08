package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private DatePicker dp_date;
    private TextView tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);

        tv_date = findViewById(R.id.tv_date);
        dp_date = findViewById(R.id.dp_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:
                String desc = String.format("您选择的日期是%d年%d月%d日",dp_date.getYear(),dp_date.getMonth() + 1,dp_date.getDayOfMonth());
                tv_date.setText(desc);
                break;
            case R.id.btn_date:
                // 获取一个日历的实例，里面包含了当前的年月日
//                Calendar calendar = Calendar.getInstance();
//                calendar.get(Calendar.YEAR);
//                calendar.get(Calendar.MONTH);
//                calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(this,this,2012,11,31);
                // 显示日期对话框
                dialog.show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String desc = String.format("您选择的日期是%d年%d月%d日",i,i1 + 1,i2);
        tv_date.setText(desc);
    }
}