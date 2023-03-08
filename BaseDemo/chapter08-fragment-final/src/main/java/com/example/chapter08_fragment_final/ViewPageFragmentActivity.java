package com.example.chapter08_fragment_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chapter08_fragment_final.adapter.ViewPageFragmentAdapter;
import com.example.chapter08_fragment_final.fragment.VPFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPageFragmentActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private ImageView home,find,personal;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_fragment);

        initData();

        home = findViewById(R.id.home);
        home.setSelected(true);
        find = findViewById(R.id.find);
        personal = findViewById(R.id.personal);
        linearLayout = findViewById(R.id.iv_container);

        viewPager = findViewById(R.id.view_page_fragment);
        ViewPageFragmentAdapter adapter = new ViewPageFragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        home.setOnClickListener(this);
        find.setOnClickListener(this);
        personal.setOnClickListener(this);
    }

    private void initData() {
        VPFragment fragment =  VPFragment.newInstance("这是Fragment 1", null);
        VPFragment fragment2 = VPFragment.newInstance("这是Fragment 2", null);
        VPFragment fragment3 = VPFragment.newInstance("这是Fragment 3", null);
        VPFragment fragment4 = VPFragment.newInstance("这是Fragment 4", null);

        fragmentList = new ArrayList<>();
        fragmentList.add(fragment);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
    }

    private void resetSelected()
    {
        home.setSelected(false);
        find.setSelected(false);
        personal.setSelected(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetSelected();
        ImageView imageView =(ImageView) linearLayout.getChildAt(position);
        imageView.setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.find:
                viewPager.setCurrentItem(1);
                break;
            case R.id.personal:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}