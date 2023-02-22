package com.example.chapter10_service;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.MyBinder myBinder = null;
    private TextView textView;

    // MainActivity与 Service的桥梁
    private ServiceConnection connection = new ServiceConnection() {
        // 服务(Service)绑定时(onBind),触发此回调函数
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder)service;
            myBinder.getTest();
            MyService myService = myBinder.getMyService();

            // 创建定时器,计数器使用
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(myService.number+"秒");
                        }
                    });
                }
            },0,1000);
        }
        // 服务(Service)解除绑定时(onUnbind),触发此回调函数
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection foreConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("test","start_bind_fore_service");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("test","start_unbind_fore_service");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.stop_service).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.unbind_service).setOnClickListener(this);
        findViewById(R.id.stop_self).setOnClickListener(this);
        findViewById(R.id.start_fore_service).setOnClickListener(this);
        findViewById(R.id.stop_fore_service).setOnClickListener(this);
        findViewById(R.id.low_fore_service).setOnClickListener(this);
        findViewById(R.id.start_bind_fore_service).setOnClickListener(this);
        findViewById(R.id.stop_bind_fore_service).setOnClickListener(this);
        findViewById(R.id.low_bind_fore_service).setOnClickListener(this);
        textView = findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 非绑定式service
            case R.id.start_service:
                Intent intent = new Intent(this, MyService.class);
                // 可以传递消息给Service中的onStartCommand()
                intent.putExtra("key","msg info");
                startService(intent);
                break;
            case R.id.stop_service:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.stop_self:
                Intent intent_stop = new Intent(this, MyService.class);
                stopService(intent_stop);
                break;

                // 绑定式service
            case R.id.bind_service:
                Intent bind_intent = new Intent(this, MyService.class);
                bind_intent.putExtra("key","bind-service");
                // 也可以先启动服务,再将服务绑定到activity上,不过会导致activity解绑service后,service并不会销毁,继续执行
                // activity销毁后,也可以再次进入,可以将未销毁的service再次绑定到activity,继续执行
                // 我也可以创建一个全新的activity绑定service,多个activity可以复用同一个service。(模拟多个客户端,一个服务器)

                startService(bind_intent);
                bindService(bind_intent,connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;


                // 前台服务
            case R.id.start_fore_service:
                Intent intent_fore_start = new Intent(this,foreService.class);
                startService(intent_fore_start);
                break;
            case R.id.stop_fore_service:
                Intent intent_fore_stop = new Intent(this,foreService.class);
                stopService(intent_fore_stop);
                break;
            case R.id.low_fore_service:
                Intent intent_fore_low = new Intent(this,foreService.class);
                intent_fore_low.putExtra("key","low");
                startService(intent_fore_low);
                break;

                // 绑定式前台服务
            case R.id.start_bind_fore_service:
                Intent intent_bind_fore_start = new Intent(this,foreService.class);
                //startService(intent_bind_fore_start);
                bindService(intent_bind_fore_start,foreConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.stop_bind_fore_service:
                unbindService(foreConnection);
                break;
            case R.id.low_bind_fore_service:
                Intent intent_bind_fore_low;
                intent_bind_fore_low = new Intent(this,foreService.class);
                intent_bind_fore_low.putExtra("key","low");
                startService(intent_bind_fore_low);
                break;
        }
    }
}