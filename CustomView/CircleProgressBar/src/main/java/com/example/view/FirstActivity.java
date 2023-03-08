package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends AppCompatActivity {

    private int current = 0;
    private Timer timer;
    private CircleProgressAcquisitionView circleProgressAcquisitionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        circleProgressAcquisitionView = this.findViewById(R.id.circle_progress_acquisition_view);
    }

    public void start(View view) {

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(current == 100){
                    current = 0;
                }
                else{
                    current++;
                }
                circleProgressAcquisitionView.setCurrent(current);
                circleProgressAcquisitionView.setRunning(true);
            }
        },100,100);
    }

    public void reStart(View view) {
        current = 0;
        if(this.timer != null){
            this.timer.cancel();
        }
        this.timer = null;
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(current == 100){
                    current = 0;
                }
                else{
                    current++;
                }
                circleProgressAcquisitionView.setCurrent(current);
                circleProgressAcquisitionView.setRunning(true);
            }
        },100,100);
    }

    public void stop(View view) {
        circleProgressAcquisitionView.setCurrent(100);
        circleProgressAcquisitionView.setRunning(false);
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}