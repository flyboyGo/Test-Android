package com.example.chapter07_extra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.chapter07_extra.adapter.RecycleViewAdapter;
import com.example.chapter07_extra.enity.Planet;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycle_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle_view = findViewById(R.id.recycle_view);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this,Planet.getDefaultList());
        recycle_view.setAdapter(recycleViewAdapter);

        /*
           RecyclerView.Adapter的写法
           线性列表   LinearLayoutManager
           网格列表   GridLayoutManager
           瀑布流列表  StaggeredGrLayoutManager
         */

        // 线性布局
           recycle_view.setLayoutManager(new LinearLayoutManager(this)); // 默认线性布局是垂直排放、非反转
        // recycle_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        // 网格布局
        // recycle_view.setLayoutManager(new GridLayoutManager(this,2));

        // 瀑布流布局
        // recycle_view.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_liner:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recycle_view.setLayoutManager(layoutManager);
                return  true;
            case R.id.menu_grid:
                RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this,2);
                recycle_view.setLayoutManager(gridLayoutManager);
                return true;
            case R.id.menu_stagger:
                RecyclerView.LayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                recycle_view.setLayoutManager(staggeredGridLayoutManager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}