package com.example.chapter08_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.chapter08_fragment.adapter.LaunchImproveAdapter;

public class LaunchImproveActivity extends AppCompatActivity {

    // 声明引导页面的图片数组
    private final int[] lanuchImageArray = {R.drawable.guide_bg1,
            R.drawable.guide_bg2, R.drawable.guide_bg3, R.drawable.guide_bg4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_improve);

        ViewPager vp_launch = findViewById(R.id.vp_launch);
        // 获取适配器、设置适配器
        LaunchImproveAdapter launchImproveAdapter = new LaunchImproveAdapter(getSupportFragmentManager(), lanuchImageArray);
        vp_launch.setAdapter(launchImproveAdapter);
    }
}