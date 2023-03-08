package com.example.chapter07.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter07.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context context;
    // 商品信息表
    private final ArrayList<Goods> goodsArrayList;

    // 声明一个图像视图列表
    private List<ImageView> viewList = new ArrayList<>();

    public ViewPagerAdapter(Context context, ArrayList<Goods> goodsArrayList) {
        this.context = context;
        this.goodsArrayList = goodsArrayList;

        for (Goods goods : goodsArrayList) {
            ImageView imageView = new ImageView(context);
            // 在代码中设置图片控件的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            // 设置图片控件的资源
            imageView.setImageResource(goods.pic);
            // 向图像视图列表中添加视图
            viewList.add(imageView);
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

    // 实例化制定位置的页面，添加到容器中
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 添加一个view到container中，而后返回一个跟这个view可以关联起来的对象
        // 这个对象可以是view自身,也可以是其余对象
        // 关键是在isViewFromObject可以将view和这个Object关联起来
        ImageView item = viewList.get(position);
        container.addView(item);
        return  item;
    }

    // 从容器中销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
    }

    // 服务于标签栏,设置标签栏的文本
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return goodsArrayList.get(position).name;
    }
}
