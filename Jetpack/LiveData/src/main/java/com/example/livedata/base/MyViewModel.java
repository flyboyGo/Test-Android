package com.example.livedata.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;


public class MyViewModel extends AndroidViewModel {

    // LiveData的子类
    private MutableLiveData<Integer> currentSecond;

    private int number;

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getCurrentSecond()
    {
        if(currentSecond == null)
        {
            currentSecond = new MutableLiveData<>();
            currentSecond.postValue(number);

            // 开始计数
            startTimer();
        }

        return currentSecond;
    }

    // 延时计数函数
    private void startTimer()
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 非ui线程 postValue
                // ui线程 setValue
                currentSecond.postValue(number++);
            }
        },1000,1000);
    }
}
