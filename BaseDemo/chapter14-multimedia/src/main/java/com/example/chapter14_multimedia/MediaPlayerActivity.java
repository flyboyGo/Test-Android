package com.example.chapter14_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    TextureView textureView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        textureView = findViewById(R.id.textureView);

        button = findViewById(R.id.btn_option);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        CharSequence text = ((Button) view).getText();
        MediaPlayer mediaPlayer = null;
        if(TextUtils.equals(text,"开始播放")) {
            ((Button) view).setText("结束播放");

            mediaPlayer = new MediaPlayer();
            // 设置准备已完成的监听器
            mediaPlayer.setOnPreparedListener(this);
            // 设置视频播放完成的监听
            mediaPlayer.setOnCompletionListener(this);
            // 设置循环播放
            // mediaPlayer.setLooping(true);
            try {
                // 设置视频源
                mediaPlayer.setDataSource(new File(getExternalFilesDir(""), "a.mp4").getAbsolutePath());
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            // 设置画布
            mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture()));
            // 准备中(异步)
            mediaPlayer.prepareAsync();
            // 获取视频的总进度
            int duration = mediaPlayer.getDuration();
            // 获取视频的当前进度
            int currentPosition = mediaPlayer.getCurrentPosition();
        }
        else
        {
            ((Button) view).setText("开始播放");
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        button.setText("开始播放");
        mp.release();
    }
}