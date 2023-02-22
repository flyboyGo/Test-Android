package com.example.chapter11_thread.plus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chapter11_thread.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        findViewById(R.id.btn_create_cached_thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_create_fixed_thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_create_single_thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_create_scheduled_thread_pool).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_cached_thread_pool:
                ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
                for (int i = 1; i <= 10; i++)
                {
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int index = i;
                    cacheThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test",Thread.currentThread().getName() + " " + index);
                        }
                    });
                }
                break;
            case R.id.btn_create_fixed_thread_pool:
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
                for (int i = 1; i <= 10; i++)
                {
                    final int index = i;
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test",Thread.currentThread().getName() + " " + index);

                            try {
                                Thread.sleep(2 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_create_single_thread_pool:
                ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
                for (int i = 1; i <= 10; i++)
                {
                    final int index = i;
                    singleThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test",Thread.currentThread().getName() + " " + index);
                            try {
                                Thread.sleep(2*1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case R.id.btn_create_scheduled_thread_pool:
                Log.d("test","create_scheduled_thread_pool");
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                // 定时3秒后执行
//                executorService.schedule(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("test","delay 3 seconds");
//                    }
//                },3, TimeUnit.SECONDS);

                // 定时2秒后执行,每隔3秒执行一次
                executorService.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test","delay 2 seconds, and execute every 3 seconds");
                    }
                }, 2, 3, TimeUnit.SECONDS);

                // 中止周期性执行任务
                executorService.shutdown();
                break;
        }
    }
}