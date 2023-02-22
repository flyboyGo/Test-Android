package com.example.chapter11_thread.plus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter11_thread.R;

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;
    private ProgressBar pb;
    private int progress;

    private Handler handler = new Handler(Looper.getMainLooper())
    {
        // 接收消息等待处理
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case 1:
                    tv_show.setText("Hello World");
                    break;
                case 2:
                    tv_show.setText(msg.obj.toString());
                    break;
                case 3:
                    if(progress < 100)
                    {
                        progress+=10;
                        pb.setProgress(progress);
                        handler.sendEmptyMessageDelayed(3, 2 * 1000);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tv_show = findViewById(R.id.tv_show);
        pb = findViewById(R.id.pb);

        findViewById(R.id.btn_send_empty_message).setOnClickListener(this);
        findViewById(R.id.btn_send_message).setOnClickListener(this);
        findViewById(R.id.btn_pb).setOnClickListener(this);
        findViewById(R.id.btn_post_runnable).setOnClickListener(this);
        findViewById(R.id.btn_post_runnable_operate_main_thread).setOnClickListener(this);
        findViewById(R.id.btn_post_runnable_operate_main_thread_other).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_empty_message:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 发送空消息,what是标记、类别
                        handler.sendEmptyMessage(1);

                        // 发送延迟的空消息
                        handler.sendEmptyMessageDelayed(1, 2 * 1000);
                    }
                }).start();
                break;
            case R.id.btn_send_message:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = "Flight Again";
                        handler.sendMessageDelayed(message,2 * 1000);
                    }
                }).start();
                break;
            case R.id.btn_pb:
                pb.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(3, 2 * 1000);
                break;
            case R.id.btn_post_runnable:
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(HandlerActivity.this, "handler post runnable into main thread", Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HandlerActivity.this, "handler post delay runnable into main thread", Toast.LENGTH_SHORT).show();
                    }
                }, 2 * 1000);
                break;
            case R.id.btn_post_runnable_operate_main_thread:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        // 在非UI线程里面更新UI控件
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_show.setText("Go On");
                            }
                        }, 2 * 1000);
                    }
                }).start();
                break;
            case R.id.btn_post_runnable_operate_main_thread_other:

//                tv_show.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_show.setText("YES");
//                    }
//                }, 1 * 1000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_show.setText("runOnUiThread");
                    }
                });
                break;
            default:
                break;
        }
    }

}