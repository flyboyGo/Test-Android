package com.example.chapter14_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView video_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        video_view = findViewById(R.id.video_view);
        video_view.setVideoPath(new File(getExternalFilesDir(""), "a.mp4").getAbsolutePath());

        MediaController controller = new MediaController(this);
        // 设置 上一个、下一个监听器
        controller.setPrevNextListeners(this,this);
        // 设置VideoView的控制器
        video_view.setMediaController(controller);

        video_view.start();
    }

    @Override
    public void onClick(View v) {
        Log.d("test","VideoView <<===>>");
    }
}