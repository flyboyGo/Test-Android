package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.base.enity.Idol;
import com.example.databinding.base.handler.EventHandlerListener;
import com.example.databinding.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFirstBinding activityFirstBinding = DataBindingUtil.setContentView(this, R.layout.activity_first);
        activityFirstBinding.setIdol(new Idol("myself",4));
        activityFirstBinding.setEventHandler(new EventHandlerListener(this));
    }
}