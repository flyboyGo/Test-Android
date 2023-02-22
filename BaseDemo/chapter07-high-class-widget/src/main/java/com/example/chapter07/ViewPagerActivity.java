package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chapter07.adapter.ViewPagerAdapter;
import com.example.chapter07.bean.Goods;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<Goods> goodsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager vp_content = findViewById(R.id.vp_content);
        // 获取信息列表
        goodsArrayList = Goods.getDefaultList();
        // 获取一个适配器
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,goodsArrayList);
        // 设置适配器
        vp_content.setAdapter(viewPagerAdapter);

        // 给翻页视图添加页面变更的监听器(注意!!!!!)
        vp_content.addOnPageChangeListener(this);
    }


    // 翻页状态改变时触发。state取值说明为：0表示静止，1表示正在滑动，2表示滑动完毕
    // 在翻页过程中，状态值变化依次为：正在滑动→滑动完毕→静止
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 在翻页过程中触发。该方法的三个参数取值说明为 ：第一个参数表示当前页面的序号
    // 第二个参数表示页面偏移的百分比，取值为0到1；第三个参数表示页面的偏移距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 在翻页结束后触发。position表示当前滑到了哪一个页面
    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "你翻到的手机品牌是:" + goodsArrayList.get(position).name , Toast.LENGTH_SHORT).show();
    }

}