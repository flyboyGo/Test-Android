package com.example.chapter11_thread.plus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chapter11_thread.R;

public class ThreadCreateActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        findViewById(R.id.btn_thread_create).setOnClickListener(this);
        findViewById(R.id.btn_runnable_create).setOnClickListener(this);
        findViewById(R.id.btn_runnable_match).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_thread_create:
                MyThread myThread = new MyThread();
                MyThread myThread2 = new MyThread();
                myThread.start();
                myThread2.start();

                Thread thread3 = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Log.d("test",Thread.currentThread().getName() + ".run()");
                    }
                };
                thread3.start();

                break;
            case R.id.btn_runnable_create:
                MyRunnable myRunnable = new MyRunnable();
                MyRunnable myRunnable2 = new MyRunnable();
                // 多个Thread对象可以共用一个 Runnable对象
                // 线程同步
                Thread thread = new Thread(myRunnable);
                Thread thread2 = new Thread(myRunnable2);
                thread.start();
                thread2.start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test",Thread.currentThread().getName() + ".run()");
                    }
                }).start();

                break;
            case R.id.btn_runnable_match:
                // 多个Thread对象可以共用一个 Runnable对象(多用于资源共享)
                // 线程同步
                SaleTicket saleTicket = new SaleTicket();
                Thread thread4  = new Thread(saleTicket,"A代理");
                Thread thread5  = new Thread(saleTicket,"B代理");
                Thread thread6  = new Thread(saleTicket,"C代理");
                thread4.start();
                thread5.start();
                thread6.start();
                break;
            default:
                break;
        }
    }

    private class MyThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            Log.d("test",Thread.currentThread().getName() + ".run()");
        }
    }

    private class MyRunnable implements Runnable
    {
        @Override
        public void run() {
            Log.d("test",Thread.currentThread().getName() + ".run()");
        }
    }
}