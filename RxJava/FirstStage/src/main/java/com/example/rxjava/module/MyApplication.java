package com.example.rxjava.module;

import android.app.Application;
import android.util.Log;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

@HiltAndroidApp
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 取消了订阅后,抛出的异常无法捕获，导致程序奔溃
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("test","setErrorHandler accept : throwable " + throwable.toString());
            }
        });
    }
}
