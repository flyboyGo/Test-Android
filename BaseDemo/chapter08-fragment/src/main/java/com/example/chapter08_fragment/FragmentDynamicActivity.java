package com.example.chapter08_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import com.example.chapter08_fragment.adapter.MobilePageAdapter;
import com.example.chapter08_fragment.bean.Goods;

import java.util.ArrayList;

public class FragmentDynamicActivity extends AppCompatActivity {

    private ArrayList<Goods> goodsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_dynamic);

        // 设置翻页标签栏的文本大小，但不设置文本等内容
        initPageStrip();

        // 初始化翻页视图
        initViewPage();
    }

    // 设置翻页标签栏的文本大小，但不设置文本等内容
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
        MobilePageAdapter mobilePageAdapter = new MobilePageAdapter(getSupportFragmentManager(), goodsArrayList);
        // 设置适配器
        vp_content.setAdapter(mobilePageAdapter);
        // 给翻页视图添加页面变更的监听器
        // vp_content.addOnPageChangeListener(this);
        // 设置默认选择项
        // vp_content.setCurrentItem(3);
    }
}