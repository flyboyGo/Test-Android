package com.example.chapter17_fragment_communication.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chapter17_fragment_communication.MyApplication;
import com.example.chapter17_fragment_communication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // 注册订阅事件
        //EventBus.getDefault().register(this);

        initWidget();
    }

    private void initWidget() {
        Button button = this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().getEventBus().post(new MessageEvent("FirstActivity Send Message"));
            }
        });
    }

      // 声明一个被 @Subscribe 注解标记的公共方法，用于处理订阅的事件
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(this, "FirstActivity send ==> " + event.message, Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册事件
        //EventBus.getDefault().unregister(this);
    }
}