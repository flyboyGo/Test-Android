package com.example.chapter17_fragment_communication.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chapter17_fragment_communication.MyApplication;
import com.example.chapter17_fragment_communication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecondActivity extends AppCompatActivity {

    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

         // 注册订阅事件
         eventBus = MyApplication.getInstance().getEventBus();
         eventBus.register(this);
    }


    // 声明一个被 @Subscribe 注解标记的公共方法，用于处理订阅的事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, "SecondActivity receive ==> " + event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册订阅事件
        eventBus.unregister(this);
    }
}