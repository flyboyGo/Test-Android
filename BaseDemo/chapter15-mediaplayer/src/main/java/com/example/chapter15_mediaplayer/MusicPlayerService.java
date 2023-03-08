package com.example.chapter15_mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private Timer timer;
    private boolean flag = false;

    public MusicPlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 实例化多媒体对象
        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor fd = null;
        try {
            fd = getAssets().openFd("creativeminds.mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
            // mediaPlayer.setBufferSize();
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                flag = true;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                 // 获取音频总时长
                int duration = mp.getDuration();
                // 当前播放的位置
                int current = mp.getCurrentPosition();
                // 计算当前缓冲的位置
                int bufferPosition = duration * percent / 100;
                // 输出缓冲进度
                Log.d("MediaPlayer", "Buffering update: " + percent + "%, buffer position: " + bufferPosition);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 绑定服务时,把音乐
        return new MusicController();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 增加计时器
    public void addTimer(){
        if(timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int duration = mediaPlayer.getDuration();// 获取歌曲总时长
                    int currentPos = mediaPlayer.getCurrentPosition();// 获取当前播放进度
//                    Message msg = MainActivity.mHandler.obtainMessage();
                    Message msg = new Message();
                    // 将音乐的总时长和播放的进度封装到消息对象中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPos",currentPos);
                    msg.setData(bundle);
                    // 将消息放送到主线程的消息队列中
                    MainActivity.mHandler.sendMessage(msg);
                }
            };
            // 开始计时任务后的5毫秒,第一次执行task任务,以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }


    class MusicController extends Binder{
        // 播放音乐
        public void play(){
//            mediaPlayer.reset();
//            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.creativeminds);
            if(flag){
                mediaPlayer.start();
                addTimer();
            }
        }
        // 暂停
        public void pause(){
            mediaPlayer.pause();
        }
        // 继续
        public void resume(){
            mediaPlayer.start(); // 播放不会重置
        }
        // 停止
        public void stop(){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(timer != null){
                try{
                    timer.cancel();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        // 打带
        public void seekTo(int ms){
            mediaPlayer.seekTo(ms);
        }
    }
}
