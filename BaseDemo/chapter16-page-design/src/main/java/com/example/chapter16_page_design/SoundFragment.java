package com.example.chapter16_page_design;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter16_page_design.child.ViewPagerFragmentTabLayoutAdapter;
import com.example.chapter16_page_design.child.SoundChildFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SoundFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    public SoundFragment() {
    }

    public static SoundFragment newInstance() {
        SoundFragment fragment = new SoundFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sound, container, false);

        initWidget(view);

        initFragment();

        ViewPagerFragmentTabLayoutAdapter adapter = new ViewPagerFragmentTabLayoutAdapter(getChildFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void initFragment() {
        SoundChildFragment fragment = SoundChildFragment.newInstance("全部");
        SoundChildFragment fragment2 = SoundChildFragment.newInstance("自然");
        SoundChildFragment fragment3 = SoundChildFragment.newInstance("动物");
        SoundChildFragment fragment4 = SoundChildFragment.newInstance("植物");
        SoundChildFragment fragment5 = SoundChildFragment.newInstance("器物");
        SoundChildFragment fragment6 = SoundChildFragment.newInstance("特殊");

        fragmentList = new ArrayList<>();
        fragmentList.add(fragment);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);

        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("自然");
        titleList.add("动物");
        titleList.add("植物");
        titleList.add("器物");
        titleList.add("特殊");
    }

    private void initWidget(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.tab_layout_fragment_container);
    }
}