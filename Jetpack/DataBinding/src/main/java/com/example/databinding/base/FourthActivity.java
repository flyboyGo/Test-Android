package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.databinding.R;
import com.example.databinding.base.viewModel.UserViewModel;
import com.example.databinding.databinding.ActivityFourthBinding;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFourthBinding activityFourthBinding =  DataBindingUtil.setContentView(this,R.layout.activity_fourth);
        activityFourthBinding.setUserViewModel(new UserViewModel());
    }
}