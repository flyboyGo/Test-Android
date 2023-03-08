package com.example.chapter08_fragment_max;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.chapter08_fragment_max.fragment.NavFragment;

public class FragmentNavActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home, find, personal;
    private FragmentManager fragmentManager;
    private FragmentTransaction beginTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_nav);

        initView();

        initEvent();
    }

    private void initEvent() {
        // 添加fragment
        fragmentManager = getSupportFragmentManager();
        beginTransaction = fragmentManager.beginTransaction();
        NavFragment navFragment = new NavFragment("这是首页");
        beginTransaction.replace(R.id.fragment_container,navFragment).commit();

        home.setOnClickListener(this);
        find.setOnClickListener(this);
        personal.setOnClickListener(this);

        home.setSelected(true);
    }

    private void initView() {
        home = findViewById(R.id.home);
        find = findViewById(R.id.find);
        personal = findViewById(R.id.personal);
    }

    private void resetImageViewState()
    {
        home.setSelected(false);
        find.setSelected(false);
        personal.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                fragmentManager = getSupportFragmentManager();
                beginTransaction = fragmentManager.beginTransaction();
                NavFragment navFragment = new NavFragment("这是首页");
                beginTransaction.replace(R.id.fragment_container,navFragment).commit();
                // 设置选中
                resetImageViewState();
                home.setSelected(true);

                break;
            case R.id.find:
                fragmentManager = getSupportFragmentManager();
                beginTransaction = fragmentManager.beginTransaction();
                NavFragment navFragment2 = new NavFragment("这是发现页面");
                beginTransaction.replace(R.id.fragment_container,navFragment2).commit();
                // 设置选中
                resetImageViewState();
                find.setSelected(true);
                break;
            case R.id.personal:
                fragmentManager = getSupportFragmentManager();
                beginTransaction = fragmentManager.beginTransaction();
                NavFragment navFragment3 = new NavFragment("这是个人页面");
                beginTransaction.replace(R.id.fragment_container,navFragment3).commit();
                // 设置选中
                resetImageViewState();
                personal.setSelected(true);
                break;
            default:
                break;
        }
    }
}