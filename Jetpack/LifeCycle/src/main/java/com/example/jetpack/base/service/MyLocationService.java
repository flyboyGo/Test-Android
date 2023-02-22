package com.example.jetpack.base.service;

import android.util.Log;

import androidx.lifecycle.LifecycleService;

public class MyLocationService extends LifecycleService {

    public MyLocationService() {
        Log.d("test","MyLocationService");

        // 实例化Service(服务)观察者对象
        MyLocationObserver observer = new MyLocationObserver(this);
        // 给当前的Service的生命周期方法添加观察者
        getLifecycle().addObserver(observer);
    }

}
