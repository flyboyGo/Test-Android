package com.example.chapter08_fragment_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.view_page_simple_use).setOnClickListener(this);
        findViewById(R.id.view_page_fragment_simple_use).setOnClickListener(this);
        findViewById(R.id.view_page_fragment_bottom_nav).setOnClickListener(this);
        findViewById(R.id.view_page_fragment_bottom_nav_tableLayout).setOnClickListener(this);
        findViewById(R.id.fragment_side_slide).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_page_simple_use:
                Intent intent = new Intent(MainActivity.this,ViewPagerSimpleActivity.class);
                startActivity(intent);
                break;
            case R.id.view_page_fragment_simple_use:
                Intent intent2 = new Intent(MainActivity.this,ViewPageFragmentActivity.class);
                startActivity(intent2);
                break;
            case R.id.view_page_fragment_bottom_nav:
                Intent intent3 = new Intent(MainActivity.this,ViewPagerFragmentBottomNavActivity.class);
                startActivity(intent3);
                break;
            case R.id.view_page_fragment_bottom_nav_tableLayout:
                Intent intent4 = new Intent(MainActivity.this, VPFragBotTabLayoutActivity.class);
                startActivity(intent4);
                break;
            case R.id.fragment_side_slide:
                Intent intent5 = new Intent(MainActivity.this,FragmentDrawerActivity.class);
                startActivity(intent5);
                break;
        }
    }
}