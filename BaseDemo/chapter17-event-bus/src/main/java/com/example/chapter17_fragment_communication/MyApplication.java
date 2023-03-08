package com.example.chapter17_fragment_communication;

import android.app.Application;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class MyApplication extends Application {

    // 单例模式
    private  static  MyApplication myApplication;

    private EventBus eventBus;

    public static MyApplication getInstance(){
        return myApplication;
    }

    // 在App启动前调用
    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        eventBus = EventBus.getDefault();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
