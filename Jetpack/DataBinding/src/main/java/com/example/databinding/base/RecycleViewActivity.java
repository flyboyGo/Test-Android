package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.databinding.R;
import com.example.databinding.base.adapter.RecycleViewAdapter;
import com.example.databinding.base.enity.Planet;
import com.example.databinding.databinding.ActivityRecycleViewBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Planet> planetList = new ArrayList<>();
        planetList.add(new Planet("地球",R.drawable.diqiu));
        planetList.add(new Planet("火星",R.drawable.huoxing));
        planetList.add(new Planet("金星",R.drawable.jinxing));
        planetList.add(new Planet("木星",R.drawable.muxing));
        planetList.add(new Planet("水星",R.drawable.shuixing));
        planetList.add(new Planet("土星",R.drawable.tuxing));

        ActivityRecycleViewBinding activityRecycleViewBinding =  DataBindingUtil.setContentView(this,R.layout.activity_recycle_view);

        RecycleViewAdapter adapter = new RecycleViewAdapter(planetList);
        activityRecycleViewBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        activityRecycleViewBinding.recycleView.setAdapter(adapter);

    }
}