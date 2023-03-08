package com.example.workmanager.base.work;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {

    public MyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        /**
         *  SystemClock.sleep()方法与Thread.sleep()方法的区别
         *
         * Thread.sleep()是java的方法, 可能会抛出InterruptedException异常, 并且可能会被中断;
         *
         * SystemClock.sleep()是Android的方法,不会抛出异常, 并且无论如何都会让当前线程休眠指定的时间。
         */
        // SystemClock.sleep(200);
        Log.d("test","MyWork doWork");


        // 任务开始
        // 获取传递的参数
        String input_data = getInputData().getString("input_data");
        Log.d("test","input_data = " + input_data);


        // 任务执行完毕,返回数据
        Data outPutData = new Data.Builder()
                .putString("output_data","任务执行完毕")
                .build();

        return Result.success(outPutData);
    }
}
