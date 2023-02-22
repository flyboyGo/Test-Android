package com.example.chapter11_thread.view_pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.chapter11_thread.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    // 自动轮播定时器
    private ScheduledExecutorService scheduledExecutorService;
    // 当前图片的索引号
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager =(ViewPager) findViewById(R.id.view_pager);
        // 适配器
        // 一: 普通适配器
        //ViewPagerAdapter  viewPagerAdapter = new ViewPagerAdapter(this,Image.imageArray);
        //viewPager.setAdapter(viewPagerAdapter);

        // 二: 无限循环适配器(欺骗适配器)
        ViewPagerAdapterPlus viewPagerAdapterPlus = new ViewPagerAdapterPlus(this, Image.imageArray);
        viewPager.setAdapter(viewPagerAdapterPlus);
        // 选择一个较大的条目选中(保证初始状态时,左边具有数据源,可以向左滑动)
        viewPager.setCurrentItem(Image.imageArray.length * 1000, true);

        // 自动轮播时,指定当前页面的索引
        currentIndex = Image.imageArray.length * 1000;


        // 三: 无限循环适配器(构造数据源)
        //ViewPagerAdapterExtra viewPagerAdapterExtra = new ViewPagerAdapterExtra(this,Image.imageArray2);
        //viewPager.setAdapter(viewPagerAdapterExtra);
        // 选择第二个条目(实际顺序的第一个元素)
        //viewPager.setCurrentItem(1,true);

        // 给翻页视图添加页面变更的监听器(注意!!!!!)
        viewPager.addOnPageChangeListener(this);
    }

    private Handler handler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    // 轮播图片
                    currentIndex++;
                    // 刷新控件(切换到主线程)
                    viewPager.setCurrentItem(currentIndex);
                    break;
                default:
                    break;
            }
        }
    };

    // 界面已经形成时,可见时,开始自动轮播(每3秒,切换以下)
    @Override
    protected void onStart() {
        super.onStart();

        // 初始化轮播定时器
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                // handler.sendEmptyMessage(1);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 轮播图片
                        currentIndex++;
                        // 刷新控件(切换到主线程)
                        viewPager.setCurrentItem(currentIndex);
                    }
                });
            }
        }, 3,3, TimeUnit.SECONDS);
    }

    // 当界面不可见时,轮播停止切换
    @Override
    protected void onStop() {
        super.onStop();
        if(scheduledExecutorService != null)
        {
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 方式三:
//        if(position == Image.imageArray2.length - 1)
//        {
//            // 当前视图为最后1个,将页面的索引设置为数据源的第2个
//            viewPager.setCurrentItem(1,true);
//        }
//        else if(position == 0)
//        {
//            // 当前视图为第1个,将页面的索引设置为数据源的倒数第2个
//            viewPager.setCurrentItem(Image.imageArray2.length - 2,true);
//        }


        // 自动轮播时,人为介入时,指定当前页面的索引
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}