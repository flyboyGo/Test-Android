package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.databinding.ActivityThirdBinding;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityThirdBinding activityThirdBinding =  DataBindingUtil.setContentView(this, R.layout.activity_third);
        activityThirdBinding.setNetworkImage("https://inews.gtimg.com/newsapp_bt/0/14297516724/641");
        activityThirdBinding.setLocalImage(R.drawable.me);
    }
}