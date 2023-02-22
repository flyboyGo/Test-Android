package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

public class SecondActivity extends AppCompatActivity {

    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CircleProgressSleepView circleProgressView  = this.findViewById(R.id.circle_progress_sleep_view);

        new CountDownTimer(10000,100){

            @Override
            public void onTick(long millisUntilFinished) {
                current++;
                // 通知UI更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circleProgressView.setCurrent(current);
                    }
                });
            }
            @Override
            public void onFinish() {
                // 倒计时结束
                current++;
                // 通知UI更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circleProgressView.setCurrent(current);
                    }
                });
            }
        }.start();
    }
}