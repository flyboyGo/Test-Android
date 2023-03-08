package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleProgressView circleProgressView  = this.findViewById(R.id.circle_progress_view);

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