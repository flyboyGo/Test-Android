package com.example.databinding.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.databinding.R;
import com.example.databinding.base.viewModel.MyViewModel;
import com.example.databinding.databinding.ActivityBasketballScoreBinding;

public class BasketballScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketball_score);

        ActivityBasketballScoreBinding activityBasketballScoreBinding = DataBindingUtil.setContentView(this,R.layout.activity_basketball_score);
        MyViewModel myViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MyViewModel.class);

        myViewModel.getATeamScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("test",integer + "");
            }
        });

        activityBasketballScoreBinding.setViewModel(myViewModel);
        activityBasketballScoreBinding.setLifecycleOwner(this);
    }
}