package com.example.chapter09_broadcastreceiver_extra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver_extra.reveiver.BootCompleteReceiver;
import com.example.chapter09_broadcastreceiver_extra.reveiver.WholeBroadCastReceiver;

public class MainActivity extends AppCompatActivity {

    private BootCompleteReceiver broadcastReceiver;
    private AppStateChangeReceiver appStateChangeReceiver;
    private WholeBroadCastReceiver wholeBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appStateChangeReceiver = new AppStateChangeReceiver();

        wholeBroadCastReceiver = new WholeBroadCastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 注册广播,设置需要监听的广播频道
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(appStateChangeReceiver,filter);

        // 注册全局广播接收器
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("whole_broad_cast");
        registerReceiver(wholeBroadCastReceiver,filter2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁广播接收器
        if(appStateChangeReceiver != null)
        {
            unregisterReceiver(appStateChangeReceiver);
        }

        // 销毁全局广播接收器
        unregisterReceiver(wholeBroadCastReceiver);
    }

    // 创建广播类,重写其中的onReceive的方法
    private  class  AppStateChangeReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null)
            {
                if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED))
                {
                    Toast.makeText(getApplicationContext(), "引用卸载", Toast.LENGTH_SHORT).show();
                    Log.d("test","应用卸载,相关信息为: " + intent.getData());
                }
                else if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED))
                {
                    Toast.makeText(getApplicationContext(), "引用安装", Toast.LENGTH_SHORT).show();
                    Log.d("test","应用安装,相关信息为: " + intent.getData());
                }
            }
        }
    }

}