package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chapter07.adapter.PlanetListWithButtonAdapter;
import com.example.chapter07.bean.Planet;
import com.example.chapter07.util.ToastUtil;

import java.util.List;

public class ListFocusActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_focus);

        ListView lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        // 获取适配器、设置适配器
        PlanetListWithButtonAdapter planetListWithButtonAdapter = new PlanetListWithButtonAdapter(this,planetList);
        lv_planet.setAdapter(planetListWithButtonAdapter);
        // 设置条目项点击事件
        lv_planet.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this,"条目被点击了," + planetList.get(position).name);
    }
}