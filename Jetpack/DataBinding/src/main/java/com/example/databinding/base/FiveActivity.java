package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.base.viewModel.UserViewModelField;
import com.example.databinding.databinding.ActivityFiveBinding;

public class FiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFiveBinding activityFiveBinding =  DataBindingUtil.setContentView(this,R.layout.activity_five);
        activityFiveBinding.setUserViewModelField(new UserViewModelField());
    }
}