package com.example.chapter16_page_design;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter16_page_design.child.ViewPagerFragmentTabLayoutAdapter;
import com.example.chapter16_page_design.child.MusicChildFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_music, container, false);

        initWidget(view);

        initFragment();

        ViewPagerFragmentTabLayoutAdapter adapter = new ViewPagerFragmentTabLayoutAdapter(getChildFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void initWidget(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.tab_layout_fragment_container);
    }

    private void initFragment(){
        MusicChildFragment fragment = MusicChildFragment.newInstance("全部");
        MusicChildFragment fragment2 = MusicChildFragment.newInstance("清晨");
        MusicChildFragment fragment3 = MusicChildFragment.newInstance("夜晚");
        MusicChildFragment fragment4 = MusicChildFragment.newInstance("学习");
        MusicChildFragment fragment5 = MusicChildFragment.newInstance("专注");
        MusicChildFragment fragment6 = MusicChildFragment.newInstance("午休");

        fragmentList = new ArrayList<>();
        fragmentList.add(fragment);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);

        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("清晨");
        titleList.add("夜晚");
        titleList.add("学习");
        titleList.add("专注");
        titleList.add("午休");
    }
}