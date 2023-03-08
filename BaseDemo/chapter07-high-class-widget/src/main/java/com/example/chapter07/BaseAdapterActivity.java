package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chapter07.adapter.PlanetBaseAdapter;
import com.example.chapter07.bean.Planet;

import java.util.List;

public class BaseAdapterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);

        Spinner sp_planet = findViewById(R.id.sp_planet);
        // 创建集合
        planetList = Planet.getDefaultList();
        // 构建适配器
        PlanetBaseAdapter planetBaseAdapter = new PlanetBaseAdapter(this, planetList);
        // 设置适配器
        sp_planet.setAdapter(planetBaseAdapter);
        // 设置默认选项
        sp_planet.setSelection(0);
        // 设置监听事件
        sp_planet.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "你选择的是" + planetList.get(position).name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}