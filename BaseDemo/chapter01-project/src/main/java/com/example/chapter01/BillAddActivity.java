package com.example.chapter01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter01.database.BillDBHelper;
import com.example.chapter01.enity.BillInfo;
import com.example.chapter01.util.DateUtil;

import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_date;
    private Calendar calendar;
    private RadioGroup rg_type;
    private EditText et_remark;
    private EditText et_amount;
    private BillDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);

        // 获取基本信息控件、设置信息
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_title.setText("请填写账单");
        tv_option.setText("账单列表");

        // 显示当前日期
        tv_date = findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();
        tv_date.setText(DateUtil.getDate(calendar));

        // 设置跳转事件
        tv_option.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        // 设置点击时间、弹出日期对话框
        tv_date.setOnClickListener(this);

        // 获取账单类型、说明、金额等显示控件
        rg_type = findViewById(R.id.rg_type);
        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_amount);

        // 保存按钮事件监听
        findViewById(R.id.btn_save).setOnClickListener(this);

        // 获取数据库帮助器
        dbHelper = BillDBHelper.getInstance(this);
        // 打开数据库的读写链接
        dbHelper.openReadLink();
        dbHelper.openWriteLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_date:
                // 弹出日期对话框
                DatePickerDialog dialog = new DatePickerDialog(this,this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.btn_save:
                // 保存订单信息,创建一个对应的实体类对象
                BillInfo bill = new BillInfo();
                bill.date = tv_date.getText().toString();
                bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
                               BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
                bill.remark = et_remark.getText().toString();
                bill.amount = Double.parseDouble(et_amount.getText().toString());
                if(dbHelper.save(bill) > 0)
                {
                    Toast.makeText(this,"添加账单成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_option:
                Intent intent = new Intent(this,BillPagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.iv_back:
                // 关闭当前页面
                finish();
                break;
        }
    }

    // 获取用户在日期弹出框中选择的时间
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置用户选择的日期时间
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        tv_date.setText(DateUtil.getDate(calendar));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 关闭数据库链接
        dbHelper.closeLink();
    }
}