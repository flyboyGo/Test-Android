package com.example.chapter08_fragment_final.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter08_fragment_final.R;
import com.example.chapter08_fragment_final.adapter.ViewPagerFragmentTabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VPHomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public VPHomeFragment() {
    }

    public static VPHomeFragment newInstance(String param1, String param2) {
        VPHomeFragment fragment = new VPHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_v_p_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.tab_layout_fragment_container);
        tabLayout = view.findViewById(R.id.tab_layout);

        initData();

        ViewPagerFragmentTabLayoutAdapter adapter = new ViewPagerFragmentTabLayoutAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);

        // tabLayout 与 viewpager 发生联动
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        VPFragment fragment1 = VPFragment.newInstance("推荐", null);
        VPFragment fragment2 = VPFragment.newInstance("关注", null);
        VPFragment fragment3 = VPFragment.newInstance("娱乐", null);
        VPFragment fragment4 = VPFragment.newInstance("军事", null);
        VPFragment fragment5 = VPFragment.newInstance("音乐", null);
        VPFragment fragment6 = VPFragment.newInstance("海外", null);
        VPFragment fragment7 = VPFragment.newInstance("政治", null);

        fragmentList = new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);
        fragmentList.add(fragment6);
        fragmentList.add(fragment7);

        titleList = new ArrayList<>();
        titleList.add("推荐");
        titleList.add("关注");
        titleList.add("娱乐");
        titleList.add("军事");
        titleList.add("音乐");
        titleList.add("海外");
        titleList.add("政治");
    }
}