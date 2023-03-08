package com.example.chapter11_thread.view_pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter11_thread.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] data;

    public ViewPagerAdapter(Context context,int []data)
    {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    // 渲染每一个页的数据
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        // 动态加载布局文件
        View layout = layoutInflater.inflate(R.layout.viewpager_item,null);
        // 获取布局文件中的组件
        ImageView iv = (ImageView)layout.findViewById(R.id.item_iv);

        // 方式一: 设置显示的图片(本地)
        // iv.setImageResource(data[position]);

        // 方式二: 模拟异步任务从网络获取
        // 设置显示加载中的等待图片
        iv.setImageResource(R.drawable.image_load);
        BitmapTask bitmapTask = new BitmapTask(context,iv);
        // 传递执行异步任务所需的参数
        bitmapTask.execute(data[position]);

        // 添加到ViewPage容器中
        container.addView(layout);
        // 返回渲染完成的视图
        return layout;
    }

    // 销毁数据
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
