package com.example.chapter09_broadcastreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ChangeDirectionActivity extends AppCompatActivity {

    private TextView tv_monitor;

    /*
       为了避免横竖屏切换时重新加载界面的情况，Android设计了一种配置变更机制，在指定
       的环境配置发生变更之时，无需重启活动页面，只需执行特定的变更行为。该机制的实现过程分为两步：

       1、修改AndroidManifest.xml，给activity节点增加 android:configChanges 属性
       2、修改活动页面的Java代码，重写活动的 onConfigurationChanged 方法，补充对应的代码处理逻辑。
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_direction);
        tv_monitor = findViewById(R.id.tv_monitor);
        Log.d("test","onCreate");
    }

    // 在配置项变更是触发，比如屏幕方向发送变更
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation)
        {
            case  Configuration.ORIENTATION_PORTRAIT:
                tv_monitor.setText("当前屏幕为竖屏方向");
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                tv_monitor.setText("当前屏幕为横屏方向");
                break;
            default:
                break;
        }
    }
}