package com.example.workmanager.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.workmanager.R;
import com.example.workmanager.base.work.MyWork;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addWork(View view) {
        // 设置触发约束条件
        Constraints constraints = new Constraints.Builder()
                // .setRequiresBatteryNotLow (true) // 不在电量不足执行
                // .setRequiresCharging (true) // 在充电时执行
                // .setRequiresStorageNotLow (true) // 不在存储容量不足时执行
                // .setRequiresDeviceIdle (true) // 在待机状态下执行调用需要API级别最低为23

                // .setRequiredNetworkType (NetworkType. CONNECTED) // 网络连接时执行
                // NetworkType. NOT REQUIRED:对网络没有要求
                // NetworkType.CONNECTED:网络连接的时候执行
                // NetworkType.UNMETERED:不计费的网络比如WIFI下执行
                // NetworkType.NOT ROAMING:非漫游网络状态
                // NetworkType.METERED:计费网络比如3G，4G下执行。
                // 注意:不代表恢复网络了，就立马执行
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // 设置网络状态(已连接),不会立即执行
                .build();

        // 设置需要传递的参数
        Data inputData = new Data.Builder()
                .putString("input_data", "jack")
                .build();

        // 配置任务
        // 一次性执行的任务
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                .setConstraints(constraints)  //设置触发约束条件
                .setInitialDelay(5, TimeUnit.SECONDS) // 设置延迟执行
                .setBackoffCriteria(BackoffPolicy.LINEAR,2,TimeUnit.SECONDS) // 设置指数退避策略
                .addTag("workRequest") // 设置tag标签
                .setInputData(inputData) // 设置需要传递的参数
                .build();

        // 将任务提交给WorkManager
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(workRequest);

        // 观察任务状态
        workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.d("test",workInfo.toString());
                if(workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED)
                {
                    String output_data = workInfo.getOutputData().getString("output_data");
                    Log.d("test","output_data = " + output_data);
                }
            }
        });

        // 取消任务
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                workManager.cancelWorkById(workRequest.getId());
//            }
//        },2000);
    }

    public void addPeriodicWork(View view) {

        // 设置触发约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // 设置网络状态(未连接),立即执行
                .build();

        // 周期性任务
        // 不能少于15分钟
        PeriodicWorkRequest  periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWork.class, Duration.ofMinutes(15))
                .addTag("periodicWorkRequest")
                .setConstraints(constraints)
                .build();

        // 将任务提交给WorkManager
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(periodicWorkRequest);
    }
}