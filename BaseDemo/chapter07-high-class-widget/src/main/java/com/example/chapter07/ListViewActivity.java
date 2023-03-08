package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chapter07.adapter.PlanetBaseAdapter;
import com.example.chapter07.bean.Planet;
import com.example.chapter07.util.ToastUtil;
import com.example.chapter07.util.Utils;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private List<Planet> planetList;
    private CheckBox ck_divider;
    private CheckBox ck_selector;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        // 获取适配器、设置适配器
        PlanetBaseAdapter planetBaseAdapter = new PlanetBaseAdapter(this, planetList);
        lv_planet.setAdapter(planetBaseAdapter);
        // 设置条目项点击事件
        lv_planet.setOnItemClickListener(this);

        
        ck_divider = findViewById(R.id.ck_divider);
        ck_selector = findViewById(R.id.ck_selector);
        ck_divider.setOnClickListener(this);
        ck_selector.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this,"你选择的是:" + planetList.get(position).name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ck_divider:
                if(ck_divider.isChecked())
                {
                    // 获取当前主题中的黑色，设置为Drawable对象
                    Drawable drawable = getResources().getDrawable(R.color.black, getTheme());
                    lv_planet.setDivider(drawable);
                    // 设置分割线的高度
                    lv_planet.setDividerHeight(Utils.dipToPx(this,1));
                }
                else
                {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(0);
                }
                break;
            case R.id.ck_selector:
                // 显示按压背景
                if(ck_selector.isChecked())
                {
                    // 设置列表项的按压状态图形
                    lv_planet.setSelector(R.drawable.list_selector);
                }
                else
                {
                    Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                    lv_planet.setSelector(drawable);
                }
                break;
        }
    }
}