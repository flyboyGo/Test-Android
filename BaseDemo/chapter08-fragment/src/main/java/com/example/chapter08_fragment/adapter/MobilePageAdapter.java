package com.example.chapter08_fragment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter08_fragment.bean.Goods;
import com.example.chapter08_fragment.fragment.DynamicFragment;

import java.util.List;

public class MobilePageAdapter extends FragmentPagerAdapter {
    private final  List<Goods> goodsList;

    public MobilePageAdapter(@NonNull FragmentManager fm, List<Goods> goodsList) {
        // 当前只有一个fragment是可用的
        // 会将当前fragment设置为Resume的状态，把上个fragment设置成Start的状态。
        // 从而可以通过fragment的onResume()来懒加载数据。
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.goodsList = goodsList;
    }

    // 返回翻页视图中的每一个fragment视图
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Goods goods = goodsList.get(position);
        return DynamicFragment.newInstance(position,goods.pic,goods.description);
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return goodsList.get(position).name;
    }
}
