package com.example.chapter15_mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView imageView;
    private static  SeekBar seekBar;
    private static TextView tv_progress,tv_total;
    private Button btn_start,btn_pause,btn_continue,btn_exit;
    private static  ObjectAnimator animator;
    private static  MusicPlayerService.MusicController controller;
    boolean flag = false;
    public static  Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPos = bundle.getInt("currentPos");
            if(duration == currentPos){
                controller.stop();
                animator.pause();
            }
            seekBar.setMax(duration);
            seekBar.setProgress(currentPos);
            String totalTime = msToMinSec(duration);
            String currentTime = msToMinSec(currentPos);
            tv_total.setText(totalTime);
            tv_progress.setText(currentTime);
        }
    };

    public static String msToMinSec(int ms){
        int sec = ms / 1000;
        int min = sec/ 60;
        sec -= min * 60;
        return String.format("%02d:%02d",min,sec);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controller = (MusicPlayerService.MusicController)service;
            Log.d("test","onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("test","onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
    }

    private void initWidget() {
        imageView = this.findViewById(R.id.image_view);
        seekBar = this.findViewById(R.id.seekbar);
        tv_progress = this.findViewById(R.id.tv_progress);
        tv_total = this.findViewById(R.id.tv_total);
        btn_start = this.findViewById(R.id.btn_start);
        btn_pause = this.findViewById(R.id.btn_pause);
        btn_continue = this.findViewById(R.id.btn_continue);
        btn_exit = this.findViewById(R.id.btn_exit);

        seekBar.setOnSeekBarChangeListener(this);

        OnClick onClick = new OnClick();
        btn_start.setOnClickListener(onClick);
        btn_pause.setOnClickListener(onClick);
        btn_continue.setOnClickListener(onClick);
        btn_exit.setOnClickListener(onClick);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","click");
                if(flag){
                    controller.pause();
                    animator.pause();
                    imageView.setImageResource(R.drawable.layer_list_combined_pause);
                    flag = false;
                }else{
                    controller.play();
                    animator.start();
                    imageView.setImageResource(R.drawable.layer_list_combined_start);
                    flag = true;
                }
            }
        });

        // 对象是imageView,动作是rotation旋转,角度是0到360度
        animator = ObjectAnimator.ofFloat(imageView,"rotation",0,360.0f);
        // 设置动画的时长,单位是毫秒,设置为10秒转一圈
        animator.setDuration(10000);
        // 旋转时间函数为线性,意为匀速旋转
        animator.setInterpolator(new LinearInterpolator());
        // 设置转动的圈速,-1为一直旋转
        animator.setRepeatCount(-1);

        Intent intent = new Intent(getApplicationContext(),MusicPlayerService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_start:
                    controller.play();
                    animator.start();
                    imageView.setImageResource(R.drawable.layer_list_combined_start);
                    break;
                case R.id.btn_pause:
                    controller.pause();
                    animator.pause();
                    imageView.setImageResource(R.drawable.layer_list_combined_pause);
                    break;
                case R.id.btn_continue:
                    controller.resume();
                    animator.resume();
                    imageView.setImageResource(R.drawable.layer_list_combined_start);
                    break;
                case R.id.btn_exit:
                    controller.stop();
                    animator.cancel();
                    imageView.setImageResource(R.drawable.layer_list_combined_pause);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // 音乐停止后,停止播放动画
        if(progress == seekBar.getMax()){
            animator.pause();
        }
        // 判断是不是用户拖动的
        if(fromUser){
            controller.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        controller.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        controller.resume();
    }

    @Override
    protected void onDestroy() {
        controller.stop();
        unbindService(conn);
        super.onDestroy();
    }
}