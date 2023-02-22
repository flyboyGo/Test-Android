package com.example.chapter08_fragment_final.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPageSimpleAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;

    public ViewPageSimpleAdapter(List<ImageView> imageViewList) {
        super();
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList == null ? 0 : imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = imageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViewList.get(position));
    }
}
