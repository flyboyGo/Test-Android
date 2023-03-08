package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.chapter07.adapter.ViewPagerAdapter;
import com.example.chapter07.bean.Goods;

import java.util.ArrayList;

public class ViewPageTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<Goods> goodsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_tab);

        initPageStrip();

        initViewPage();
    }

    // 初始化翻页标签栏
    private void initPageStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tap);
        // 设置翻页标签栏的文本大小
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        pts_tab.setTextColor(Color.BLACK);
    }

    // 初始化翻页视图
    private void initViewPage() {

        ViewPager vp_content = findViewById(R.id.vp_content);
        // 获取信息列表
        goodsArrayList = Goods.getDefaultList();
        // 获取一个适配器
        ViewPagerAdapter imagePagerAdapter = new ViewPagerAdapter(this,goodsArrayList);
        // 设置适配器
        vp_content.setAdapter(imagePagerAdapter);
        // 给翻页视图添加页面变更的监听器
        vp_content.addOnPageChangeListener(this);
        // 设置默认选择项
        vp_content.setCurrentItem(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "你翻到的手机品牌是:" + goodsArrayList.get(position).name , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}