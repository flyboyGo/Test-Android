package com.example.chapter11_thread.view_pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter11_thread.R;

public class ViewPagerAdapterPlus extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] data;

    public ViewPagerAdapterPlus(Context context, int []data)
    {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        // 设置渲染无数个视图
        return Integer.MAX_VALUE;
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
        RadioGroup radioGroup = (RadioGroup) layout.findViewById(R.id.rg_indicate);

        for (int j = 0; j < data.length; j++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            // 设置单选按钮的间距、并添加到按钮组中(RadioGroup)
            radioButton.setPadding(10,10,10,10);
            radioGroup.addView(radioButton);
        }

        // 当前位置的单选按钮要高亮显示，比如第二个引导页就高亮第二个单选按钮
        ((RadioButton)radioGroup.getChildAt(position % data.length)).setChecked(true);

        // 图片控件
        ImageView iv = (ImageView)layout.findViewById(R.id.item_iv);
        // 模拟异步任务从网络获取
        // 设置显示加载中的等待图片
        iv.setImageResource(R.drawable.image_load);
        BitmapTask bitmapTask = new BitmapTask(context,iv);
        // 传递执行异步任务所需的参数
        // 对position进行取余(防止数组下标越界错误)
        bitmapTask.execute(data[position % data.length]);

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
