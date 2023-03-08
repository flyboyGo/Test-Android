package com.example.jetpack.base.commonwidget;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyChronometer extends Chronometer implements LifecycleObserver {

    private long elapsedTime;

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startMeter()
    {
        // 类似于回调方法,当被观察者的生命周期与观察着监听的生命周期一致时,促发此回调函数
        setBase(SystemClock.elapsedRealtime() - elapsedTime);
        start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopMeter()
    {
        elapsedTime = SystemClock.elapsedRealtime() - getBase();
        stop();
    }
}
