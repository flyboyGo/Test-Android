package com.example.chapter10_service_extra.service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ExtendJobService extends JobService {

    private List<Thread> threadList = new ArrayList<>();

    private int count = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                jobFinished((JobParameters)msg.obj,false);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","ExtendJobService onCreate");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("test","ExtendJobService onStartJob");

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    Log.d("test",getName()+" count = " + count);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    count++;
                    if(count == 10){
                        jobFinished(params,false);
                    }
                }
            }
        };
        thread.start();
        threadList.add(thread);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("test","ExtendJobService onStopJob");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","ExtendJobService onDestroy");
        for (Thread thread : threadList) {
            if(thread != null){
                // 中止线程
                thread.interrupt();
            }
        }
        threadList.clear();
    }
}
