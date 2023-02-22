package com.example.chapter01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter01.R;
import com.example.chapter01.adapter.BillPageAdapter;
import com.example.chapter01.database.BillDBHelper;
import com.example.chapter01.enity.BillInfo;
import com.example.chapter01.util.DateUtil;

import java.util.Calendar;

public class BillPagerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, ViewPager.OnPageChangeListener {

    private TextView tv_month;
    private Calendar calendar;
    private ViewPager vp_bill;
    private BillDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);

        // 获取基本信息控件、设置信息
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_title.setText("账单列表");
        tv_option.setText("添加账单");

        // 设置跳转事件
        tv_option.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        // 显示当前日期
        tv_month = findViewById(R.id.tv_month);
        calendar = Calendar.getInstance();
        tv_month.setText(DateUtil.getMonth(calendar));

        // 设置点击时间、弹出日期对话框
        tv_month.setOnClickListener(this);

        // 打开数据库链接
        dbHelper = BillDBHelper.getInstance(this);
        dbHelper.openReadLink();
        dbHelper.openWriteLink();

        // 初始化翻页视图
        initViewPage();
    }

    // 初始化翻页视图
    private void initViewPage() {
        // 从布局视图中获取名叫pts_bill的翻页标签栏
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        // 设置翻页标签栏的文本大小
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        // 获取翻页
        vp_bill = findViewById(R.id.vp_bill);
        // 获取适配器、设置适配器,要通过构造传入年份
        BillPageAdapter billPageAdapter = new BillPageAdapter(getSupportFragmentManager(),calendar.get(Calendar.YEAR));
        vp_bill.setAdapter(billPageAdapter);
        // 设置默认选项
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
        // 给翻页视图添加页面变更的监听器
        vp_bill.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_month:
                // 弹出日期对话框
                DatePickerDialog dialog = new DatePickerDialog(this,this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
                break;
            case R.id.tv_option:
                Intent intent = new Intent(this,BillAddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.iv_back:
                // 关闭当前页面
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置用户选择的日期时间
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tv_month.setText(DateUtil.getMonth(calendar));
        // 设置翻页视图显示第几页
        vp_bill.setCurrentItem(month);
    }

    @Override
    public void onPageSelected(int position) {
        calendar.set(Calendar.MONTH,position);
        tv_month.setText(DateUtil.getMonth(calendar));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 不需要关闭数据库,主界面是BillAddActivity,会重复关闭数据库
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dbHelper.closeLink();
//    }

}