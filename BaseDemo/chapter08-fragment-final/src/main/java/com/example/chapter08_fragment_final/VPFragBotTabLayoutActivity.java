package com.example.chapter08_fragment_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chapter08_fragment_final.adapter.ViewPagerFragmentBottomNavAdapter;
import com.example.chapter08_fragment_final.fragment.VPFragment;
import com.example.chapter08_fragment_final.fragment.VPHomeFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class VPFragBotTabLayoutActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpfrag_bot_table_layout);

        viewPager = findViewById(R.id.view_page_fragment_bottom_nav_tableLayout);
        bottomNavigationView = findViewById(R.id.bottom_menu);

        initData();

        ViewPagerFragmentBottomNavAdapter adapter = new ViewPagerFragmentBottomNavAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        // 联动
        viewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // 设置消息数
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.menu_personal);
        badge.setNumber(100);
        badge.setMaxCharacterCount(3);

        BadgeDrawable badge2 = bottomNavigationView.getOrCreateBadge(R.id.menu_find);
    }

    private void initData() {
        VPHomeFragment homeFragment = VPHomeFragment.newInstance("首页", null);
        VPFragment fragment2 = VPFragment.newInstance("发现", null);
        VPFragment fragment3 = VPFragment.newInstance("我的", null);

        fragmentList = new ArrayList<>();
        fragmentList.add(homeFragment);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
    }

    private void onPagerSelected(int position) {
        switch (position){
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.menu_find);
                break;
            case 2:
                // 消除消息
                bottomNavigationView.removeBadge(R.id.menu_personal);
                bottomNavigationView.setSelectedItemId(R.id.menu_personal);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onPagerSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.menu_find:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menu_personal:
                viewPager.setCurrentItem(2);
                break;
        }
        return true;
    }
}