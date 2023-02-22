package com.example.chapter08_fragment_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chapter08_fragment_final.adapter.ViewPageSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSimpleActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<ImageView> imageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_simple);

        initData();

        viewPager = findViewById(R.id.view_page_simple);
        ViewPageSimpleAdapter adapter = new ViewPageSimpleAdapter(imageViewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initData() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.guide_bg1);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.guide_bg2);
        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(R.drawable.guide_bg3);
        ImageView imageView4 = new ImageView(this);
        imageView4.setImageResource(R.drawable.guide_bg4);

        imageViewList = new ArrayList<>();
        imageViewList.add(imageView);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        imageViewList.add(imageView4);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(ViewPagerSimpleActivity.this,"选中了"+position+"页",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}