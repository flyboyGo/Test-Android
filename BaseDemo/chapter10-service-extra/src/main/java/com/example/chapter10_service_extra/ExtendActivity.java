package com.example.chapter10_service_extra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.chapter10_service_extra.service.ExtendIntentService;
import com.example.chapter10_service_extra.service.ExtendJobIntentService;
import com.example.chapter10_service_extra.service.ExtendJobService;
import com.example.chapter10_service_extra.service.ForegroundService;

public class ExtendActivity extends AppCompatActivity {

    // 自定义Handler(子线程中的Handler)
    // HandlerThread
    // JobScheduler、AlarmManager、WorkManager

    int intentServiceParam = 1;
    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };

    private static ImageView imageView;
    private static final Handler mUIHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend);
        imageView = this.findViewById(R.id.imageView2);
        Intent intent = new Intent(this,ExtendIntentService.class);
        for(int i = 0; i < 7; i++){
            intent.putExtra(ExtendIntentService.DOWNLOAD_URL,url[i]);
            intent.putExtra(ExtendIntentService.INDEX_FLAG,i);
            startService(intent);
        }

        // 必须通过Handler去更新，该方法为异步方法，不可更新UI
        ExtendIntentService.setUpdateUI(new ExtendIntentService.UpdateUI() {
            @Override
            public void updateUI(Message message) {
                mUIHandler.sendMessageDelayed(message,message.what * 1000);
            }
        });
    }

    public void startIntentService(View view) {
        Intent intent = new Intent(ExtendActivity.this, ExtendIntentService.class);
        startService(intent);
    }

    public void stopIntentService(View view) {
        stopService(new Intent(ExtendActivity.this, ExtendIntentService.class));
    }

    public void startJobIntentService(View view) {
        Intent intent = new Intent();
        intent.putExtra("param",++intentServiceParam);
        ExtendJobIntentService.enqueueWork(ExtendActivity.this,ExtendJobIntentService.class,
                ExtendJobIntentService.JOB_ID,intent);
    }

    public void stopJboIntentService(View view) {
        stopService(new Intent(ExtendActivity.this,ExtendJobIntentService.class));
    }

    public void startForegroundService(View view) {
        Intent intent = new Intent(this, ForegroundService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
        }else{
            startService(intent);
        }
    }

    public void stopForegroundService(View view) {
        Intent intent = new Intent(this,ForegroundService.class);
        stopService(intent);
    }

    private int mJobId = 0;
    public void startJobService(View view) {
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++,new ComponentName(this, ExtendJobService.class));
        builder.setRequiresCharging(false); // 是否在充电执行
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresDeviceIdle(false);// 是否在空闲执行
        builder.setMinimumLatency(1000 * 2);// 延迟多久后执行,毫秒
        builder.setOverrideDeadline(1000 * 6);// 最多延迟多久
        // Job调度者
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // builder.setExtras();
        scheduler.schedule(builder.build());
    }
}