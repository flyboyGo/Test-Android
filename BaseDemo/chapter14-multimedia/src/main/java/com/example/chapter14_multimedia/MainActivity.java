package com.example.chapter14_multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chapter14_multimedia.util.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 请求号码
    private  static final int REQUEST_CODE = 1;

    private static final String[]PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 动态获取权限(饿汉式)
        PermissionUtil.checkPermission(this,PERMISSIONS,REQUEST_CODE);

        findViewById(R.id.btn_recorder_media).setOnClickListener(this);
        findViewById(R.id.btn_player_media).setOnClickListener(this);
        findViewById(R.id.btn_sound_pool).setOnClickListener(this);
        findViewById(R.id.btn_video_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recorder_media:
                Intent intent = new Intent(MainActivity.this,MediaRecorderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_player_media:
                Intent intent2 = new Intent(MainActivity.this,MediaPlayerActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_video_view:
                Intent intent3 = new Intent(MainActivity.this, VideoViewActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_sound_pool:
                Intent intent4 = new Intent(MainActivity.this,SoundPoolActivity.class);
                startActivity(intent4);
                break;
            default:
                break;

        }
    }

    // 权限获取的回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CODE:
                if(PermissionUtil.checkGrant(grantResults))
                {
                    Toast.makeText(this, "相关权限开启成功!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // 部分权限获取失败
                    for(int i = 0; i < grantResults.length; i++)
                    {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            // 判断是什么权限获取失败
                            switch (permissions[i])
                            {
                                case Manifest.permission.RECORD_AUDIO:
                                    Toast.makeText(this,"录音相关权限开启失败",Toast.LENGTH_SHORT).show();
                                    jumpToSettings();
                                    return;

                                case Manifest.permission.CAMERA:
                                    Toast.makeText(this,"相机相关权限开启失败",Toast.LENGTH_SHORT).show();
                                    jumpToSettings();
                                    return;

                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    // 跳转到应用设置界面
    private void jumpToSettings()
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package",getPackageName(),null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}