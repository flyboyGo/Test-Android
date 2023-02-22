package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.base.enity.Idol;
import com.example.databinding.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding activitySecondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        Idol idol = new Idol("myself",3);
        activitySecondBinding.setIdol(idol);
    }
}