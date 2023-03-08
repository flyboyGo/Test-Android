package com.example.chapter14_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MediaRecorderActivity extends AppCompatActivity implements View.OnClickListener {

    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);

        textureView = findViewById(R.id.textureView);

        findViewById(R.id.btn_option).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MediaRecorder mediaRecorder = null;
        Camera camera = null;
        CharSequence text = ((Button) view).getText();
        if(TextUtils.equals(text,"开始录制"))
        {
            ((Button) view).setText("结束录制");
            // 设置摄像头的拍摄角度旋转90
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            camera.unlock();

            mediaRecorder = new MediaRecorder();
            // 设置对应自定义的摄像头
            mediaRecorder.setCamera(camera);
            // 设置音频源 麦克风
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置视频源 摄像头
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // 指定视频文件的格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 设置音频的格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            // 设置视频的格式
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            // 设置视频文件中的方向旋转90
            mediaRecorder.setOrientationHint(90);
            // 设置视频输出文件
            mediaRecorder.setOutputFile(new File(getExternalFilesDir(""), "a.mp4").getAbsoluteFile());
            // 设置视频的大小
            mediaRecorder.setVideoSize(640, 480);
            // 设置视频的帧率
            mediaRecorder.setVideoFrameRate(30);
            // 设置视频的预览
            mediaRecorder.setPreviewDisplay(new Surface(textureView.getSurfaceTexture()));
            // 进入准备状态
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 开始录制
            mediaRecorder.start();
        }
        else
        {
            ((Button) view).setText("开始录制");
            // 结束录制
            mediaRecorder.stop();
            // 释放
            mediaRecorder.release();
            mediaRecorder = null;
            camera.stopPreview();
            camera.release();
        }
    }
}