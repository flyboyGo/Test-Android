package com.example.chapter07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.chapter07.adapter.PlanetGridAdapter;
import com.example.chapter07.bean.Planet;
import com.example.chapter07.util.ToastUtil;

import java.util.List;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gv_planet;
    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gv_planet = findViewById(R.id.gv_planet);

        planetList = Planet.getDefaultList();
        // 获取适配器、设置适配器
        PlanetGridAdapter planetGridAdapter = new PlanetGridAdapter(this, planetList);
        gv_planet.setAdapter(planetGridAdapter);
        gv_planet.setOnItemClickListener(this);
        // 设置条目项点击事件
        gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this,"你选择了" + planetList.get(position).name);
    }
}