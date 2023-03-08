package com.example.jetpack.base.owner;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 使用ProcessLifecycleOwner监听应用程序生命周期
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
    }
}
