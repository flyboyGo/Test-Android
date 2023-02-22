package com.example.chapter08_fragment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter08_fragment.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentPagerAdapter {

    private  final  int [] imageArray;

    public LaunchImproveAdapter(@NonNull FragmentManager fm,int[] imageArray) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.imageArray = imageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return LaunchFragment.newInstance(position,imageArray[position],imageArray.length);
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }
}
