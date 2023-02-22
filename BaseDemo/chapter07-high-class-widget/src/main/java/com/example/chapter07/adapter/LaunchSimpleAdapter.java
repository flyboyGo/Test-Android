package com.example.chapter07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter07.R;

import java.util.ArrayList;
import java.util.List;

public class LaunchSimpleAdapter extends PagerAdapter {

    // 视图列表
    private List<View> viewList = new ArrayList<>();

    // 引导页适配器的构造方法，传入上下文与图片数组
    public LaunchSimpleAdapter(Context context, int[] imageArray)
    {
        for (int i = 0; i < imageArray.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_launch, null);
            // 获取布局中的各个控件
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup radioGroup = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);

            // 设置图片页面
            iv_launch.setImageResource(imageArray[i]);

            // 每个页面都分配一组对应的单选按钮
            for (int j = 0; j < imageArray.length; j++) {
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
            ((RadioButton)radioGroup.getChildAt(i)).setChecked(true);

            // 如果是最后一个引导页，则显示入口按钮，以便用户点击按钮进入主页
            if(i == imageArray.length - 1)
            {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "欢迎你开启美好生活", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            viewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View item = viewList.get(position);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }
}
