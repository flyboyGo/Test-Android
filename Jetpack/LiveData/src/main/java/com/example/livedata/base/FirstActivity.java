package com.example.livedata.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.livedata.R;

import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends AppCompatActivity {

    private TextView textView;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textView = findViewById(R.id.text_view);
        // 实例化ViewModel对象
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                               .get(MyViewModel.class);

        viewModel.getCurrentSecond().observe(this,new Observer<Integer>(){
            @Override
            public void onChanged(Integer i) {
                textView.setText(String.valueOf(i));
            }
        });

        // 开始计数
        // startTimer();
    }

    private void startTimer()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 非ui线程 postValue
                // ui线程 setValue
                viewModel.getCurrentSecond().postValue(viewModel.getCurrentSecond().getValue() + 1);
            }
        },1000,1000);
    }
}